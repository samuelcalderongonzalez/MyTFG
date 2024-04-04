package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.AbstractController;
import utils.Constants;

public class ControllerAddFilm extends AbstractController {

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
		if (cbStatus.getSelectionModel().getSelectedItem() != null) {
			currentFilm.setUserId(currentUser.getId());
			currentFilm.setStatus(cbStatus.getSelectionModel().getSelectedItem());
			currentFilm.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
			currentFilm.setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
			filmRepository.insertFilm(currentFilm);
			currentFilm = null;
			setViewFilm();
		} else {
			System.err.println(Constants.STATUS_NULL_ERROR);
		}
	}

}
