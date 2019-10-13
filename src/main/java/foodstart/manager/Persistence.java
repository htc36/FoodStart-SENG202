package foodstart.manager;

import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;

import java.io.File;

/**
 * Represents a possible persistence manager
 * @author Alex Hobson
 */
public abstract class Persistence {

	/**
	 *
	 */
	protected Persistence() {
	}

	/**
	 * Reads and imports a specified file assuming it contains data of the specified
	 * DataType
	 *
	 * @param file     File to read
	 * @param dataType The type of file to read
	 * @throws ImportFailureException if the data is inconsistent
	 */
	public abstract void importFile(File file, DataType dataType);

	/**
	 * Exports all data stored in memory of a given DataType to a specified file
	 *
	 * @param file     File to export to
	 * @param dataType The data type to export
	 */

	public abstract void exportFile(File file, DataType dataType);

	/**
	 * Saves all of the data stored in memory to internal storage (foodstart
	 * directory)
	 * @return true if the data was saved correctly; false otherwise
	 */
	public abstract boolean saveAllFiles();

	/**
	 * Loads all of the data stored in the foodstart directory to memory
	 */
	public abstract void loadAllFiles();

}
