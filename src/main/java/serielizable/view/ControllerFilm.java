package serielizable.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import serielizable.entity.Film;
import utils.AbstractController;

public class ControllerFilm extends AbstractController {

	private ObservableList<Film> films;

	@FXML
	private Label title;

	@FXML
	private Label score;

	@FXML
	private Label totalVotes;

	@FXML
	private Label genres;

	@FXML
	private Label completedDate;

	@FXML
	private Label sinopsis;

	@FXML
	private Label review;

	@FXML
	private Button btEditFilm;
	
	@FXML
	private Button btFavorite;

	@FXML
	private TableView<Film> tableFootage;
	@FXML
	private TableColumn<Film, String> tcTitle;
	@FXML
	private TableColumn<Film, String> tcStatus;
	@FXML
	private TableColumn<Film, String> tcPersonalScore;
	@FXML
	private TableColumn<Film, String> tcReleaseDate;
	@FXML
	private TableColumn<Film, String> tcDuration;

	private ImageView editImageView;
	
	private ImageView imageFavoriteBtn;
	
	Image favoriteImg = new Image(getClass().getResourceAsStream("../../utils/favorite.png"));
	Image noFavoriteImg = new Image(getClass().getResourceAsStream("../../utils/noFavorite.png"));
	
	@FXML
	private Rectangle posterImageRec;

	@FXML
	public void initialize() {
		setButtonIcon();
		currentFilms = filmRepository.getAllByUserId(currentUser.getId());
		films = FXCollections.observableArrayList(currentFilms);
		tcTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
		tcStatus.setCellValueFactory(param -> param.getValue().getSPStatus());
		tcPersonalScore.setCellValueFactory(param -> param.getValue().getSPPersonalScore());
		tcReleaseDate.setCellValueFactory(param -> param.getValue().getSPReleaseDate());
		tcDuration.setCellValueFactory(param -> param.getValue().getSPDuration());
		tableFootage.setItems(films);
		tableFootage.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				clearLabels();
				currentFilm = tableFootage.getSelectionModel().getSelectedItem();
				System.out.println(currentFilm);
				pupulateLabels();
			}
		});
		// Mostrar la primera película al iniciar la vista
		currentFilm = currentFilms.get(0);
		pupulateLabels();

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
	public void serie() {
		setViewSerie();
	}

	@FXML
	public void editFilm() {
		setViewEditFilm();
	}

	private void pupulateLabels() {
		setFavoriteImage();
		btEditFilm.setVisible(true);
		score.setText(currentFilm.getStringScore());
		totalVotes.setText(currentFilm.getStringTotalScoreVotes());
		genres.setText(currentFilm.getGenres());
		completedDate.setText(currentFilm.getCompletedDate());
		sinopsis.setText(currentFilm.getSynopsis());
		review.setText(currentFilm.getReview());
//		try {
//			Image imgPoster = new Image(currentFilm.getImageLink());
//			System.out.println(currentFilm.getImageLink());
//			posterImage = new ImageView(imgPoster);
//			posterImage.setFitHeight(150);
//			posterImage.setFitWidth(130);
//			posterImage.setImage(imgPoster);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		setPosterImg();
		title.setText(currentFilm.getTitle());

	}

	private void setPosterImg() {
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.png"));
		if(currentFilm.getImageLink() != null) {
			Image imgPoster = new Image(currentFilm.getImageLink());
			if(!imgPoster.isError()) {
				posterImageRec.setFill(new ImagePattern(imgPoster));
			} else {
				posterImageRec.setFill(new ImagePattern(imgPosterDefault));
			}
		} else {
			posterImageRec.setFill(new ImagePattern(imgPosterDefault));
		}
	}


	private void clearLabels() {
		score.setText("");
		totalVotes.setText("votes");
		genres.setText("");
		completedDate.setText("");
		sinopsis.setText("");
		review.setText("");
	}

	public ControllerFilm() {

	}

	private void setButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/edit.png"));
		editImageView = new ImageView(editImg);
		editImageView.setFitHeight(50);
		editImageView.setFitWidth(50);
		btEditFilm.setGraphic(editImageView);
	}
	
	private void setFavoriteImage() {
		if(currentFilm.isFavorite()) {
			imageFavoriteBtn = new ImageView(favoriteImg);
			resizeFavoriteImage();
		} else {
			imageFavoriteBtn = new ImageView(noFavoriteImg);
			resizeFavoriteImage();
		}
		
	}
	
	private void resizeFavoriteImage() {
		imageFavoriteBtn.setFitHeight(30);
		imageFavoriteBtn.setFitWidth(30);
		btFavorite.setGraphic(imageFavoriteBtn);
	}
	
//	private void toggleFavorite() {
//		currentFilm.setFavorite(!currentFilm.isFavorite());
//		pupulateLabels();
//	}
}
