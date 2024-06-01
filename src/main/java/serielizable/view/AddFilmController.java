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
		cbStatus.getItems().addAll("Completada", "Pendiente", "Abandonada");
		cbStatus.getSelectionModel().select("Pendiente");
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		cbPersonalScore.getSelectionModel().select("-");
		populateTitle();
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
			currentFilm.setCompletedDate(
					cbStatus.getSelectionModel().getSelectedItem().equals("Completada") ? DateUtils.getCurrentDate()
							: null);
			filmRepository.insertFilm(currentFilm);
			currentFilm = null;
			setViewFilm();
		} else {
			System.err.println(Constants.STATUS_NULL_ERROR);
		}
	}
	
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButtonWhite.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(50);
		imageViewBack.setFitWidth(50);
		getBackButton.setGraphic(imageViewBack);
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
	
	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

}
