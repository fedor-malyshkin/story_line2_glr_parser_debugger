package ru.nlp_project.story_line2.glr_parser_debugger;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.nlp_project.story_line2.glr_parser.GLRParser;

public class DebuggerApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private Pane rootPane;
  private GLRParser glrParser;
  private TemplateManager templateManager;
  private WebViewManager webViewManager;
  private ConfigListManager configListManager;
  private GLRLogger logger;
  private FactListener factListener;

  @Override
  public void start(Stage primaryStage) throws Exception {
    URL fxml = Thread.currentThread().getContextClassLoader().getResource(
        "ru/nlp_project/story_line2/glr_parser_debugger/fxml/mainForm.fxml");
    rootPane = FXMLLoader.load(fxml);
    rootPane.getStylesheets()
        .add("ru/nlp_project/story_line2/glr_parser_debugger/css/javafx.css");
    primaryStage.setScene(new Scene(rootPane, 800, 600));
    // primaryStage.setMaximized(true);
    primaryStage.setTitle("GLR Parser Debugger");

    primaryStage.show();

    primaryStage.setOnCloseRequest(e -> Platform.exit());
    initializeComponents();
  }

  private TextAreaManager textAreaManager;

  private void initializeComponents() {
    templateManager = new TemplateManager();
    templateManager.initialize();
    webViewManager = new WebViewManager(rootPane);
    webViewManager.initialize();
    configListManager = new ConfigListManager(rootPane, this, textAreaManager);
    configListManager.initialize();
    textAreaManager = new TextAreaManager(rootPane, this, configListManager);
    textAreaManager.initialize();

    progressBarCtrl = (ProgressBar) rootPane.lookup("#progressBar");

    logger = new GLRLogger(webViewManager, templateManager);
    factListener = new FactListener(webViewManager, templateManager);

  }

  private ProgressBar progressBarCtrl;

  @Override
  public void stop() throws Exception {
    configListManager.shutdown();
    textAreaManager.shutdown();
    if (glrParser != null)
      glrParser.shutdown();
  }

  public void analyse(File dirFile) {
    progressBarCtrl.setProgress(0);
    webViewManager.resetContent();
    webViewManager.appendContent(templateManager.header());
    long start = System.currentTimeMillis();
    try {
      glrParser =
          GLRParser.newInstance(dirFile.getAbsolutePath() + "/glr-config.json",
              logger, factListener, true, false);
templateManager.setStartAnalysisTime(start);
      glrParser.processText(textAreaManager.getText());
    } catch (Exception e) {
      e.printStackTrace();
      logger.printStackTrace(e);
    }
    long end = System.currentTimeMillis();
    webViewManager.appendContent(templateManager.footer(end - start));
    webViewManager.showContent();
    progressBarCtrl.setProgress(1);
  }

}
