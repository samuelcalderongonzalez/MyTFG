package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.AbstractController;

public class ControllerEditFilm extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private TextField tfStatus;

	@FXML
	private TextField tfPersonalScore;

	@FXML
	private TextField tfReview;

	@FXML
	public void initialize() {
		populateFields();
	}

	@FXML
	public void logOff() {
		currentFilm = null;
		currentUser = null;
		setViewLogin();
	}

	@FXML
	public void search() {
		currentFilm = null;
		setViewSearch();
	}

	@FXML
	public void handleBack() {
		currentFilm = null;
		setViewFilm();
	}

	private void populateFields() {
		title.setText(currentFilm.getTitle());
		tfStatus.setText(currentFilm.getStatus());
		tfPersonalScore
				.setText(currentFilm.getPersonalScore() != null ? currentFilm.getPersonalScore().toString() : "");
		tfReview.setText(currentFilm.getReview() != null ? currentFilm.getReview() : "");

	}

	@FXML
	private void deleteFilm() {
		if (validateTextFields()) {
			filmRepository.deleteFilm(currentFilm);
			handleBack();
		}
	}

	@FXML
	private void saveFilm() {
		if (validateTextFields()) {
			currentFilm.setStatus(tfStatus.getText());
			currentFilm.setReview(tfReview.getText());
			if(!tfPersonalScore.getText().isEmpty())
				currentFilm.setPersonalScore(Double.parseDouble(tfPersonalScore.getText()));
			filmRepository.updateFilm(currentFilm);
			handleBack();
		}
	}

	private boolean validateTextFields() {
		if (!tfPersonalScore.getText().isEmpty()) {
			try {
				Double.parseDouble(tfPersonalScore.getText());
				return true;
			} catch (Exception e) {
				System.err.println("Score must be numeric");
				return false;
			}
		} else {
			return true;
		}
	}

}
