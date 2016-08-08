package ru.nlp_project.story_line2.glr_parser_debugger;

import java.net.URL;

import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class WebViewManager {

  private Pane rootPaneCtrl;
  private WebEngine webEngine;
  private WebView mainWebViewCtrl;
  private StringBuffer stringBuffer = new StringBuffer();

  public WebViewManager(Pane rootPane) {
    this.rootPaneCtrl = rootPane;
  }

  public void appendContent(String content) {
    stringBuffer.append(content);
  }

  public void initialize() {
    mainWebViewCtrl = (WebView) rootPaneCtrl.lookup("#mainWebView");
    webEngine = mainWebViewCtrl.getEngine();
    URL url = Thread.currentThread().getContextClassLoader().getResource(
        "ru/nlp_project/story_line2/glr_parser_debugger/css/html.css");
    webEngine.setUserStyleSheetLocation(url.toExternalForm());
    // loadJQuery();
    createSourceScene();
    loadContextMenu();
    appendContent("<p>Нажмите кнопку \"Анализ\" для начала анализа...</p>");
  }

  private void createSourceScene() {

  }

  private void loadContextMenu() {
    mainWebViewCtrl.setContextMenuEnabled(false);

    ContextMenu contextMenu = new ContextMenu();
    MenuItem showSource = new MenuItem("Показать исходник");
    contextMenu.getItems().add(showSource);

    mainWebViewCtrl.setOnMousePressed(e -> {
      if (e.getButton() == MouseButton.SECONDARY) {
        contextMenu.show(mainWebViewCtrl, e.getScreenX(), e.getScreenY());
      } else {
        contextMenu.hide();
      }
    });

    showSource.setOnAction(e -> {
      TextArea sourceTextAreaCtrl = new TextArea();
      BorderPane sourceSceneLyt = new BorderPane(sourceTextAreaCtrl);
      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setOnCloseRequest(e2 -> {
        stage.hide();
      });
      stage.setTitle("Исходный текст");
      sourceTextAreaCtrl.setText(stringBuffer.toString());
      stage.setScene(new Scene(sourceSceneLyt, 800, 600));
      stage.show();
    });

  }

  public void resetContent() {
    stringBuffer = new StringBuffer();
    webEngine.loadContent(stringBuffer.toString());
  }

  public void showContent() {
    webEngine.loadContent(stringBuffer.toString());
  }

}
