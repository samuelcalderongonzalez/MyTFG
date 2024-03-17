package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import serielizable.entity.Film;
import utils.AbstractController;

public class ControllerMainApp extends AbstractController {

	private ObservableList<Film> films;

	@FXML
	private TableView<Film> tableFootage;
	@FXML
	private TableColumn<Film, String> tcTitle;
	@FXML
	private TableColumn<Film, String> tcStatus;
	@FXML
	private TableColumn<Film, String> tcProgress;
	@FXML
	private TableColumn<Film, String> trvBeneficio;

	@FXML
	public void initialize() {
		films = FXCollections.observableArrayList(currentFilms);
		tcTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
		tcStatus.setCellValueFactory(param -> param.getValue().getSPStatus());
		tableFootage.setItems(films);

	}

	@FXML
	public void logOff() {
		currentUser = null;
		setView("Login");
	}
	
	@FXML
	public void search() {
		setView("Search");
	}
	
	public ControllerMainApp () {

	}
}
