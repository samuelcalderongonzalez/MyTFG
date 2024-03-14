package serielizable;

import javafx.scene.layout.BorderPane;

public interface CAPPI {
	public BorderPane getPrimaryStage();

	/**
	 * Permite cambiar la vista.
	 * 
	 * @param root String con la ruta de la vista.
	 */
	public void setView(String view);
	
}
