package seng202.team5.gui;

import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class starts the javaFX application window
 * @author seng202 teaching team
 */
public class MainWindow extends Application {

    /**
     * Opens the gui with the fxml content specified in resources/fxml/main.fxml
     * @param primaryStage The current fxml stage, handled by javaFX Application class
     * @throws IOException if there is an issue loading fxml file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = baseLoader.load();

        NavigationController baseController = baseLoader.getController();
        primaryStage.setTitle("SmartTransit Dashboard");
        Scene scene = new Scene(root, 1024, 768);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        primaryStage.setScene(scene);
        baseController.init(primaryStage);
        primaryStage.show();
        scene.getStylesheets().add("/css/Main.css");
        Image image = new Image("/img/icon.png");
        primaryStage.getIcons().add(image);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }

    /**
     * Launches the FXML application, this must be called from another class (in this cass App.java) otherwise JavaFX
     * errors out and does not run
     * @param args command line arguments
     */
    public static void main(String [] args) {
        launch(args);
    }

}
