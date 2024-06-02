package serielizable.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import serielizable.entity.Season;
import utils.AbstractController;
import utils.DateUtils;

/**
 * The add serie controller class
 * 
 * @author Samuel Calderón González
 *
 */
public class AddSerieController extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private CheckBox allCompleted;

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
	private TextArea tfReview;

	@FXML
	private Button btnNext;

	@FXML
	private Button btnPrev;

	@FXML
	private Button btnLast;

	@FXML
	private Button btnFirst;

	private int page;

	private int maxPage;

	private ImageView imageViewBack;

	@FXML
	private Button getBackButton;
	@FXML
	private Button btFavorite;

	private ImageView imageFavoriteBtn;

	@FXML
	private Rectangle posterImageRec;

	@FXML
	private Rectangle backgroundImage;

	@FXML
	private Label pagination;

	Image favoriteImg = new Image(getClass().getResourceAsStream("../../utils/favorite.png"));
	Image noFavoriteImg = new Image(getClass().getResourceAsStream("../../utils/noFavorite.png"));

	@FXML
	public void initialize() {
		page = -1;
		setBackgroundImage();
		setFavoriteImage();
		setPosterImg();
		setBackButtonIcon();
		cbStatus.getItems().addAll("Completada", "En curso", "Abandonada", "Pendiente");
		cbStatus.getSelectionModel().select("Pendiente");
		cbStatus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				checkIfCompleted();
			}
		});
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		cbPersonalScore.getSelectionModel().select("-");
		maxPage = currentSerie.getSeasons().size() - 1;
		populateTitleAndPagination();
	}

	/**
	 * Log off method
	 */
	@FXML
	public void logOff() {
		currentUser = null;
		currentFilm = null;
		currentSerie = null;
		setViewLogin();
	}

	/**
	 * Close the app
	 */
	@FXML
	public void exit() {
		Platform.exit();
	}

	/**
	 * Open the search view
	 */
	@FXML
	public void search() {
		currentSerie = null;
		setViewSearch();
	}

	/**
	 * Open the serie view
	 */
	@FXML
	public void serie() {
		setViewSerie();
	}

	/**
	 * Open the film view
	 */
	@FXML
	public void film() {
		setViewFilm();
	}

	/**
	 * Open the film stats view
	 */
	@FXML
	public void filmStats() {
		setViewFilmStats();
	}

	/**
	 * Open the serie stats view
	 */
	@FXML
	public void serieStats() {
		setViewSerieStats();
	}

	/**
	 * Go back to the previous view
	 */
	@FXML
	public void handleBack() {
		currentSerie = null;
		setViewSearch();
	}

	/**
	 * Populate the title and the pagination labels
	 */
	public void populateTitleAndPagination() {
		pagination.setText(String.valueOf(page + 2).concat("/").concat(String.valueOf(maxPage + 2)));
		if (page < 0)
			title.setText(currentSerie.getTitle());
		else
			title.setText(currentSerie.getSeasons().get(page).getName());

	}

	/**
	 * Goes to the next page
	 */
	@FXML
	public void handleNext() {
		saveSerie();
		page++;
		buttonsAndFieldsVisibility();
		clearAndPopulateFields();
		checkIfCompleted();
	}

	/**
	 * Goes to the last page
	 */
	@FXML
	public void handleLast() {
		saveSerie();
		page = maxPage;
		buttonsAndFieldsVisibility();
		clearAndPopulateFields();
		checkIfCompleted();
	}

	/**
	 * Goes to the previous page
	 */
	@FXML
	public void handlePrev() {
		saveSerie();
		page--;
		buttonsAndFieldsVisibility();
		clearAndPopulateFields();
		checkIfCompleted();
	}

	/**
	 * Goes to the first page
	 */
	@FXML
	public void handleFirst() {
		saveSerie();
		page = -1;
		buttonsAndFieldsVisibility();
		clearAndPopulateFields();
		checkIfCompleted();
	}

	/**
	 * Saves the current state of the serie/season fields, depending on the
	 * pagination
	 */
	public void saveSerie() {
		// If page is -1, then saves the info related to the serie
		if (page < 0) {
			currentSerie.setUserId(currentUser.getId());
			currentSerie.setStatus(cbStatus.getSelectionModel().getSelectedItem());
			currentSerie.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
			currentSerie.setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
			// If status is completed, then completed date is current date
			currentSerie.setCompletedDate(
					cbStatus.getSelectionModel().getSelectedItem().equals("Completada") ? DateUtils.getCurrentDate()
							: null);
			// Else, saves the info related to the current season
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
			// If status is completed, then completedDate is current date
			currentSerie.getSeasons().get(page)
					.setCompletedDate(cbStatus.getSelectionModel().getSelectedItem().equals("Completada")
							? DateUtils.getCurrentDate()
							: null);

		}
	}

	/**
	 * Saves the series and season info to the database
	 */
	@FXML
	public void handleAddSerie() {
		saveSerie();
		for (Season season : currentSerie.getSeasons()) {
			if (season.getStatus() == null) {
				// If any season has no status, sets to Pending by default
				season.setStatus("Pendiente");
			}
			season.setUserId(currentUser.getId());
		}
		// If the checkbox mark everything as completed is checked, then marks
		// everything as completed
		if (allCompleted.isSelected())
			markEverythingAsCompleted();
		// Saves the serie then goes to the serie view
		serieRepository.insertSerie(currentSerie);
		currentSerie = null;
		setViewSerie();

	}

	/**
	 * Changes the buttons and fields visibility depending on the current page
	 * status
	 */
	private void buttonsAndFieldsVisibility() {
		setFavoriteImage();
		if (page < 0) {
			btnPrev.setVisible(false);
			btnFirst.setVisible(false);
			btnNext.setVisible(true);
			btnLast.setVisible(true);
			totalEpisodes.setVisible(false);
			progress.setVisible(false);
			tfProgress.setVisible(false);
			btFavorite.setVisible(true);
			allCompleted.setVisible(true);
		} else if (page == maxPage) {
			btnNext.setVisible(false);
			btnLast.setVisible(false);
			btnFirst.setVisible(true);
			btnPrev.setVisible(true);
			totalEpisodes.setVisible(true);
			progress.setVisible(true);
			tfProgress.setVisible(true);
			btFavorite.setVisible(false);
			allCompleted.setVisible(false);
		} else {
			btnPrev.setVisible(true);
			btnNext.setVisible(true);
			btnLast.setVisible(true);
			btnFirst.setVisible(true);
			totalEpisodes.setVisible(true);
			progress.setVisible(true);
			tfProgress.setVisible(true);
			btFavorite.setVisible(false);
			allCompleted.setVisible(false);
		}
	}

	/**
	 * Clear and populates certain fields depending on the current state of the
	 * pagination
	 */
	private void clearAndPopulateFields() {
		populateTitleAndPagination();
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
			cbPersonalScore.getSelectionModel()
					.select((currentSerie.getSeasons().get(page).getPersonalScore() == null ? "-"
							: currentSerie.getSeasons().get(page).getPersonalScore().toString()));
			tfReview.setText(currentSerie.getSeasons().get(page).getReview() == null ? ""
					: currentSerie.getSeasons().get(page).getReview());
			tfProgress.setText(currentSerie.getSeasons().get(page).getCurrentEpisodes() == null ? ""
					: currentSerie.getSeasons().get(page).getCurrentEpisodes().toString());
		}
	}

	/**
	 * Mark every season and the serie itself as completed
	 */
	private void markEverythingAsCompleted() {
		currentSerie.setStatus("Completada");
		currentSerie.setCompletedDate(DateUtils.getCurrentDate());
		for (Season s : currentSerie.getSeasons()) {
			s.setStatus("Completada");
			s.setCurrentEpisodes(s.getTotalEpisodes());
			s.setCompletedDate(DateUtils.getCurrentDate());
		}
	}

	/**
	 * Populate the total episode field when in a season page
	 */
	private void pupulateTotalEpisodes() {
		if (page < 0)
			totalEpisodes.setText("");
		else
			totalEpisodes.setText("de " + currentSerie.getSeasons().get(page).getTotalEpisodes().toString());
	}

	/**
	 * Checks if the status is completed and set the episode field to max when in a
	 * season page
	 */
	private void checkIfCompleted() {
		if (page >= 0) {
			if (cbStatus.getSelectionModel().getSelectedItem().equals("Completada")) {
				tfProgress.setText(currentSerie.getSeasons().get(page).getStringTotalEpisodes());
				tfProgress.setEditable(false);
			} else
				tfProgress.setEditable(true);
		}
	}

	/**
	 * Set the back button icon and resizes it
	 */
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButtonWhite.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(50);
		imageViewBack.setFitWidth(50);
		getBackButton.setGraphic(imageViewBack);
	}

	/**
	 * Set the background image
	 */
	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

	/**
	 * Set the poster image of the film. If the image can't be accessed, puts a
	 * default image
	 */
	private void setPosterImg() {
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.jpg"));
		if (currentSerie.getImageLink() != null) {
			Image imgPoster = new Image(currentSerie.getImageLink());
			if (!imgPoster.isError()) {
				posterImageRec.setFill(new ImagePattern(imgPoster));
			} else {
				posterImageRec.setFill(new ImagePattern(imgPosterDefault));
			}
		} else {
			posterImageRec.setFill(new ImagePattern(imgPosterDefault));
		}
	}

	/**
	 * Set the favorite image based on the favorite's film field
	 */
	private void setFavoriteImage() {
		if (currentSerie.isFavorite()) {
			imageFavoriteBtn = new ImageView(favoriteImg);
			resizeFavoriteImage();
		} else {
			imageFavoriteBtn = new ImageView(noFavoriteImg);
			resizeFavoriteImage();
		}
	}

	/**
	 * Resize the favorite image
	 */
	private void resizeFavoriteImage() {
		imageFavoriteBtn.setFitHeight(125);
		imageFavoriteBtn.setFitWidth(125);
		btFavorite.setGraphic(imageFavoriteBtn);
	}

	/*
	 * Toggles the favorite film image and field
	 */
	@FXML
	private void toggleFavorite() {
		currentSerie.setFavorite(!currentSerie.isFavorite());
		setFavoriteImage();
	}

}
