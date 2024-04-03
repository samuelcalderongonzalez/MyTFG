package serielizable.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import serielizable.entity.Film;
import serielizable.entity.Season;
import serielizable.entity.Serie;
import utils.AbstractController;
import utils.Constants;

public class ControllerAddSerie extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private Label progress;

	@FXML
	private Label totalEpisodes;

	@FXML
	private TextField tfStatus;

	@FXML
	private TextField tfProgress;

	@FXML
	private TextField tfPersonalScore;

	@FXML
	private TextField tfReview;

	@FXML
	private Button btnNext;

	@FXML
	private Button btnPrev;

	private int page;

	private int maxPage;

	@FXML
	public void initialize() {
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
	}

	@FXML
	public void handlePrev() {
		saveSerie();
		page--;
		buttonsAndFieldsVisibility();
		clearAndPopulateFields();
	}

	public void saveSerie() {
		if (page < 0) {
			currentSerie.setUserId(currentUser.getId());
			currentSerie.setStatus(tfStatus.getText().isEmpty() ? null : tfStatus.getText());
			try {
				currentSerie.setPersonalScore(
						tfPersonalScore.getText().isEmpty() ? null : Double.parseDouble(tfPersonalScore.getText()));
			} catch (NumberFormatException e) {
			}
			currentSerie.setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
		} else {
			currentSerie.getSeasons().get(page)
					.setStatus(tfStatus.getText().isEmpty() ? null : tfStatus.getText());
			try {
				currentSerie.getSeasons().get(page).setPersonalScore(
						tfPersonalScore.getText().isEmpty() ? null : Double.parseDouble(tfPersonalScore.getText()));
			} catch (NumberFormatException e) {
			}
			currentSerie.getSeasons().get(page).setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
			try {
				currentSerie.getSeasons().get(page)
						.setCurrentEpisodes(tfProgress.getText().isEmpty() ? null : Integer.parseInt(tfProgress.getText()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void handleAddSerie() {
		saveSerie();
		for (Season season : currentSerie.getSeasons()) {
			if(season.getStatus() == null) {
				season.setStatus("Pendiente");
			}
			season.setUserId(currentUser.getId());
		}
		serieRepository.insertSerie(currentSerie);
		currentSerie = null;
		setViewFilm(); // TODO cambiar

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

	private void clearAndPopulateFields() {
		populateTitle();
		pupulateTotalEpisodes();
		if (page < 0) {
			tfStatus.setText(currentSerie.getStatus() == null  ? " " : currentSerie.getStatus());
			tfPersonalScore.setText(currentSerie.getPersonalScore() == null ? " "
					: currentSerie.getPersonalScore().toString());
			tfReview.setText(currentSerie.getReview() == null  ? " " : currentSerie.getReview());
		} else {
			tfStatus.setText(currentSerie.getSeasons().get(page).getStatus() == null ? " "
					: currentSerie.getSeasons().get(page).getStatus());
			tfPersonalScore.setText(currentSerie.getSeasons().get(page).getPersonalScore() == null ? " "
					: currentSerie.getSeasons().get(page).getPersonalScore().toString());
			tfReview.setText(currentSerie.getSeasons().get(page).getReview() == null  ? " "
					: currentSerie.getSeasons().get(page).getReview());
			tfProgress.setText(currentSerie.getSeasons().get(page).getCurrentEpisodes()  == null ? " "
					: currentSerie.getSeasons().get(page).getCurrentEpisodes().toString());
		}
	}

	private void pupulateTotalEpisodes() {
		if (page < 0)
			totalEpisodes.setText("");
		else
			totalEpisodes.setText("de " + currentSerie.getSeasons().get(page).getTotalEpisodes().toString());
	}

}
