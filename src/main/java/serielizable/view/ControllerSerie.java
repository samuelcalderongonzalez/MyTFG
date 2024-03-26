package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import serielizable.entity.Serie;
import utils.AbstractController;

public class ControllerSerie extends AbstractController {

	private ObservableList<Serie> series;

	@FXML
	private TableView<Serie> tableFootage;
	@FXML
	private TableColumn<Serie, String> tcTitle;
	@FXML
	private TableColumn<Serie, String> tcStatus;
	@FXML
	private TableColumn<Serie, String> tcProgress;
	@FXML
	private TableColumn<Serie, String> trvBeneficio;

	@FXML
	public void initialize() {
		currentSeries = serieRepository.getAllByUserId(currentUser.getId());
		series = FXCollections.observableArrayList(currentSeries);
		tcTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
		tcStatus.setCellValueFactory(param -> param.getValue().getSPStatus());
		tableFootage.setItems(series);
		tableFootage.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				currentSerie = tableFootage.getSelectionModel().getSelectedItem();
				System.out.println(currentSerie);
				editSerie();
			}
		});

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

	public void editSerie() {
//		setViewEditSerie();
	}

	public ControllerSerie() {

	}
}
