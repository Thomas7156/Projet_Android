package fr.android.moi.app_projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PicturesActivity extends AppCompatActivity {
    private int id,duration;
    private String p1,p2,date;
    private Double latitude,longitude;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        Bundle before = getIntent().getExtras();
        if (before!=null)
        {
            id = before.getInt("match_id");
            p1 = before.getString("joueur1");
            p2 = before.getString("joueur2");
            date = before.getString("date");
            duration = before.getInt("duration");
            latitude = before.getDouble("latitude");
            longitude = before.getDouble("longitude");
            this.title = (TextView) findViewById(R.id.title_pictures);
            title.setText(p1 + " vs. " + p2 + " " + date + " - " + duration + " " + latitude + ", " + longitude);
        }
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
    public boolean previous_games(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        return (true);
    }
}
