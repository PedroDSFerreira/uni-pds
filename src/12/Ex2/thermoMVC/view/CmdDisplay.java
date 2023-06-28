package lab12.Ex2.thermoMVC.view;
import lab12.Ex2.thermoMVC.model.Thermometer;
import lab12.Ex2.thermoMVC.model.ThermometerListener;

public class CmdDisplay implements ThermometerListener {
    // The Unicode symbol for degrees
	private static final char DEGREE_SYMBOL = '\u00B0';

    /** The thermometer whose temperature is being displayed */
	protected Thermometer thermometer;

    /**
	 * Creates a thermometer in terminal
	 * @param t the thermometer whose temperature is displayed
	 */
    public CmdDisplay(Thermometer t) {
		thermometer = t;
        
	}

	/**
	 * Create the string to display in the thermometer
	 * @return the string to display in the thermometer
	 */
	private String getDisplayString() {
		return "" + thermometer.getTemperature() + DEGREE_SYMBOL + "F";
	}
	
	/**
	 * Change the temperature displayed
	 */
    @Override
	public void temperatureChanged() {
		System.out.print('\r' + getDisplayString()); 
	}
}
