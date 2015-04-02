package edu.augustana.csc490.mapgame;

import android.app.Activity;
import android.app.FragmentManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.location.Address;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;


public class MainActivity extends Activity implements OnStreetViewPanoramaReadyCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.streetview);

        StreetViewPanoramaFragment panoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        panoramaFragment.getStreetViewPanoramaAsync(this);

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama){
        panorama.setPosition(playRound(), 5000);
        panorama.setStreetNamesEnabled(false);
    }

    public LatLng playRound(){

        Random r = new Random();

        int zipcode = 0;

        while(zipcode<90001 || zipcode>96162){
            zipcode = r.nextInt(99999);
        }

        String stringZipCode = String.valueOf(zipcode);

        return getLocationFromZipCode(stringZipCode);

    }

    // code from stackoverflow.com/questions/3641304/get-latitude-and-longitude-using-zipcode
    public LatLng getLocationFromZipCode(String zipCode){

        final Geocoder geocoder = new Geocoder(this);

        Log.w("geocoder zipcode input", zipCode);

        LatLng location = new LatLng(41.507844, -90.514576);

        try{
            List<Address> addresses = geocoder.getFromLocationName(zipCode,1);
            if(addresses != null && !addresses.isEmpty()){
                Address address = addresses.get(0);

                location = new LatLng(address.getLatitude(),address.getLongitude());
            }else{
                Toast.makeText(this, "Geocode from zip error", Toast.LENGTH_LONG).show();
            }
        }catch(IOException e){

        }
        return location;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
