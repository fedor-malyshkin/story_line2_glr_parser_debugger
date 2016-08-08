package ru.nlp_project.story_line2.glr_parser_debugger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;

class ConfigListManager {

  class ConfigListVewModel {

    private File dirFile;
    private String dirStr;

    public ConfigListVewModel(String dir, File file) {
      dirStr = dir;
      dirFile = file;
    }

    @Override
    public String toString() {
      return dirStr;
    }

  }

  private static final String PREF_KEY_SELECTED_DIR = ".selectedDir";
  private Button chDirButtornCtrl;
  private ObservableList<ConfigListVewModel> configList;
  private DebuggerApp debuggerApp;
  private ListView<ConfigListVewModel> listViewCtrl;
  private Pane rootPaneCtrl;
  private File selectedDirectory = null;

  public ConfigListManager(Pane rootPane, DebuggerApp debuggerApp,
      TextAreaManager textAreaManager) {
    this.debuggerApp = debuggerApp;
    this.rootPaneCtrl = rootPane;
  }

  protected void analyse(File dirFile) {
    debuggerApp.analyse(dirFile);
  }

  public File getSelectedDir() {
    ConfigListVewModel item =
        listViewCtrl.getSelectionModel().getSelectedItem();
    if (item == null)
      return null;
    return item.dirFile;
  }

  @SuppressWarnings("unchecked")
  public void initialize() {
    chDirButtornCtrl = (Button) rootPaneCtrl.lookup("#changeDir");
    chDirButtornCtrl.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("GLR Parser configuration dirs");
        chooser.setInitialDirectory(selectedDirectory);
        selectedDirectory = chooser.showDialog(null);
        uploadListView();
      }
    });
    chDirButtornCtrl.setStyle("-fx-font-weight: bold;");
    
    listViewCtrl =
        (ListView<ConfigListVewModel>) rootPaneCtrl.lookup("#configList");
    configList = FXCollections.observableArrayList();
    listViewCtrl.setItems(configList);
    listViewCtrl.setOnMouseClicked(a -> {
      if (a.getClickCount() < 2)
        return;
      analyse(getSelectedDir());
    });

    Preferences preferences = Preferences.userNodeForPackage(DebuggerApp.class);
    String dirName =
        preferences.get(this.getClass().getSimpleName() + PREF_KEY_SELECTED_DIR,
            FileUtils.getUserDirectoryPath());
    selectedDirectory = new File(dirName);
    uploadListView();

  }

  public void shutdown() {
    Preferences preferences = Preferences.userNodeForPackage(DebuggerApp.class);
    preferences.put(this.getClass().getSimpleName() + PREF_KEY_SELECTED_DIR,
        selectedDirectory.getAbsolutePath());
  }

  protected void uploadListView() {
    String[] list = selectedDirectory.list(DirectoryFileFilter.DIRECTORY);
    List<String> asList = Arrays.asList(list);
    configList.clear();
    asList.stream().sorted().forEach(e -> configList
        .add(new ConfigListVewModel(e, new File(selectedDirectory, e))));

  }

}
