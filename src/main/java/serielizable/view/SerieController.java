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
import serielizable.entity.Serie;
import utils.AbstractController;

/**
 * The serie controller class
 * 
 * @author Samuel Calderón González
 *
 */
public class SerieController extends AbstractController {

	private ObservableList<Serie> series;

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
	private Button btnEditSerie;

	@FXML
	private Button btnSeasons;

	@FXML
	private ComboBox<String> genreFilter;

	@FXML
	private ComboBox<String> favoriteFilter;

	@FXML
	private ComboBox<String> statusFilter;

	@FXML
	private TextField titleFilter;

	@FXML
	private TableView<Serie> tableFootage;
	@FXML
	private TableColumn<Serie, String> tcTitle;
	@FXML
	private TableColumn<Serie, String> tcStatus;
	@FXML
	private TableColumn<Serie, String> tcPersonalScore;
	@FXML
	private TableColumn<Serie, String> tcReleaseDate;
	@FXML
	private TableColumn<Serie, String> tcSeasons;

	private ImageView imageView;

	private ImageView imageViewSeason;

	private List<Serie> currentFilteredSeries = new ArrayList<Serie>();

	@FXML
	private Rectangle posterImageRectangle;

	@FXML
	private Rectangle backgroundImage;

	@FXML
	private Rectangle favoriteImage;

