package serielizable.view;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import serielizable.entity.Film;
import serielizable.entity.Serie;
import utils.APILibrary;
import utils.AbstractController;

public class SearchController extends AbstractController {
	APILibrary api = new APILibrary();

	List<Film> foundFilms;

	List<Serie> foundSeries;

	@FXML
	private Button searchButton;

	@FXML
	private Label logMessage;

	@FXML
	private TextField searchTextField;

	private ObservableList<Film> films;

	private ObservableList<Serie> series;

	@FXML
	private TableView<Film> tableFilm;
	@FXML
	private TableColumn<Film, String> tcFilmTitle;
	@FXML
	private TableColumn<Film, String> tcFilmRelease;
	@FXML
	private TableColumn<Film, String> tcFilmScore;

	@FXML
	private TableView<Serie> tableSerie;
	@FXML
	private TableColumn<Serie, String> tcSerieTitle;
	@FXML
	private TableColumn<Serie, String> tcSerieRelease;
	@FXML
	private TableColumn<Serie, String> tcSerieScore;
	@FXML
	private TableColumn<Serie, String> tcSerieSeasons;

	@FXML
	private Rectangle backgroundImage;

	private boolean isFilm;

	@FXML
	public void initialize() {
		setBackgroundImage();

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
		setViewSerie();
	}

	@FXML
	public void film() {
		setViewFilm();
	}

	@FXML
	public void filmStats() {
		setViewFilmStats();
	}

	@FXML
	public void serieStats() {
		setViewSerieStats();
	}

	@FXML
	public void searchFilm() {
		isFilm = true;
		foundFilms = api.searchFilmByTitle(searchTextField.getText());
		films = FXCollections.observableArrayList(foundFilms);
		if (films.isEmpty())
			logMessage.setText("No se encontraron resultados");
		populateTable();

	}

	@FXML
	public void searchSerie() {
		isFilm = false;
		foundSeries = api.searchSerieByTitle(searchTextField.getText());
		series = FXCollections.observableArrayList(foundSeries);
		if (series.isEmpty())
			logMessage.setText("No se encontraron resultados");
		populateTable();
	}

	private void populateTable() {
		if (isFilm) {
			tableSerie.setVisible(false);
			tableFilm.setVisible(true);
			tcFilmTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
			tcFilmRelease.setCellValueFactory(param -> param.getValue().getSPReleaseDate());
			tcFilmScore.setCellValueFactory(param -> param.getValue().getSPScore());
			tableFilm.setItems(films);
			if (!films.isEmpty())
				logMessage.setText(" ");
			tableFilm.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2) {
					currentFilm = tableFilm.getSelectionModel().getSelectedItem();
					setViewAddFilm();
				}
			});
		} else {
			tableFilm.setVisible(false);
			tableSerie.setVisible(true);
			tcSerieTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
			tcSerieRelease.setCellValueFactory(param -> param.getValue().getSPReleaseDate());
			tcSerieScore.setCellValueFactory(param -> param.getValue().getSPScore());
			tcSerieSeasons.setCellValueFactory(param -> param.getValue().getSPTotalSeasons());
			tableSerie.setItems(series);
			if (!series.isEmpty())
				logMessage.setText(" ");
			tableSerie.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2) {
					currentSerie = tableSerie.getSelectionModel().getSelectedItem();
					setViewAddSerie();
				}
			});
		}
	}

	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}
}
