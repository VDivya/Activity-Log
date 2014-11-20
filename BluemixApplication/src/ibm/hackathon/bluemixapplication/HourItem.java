package ibm.hackathon.bluemixapplication;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("HourItem")
public class HourItem extends IBMDataObject {
	
	public static final String CLASS_NAME = "HourItem";
	private static final String POINTS = "points";
	private static final String HOUR = "hour";
	private static final String DATE = "date";
	
	/**
	 * Gets the name of the Item.
	 * @return String itemName
	 */
	public String getPoints() {
		return (String) getObject(POINTS);
	}

	/**
	 * Sets the name of a list item, as well as calls setCreationTime().
	 * @param String itemName
	 */
	public void setPoints(String itemName) {
		setObject(POINTS, (itemName != null) ? itemName : "");
	}
	
	
	/**
	 * Gets the name of the Item.
	 * @return String itemName
	 */
	public String getHour() {
		return (String) getObject(HOUR);
	}

	/**
	 * Sets the name of a list item, as well as calls setCreationTime().
	 * @param String itemName
	 */
	public void setHour(String hour) {
		setObject(HOUR, (hour != null) ? hour : "");
	}
	
	
	/**
	 * Gets the name of the Item.
	 * @return String itemName
	 */
	public String getDate() {
		return (String) getObject(DATE);
	}

	/**
	 * Sets the name of a list item, as well as calls setCreationTime().
	 * @param String itemName
	 */
	public void setDate(String date) {
		setObject(DATE, (date != null) ? date : "");
	}
	
	
	/**
	 * When calling toString() for an item, we'd really only want the name.
	 * @return String theItemName
	 */
	public String toString() {
		String theItemName = "";
		theItemName = getPoints();
		return theItemName;
	}
}