	@FXML
	public void initialize() {
		Image imgFavorite = new Image(getClass().getResourceAsStream("../../utils/favoriteIconShow.png"));
		favoriteImage.setFill(new ImagePattern(imgFavorite));
		setBackgroundImage();
		setButtonIcon();
		populateComboBoxes();
		currentSeries = serieRepository.getAllByUserId(currentUser.getId());
		series = FXCollections.observableArrayList(currentSeries);
		tcTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
		tcStatus.setCellValueFactory(param -> param.getValue().getSPStatus());
		tcPersonalScore.setCellValueFactory(param -> param.getValue().getSPPersonalScore());
		tcReleaseDate.setCellValueFactory(param -> param.getValue().getSPReleaseDate());
		tcSeasons.setCellValueFactory(param -> param.getValue().getSPCountSeasons());
		tableFootage.setItems(series);
		tableFootage.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				clearLabels();
				currentSerie = tableFootage.getSelectionModel().getSelectedItem();
				pupulateLabels();
			}
		});
		// Mostrar la primera serie al iniciar la vista
		currentSerie = currentSeries.get(0);
		pupulateLabels();

	}

	/**
	 * Set the background image
	 */
	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

	/**
	 * Log off method
	 */
	@FXML
	public void logOff() {
		currentUser = null;
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
	 * Open the edit serie view
	 */
	@FXML
	public void editSerie() {
		setViewEditSerie();
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
	 * Open the season view
	 */
	@FXML
	public void viewSeasons() {
		setViewSeason();
	}

	/**
	 * Populate the labels
	 */
	private void pupulateLabels() {
		setFavoriteImage();
		btnEditSerie.setVisible(true);
		btnSeasons.setVisible(true);
		score.setText(currentSerie.getStringScore());
		totalVotes.setText(currentSerie.getStringTotalScoreVotes());
		genres.setText(currentSerie.getGenres());
		completedDate.setText(currentSerie.getCompletedDate());
		sinopsis.setText(currentSerie.getSynopsis());
		review.setText(currentSerie.getReview());
		setPosterImg();
		title.setText(currentSerie.getTitle());
	}

	/**
	 * This method fills the poster with the serie image
	 */
	private void setPosterImg() {
		// Create a default image
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.jpg"));
		// If the current serie has an url:
		if (currentSerie.getImageLink() != null) {
			// Create an image with the url
			Image imgPoster = new Image(currentSerie.getImageLink());
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
	 * This method is used to change favorite´s image icon after using it
	 */
	private void setFavoriteImage() {
		favoriteImage.setVisible(currentSerie.isFavorite());
	}

	/**
	 * Clear the labels
	 */
	private void clearLabels() {
		score.setText("");
		totalVotes.setText("votes");
		genres.setText("");
		completedDate.setText("");
		sinopsis.setText("");
		review.setText("");
	}

	public SerieController() {

	}

	/**
	 * Set the edit and season details button image and resizes it
	 */
	private void setButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/edit.png"));
		imageView = new ImageView(editImg);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnEditSerie.setGraphic(imageView);

		Image goSeason = new Image(getClass().getResourceAsStream("../../utils/seasonDetails.png"));
		imageViewSeason = new ImageView(goSeason);
		imageViewSeason.setFitHeight(50);
		imageViewSeason.setFitWidth(50);
		btnSeasons.setGraphic(imageViewSeason);
	}

	/**
	 * Populate status, favorite and genre combo boxes
	 */
	private void populateComboBoxes() {
		genreFilter.getItems().addAll("Sin filtro", "Action & Adventure", "Animación", "Comedia", "Crimen",
				"Documental", "Drama", "Familia", "Kids", "Misterio", "News", "Reality", "Sci-fi & Fantasy", "Soap",
				"Talk", "War & Politics", "Western");
		favoriteFilter.getItems().addAll("Sin filtro", "Favorito", "No favorito");
		statusFilter.getItems().addAll("Sin filtro", "Completada", "En curso", "Pendiente", "Abandonada");
		defaultComboBoxes();
	}

	/**
	 * Set the combo boxes to No filters by default
	 */
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

	/**
	 * Apply the current filters
	 */
	@FXML
	private void applyFilters() {
		for (Serie serie : currentSeries) {
			if (checkFilterCases(serie))
				currentFilteredSeries.add(serie);
		}
		if (!titleFilter.getText().isEmpty()) {
			List<Serie> trueFilteredList = new ArrayList<Serie>();
			for (Serie serie : currentFilteredSeries) {
				if (serie.getTitle().toLowerCase().contains(titleFilter.getText().toLowerCase()))
					trueFilteredList.add(serie);
			}
			series = FXCollections.observableArrayList(trueFilteredList);
		} else {
			series = FXCollections.observableArrayList(currentFilteredSeries);
		}
		tableFootage.setItems(series);
		currentFilteredSeries = new ArrayList<Serie>();

	}

	/**
	 * If return true, the serie is added to the filtered list after passing all the
	 * checks
	 * 
	 * @param serie
	 * @return true or false
	 */
	private boolean checkFilterCases(Serie serie) {
		if (!genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (serie.getGenres().contains(genreFilter.getSelectionModel().getSelectedItem())
					&& serie.getStatus().equals(statusFilter.getSelectionModel().getSelectedItem())
					&& ((serie.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("Favorito"))
							|| (!serie.isFavorite()
									&& favoriteFilter.getSelectionModel().getSelectedItem().equals("No favorito"))))
				return true;
			else
				return false;
		else if (!genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (serie.getGenres().contains(genreFilter.getSelectionModel().getSelectedItem()))
				return true;
			else
				return false;
		else if (genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (serie.getStatus().equals(statusFilter.getSelectionModel().getSelectedItem()))
				return true;
			else
				return false;
		else if (genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if ((serie.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("Favorito"))
					|| (!serie.isFavorite()
							&& favoriteFilter.getSelectionModel().getSelectedItem().equals("No favorito")))
				return true;
			else
				return false;
		else if (!genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (serie.getGenres().contains(genreFilter.getSelectionModel().getSelectedItem())
					&& serie.getStatus().equals(statusFilter.getSelectionModel().getSelectedItem()))
				return true;
			else
				return false;
		else if (!genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (serie.getGenres().contains(genreFilter.getSelectionModel().getSelectedItem())
					&& ((serie.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("Favorito"))
							|| (!serie.isFavorite()
									&& favoriteFilter.getSelectionModel().getSelectedItem().equals("No favorito"))))
				return true;
			else
				return false;
		else if (genreFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !statusFilter.getSelectionModel().getSelectedItem().equals("Sin filtro")
				&& !favoriteFilter.getSelectionModel().getSelectedItem().equals("Sin filtro"))
			if (serie.getStatus().equals(statusFilter.getSelectionModel().getSelectedItem())
					&& ((serie.isFavorite() && favoriteFilter.getSelectionModel().getSelectedItem().equals("Favorito"))
							|| (!serie.isFavorite()
									&& favoriteFilter.getSelectionModel().getSelectedItem().equals("No favorito"))))
				return true;
			else
				return false;
		else
			return true;
	}
}
