package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import serielizable.entity.User;
import serielizable.repository.UserRepository;
import utils.AbstractController;
import utils.Constants;

public class ControllerRegister extends AbstractController {

	// Password String regex
	private final String pattern = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

	@FXML
	private TextField userNameTextField;

	@FXML
	private PasswordField firstPasswordField;

	@FXML
	private TextField passwordFieldViewable;

	@FXML
	private Button passwordViewButton;

	@FXML
	private PasswordField repeatPasswordField;

	@FXML
	private TextField repeatPasswordFieldViewable;

	@FXML
	private Button repeatPasswordViewButton;

	@FXML
	private Button getBackButton;

	@FXML
	private Button registerButton;

	// Define the button image
	Image showPasswordImg = new Image(getClass().getResourceAsStream("../../utils/eye.png"));
	Image hidePasswordImg = new Image(getClass().getResourceAsStream("../../utils/hidden.png"));
	// Define the ImageView to resize it
	@FXML
	private ImageView imageViewLogo;

	ImageView imageView;
	
	ImageView imageViewBack;

	// This is used for the password visibility
	private boolean passwordIsVisible = false;
	private boolean repeatPasswordIsVisible = false;

	@FXML
	public void initialize() {
		userRepository = new UserRepository();
		setPasswordImage();
		setRepeatPasswordImage();
		setBackButtonIcon();
	}

	@FXML
	public void handleRegister() {
		if (passwordIsVisible) {
			changePasswordVisivility();
		}
		if (repeatPasswordIsVisible) {
			changeRepeatPasswordVisivility();
		}
		// If everything is filled:
		if (!userNameTextField.getText().isEmpty() && !firstPasswordField.getText().isEmpty()
				&& !repeatPasswordField.getText().isEmpty()) {
			// If the username is not used yet:
			if (!userRepository.exists(userNameTextField.getText())) {
				// If both passwords are the same then:
				if (firstPasswordField.getText().equals(repeatPasswordField.getText())) {
					// If the password matches some rules:
					if (firstPasswordField.getText().matches(pattern)) {
						// Create a new user
						userRepository.insertUser(new User(userNameTextField.getText(), firstPasswordField.getText()));
						// And get back to the login screen
						setViewLogin();
					} else {
						// Warn the user about it
						System.err.println(Constants.REGEX_ERROR);
					}
				}
				// If the passwords are not the same:
				else {
					// Warn the user about it
					System.err.println(Constants.PASSWORDS_DONT_MATCH);
				}
			}
			// If the username is alredy used:
			else {
				// Warn the user about it
				System.err.println(Constants.USERNAME_ALREADY_USED);
			}
		}
		// If any field is empty:
		else {
			// Warn the user about it
			System.err.println(Constants.USER_AND_PASSWORD_EMPTY);
		}
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
	 * This method resizes the password visibility button image
	 */
	private void resizeRepeatPasswordImage() {
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		// Load the image into the button
		repeatPasswordViewButton.setGraphic(imageView);
	}

	/**
	 * This method changes the password visibility button image
	 */
	private void setPasswordImage() {
		if (!passwordIsVisible) {
			// Change the image of the button
			imageView = new ImageView(showPasswordImg);
			resizePasswordImage();
		} else {
			// Change the image of the button
			imageView = new ImageView(hidePasswordImg);
			resizePasswordImage();
		}

	}

	/**
	 * This method changes the password visibility button image
	 */
	private void setRepeatPasswordImage() {
		if (!repeatPasswordIsVisible) {
			// Change the image of the button
			imageView = new ImageView(showPasswordImg);
			resizeRepeatPasswordImage();
		} else {
			// Change the image of the button
			imageView = new ImageView(hidePasswordImg);
			resizeRepeatPasswordImage();
		}

	}

	@FXML
	/**
	 * This method changed the password visibility
	 */
	private void changePasswordVisivility() {
		// If the password is not visible:
		if (!passwordIsVisible) {
			// Set the normal textfield available
			passwordFieldViewable.setVisible(true);
			passwordFieldViewable.setDisable(false);
			passwordFieldViewable.setText(firstPasswordField.getText());
			// Disable the password field
			firstPasswordField.setText("");
			firstPasswordField.setVisible(false);
			firstPasswordField.setDisable(true);
			// And change the value of the variable used to check visibility
			passwordIsVisible = true;
			setPasswordImage();
		}
		// If the password is visible:
		else {
			// Set the password field available
			firstPasswordField.setVisible(true);
			firstPasswordField.setDisable(false);
			// Set the text from the other field to "save" the progress
			firstPasswordField.setText(passwordFieldViewable.getText());
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
	 * This method changed the password visibility
	 */
	private void changeRepeatPasswordVisivility() {
		// If the password is not visible:
		if (!repeatPasswordIsVisible) {
			// Set the normal textfield available
			repeatPasswordFieldViewable.setVisible(true);
			repeatPasswordFieldViewable.setDisable(false);
			repeatPasswordFieldViewable.setText(repeatPasswordField.getText());
			// Disable the password field
			repeatPasswordField.setText("");
			repeatPasswordField.setVisible(false);
			repeatPasswordField.setDisable(true);
			// And change the value of the variable used to check visibility
			repeatPasswordIsVisible = true;
			setRepeatPasswordImage();
		}
		// If the password is visible:
		else {
			// Set the password field available
			repeatPasswordField.setVisible(true);
			repeatPasswordField.setDisable(false);
			// Set the text from the other field to "save" the progress
			repeatPasswordField.setText(repeatPasswordFieldViewable.getText());
			// Disable the normal textfield
			repeatPasswordFieldViewable.setText("");
			repeatPasswordFieldViewable.setVisible(false);
			repeatPasswordFieldViewable.setDisable(true);
			// And change the value of the variable used to check visibility
			repeatPasswordIsVisible = false;
			setRepeatPasswordImage();
		}
	}

	@FXML
	/**
	 * This method returns you back to the login view
	 */
	public void handleBack() {
		setViewLogin();
	}
	
	/**
	 * This method prepares the return button
	 */
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButton.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(40);
		imageViewBack.setFitWidth(40);
		getBackButton.setGraphic(imageViewBack);
	}
}