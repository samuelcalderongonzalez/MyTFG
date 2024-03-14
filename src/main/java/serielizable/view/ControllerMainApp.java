package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import serielizable.entity.Footage;
import utils.AbstractController;

public class ControllerMainApp extends AbstractController {

	private ObservableList<Footage> footages;

	@FXML
	private TableView<Footage> tableFootage;
	@FXML
	private TableColumn<Footage, String> tcTitle;
	@FXML
	private TableColumn<Footage, String> tcStatus;
	@FXML
	private TableColumn<Footage, String> tcProgress;
	@FXML
	private TableColumn<Footage, String> trvBeneficio;

	@FXML
	public void initialize() {
		footages = FXCollections.observableArrayList(currentFootages);
		tcTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
		tcStatus.setCellValueFactory(param -> param.getValue().getSPStatus());
		tcProgress.setCellValueFactory(param -> param.getValue().getSPProgress());
		tableFootage.setItems(footages);

	}

	@FXML
	public void logOff() {
		currentUser = null;
		setView("Login");
	}
	
	public ControllerMainApp () {

	}
}
