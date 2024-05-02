package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.AbstractController;
import utils.DateUtils;

public class ControllerEditSerie extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private ComboBox<String> cbPersonalScore;

	@FXML
	private TextField tfReview;

	private ImageView imageViewBack;
	@FXML
	private Button getBackButton;

	public void initialize() {
		setBackButtonIcon();
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
		cbStatus.getItems().addAll("Completada", "Pendiente", "Abandonada", "En curso");
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
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
		currentSerie.setCompletedDate(
				cbStatus.getSelectionModel().getSelectedItem().equals("Completada") ? DateUtils.getCurrentDate()
						: null);
		handleBack();
	}

	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButton.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(50);
		imageViewBack.setFitWidth(50);
		getBackButton.setGraphic(imageViewBack);
	}

}
