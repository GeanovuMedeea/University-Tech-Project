package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("runProgram.fxml"));
        Parent mainWindow = mainLoader.load();

        RunProgramController mainWindowController = mainLoader.getController();

        primaryStage.setTitle("Program State Viewer Window");
        Scene sceneToEdit1 = new Scene(mainWindow,650,650);
        String css = this.getClass().getResource("application.css").toExternalForm();
        sceneToEdit1.getStylesheets().add(css);
        primaryStage.setScene(sceneToEdit1);
        primaryStage.show();


        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("selectOption.fxml"));
        Parent secondaryWindow = secondaryLoader.load();

        SelectOptionController selectWindowController = secondaryLoader.getController();
        selectWindowController.setMainWindowController(mainWindowController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select Option Window");
        Scene sceneToEdit2 = new Scene(secondaryWindow,650,650);
        String css2 = this.getClass().getResource("application2.css").toExternalForm();
        sceneToEdit2.getStylesheets().add(css2);
        secondaryStage.setScene(sceneToEdit2);
        secondaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}