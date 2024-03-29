package utils;

import java.util.List;

import serielizable.CAPPI;
import serielizable.entity.User;
import serielizable.entity.Film;
import serielizable.entity.Serie;
import serielizable.repository.FilmRepository;
import serielizable.repository.SerieRepository;
import serielizable.repository.UserRepository;

public abstract class AbstractController {
	public UserRepository userRepository = new UserRepository();
	public FilmRepository filmRepository = new FilmRepository();
	public SerieRepository serieRepository = new SerieRepository();

	public Constants constants;
	public CAPPI cApp;
	public static User currentUser;
	public static Film currentFilm;
	public static Serie currentSerie;
	public static List<Film> currentFilms;
	public static List<Serie> currentSeries;


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
	
	public void setViewFilm() {
		setView("Film");
	}
	
	public void setViewLogin() {
		setView("Login");
	}
	
	public void setViewRegister() {
		setView("Register");
	}
	
	public void setViewSearch() {
		setView("Search");
	}
	
	public void setViewEditFilm() {
		setView("EditFilm");
	}
	
	public void setViewAddFilm() {
		setView("AddFilm");
	}
	
	public void setViewAddSerie() {
		setView("AddSerie");
	}

}
