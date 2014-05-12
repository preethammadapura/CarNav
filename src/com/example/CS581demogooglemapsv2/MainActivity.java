package com.example.CS581demogooglemapsv2;

import org.w3c.dom.Document;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {
    private final LatLng LOCATION_START = new LatLng(41.874588,-87.651293);
    private final LatLng LOCATION_END = new LatLng(41.874758,-87.650053);
    TextView distval;
	private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        distval = (TextView)findViewById(R.id.textView1); 
    	//map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
       
       
    }

   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void onClick_StartPoint(View view){
    	//DistanceCalucator obj = new DistanceCalucator();
    	 GetRouteTask getRoute = new GetRouteTask();
         getRoute.execute();
    	
    	
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    private class GetRouteTask extends AsyncTask<String, Void, String> {
        String val = "";
        TextView setVal;
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			DistanceCalucator obj = new DistanceCalucator();
			Document doc = obj.getDocument(LOCATION_START, LOCATION_END);
	    	val = obj.getDirectionsText(doc);
            return val;
		}
		
		 @Override
         protected void onPostExecute(String result) {
			setVal = (TextView)findViewById(R.id.textView1);
			 setVal.setText(val);	 
		 }
    	
    }

}
