package serielizable;

import javafx.scene.layout.BorderPane;

public interface CAPPI {
	public BorderPane getPrimaryStage();

	/**
	 * Change the view.
	 * 
	 * @param view name without extension.
	 */
	public void setView(String view);
	
}
