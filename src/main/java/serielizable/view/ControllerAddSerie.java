package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.AbstractController;
import utils.Constants;

public class ControllerAddSerie extends AbstractController {

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
		currentSerie = null;
		setViewSearch();
	}

	public void populateTitle() {
		title.setText(currentSerie.getTitle());
	}

	@FXML
	public void handleAddSerie() {
		if (!tfStatus.getText().isEmpty()) {
			currentSerie.setUserId(currentUser.getId());
			currentSerie.setStatus(tfStatus.getText());
			currentSerie.setPersonalScore(tfPersonalScore.getText().isEmpty() ? null : Double.parseDouble(tfPersonalScore.getText()));
			currentSerie.setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
			serieRepository.insertSerie(currentSerie);
			currentSerie = null;
			setViewFilm();
		} else {
			System.err.println(Constants.STATUS_NULL_ERROR);
		}
	}

}
