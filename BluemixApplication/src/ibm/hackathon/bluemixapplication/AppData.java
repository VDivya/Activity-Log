package ibm.hackathon.bluemixapplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import android.app.Application;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;

public class AppData extends Application{
	
	private static final String APP_ID = "applicationID";
	private static final String APP_SECRET = "applicationSecret";
	private static final String APP_ROUTE = "applicationRoute";
	private static final String PROPS_FILE = "bluelist.properties";
	
	private static final String CLASS_NAME = AppData.class
			.getSimpleName();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		// Read from properties file.
				Properties props = new java.util.Properties();
				Context context = getApplicationContext();
				try {
					AssetManager assetManager = context.getAssets();
					props.load(assetManager.open(PROPS_FILE));
					Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
				} catch (FileNotFoundException e) {
					Log.e(CLASS_NAME, "The bluelist.properties file was not found.", e);
				} catch (IOException e) {
					Log.e(CLASS_NAME,
							"The bluelist.properties file could not be read properly.", e);
				}
				// Initialize the IBM core backend-as-a-service.
				IBMBluemix.initialize(this, props.getProperty(APP_ID), props.getProperty(APP_SECRET), props.getProperty(APP_ROUTE));
				// Initialize the IBM Data Service.
				IBMData.initializeService();
				// Register the Item Specialization.
				HourItem.registerSpecialization(HourItem.class);
	}
}
