package seng202.team5.gui;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Class which handles displaying the main splash art when the application is launched
 * @author Sergey Antonets
 */
public class HomeViewController {
    @FXML
    private ImageView splashImageView;
    @FXML
    private AnchorPane parentPane;

    /**
     * Initialize the main stage with the splash art, apply the css styling and anchor the image to the stage.
     * @param stage The stage on which the home view is displayed on.
     */
    public void init(Stage stage) {
        stage.getScene().getStylesheets().add("/css/Main.css");
        splashImageView.fitWidthProperty().bind(parentPane.widthProperty());
        splashImageView.fitHeightProperty().bind(parentPane.heightProperty());
    }
}
