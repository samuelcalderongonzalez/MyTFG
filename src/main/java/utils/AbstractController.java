package utils;

import java.util.List;

import serielizable.CAPPI;
import serielizable.entity.User;
import serielizable.entity.Footage;
import serielizable.repository.FootageRepository;
import serielizable.repository.UserRepository;

public abstract class AbstractController {
	public UserRepository userRepository = new UserRepository();
	public FootageRepository footageRepository = new FootageRepository();
	public Constants constants;
	public CAPPI cApp;
	public static User currentUser;
	public static List<Footage> currentFootages;

	/**
	 * Llamada por la aplicacion principal, creando una referencia entre la
	 * aplicacion principal y el controlador.
	 * 
	 * @param cApp ConcesionarioApp
	 */
	public void setMainApp(CAPPI cApp) {
		this.cApp = cApp;
	}

	public String sayMainApp() {
		return cApp.toString();
	}

	/**
	 * Configura la vista dependiendo del tipo de usuario y la vista correspondiente
	 * 
	 * @param tipoVista concesionarioView
	 * @param vista     String
	 */
	public void setView(String vista) {
		cApp.setView("view/" + vista + ".fxml");
	}

}
