package serielizable.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import utils.AbstractController;
import utils.Constants;
import utils.DateUtils;

/**
 * The add film controller class
 * 
 * @author Samuel Calderón González
 *
 */
public class AddFilmController extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private ComboBox<String> cbPersonalScore;

	@FXML
	private TextArea tfReview;

	private ImageView imageViewBack;

	@FXML
	private Button getBackButton;

	@FXML
	private Button btFavorite;

	private ImageView imageFavoriteBtn;

	@FXML
	private Rectangle posterImageRec;

	Image favoriteImg = new Image(getClass().getResourceAsStream("../../utils/favorite.png"));
	Image noFavoriteImg = new Image(getClass().getResourceAsStream("../../utils/noFavorite.png"));

	@FXML
	public void initialize() {
		setBackgroundImage();
		setFavoriteImage();
		setPosterImg();
		setBackButtonIcon();
		cbStatus.getItems().addAll("Completada", "Pendiente", "Abandonada");
		cbStatus.getSelectionModel().select("Pendiente");
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		cbPersonalScore.getSelectionModel().select("-");
		populateTitle();
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
		currentFilm = null;
		setViewSearch();
	}

	/**
	 * Open the serie view
	 */
	@FXML
	public void serie() {
		currentFilm = null;
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
		currentFilm = null;
		setViewFilmStats();
	}

	/**
	 * Open the serie stats view
	 */
	@FXML
	public void serieStats() {
		currentFilm = null;
		setViewSerieStats();
	}

	/**
	 * Go to the previous view
	 */
	@FXML
	public void handleBack() {
		currentFilm = null;
		setViewSearch();
	}

	/**
	 * Populate the title with the current film title
	 */
	public void populateTitle() {
		title.setText(currentFilm.getTitle());
	}

	/**
	 * Performs the action of adding a film to the database
	 */
	@FXML
	public void handleAddFilm() {
		// checks if the status combobox is null
		if (cbStatus.getSelectionModel().getSelectedItem() != null) {
			currentFilm.setUserId(currentUser.getId());
			currentFilm.setStatus(cbStatus.getSelectionModel().getSelectedItem());
			currentFilm.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
			currentFilm.setReview(tfReview.getText().isEmpty() ? null : tfReview.getText());
			// If status is Completed, then sets CompletedDate to current date
			currentFilm.setCompletedDate(
					cbStatus.getSelectionModel().getSelectedItem().equals("Completada") ? DateUtils.getCurrentDate()
							: null);
			// Insert the film and return to the film view
			filmRepository.insertFilm(currentFilm);
			currentFilm = null;
			setViewFilm();
		} else {
			// If its null, returns an error message
			System.err.println(Constants.STATUS_NULL_ERROR);
		}
	}

	/**
	 * Set an image to the get back button and resized his dimensions
	 */
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButtonWhite.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(50);
		imageViewBack.setFitWidth(50);
		getBackButton.setGraphic(imageViewBack);
	}

	/**
	 * Set the favorite image based on the favorite's film field
	 */
	private void setFavoriteImage() {
		if (currentFilm.isFavorite()) {
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
		currentFilm.setFavorite(!currentFilm.isFavorite());
		setFavoriteImage();
	}

	/**
	 * Set the poster image of the film. If the image can't be accessed, puts a
	 * default image
	 */
	private void setPosterImg() {
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.jpg"));
		if (currentFilm.getImageLink() != null) {
			Image imgPoster = new Image(currentFilm.getImageLink());
			if (!imgPoster.isError()) {
				posterImageRec.setFill(new ImagePattern(imgPoster));
			} else {
				posterImageRec.setFill(new ImagePattern(imgPosterDefault));
			}
		} else {
			posterImageRec.setFill(new ImagePattern(imgPosterDefault));
		}
	}


}
