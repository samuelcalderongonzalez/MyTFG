package serielizable.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.AbstractController;
import utils.DateUtils;

public class ControllerEditSeason extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private ComboBox<String> cbPersonalScore;

	@FXML
	private TextField tfReview;

	@FXML
	private TextField tfProgress;

	@FXML
	private Label progress;

	@FXML
	public void initialize() {
		cbStatus.getItems().addAll("Completada", "En Curso", "Pendiente", "Abandonada");
		cbStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	checkIfCompleted();
            }
        });
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		populateFields();
	}

	@FXML
	public void logOff() {
		currentSeason = null;
		currentUser = null;
		setViewLogin();
	}

	@FXML
	public void search() {
		currentSeason = null;
		setViewSearch();
	}

	@FXML
	public void handleBack() {
		currentSeason = null;
		setViewSeason();
	}

	private void populateFields() {
		title.setText(currentSerie.getTitle() + " : " + currentSeason.getName());
		progress.setText("de " + currentSeason.getTotalEpisodes().toString());
		cbStatus.getSelectionModel().select(currentSeason.getStatus());
		cbPersonalScore.getSelectionModel().select(currentSeason.getStringPersonalScore());
		tfReview.setText(currentSeason.getReview() != null ? currentSeason.getReview() : "");
		tfProgress.setText(currentSeason.getStringCurrentEpisodes());

	}

	@FXML
	private void saveSeason() {
		currentSeason.setStatus(cbStatus.getSelectionModel().getSelectedItem());
		currentSeason.setReview(tfReview.getText());
		if (cbPersonalScore.getSelectionModel().getSelectedItem() != null)
			currentSeason.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
		currentSeason.setCurrentEpisodes(tfProgress.getText());
		seasonRepository.updateSeason(currentSeason);
		if(cbStatus.getSelectionModel().getSelectedItem().equals("Completada"))
			currentSeason.setCompletedDate(DateUtils.getCurrentDate());
		handleBack();
	}
	
	private void checkIfCompleted() {
		if(cbStatus.getSelectionModel().getSelectedItem().equals("Completada"))	 {
			tfProgress.setText(currentSeason.getStringTotalEpisodes());
			tfProgress.setEditable(false);
		} else
			tfProgress.setEditable(true);
	}

}
