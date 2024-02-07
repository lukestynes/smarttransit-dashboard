package seng202.team5.gui;

import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import seng202.team5.App;
import java.io.IOException;

/**
 * Creates a pop-up to communicate to the user the current progress of loading a given task.
 *
 * @author Sergey Antonets, Rinlada Tolley
 */
public class LoadingBarController {

    @FXML
    private Text progressMessage;
    @FXML
    private ProgressBar progressBar;

    @FXML
    private ProgressIndicator loadingIndicator;

    public static boolean disabledForTesting = false;

    /**
     * Used to disable loading bar when testing is occurring. This is as loading bar is called from lower level methods
     * (ie when loading a file) but most tests are run without java fx active
     */
    public static void testMode()
    {
        disabledForTesting = true;
    }

    /**
     * Method to create a loading bar when given a service, it will auto update with the progress of the service by binding
     * the progress bar progression to the service progression
     *
     * @param ownerStage The parent stage (may be null).
     * @param task The task whose progress the loader represents.
     * @param showProgressBar Decide whether the displayed loader is a progress bar or a progress indicator.
     * @return An instance of the progress bar.
     * @throws IOException If there is an error with finding resources, parsing FXML or binding, an IOException will be thrown.
     **/

    public static Stage showProgress(Stage ownerStage, final Task<Void> task, boolean showProgressBar) throws IOException {


        if (disabledForTesting)
        {
            task.stateProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);
            });

            return null;
        }

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/loadingbar.fxml"));
        Parent root = loader.load();
        Stage indicatorStage = new Stage();

        indicatorStage.initStyle(StageStyle.UNDECORATED);
        indicatorStage.initOwner(ownerStage);
        indicatorStage.initModality(Modality.APPLICATION_MODAL);
        indicatorStage.setScene(new Scene(root));
        indicatorStage.getScene().getStylesheets().add("/css/Main.css");
        indicatorStage.getScene().getStylesheets().add("/css/LoadingIndicator.css");

        Text progressMessage = (Text)loader.getNamespace().get("progressMessage");
        progressMessage.textProperty().bind(task.messageProperty());
//        task.messageProperty().addListener((observable, oldValue, newValue) -> {progressMessage.setText(newValue);});

        ProgressBar progressBar = (ProgressBar)loader.getNamespace().get("progressBar");
        ProgressIndicator loadingIndicator = (ProgressIndicator) loader.getNamespace().get("loadingIndicator");

        if (showProgressBar) {
            progressBar.setVisible(true);
            loadingIndicator.setVisible(false);
            progressBar.progressProperty().bind(task.progressProperty());
        } else {
            progressBar.setVisible(false);
            loadingIndicator.setVisible(true);
        }

        task.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == State.FAILED || newValue == State.SUCCEEDED) {
                indicatorStage.hide();
            }
        });

        indicatorStage.show();
        return indicatorStage;
    }

}
