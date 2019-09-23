package foodstart.ui;

/**
 * Interface for controllers who's table data can be changed
 * @author Alex Hobson on 18/09/2019
 */
public interface Refreshable {

	/**
	 * Refresh the displayed entries on the table
	 */
	void refreshTable();
}
