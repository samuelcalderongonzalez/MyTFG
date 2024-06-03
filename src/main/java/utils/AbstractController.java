package utils;

import java.util.List;

import serielizable.CAPPI;
import serielizable.entity.Film;
import serielizable.entity.Season;
import serielizable.entity.Serie;
import serielizable.entity.User;
import serielizable.repository.FilmRepository;
import serielizable.repository.SeasonRepository;
import serielizable.repository.SerieRepository;
import serielizable.repository.UserRepository;

/**
 * The abstract controller class, which contains some useful variables and
 * methods
 * 
 * @author Samuel Calderón González
 *
 */
public abstract class AbstractController {
	public UserRepository userRepository = new UserRepository();
	public FilmRepository filmRepository = new FilmRepository();
	public SerieRepository serieRepository = new SerieRepository();
	public SeasonRepository seasonRepository = new SeasonRepository();
	public static APILibrary api = new APILibrary();

	public Constants constants;
	public CAPPI cApp;
	public static User currentUser;
	public static Film currentFilm;
	public static Serie currentSerie;
	public static Season currentSeason;
	public static List<Film> currentFilms;
	public static List<Serie> currentSeries;

	public void setMainApp(CAPPI cApp) {
		this.cApp = cApp;
	}

	public String sayMainApp() {
		return cApp.toString();
	}

	/**
	 * Sets the view by a provided name and adds the extension
	 * 
	 * @param vista
	 */
	public void setView(String vista) {
		cApp.setView("view/" + vista + ".fxml");
	}

	/**
	 * Set view film
	 */
	public void setViewFilm() {
		setView("Film");
	}

	/**
	 * Set view serie
	 */
	public void setViewSerie() {
		setView("Serie");
	}

	/**
	 * Set view season
	 */
	public void setViewSeason() {
		setView("Season");
	}

	/**
	 * Set view login
	 */
	public void setViewLogin() {
		setView("Login");
	}

	/**
	 * Set view register
	 */
	public void setViewRegister() {
		setView("Register");
	}

	/**
	 * Set view search
	 */
	public void setViewSearch() {
		setView("Search");
	}

	/**
	 * Set view edit film
	 */
	public void setViewEditFilm() {
		setView("EditFilm");
	}

	/**
	 * Set view edit serie
	 */
	public void setViewEditSerie() {
		setView("EditSerie");
	}

	/**
	 * Set view edit season
	 */
	public void setViewEditSeason() {
		setView("EditSeason");
	}

	/**
	 * Set view add film
	 */
	public void setViewAddFilm() {
		setView("AddFilm");
	}

	/**
	 * Set view add serie
	 */
	public void setViewAddSerie() {
		setView("AddSerie");
	}

	/**
	 * Set view film stats
	 */
	public void setViewFilmStats() {
		setView("FilmStats");
	}

	/**
	 * Set view serie stats
	 */
	public void setViewSerieStats() {
		setView("SerieStats");
	}

}
