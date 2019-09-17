package foodstart.manager;

import java.io.File;
import java.io.IOException;

import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;

/**
 * @author Alex Hobson on 22/08/2019
 */
public abstract class Persistence {

	/**
	 * Reads and imports a specified file assuming it contains
	 * data of the specified DataType
	 *
	 * @param file     File to read
	 * @param dataType The type of file to read
	 * @throws ImportFailureException if the data is inconsistent
	 */
	public abstract void importFile(File file, DataType dataType);

	/**
	 * Exports all data stored in memory of a given DataType to
	 * a specified file
	 *
	 * @param file     File to export to
	 * @param dataType The data type to export
	 */

	public abstract void exportFile(File file, DataType dataType);

}

