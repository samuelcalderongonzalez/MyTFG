package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.AbstractController;
import utils.DateUtils;

public class ControllerEditFilm extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private ComboBox<String> cbPersonalScore;

	@FXML
	private TextField tfReview;

	@FXML
	public void initialize() {
		cbStatus.getItems().addAll("Completada", "Pendiente", "Abandonada");
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
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
		cbStatus.getSelectionModel().select(currentFilm.getStatus());
		cbPersonalScore.getSelectionModel().select(currentFilm.getStringPersonalScore());
		tfReview.setText(currentFilm.getReview() != null ? currentFilm.getReview() : "");

	}

	@FXML
	private void deleteFilm() {
		filmRepository.deleteFilm(currentFilm);
		handleBack();

	}

	@FXML
	private void saveFilm() {
		currentFilm.setStatus(cbStatus.getSelectionModel().getSelectedItem());
		currentFilm.setReview(tfReview.getText());
		if (cbPersonalScore.getSelectionModel().getSelectedItem() != null)
			currentFilm.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
		currentFilm.setCompletedDate(
				cbStatus.getSelectionModel().getSelectedItem().equals("Completada") ? DateUtils.getCurrentDate()
						: null);
		filmRepository.updateFilm(currentFilm);
		handleBack();
	}

}
