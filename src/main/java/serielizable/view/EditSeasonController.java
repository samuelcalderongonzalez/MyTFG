package serielizable.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import utils.AbstractController;
import utils.DateUtils;

public class EditSeasonController extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private ComboBox<String> cbPersonalScore;

	@FXML
	private TextArea tfReview;

	@FXML
	private TextField tfProgress;

	@FXML
	private Label progress;

	private ImageView imageViewBack;
	
	@FXML
	private Button getBackButton;
	
	@FXML
	private Rectangle posterImageRec;

	@FXML
	private Rectangle backgroundImage;

	@FXML
	public void initialize() {
		setBackgroundImage();
		setPosterImg();
		setBackButtonIcon();
		populateFields();
	}

	@FXML
	public void logOff() {
		currentUser = null;
		currentFilm = null;
		currentSerie = null;
		currentSeason = null;
		setViewLogin();
	}

	@FXML
	public void exit() {
		Platform.exit();
	}

	@FXML
	public void search() {
		currentSeason = null;
		setViewSearch();
	}

	@FXML
	public void serie() {
		currentSeason = null;
		setViewSerie();
	}

	@FXML
	public void film() {
		currentSeason = null;
		setViewFilm();
	}

	@FXML
	public void filmStats() {
		currentSeason = null;
		setViewFilmStats();
	}

	@FXML
	public void serieStats() {
		currentSeason = null;
		setViewSerieStats();
	}

	@FXML
	public void handleBack() {
		currentSeason = null;
		setViewSeason();
	}

	private void populateFields() {
		cbStatus.getItems().addAll("Completada", "En Curso", "Pendiente", "Abandonada");
		cbStatus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				checkIfCompleted();
			}
		});
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		title.setText(currentSerie.getTitle() + " : " + currentSeason.getName());
		progress.setText("de " + currentSeason.getTotalEpisodes().toString());
		cbStatus.getSelectionModel().select(currentSeason.getStatus());
		cbPersonalScore.getSelectionModel().select(currentSeason.getStringPersonalScore());
		tfReview.setText(currentSeason.getReview() != null ? currentSeason.getReview() : "");
		tfProgress.setText(currentSeason.getStringCurrentEpisodes());

	}

	@FXML
	private void saveSeason() {
		currentSeason.setStatus(cbStatus.getSelectionModel().getSelectedItem());
		currentSeason.setReview(tfReview.getText());
		if (cbPersonalScore.getSelectionModel().getSelectedItem() != null)
			currentSeason.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
		currentSeason.setCurrentEpisodes(tfProgress.getText());
		seasonRepository.updateSeason(currentSeason);
		if (cbStatus.getSelectionModel().getSelectedItem().equals("Completada"))
			currentSeason.setCompletedDate(DateUtils.getCurrentDate());
		handleBack();
	}

	private void checkIfCompleted() {
		if (cbStatus.getSelectionModel().getSelectedItem().equals("Completada")) {
			tfProgress.setText(currentSeason.getStringTotalEpisodes());
			tfProgress.setEditable(false);
		} else
			tfProgress.setEditable(true);
	}

	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButton.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(50);
		imageViewBack.setFitWidth(50);
		getBackButton.setGraphic(imageViewBack);
	}
	
	private void setPosterImg() {
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.jpg"));
		if (currentSerie.getImageLink() != null) {
			Image imgPoster = new Image(currentSerie.getImageLink());
			if (!imgPoster.isError()) {
				posterImageRec.setFill(new ImagePattern(imgPoster));
			} else {
				posterImageRec.setFill(new ImagePattern(imgPosterDefault));
			}
		} else {
			posterImageRec.setFill(new ImagePattern(imgPosterDefault));
		}
	}
	
	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

}
