package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.AbstractController;
import utils.Constants;

public class ControllerAddFilm extends AbstractController {

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
		populateTitle();
	}

	@FXML
	public void logOff() {
		currentUser = null;
		setViewLogin();
	}

	@FXML
	public void search() {
		setViewSearch();
	}

	@FXML
	public void handleBack() {
		currentFilm = null;
		setViewSearch();
	}

	public void populateTitle() {
		title.setText(currentFilm.getTitle());
	}

	@FXML
	public void handleAddFilm() {
		if (!tfStatus.getText().isEmpty()) {
			currentFilm.setUserId(currentUser.getId());
			currentFilm.setStatus(tfStatus.getText());
			currentFilm.setPersonalScore(tfPersonalScore.getText().isEmpty() ? null : Double.parseDouble(tfPersonalScore.getText()));
			currentFilm.setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
			filmRepository.insertFilm(currentFilm);
			currentFilm = null;
			setViewFilm();
		} else {
			System.err.println(Constants.STATUS_NULL_ERROR);
		}
	}

}
