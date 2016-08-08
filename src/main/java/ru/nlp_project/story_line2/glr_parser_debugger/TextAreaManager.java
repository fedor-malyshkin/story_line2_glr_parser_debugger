package ru.nlp_project.story_line2.glr_parser_debugger;

import java.io.File;
import java.util.prefs.Preferences;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

class TextAreaManager {

  private static final String PREF_KEY_ORIGINAL_TEXT = ".originalText";
  private Button parseTextButtonCtrl;
  private TextArea originalTextTextAreaCtrl;
  private Pane rootPaneCtrl;
  private ConfigListManager configListManager;
  private DebuggerApp debuggerApp;

  public TextAreaManager(Pane rootPane, DebuggerApp debuggerApp,
      ConfigListManager configListManager) {
    this.rootPaneCtrl = rootPane;
    this.configListManager = configListManager;
    this.debuggerApp = debuggerApp;
  }

  protected void analyse(File dirFile) {
    debuggerApp.analyse(dirFile);
  }

  public String getText() {
    return originalTextTextAreaCtrl.getText();
  }

  public void initialize() {
    originalTextTextAreaCtrl =
        (TextArea) rootPaneCtrl.lookup("#originalTextTextArea");
    parseTextButtonCtrl = (Button) rootPaneCtrl.lookup("#parseTextButton");
    parseTextButtonCtrl.setStyle("-fx-font-weight: bold;");
    parseTextButtonCtrl.setOnAction(a -> {
      if (configListManager.getSelectedDir() == null)
        return;
      analyse(configListManager.getSelectedDir());
    });

    // restore text
    Preferences preferences = Preferences.userNodeForPackage(DebuggerApp.class);
    originalTextTextAreaCtrl.setText(preferences
        .get(this.getClass().getSimpleName() + PREF_KEY_ORIGINAL_TEXT, ""));
  }

  public void shutdown() {
    Preferences preferences = Preferences.userNodeForPackage(DebuggerApp.class);
    preferences.put(this.getClass().getSimpleName() + PREF_KEY_ORIGINAL_TEXT,
        originalTextTextAreaCtrl.getText());
  }

}
