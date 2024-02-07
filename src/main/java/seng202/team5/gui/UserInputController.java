package seng202.team5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.*;

/**
 * This class manages any calls from the UI to modify the user input view. It calls a Chart View
 * Controller to implement most of these changes.
 *
 * @author Zoe Perry
 */
public class UserInputController {
  @FXML private AnchorPane inputContainer;
  @FXML private TextField textInputField;
  @FXML private Button enterButton;
  @FXML private Text prompt;
  private ChartViewController controller;
  private Stage inputView;
  private InputType type;

  /** Represents the type of input, used to specify the purpose of the user input. */
  public enum InputType {
    TITLE("title"),
    FILENAME("filename");
    public String name;

    InputType(String name) {
      this.name = name;
    }
  }

  /**
   * Initializes the UserInputController with the provided parameters.
   *
   * @param prompt The text to be displayed as a prompt in the input view.
   * @param controller The associated ChartViewController for implementing changes.
   * @param inputView The JavaFX Stage representing the input view.
   * @param type The type of input.
   */
  public void init(String prompt, ChartViewController controller, Stage inputView, InputType type) {
    this.prompt.setText(prompt);
    this.controller = controller;
    this.inputView = inputView;
    inputView.getScene().getStylesheets().add("/css/Main.css");
    this.type = type;
  }

  /**
   * Handles the action when the "Enter" button is clicked. Validates the input and performs the
   * corresponding action. If the input is empty, it displays an error message. If the input is
   * valid, it calls the appropriate method in the ChartViewController and closes the input view.
   */
  @FXML
  void toggleEnterButton() {
    if (getInput().equals("")) {
      ErrorModalController.showError("Please enter a valid " + type.name);
    } else if (type == InputType.TITLE) {
      controller.newTitleChart(getInput());
      inputView.close();
    } else {
      controller.saveImage(getInput());
      inputView.close();
    }
  }

  @FXML
  public void onEnter(KeyEvent e) {
    if (e.getCode().toString().equals("ENTER")) {
      toggleEnterButton();
    }
  }

  /**
   * Retrieves the input text from the text input field.
   *
   * @return The user-entered input text.
   */
  public String getInput() {
    return textInputField.getText();
  }
}
