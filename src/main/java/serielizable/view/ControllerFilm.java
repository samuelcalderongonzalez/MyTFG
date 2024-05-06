package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private TextField genres;

	@FXML
	private TextField completedDate;

	@FXML
	private TextArea sinopsis;

	@FXML
	private TextArea review;

	@FXML
	private Button editFilmButton;
	
	@FXML
	private Button favoriteButton;

	@FXML
	private TableView<Film> tableFootage;
	@FXML
	private TableColumn<Film, String> titleTableColumn;
	@FXML
	private TableColumn<Film, String> statusTableColumn;
	@FXML
	private TableColumn<Film, String> personalRateTableColumn;
	@FXML
	private TableColumn<Film, String> releaseDateTableColumn;
	@FXML
	private TableColumn<Film, String> durationTableColumn;

	private ImageView editImageView;
	
	private ImageView favoriteButtonImage;
	
	Image favoriteImg = new Image(getClass().getResourceAsStream("../../utils/favorite.png"));
	Image noFavoriteImg = new Image(getClass().getResourceAsStream("../../utils/noFavorite.png"));
	
	@FXML
	private Rectangle posterImageRectangle;

	@FXML
	public void initialize() {
		setEditButtonIcon();
		// Get all the films of the logged user
		currentFilms = filmRepository.getAllByUserId(currentUser.getId());
		// Save them
		films = FXCollections.observableArrayList(currentFilms);
		// Assign the data collected to each label
		titleTableColumn.setCellValueFactory(param -> param.getValue().getSPTitle());
		statusTableColumn.setCellValueFactory(param -> param.getValue().getSPStatus());
		personalRateTableColumn.setCellValueFactory(param -> param.getValue().getSPPersonalScore());
		releaseDateTableColumn.setCellValueFactory(param -> param.getValue().getSPReleaseDate());
		durationTableColumn.setCellValueFactory(param -> param.getValue().getSPDuration());
		tableFootage.setItems(films);
		// Assign clicks functionality
		tableFootage.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				clearLabels();
				currentFilm = tableFootage.getSelectionModel().getSelectedItem();
				System.out.println(currentFilm);
				pupulateLabels();
			}
		});
		// Select first film when initializing the view
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

	/**
	 * This method gets the film information and sends them to the proper places
	 */
	private void pupulateLabels() {
		setFavoriteImage();
		// Set the film editable
		editFilmButton.setVisible(true);
		// Set info
		score.setText(currentFilm.getStringScore());
		totalVotes.setText(currentFilm.getStringTotalScoreVotes());
		genres.setText(currentFilm.getGenres());
		completedDate.setText(currentFilm.getCompletedDate());
		sinopsis.setText(currentFilm.getSynopsis());
		review.setText(currentFilm.getReview());
		setPosterImg();
		title.setText(currentFilm.getTitle());

	}

	/**
	 * This method fills the poster with the film image
	 */
	private void setPosterImg() {
		// Create a default image
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.png"));
		// If the current film has an url:
		if(currentFilm.getImageLink() != null) {
			// Create an image with the url
			Image imgPoster = new Image(currentFilm.getImageLink());
			// If the image is valid:
			if(!imgPoster.isError()) {
				// Assign the image
				posterImageRectangle.setFill(new ImagePattern(imgPoster));
			}
			// Assign the default image
			else {
				posterImageRectangle.setFill(new ImagePattern(imgPosterDefault));
			}
		}
		// Assign the default image
		else {
			posterImageRectangle.setFill(new ImagePattern(imgPosterDefault));
		}
	}


	/**
	 * This method clears the labels
	 */
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

	/**
	 * This method prepares visually the edit button
	 */
	private void setEditButtonIcon() {
		// Create the image
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/edit.png"));
		// Assign the image
		editImageView = new ImageView(editImg);
		// Set some properties
		editImageView.setFitHeight(50);
		editImageView.setFitWidth(50);
		// Apply the image to the button
		editFilmButton.setGraphic(editImageView);
	}
	
	/**
	 * This method prepares visually the favorite button
	 */	
	private void resizeFavoriteImage() {
		favoriteButtonImage.setFitHeight(30);
		favoriteButtonImage.setFitWidth(30);
		favoriteButton.setGraphic(favoriteButtonImage);
	}
	
	/**
	 * This method is used to change favorite´s button icon after using it
	 */
	private void setFavoriteImage() {
		if(currentFilm.isFavorite()) {
			favoriteButtonImage = new ImageView(favoriteImg);
			resizeFavoriteImage();
		} else {
			favoriteButtonImage = new ImageView(noFavoriteImg);
			resizeFavoriteImage();
		}
		
	}
	
//	private void toggleFavorite() {
//		currentFilm.setFavorite(!currentFilm.isFavorite());
//		pupulateLabels();
//	}
}
