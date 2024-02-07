package seng202.team5.gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * Generic error that displays a custom error message. Currently only displays warning alerts but
 * info, and error alerts can be implemented later if required
 *
 * @author Rinlada Tolley
 */
public class ErrorModalController {

  /**
   * Creates an alert modal that displays a given warning message
   *
   * @param message warning message to be displayed
   */
  public static void showError(String message) {
    Platform.runLater(
        () -> {
          Alert alert = new Alert(Alert.AlertType.WARNING, "");
          alert.setTitle("Notice");
          alert.setHeaderText("Warning!");
          alert.getDialogPane().setMinSize(300, 100);
          alert.getDialogPane().setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
          Label label = new Label(message);
          label.setWrapText(true);
          alert.getDialogPane().setContent(label);
          alert.getDialogPane().getStylesheets().add("/css/Main.css");
          alert.getDialogPane().getStylesheets().add("/css/ErrorModal.css");
          alert.showAndWait();
        });
  }

  /**
   * Creates an alert modal that displays a given info message
   *
   * @param message warning message to be displayed
   * @param title warning title to be displayed
   */
  public static void showInfo(String message, String title) {
    Platform.runLater(
        () -> {
          Alert alert = new Alert(Alert.AlertType.INFORMATION, "");
          alert.setTitle("Notice");
          alert.setHeaderText(title);
          alert.getDialogPane().setMinSize(300, 100);
          alert.getDialogPane().setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
          Label label = new Label(message);
          label.setWrapText(true);
          alert.getDialogPane().setContent(label);
          alert.getDialogPane().getStylesheets().add("file:src/main/resources/css/Main.css");
          alert.getDialogPane().getStylesheets().add("file:src/main/resources/css/ErrorModal.css");
          alert.showAndWait();
        });
  }
}
