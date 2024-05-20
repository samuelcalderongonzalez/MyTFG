package serielizable.view;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class FilmController extends AbstractController {

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
	private ComboBox<String> genreFilter;

	@FXML
	private ComboBox<String> favoriteFilter;

	@FXML
	private ComboBox<String> statusFilter;

	@FXML
	private TextField titleFilter;

	@FXML
	private Button searchFilters;

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

	private List<Film> currentFilteredFilms = new ArrayList<Film>();

	@FXML	
	private Rectangle posterImageRectangle;
	
	@FXML
	private Rectangle backgroundImage;
	
	@FXML
	private Rectangle favoriteImage;

	@FXML
	public void initialize() {
		setBackgroundImage();
		setEditButtonIcon();
		populateComboBoxes();
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
				pupulateLabels();
			}
		});
		// Select first film when initializing the view
		currentFilm = currentFilms.get(0);
		pupulateLabels();

	}

	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

	@FXML
	public void logOff() {
		currentUser = null;
		setViewLogin();
	}
	
	@FXML
	public void exit() {
		Platform.exit();
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
	public void film() {
		setViewFilm();
	}

	@FXML
	public void editFilm() {
		setViewEditFilm();
	}
	
	@FXML
	public void filmStats() {
		setViewFilmStats();
	}
	
	@FXML
	public void serieStats() {
		setViewSerieStats();
	}

	/**
	 * This method gets the film information and sends them to the proper places
	 */
	private void pupulateLabels() {
		Image imgFavorite = new Image(getClass().getResourceAsStream("../../utils/favoriteIconShow.png"));
		favoriteImage.setFill(new ImagePattern(imgFavorite));
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
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.jpg"));
		// If the current film has an url:
		if (currentFilm.getImageLink() != null) {
			// Create an image with the url
			Image imgPoster = new Image(currentFilm.getImageLink());
			// If the image is valid:
			if (!imgPoster.isError()) {
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

	public FilmController() {

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
	 * This method is used to change favorite´s image icon after using it
	 */
	private void setFavoriteImage() {
		favoriteImage.setVisible(currentFilm.isFavorite());
	}

	private void populateComboBoxes() {
		genreFilter.getItems().addAll("Sin filtro", "Acción", "Aventura", "Animación", "Comedia", "Crimen",
				"Documental", "Drama", "Familia", "Fantasía", "Historia", "Terror", "Música", "Misterio", "Romance",
				"Ciencia ficción", "Película de TV", "Suspense", "Bélica", "Western");
		favoriteFilter.getItems().addAll("Sin filtro", "Favorito", "No favorito");
		statusFilter.getItems().addAll("Sin filtro", "Completada", "Pendiente", "Abandonada");
		defaultComboBoxes();

	}

	@FXML
	private void applyFilters() {
		for (Film film : currentFilms) {
			if (checkFilterCases(film))
				currentFilteredFilms.add(film);
		}
		if(!titleFilter.getText().isEmpty()) {
			List<Film> trueFilteredList = new ArrayList<Film>();
			for (Film film : currentFilteredFilms) {
				if(film.getTitle().toLowerCase().contains(titleFilter.getText().toLowerCase()))
					trueFilteredList.add(film);
			}
			films = FXCollections.observableArrayList(trueFilteredList);
		} else {
			films = FXCollections.observableArrayList(currentFilteredFilms);
		}
		tableFootage.setItems(films);
		currentFilteredFilms = new ArrayList<Film>();

	}

	private boolean checkFilterCases(Film film) {
		if (!genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (film.getGenres().contains(genreFilter.getSelectionModel().getSelectedItem())
					&& film.getStatus().equals(statusFilter.getSelectionModel().getSelectedItem())
					&& ((film.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("Favorito"))
							|| (!film.isFavorite()
									&& favoriteFilter.getSelectionModel().getSelectedItem().equals("No favorito"))))
				return true;
			else
				return false;
		else if (!genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (film.getGenres().contains(genreFilter.getSelectionModel().getSelectedItem()))
				return true;
			else
				return false;
		else if (genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (film.getStatus().equals(statusFilter.getSelectionModel().getSelectedItem()))
				return true;
			else
				return false;
		else if (genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if ((film.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("Favorito"))
					|| (!film.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("No favorito")))
				return true;
			else
				return false;
		else if (!genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (film.getGenres().contains(genreFilter.getSelectionModel().getSelectedItem())
					&& film.getStatus().equals(statusFilter.getSelectionModel().getSelectedItem()))
				return true;
			else
				return false;
		else if (!genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (film.getGenres().contains(genreFilter.getSelectionModel().getSelectedItem())
					&& ((film.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("Favorito"))
							|| (!film.isFavorite()
									&& favoriteFilter.getSelectionModel().getSelectedItem().equals("No favorito"))))
				return true;
			else
				return false;
		else if (genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (film.getStatus().equals(statusFilter.getSelectionModel().getSelectedItem())
					&& ((film.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("Favorito"))
							|| (!film.isFavorite()
									&& favoriteFilter.getSelectionModel().getSelectedItem().equals("No favorito"))))
				return true;
			else
				return false;
		else
			return true;
	}

	private void defaultComboBoxes() {
		if (genreFilter.getSelectionModel().getSelectedItem() == null) {
			genreFilter.getSelectionModel().select("Sin filtro");
		}
		if (favoriteFilter.getSelectionModel().getSelectedItem() == null) {
			favoriteFilter.getSelectionModel().select("Sin filtro");
		}
		if (statusFilter.getSelectionModel().getSelectedItem() == null) {
			statusFilter.getSelectionModel().select("Sin filtro");
		}
	}

}
