package ibm.hackathon.bluemixapplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

public class DemoBlumix extends Activity implements SensorEventListener{

	public static final String CLASS_NAME = "DemoBlumix";
	List<HourItem> itemList = new ArrayList<HourItem>();
	Button showData;
	TextView data;
	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	
	private long lastUpdate = 0;
	private float last_x, last_y, last_z;
	private static final int SHAKE_THRESHOLD = 400;
	private int counter = 0;
	private Handler handle;
	String viewData = "";
	long start;
	String dateD;
	String time;
	Button statistics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		statistics = (Button)findViewById(R.id.graph);
		 String[] mtime = new String[] {
			        "6am", "7am" , "8am", "9am", "10am", "11am",
			        "12pm", "1pm" , "2pm", "3pm", "4pm", "5pm","6pm", "7pm" , "8pm", "9pm", "10pm", "11pm",
			        "12am", "1am" , "2am", "3am", "4am", "5am"
			    };
		 int[] mScore = { 0,20,34,40,71,23,36,38,44,47,28,98,104,53,71,31,27,19,13,9,0,0,0,0};
		
		 String Date = "19 Nov 2014";
		 for(int i=0; i< mtime.length; i++){
				createEntry(mScore[i]+"", mtime[i]+"", Date);
			    }
	       Date date1 = new Date();
	       start = System.currentTimeMillis();
	       
	        
	       // display formatted date
	       dateD = String.format("%tB %<te, %<tY", date1);
	       GregorianCalendar gTime = new GregorianCalendar();
	       time = gTime.get(Calendar.HOUR) +"";
		final TextView sensorData = (TextView)findViewById(R.id.sensorData);
		 sensorData.setText("0");
		handle = new Handler(){
			
			 @Override
		      public void handleMessage(Message msg) {
		        
				 sensorData.setText(msg.what+"");
		      }
		};
		senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
		/*String points = "10";
		String hour = "2";
		String date = "11-20-2014";*/
	   
		showData = (Button) findViewById(R.id.getData);
		data = (TextView) findViewById(R.id.data);
		showData();
		showData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				data.setText(viewData);
			}
		});
		statistics.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent i = new Intent(DemoBlumix.this, Statistics.class);
			startActivity(i);
			}
		});

	}

	private void createEntry(String points, String hour, String date) {

		HourItem item = new HourItem();
		if (!points.equals("")) {
			item.setPoints(points);
			item.setDate(date);
			item.setHour(hour);
			// Use the IBMDataObject to create and persist the Item object.
			item.save().continueWith(new Continuation<IBMDataObject, Void>() {

				@Override
				public Void then(Task<IBMDataObject> task) throws Exception {
					// Log if the save was cancelled.
					if (task.isCancelled()) {
						Log.e(CLASS_NAME, "Exception : Task " + task.toString()
								+ " was cancelled.");
					}
					// Log error message, if the save task fails.
					else if (task.isFaulted()) {
						Log.e(CLASS_NAME, "Exception : "
								+ task.getError().getMessage());
					}

					// If the result succeeds, load the list.
					else {
						// listItems();
						Log.e(CLASS_NAME, "Success : ");
					}
					return null;
				}

			});

			// Set text field back to empty after item is added.

		}

	}

	private void showData() {
		try {
			IBMQuery<HourItem> query = IBMQuery.queryForClass(HourItem.class);
			// Query all the Item objects from the server.
			query.find().continueWith(new Continuation<List<HourItem>, Void>() {

				@Override
				public Void then(Task<List<HourItem>> task) throws Exception {
					final List<HourItem> objects = task.getResult();
					// Log if the find was cancelled.
					if (task.isCancelled()) {
						Log.e(CLASS_NAME, "Exception : Task " + task.toString()
								+ " was cancelled.");
					}
					// Log error message, if the find task fails.
					else if (task.isFaulted()) {
						Log.e(CLASS_NAME, "Exception : "
								+ task.getError().getMessage());
					}

					// If the result succeeds, load the list.
					else {
						// Clear local itemList.
						// We'll be reordering and repopulating from
						// DataService.
						itemList.clear();
						for (IBMDataObject item : objects) {
							itemList.add((HourItem) item);
						}
						
							for (HourItem hItem : itemList) {
								viewData = viewData+hItem.getDate() +"          " + hItem.getHour() + "          " + hItem.getPoints()
										+ "\n";
							}
						
						
					}
					return null;
				}
			}, Task.UI_THREAD_EXECUTOR);

		} catch (IBMDataException error) {
			Log.e(CLASS_NAME, "Exception : " + error.getMessage());
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		 senSensorManager.unregisterListener(this);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		 senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		Sensor mySensor = sensorEvent.sensor;
		 
	    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	    	float x = sensorEvent.values[0];
	        float y = sensorEvent.values[1];
	        float z = sensorEvent.values[2];
	 
	        long curTime = System.currentTimeMillis();
	 
	        if ((curTime - lastUpdate) > 100) {
	            long diffTime = (curTime - lastUpdate);
	            lastUpdate = curTime;
	 
	            float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;
	 
	            if (speed > SHAKE_THRESHOLD) {
	            	counter++;
	            	handle.sendEmptyMessage(counter);
	            	
	            }
	 
	            last_x = x;
	            last_y = y;
	            last_z = z;
	        }
	         
	    }
		
	}

}
