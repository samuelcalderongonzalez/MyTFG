package serielizable;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.APILibrary;
import utils.AbstractController;
import utils.HibernateUtils;

public class SerielizableApp extends Application implements CAPPI {
	private Stage primaryStage;
	// Window elements
	private AnchorPane loginLayout;
	private BorderPane rootLayout;
	private static APILibrary api;

	public static void main(String[] args) throws ClientProtocolException, IOException {
		api = new APILibrary();
//		api.searchSerieRuntime(1396);
		api.searchSerieByTitle("Breaking Bad");
		api.searchFilmByTitle("Matrix");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Serielizable");
		primaryStage.setOnCloseRequest(event -> {
			HibernateUtils.close();
		});
		HibernateUtils.getSession();
		initLoginLayout();

	}

	/**
	 * This method launches the first stage
	 */
	private void initLoginLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			// Define the view
			loader.setLocation(SerielizableApp.class.getResource("view/Login.fxml"));
			loginLayout = (AnchorPane) loader.load();
			// Define and load the scene
			Scene scene = new Scene(loginLayout);
			// Load and show the scene
			primaryStage.setScene(scene);
			primaryStage.show();

			AbstractController controlador = loader.getController();
			controlador.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public BorderPane getPrimaryStage() {
		return rootLayout;
	}

	public void setRoot(String root) {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(SerielizableApp.class.getResource(root));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			AbstractController controlador = loader.getController();
			if (controlador != null)
				controlador.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setView(String view) {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(SerielizableApp.class.getResource(view));
			System.out.println(loader.getLocation());
			AnchorPane centralPane = (AnchorPane) loader.load();

			AbstractController controlador = loader.getController();
			if (controlador != null) {
				controlador.setMainApp(this);
				controlador.sayMainApp();
			}
			Scene scene = new Scene(centralPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
