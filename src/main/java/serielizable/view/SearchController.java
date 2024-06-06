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
import serielizable.entity.Film;
import serielizable.entity.Serie;
import utils.AbstractController;

/**
 * The search controller class
 * 
 * @author Samuel Calderón González
 *
 */
public class SearchController extends AbstractController {

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

	private boolean isFilm;

	@FXML
	public void initialize() {
		setBackgroundImage();

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
	 * Search a film by a provided title and populate the table with the result list
	 */
	@FXML
	public void searchFilm() {
		isFilm = true;
		foundFilms = api.searchFilmByTitle(searchTextField.getText());
		films = FXCollections.observableArrayList(foundFilms);
		if (films.isEmpty())
			logMessage.setText("No se encontraron resultados");
		populateTable();

	}

	/**
	 * Search a serie by a provided title and populate the table with the result
	 * list
	 */
	@FXML
	public void searchSerie() {
		isFilm = false;
		foundSeries = api.searchSerieByTitle(searchTextField.getText());
		series = FXCollections.observableArrayList(foundSeries);
		if (series.isEmpty())
			logMessage.setText("No se encontraron resultados");
		populateTable();
	}

	/**
	 * Switch which table is visible depending if the user searched a for a film or
	 * series and populate it
	 */
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
				// If double click, go to the add film view
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
				// If double click, go to the add serie view
				if (event.getClickCount() == 2) {
					currentSerie = tableSerie.getSelectionModel().getSelectedItem();
					setViewAddSerie();
				}
			});
		}
	}
}
