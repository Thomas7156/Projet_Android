package fr.android.moi.app_projet;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class NewActivity extends AppCompatActivity implements LocationListener {
    private String latitude;
    private String longitude;
    private LocationManager locationManager;
    private String provider;
    private TextView latEtLong;
    private EditText player1, player2;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        this.latEtLong = (TextView) findViewById(R.id.localisation_text);
        this.player1 = (EditText) findViewById(R.id.text_player1);
        this.player2 = (EditText) findViewById(R.id.text_player2);
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria,false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        System.out.println("onCreate");
        System.out.println(provider);
        System.out.println(locationManager.getLastKnownLocation(provider));
        System.out.println("onCreate ended");
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
            System.out.println("location selected ");
        } else {
            latitude = null;
            longitude = null;
            latEtLong.setText("NEED A LOCALISATION");
            System.out.println("Location not available onCreate");
        }

    }
    @Override
    public void onLocationChanged(Location location_s) {
        double lat = (double) (location_s.getLatitude());
        double lng = (double) (location_s.getLongitude());
        this.latitude = String.valueOf(lat);
        this.longitude = String.valueOf(lng);
        latEtLong.setText(latitude+","+longitude);
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
    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();

        this.latEtLong = (TextView) findViewById(R.id.localisation_text);
        this.player1 = (EditText) findViewById(R.id.text_player1);
        this.player2 = (EditText) findViewById(R.id.text_player2);
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria,false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /*requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("requestPermissions onResume");*/
            return;
        }
            locationManager.requestLocationUpdates(provider,400,1,(LocationListener) this);
            System.out.println(provider);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
    public boolean previous_games(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        return (true);
    }
    public boolean start(View view)
    {
        //GET INTENT EXTRA HERE

        if(player1.getText().toString().equals("") || player1.getText().toString().equals(null))
        {
            player1.setText("NEED A PLAYER 1");
            System.out.println("ERROR 1");
        }
        if(player2.getText().toString().equals("") || player2.getText().toString().equals(null))
        {
            player2.setText("NEED A PLAYER 2");
            System.out.println("ERROR 2");
        }
        if(latEtLong.getText().toString().equals("") || latEtLong.getText().toString().equals(null))
        {
            latEtLong.setText("NEED A LOCALISATION");
            System.out.println("ERROR LOCALISATION");
        }

        System.out.println(player1.getText().toString());
        System.out.println(player2.getText().toString());
        System.out.println(latEtLong.getText().toString());


        Log.e("player1",player1.getText().toString());
        Log.e("player2",player2.getText().toString());
        Log.e("localisation",latEtLong.getText().toString());
        String player1_text=player1.getText().toString();
        String player2_text=player2.getText().toString();
        String localisation_text= latEtLong.getText().toString();

        if(!player1_text.equals("") && !player1_text.equals("NEED A PLAYER 1") && !player2_text.equals("")&& !player2_text.equals("NEED A PLAYER 2") && !localisation_text.equals("NEED A LOCALISATION") && !localisation_text.equals("") ) {


            Intent intent = new Intent(this,RecordingActivity.class);
            String player_1_name = player1.getText().toString();
            String player_2_name = player2.getText().toString();
            String localisation = latEtLong.getText().toString();
            intent.putExtra("localisation", localisation);
            intent.putExtra("player1", player_1_name);
            intent.putExtra("player2", player_2_name);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            System.out.println("FAILED OK");
            startActivity(intent);
        }
        else {
            System.out.println("FAILED START");
        }
        return (true);
    }
}
