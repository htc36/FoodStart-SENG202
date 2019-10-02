package foodstart.ui.util;

import javafx.scene.control.Spinner;
import javafx.util.StringConverter;

/**
 * A string converter that rejects non integer values
 * @author Alex Hobson
 * @date 01/10/2019
 *
 */
public class BetterStringConverter extends StringConverter<Integer> {
	
	/**
	 * Spinner we're applied to
	 */
	private final Spinner<Integer> spinner;
	
	/**
	 * Constructor for BetterStringConverter
	 * @param spinner Spinner this class is applied to
	 */
	public BetterStringConverter(Spinner<Integer> spinner) {
		this.spinner = spinner;
	}

	@Override
	public String toString(Integer object) {
		return String.valueOf(object);
	}

	@Override
	public Integer fromString(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			//Reset the box to what it was before
			int val = spinner.getValue();
			spinner.getValueFactory().setValue(-1);
			spinner.getValueFactory().setValue(val);
			return val;
		}
	}

}
