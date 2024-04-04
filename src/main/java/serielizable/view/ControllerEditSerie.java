package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.AbstractController;

public class ControllerEditSerie extends AbstractController {

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
		currentSerie = null;
		currentUser = null;
		setViewLogin();
	}

	@FXML
	public void search() {
		currentSerie = null;
		setViewSearch();
	}

	@FXML
	public void handleBack() {
		currentSerie = null;
		setViewSerie();
	}

	private void populateFields() {
		title.setText(currentSerie.getTitle());
		cbStatus.getSelectionModel().select(currentSerie.getStatus());
		cbPersonalScore.getSelectionModel().select(currentSerie.getStringPersonalScore());
		tfReview.setText(currentSerie.getReview() != null ? currentSerie.getReview() : "");

	}

	@FXML
	private void deleteSerie() {
		serieRepository.deleteSerie(currentSerie);
		handleBack();

	}

	@FXML
	private void saveSerie() {
		currentSerie.setStatus(cbStatus.getSelectionModel().getSelectedItem());
		currentSerie.setReview(tfReview.getText());
		if (cbPersonalScore.getSelectionModel().getSelectedItem() != null)
			currentSerie.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
		serieRepository.updateSerie(currentSerie);
		handleBack();
	}

}
