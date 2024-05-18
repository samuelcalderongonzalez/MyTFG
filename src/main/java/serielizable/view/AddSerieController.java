package serielizable.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import serielizable.entity.Season;
import utils.AbstractController;
import utils.DateUtils;

public class AddSerieController extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private Label progress;

	@FXML
	private Label totalEpisodes;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private ComboBox<String> cbPersonalScore;

	@FXML
	private TextField tfProgress;

	@FXML
	private TextField tfReview;

	@FXML
	private Button btnNext;

	@FXML
	private Button btnPrev;

	private int page;

	private int maxPage;

	private ImageView imageViewBack;
	
	@FXML
	private Button getBackButton;

	@FXML
	public void initialize() {
		setBackButtonIcon();
		cbStatus.getItems().addAll("Completada", "En curso", "Abandonada", "Pendiente");
		cbStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	checkIfCompleted();
            }
        });
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		page = -1;
		maxPage = currentSerie.getSeasons().size() - 1;
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
		if (page < 0)
			title.setText(currentSerie.getTitle());
		else
			title.setText(currentSerie.getSeasons().get(page).getName());

	}

	@FXML
	public void handleNext() {
		saveSerie();
		page++;
		buttonsAndFieldsVisibility();
		clearAndPopulateFields();
		checkIfCompleted();
	}

	@FXML
	public void handlePrev() {
		saveSerie();
		page--;
		buttonsAndFieldsVisibility();
		clearAndPopulateFields();
		checkIfCompleted();
	}

	// TODO Esto es horroroso, hay que refactorizar.
	public void saveSerie() {
		if (page < 0) {
			currentSerie.setUserId(currentUser.getId());
			currentSerie.setStatus(cbStatus.getSelectionModel().getSelectedItem());
			currentSerie.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
			currentSerie.setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
			currentSerie.setCompletedDate(cbStatus.getSelectionModel().getSelectedItem().equals("Completada") ? DateUtils.getCurrentDate() : null);
		} else {
			currentSerie.getSeasons().get(page).setStatus(cbStatus.getSelectionModel().getSelectedItem());
			currentSerie.getSeasons().get(page).setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
			currentSerie.getSeasons().get(page).setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
			try {
				currentSerie.getSeasons().get(page).setCurrentEpisodes(
						tfProgress.getText().isEmpty() ? null : Integer.parseInt(tfProgress.getText()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			currentSerie.getSeasons().get(page).setCompletedDate(cbStatus.getSelectionModel().getSelectedItem().equals("Completada") ? DateUtils.getCurrentDate() : null);

		}
	}

	@FXML
	public void handleAddSerie() {
		saveSerie();
		for (Season season : currentSerie.getSeasons()) {
			if (season.getStatus() == null) {
				season.setStatus("Pendiente");
			}
			season.setUserId(currentUser.getId());
		}
		serieRepository.insertSerie(currentSerie);
		currentSerie = null;
		setViewSerie();

	}

	private void buttonsAndFieldsVisibility() {
		if (page < 0) {
			btnPrev.setVisible(false);
			totalEpisodes.setVisible(false);
			progress.setVisible(false);
			tfProgress.setVisible(false);
		} else if (page == maxPage) {
			btnNext.setVisible(false);
			totalEpisodes.setVisible(true);
			progress.setVisible(true);
			tfProgress.setVisible(true);
		} else {
			btnPrev.setVisible(true);
			btnNext.setVisible(true);
			totalEpisodes.setVisible(true);
			progress.setVisible(true);
			tfProgress.setVisible(true);
		}
	}
	
	//TODO muy mejorable. Añadir lambdas a ser posible
	private void clearAndPopulateFields() {
		populateTitle();
		pupulateTotalEpisodes();
		if (page < 0) {
			if (currentSerie.getStatus() != null) {
				cbStatus.getSelectionModel().select(currentSerie.getStatus());
			} else {
				cbStatus.getSelectionModel().select("Pendiente");
			}
			cbPersonalScore.getSelectionModel().select(
					(currentSerie.getPersonalScore() == null ? "-" : currentSerie.getPersonalScore().toString()));
			tfReview.setText(currentSerie.getReview() == null ? "" : currentSerie.getReview());
		} else {
			if (currentSerie.getSeasons().get(page).getStatus() != null) {
				cbStatus.getSelectionModel().select(currentSerie.getSeasons().get(page).getStatus());
			} else {
				cbStatus.getSelectionModel().select("Pendiente");
			}
			cbPersonalScore.getSelectionModel().select(
					(currentSerie.getSeasons().get(page).getPersonalScore() == null ? "-" : currentSerie.getSeasons().get(page).getPersonalScore().toString()));
			tfReview.setText(currentSerie.getSeasons().get(page).getReview() == null ? ""
					: currentSerie.getSeasons().get(page).getReview());
			tfProgress.setText(currentSerie.getSeasons().get(page).getCurrentEpisodes() == null ? ""
					: currentSerie.getSeasons().get(page).getCurrentEpisodes().toString());
		}
	}
	//TODO cambiar por lambda
	private void pupulateTotalEpisodes() {
		if (page < 0)
			totalEpisodes.setText("");
		else
			totalEpisodes.setText("de " + currentSerie.getSeasons().get(page).getTotalEpisodes().toString());
	}
	
	private void checkIfCompleted() {
		if (page >= 0) {
			if (cbStatus.getSelectionModel().getSelectedItem().equals("Completada")) {
				tfProgress.setText(currentSerie.getSeasons().get(page).getStringTotalEpisodes());
				tfProgress.setEditable(false);
			} else
				tfProgress.setEditable(true);
		}
	}
	
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButton.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(50);
		imageViewBack.setFitWidth(50);
		getBackButton.setGraphic(imageViewBack);
	}

}