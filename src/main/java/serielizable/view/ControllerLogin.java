package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import serielizable.entity.User;
import utils.AbstractController;
import utils.Constants;

public class ControllerLogin extends AbstractController {

	@FXML
	private TextField userNameTextField;

	@FXML
	private PasswordField passwordField;
	
	@FXML
	private TextField passwordFieldViewable;

	@FXML
	private Button accessButton;
	
	@FXML
	private Button registerButton;
	
	@FXML
	private Button passwordViewButton;

	// Define the button image
	Image showPasswordImg = new Image(getClass().getResourceAsStream("../../utils/eye.png"));
	Image hidePasswordImg = new Image(getClass().getResourceAsStream("../../utils/hidden.png"));
	// Define the ImageView to resize it
	ImageView imageView;
	// This is used for the password visibility
	private boolean passwordIsVisible = false;

	@FXML
	public void initialize() {
		setPasswordImage();
	}
	
	@FXML
	/**
	 * This method is used to access and check the credentials
	 */
	private void handleAcceder() {
		// If the credentials are good:
		if (checkAcceso()) 
			// Go to the next view
			setView("MainApp");
		
	}
	
	/**
	 * This method resizes the password visibility button image
	 */
	private void resizePasswordImage() {
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		// Load the image into the button
		passwordViewButton.setGraphic(imageView);
	}
	
	/**
	 * This method changes the password visibility button image
	 */
	private void setPasswordImage() {
		if(!passwordIsVisible) {
			// Change the image of the button
			imageView = new ImageView(showPasswordImg);
			resizePasswordImage();
		}else {
			// Change the image of the button
			imageView = new ImageView(hidePasswordImg);
			resizePasswordImage();
		}
		
	}
	
	@FXML
	/**
	 * This method changed the password visibility
	 */
	private void changePasswordVisivility() {
		// If the password is not visible:
		if(!passwordIsVisible) {
			// Set the normal textfield available
			passwordFieldViewable.setVisible(true);
			passwordFieldViewable.setDisable(false);
			passwordFieldViewable.setText(passwordField.getText());
			// Disable the password field
			passwordField.setText("");
			passwordField.setVisible(false);
			passwordField.setDisable(true);
			// And change the value of the variable used to check visibility
			passwordIsVisible = true;
			setPasswordImage();
		}
		// If the password is visible:
		else {
			// Set the password field available
			passwordField.setVisible(true);
			passwordField.setDisable(false);
			// Set the text from the other field to "save" the progress
			passwordField.setText(passwordFieldViewable.getText());
			// Disable the normal textfield
			passwordFieldViewable.setText("");
			passwordFieldViewable.setVisible(false);
			passwordFieldViewable.setDisable(true);
			// And change the value of the variable used to check visibility
			passwordIsVisible = false;
			setPasswordImage();
		}
	}
	
	@FXML
	/**
	 * This method is used to change the view to the register one
	 */
	private void handleRegister() {
		setView("Register");
	}

	@FXML
	/**
	 * This method checks the user credentials
	 * @return
	 */
	private boolean checkAcceso() {
		if(passwordIsVisible) {
			// First we have to check if the fields are filled
			if (!userNameTextField.getText().isEmpty() && !passwordFieldViewable.getText().isEmpty()) {
				// If there is content in the fields then:
				// Define the user that will be checked
				User user = userRepository.getByUserName(userNameTextField.getText());
				// If the user exists:
				if (user != null) {
					// If the password is the same:
					if (user.getPassword().equals(passwordFieldViewable.getText())) {
						// Save the user
						currentUser = user;
						currentFilms = filmRepository.getAllByUserId(currentUser.getId());
						currentSeries = serieRepository.getAllByUserId(currentUser.getId());
						return true;
					} 
					// If the credentials don´t match:
					else {
						// Warn about the mistake
						System.err.println(Constants.PASSWORDS_DONT_MATCH);
					}
				}
				// If the user doesn´t exist:
				else {
					// Warn about the non existence of the user
					System.err.println(Constants.USER_DOESNT_EXISTS);
				}
			}
			// If any field is empty inform it
			else {
				System.err.println(Constants.USER_AND_PASSWORD_EMPTY);
			}
			return false;
		}else {
			// First we have to check if the fields are filled
			if (!userNameTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
				// If there is content in the fields then:
				// Define the user that will be checked
				User user = userRepository.getByUserName(userNameTextField.getText());
				// If the user exists:
				if (user != null) {
					// If the password is the same:
					if (user.getPassword().equals(passwordField.getText())) {
						// Save the user
						currentUser = user;
						currentFilms = filmRepository.getAllByUserId(currentUser.getId());
						currentSeries = serieRepository.getAllByUserId(currentUser.getId());
						return true;
					} 
					// If the credentials don´t match:
					else {
						// Warn about the mistake
						System.err.println(Constants.PASSWORDS_DONT_MATCH);
					}
				}
				// If the user doesn´t exist:
				else {
					// Warn about the non existence of the user
					System.err.println(Constants.USER_DOESNT_EXISTS);
				}
			}
			// If any field is empty inform it
			else {
				System.err.println(Constants.USER_AND_PASSWORD_EMPTY);
			}
			return false;
		}
	}

}