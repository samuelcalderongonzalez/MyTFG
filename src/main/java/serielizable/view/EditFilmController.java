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
import utils.DateUtils;

public class EditFilmController extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private ComboBox<String> cbPersonalScore;

	@FXML
	private TextArea tfReview;

	@FXML
	private Button btFavorite;

	@FXML
	private Button getBackButton;

	private ImageView imageViewBack;

	private ImageView imageFavoriteBtn;

	@FXML
	private Rectangle posterImageRec;

	@FXML
	private Rectangle backgroundImage;

	Image favoriteImg = new Image(getClass().getResourceAsStream("../../utils/favorite.png"));
	Image noFavoriteImg = new Image(getClass().getResourceAsStream("../../utils/noFavorite.png"));

	@FXML
	public void initialize() {
		setBackgroundImage();
		setFavoriteImage();
		setPosterImg();
		setBackButtonIcon();
		populateFields();
	}

	@FXML
	public void logOff() {
		currentUser = null;
		currentFilm = null;
		currentSerie = null;
		setViewLogin();
	}

	@FXML
	public void exit() {
		Platform.exit();
	}

	@FXML
	public void search() {
		currentFilm = null;
		setViewSearch();
	}

	@FXML
	public void serie() {
		currentFilm = null;
		setViewSerie();
	}

	@FXML
	public void film() {
		setViewFilm();
	}

	@FXML
	public void filmStats() {
		currentFilm = null;
		setViewFilmStats();
	}

	@FXML
	public void serieStats() {
		currentFilm = null;
		setViewSerieStats();
	}

	@FXML
	public void handleBack() {
		currentFilm = null;
		setViewFilm();
	}

	private void populateFields() {
		cbStatus.getItems().addAll("Completada", "Pendiente", "Abandonada");
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
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
		currentFilm.setLastUpdateDate(DateUtils.getCurrentDate());
		filmRepository.updateFilm(currentFilm);
		handleBack();
	}

	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButtonWhite.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(50);
		imageViewBack.setFitWidth(50);
		getBackButton.setGraphic(imageViewBack);
	}

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

	private void setFavoriteImage() {
		if (currentFilm.isFavorite()) {
			imageFavoriteBtn = new ImageView(favoriteImg);
			resizeFavoriteImage();
		} else {
			imageFavoriteBtn = new ImageView(noFavoriteImg);
			resizeFavoriteImage();
		}

	}

	private void resizeFavoriteImage() {
		imageFavoriteBtn.setFitHeight(125);
		imageFavoriteBtn.setFitWidth(125);
		btFavorite.setGraphic(imageFavoriteBtn);
	}

	@FXML
	private void toggleFavorite() {
		currentFilm.setFavorite(!currentFilm.isFavorite());
		setFavoriteImage();
	}

	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

}
