package serielizable.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import serielizable.entity.Film;
import utils.APILibrary;
import utils.AbstractController;

public class ControllerSearch extends AbstractController {
	APILibrary api = new APILibrary();

	List<Film> foundFilms;
	@FXML
	private Button searchButton;
	
	@FXML
	private TextField searchTextField;
	
	private ObservableList<Film> films;

	@FXML
	private TableView<Film> tableFootage;
	@FXML
	private TableColumn<Film, String> tcTitle;
	@FXML
	private TableColumn<Film, String> tcRelease;
	@FXML
	private TableColumn<Film, String> tcScore;
	
	@FXML
	public void initialize() {

	}

	
	@FXML
	public void logOff() {
		currentUser = null;
		setView("Login");
	}
	
	@FXML
	public void mainApp() {
		setView("MainApp");
	}
	
	@FXML
	public void searchFilm() {
		foundFilms = api.searchFilmByTitle(searchTextField.getText());	
		films = FXCollections.observableArrayList(foundFilms);
		populateTable();
	}
	
	public void populateTable() {
		tcTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
		tableFootage.setItems(films);
	}

}
