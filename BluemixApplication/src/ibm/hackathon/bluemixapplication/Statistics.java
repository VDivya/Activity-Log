package ibm.hackathon.bluemixapplication;
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
 
public class Statistics extends Activity {
 
    private String[] time = new String[] {
        "6am", "7am" , "8am", "9am", "10am", "11am",
        "12pm", "1pm" , "2pm", "3pm", "4pm", "5pm","6pm", "7pm" , "8pm", "9pm", "10pm", "11pm",
        "12am", "1am" , "2am", "3am", "4am", "5am"
    };
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
                openChart();
      
 
    }
 
    private void openChart(){
        int[] x = { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        int[] mScore = { 0,20,34,40,71,23,36,38,44,47,28,98,104,53,71,31,27,19,13,9,0,0,0,0};
 
        XYSeries scoreSeries = new XYSeries("Score");
        for(int i=0;i<x.length;i++){
            scoreSeries.add(i,mScore[i]);
        }
 
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(scoreSeries);
 
        XYSeriesRenderer scoreRenderer = new XYSeriesRenderer();
        scoreRenderer.setColor(Color.rgb(130, 130, 230));
        scoreRenderer.setFillPoints(true);
        scoreRenderer.setLineWidth(2);
        
        scoreRenderer.setDisplayChartValues(true);
 

 
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Time vs Score");
        multiRenderer.setXTitle("Time");
        multiRenderer.setYTitle("Score");
        multiRenderer.setBarSpacing(.5);
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.setAxisTitleTextSize(26);
		multiRenderer.setChartTitleTextSize(25);
		multiRenderer.setLabelsTextSize(25);
		multiRenderer.setLegendTextSize(25);
        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, time[i]);
        }
 
        multiRenderer.addSeriesRenderer(scoreRenderer);
 
        Intent intent = ChartFactory.getBarChartIntent(getBaseContext(), dataset, multiRenderer, Type.DEFAULT);
 
        startActivity(intent);
 
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistics, menu);
        return true;
    }
}