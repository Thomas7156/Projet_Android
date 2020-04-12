package fr.android.moi.app_projet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

public class StatistiquesActivity extends AppCompatActivity {
    private int id;
    private String p1,p2;
    private int duration;
    private String date;
    private double latitude, longitude;
    private int p1_first, p1_second;
    private int p2_first, p2_second;
    private int p1_points, p1_sets, p1_firstball, p1_secondball, p1_aces, p1_doublefault, p1_directfouls;
    private int p2_points, p2_sets, p2_firstball, p2_secondball, p2_aces, p2_doublefault, p2_directfouls;
    private int p1_win_point,p2_win_point;
    private TextView j1_1,j1_2,j1_3,j1_4,j2_1,j2_2,j2_3,j2_4;
    private TextView title;
    private TextView duration_text, p1_points_text, p2_points_text;
    private TextView p1_first_text, p2_first_text, p1_aces_text, p2_aces_text;
    private TextView p1_double_text, p2_double_text, p1_first_win_text,p2_first_win_text, p1_second_win_text,p2_second_win_text, p1_win_text,p2_win_text,p1_fault_text,p2_fault_text;
    private int p1_first_win, p2_first_win, p1_second_win, p2_second_win;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiques);
        Bundle before = getIntent().getExtras();
        if (before!=null) {
            this.id = before.getInt("id");
            this.p1 = before.getString("p1");
            this.p2 = before.getString("p2");
            this.duration = (before.getInt("duration"));
            this.date = before.getString("date");
            this.latitude = (before.getDouble("latitude"));
            this.longitude = (before.getDouble("longitude"));
            this.p1_first = before.getInt("p1_first");
            this.p1_second = before.getInt("p1_second");
            this.p2_first = before.getInt("p2_first");
            this.p2_second = before.getInt("p2_second");
            this.p1_win_point = before.getInt("p1_points");
            this.p1_sets = before.getInt("p1_sets");
            this.p1_firstball = before.getInt("p1_firstball");
            this.p1_secondball = before.getInt("p1_secondball");
            this.p1_aces = before.getInt("p1_aces");
            this.p1_directfouls = before.getInt("p1_directfouls");
            this.p1_doublefault = before.getInt("p1_doublefault");
            this.p2_win_point = before.getInt("p2_points");
            this.p2_sets = before.getInt("p2_sets");
            this.p2_firstball = before.getInt("p2_firstball");
            this.p2_secondball = before.getInt("p2_secondball");
            this.p2_aces = before.getInt("p2_aces");
            this.p2_directfouls = before.getInt("p2_directfouls");
            this.p2_doublefault = before.getInt("p2_doublefault");
            this.j1_1 = (TextView) findViewById(R.id.joueur1_1);
            this.j1_1.setText(p1);
            this.j1_2 = (TextView) findViewById(R.id.joueur1_2);
            this.j1_2.setText(p1);
            this.j1_3 = (TextView) findViewById(R.id.joueur1_3);
            this.j1_3.setText(p1);
            this.j1_4 = (TextView) findViewById(R.id.joueur1_4);
            this.j1_4.setText(p1);
            this.j2_1 = (TextView) findViewById(R.id.joueur2_1);
            this.j2_1.setText(p2);
            this.j2_2 = (TextView) findViewById(R.id.joueur2_2);
            this.j2_2.setText(p2);
            this.j2_3 = (TextView) findViewById(R.id.joueur2_3);
            this.j2_3.setText(p2);
            this.j2_4 = (TextView) findViewById(R.id.joueur2_4);
            this.j2_4.setText(p2);
            this.title = (TextView) findViewById(R.id.title_statistiques);
            title.setText(p1 + " vs. " + p2 + " " + date + " - " + duration + " " + latitude + ", " + longitude);
            p1_points=p1_win_point + p2_directfouls;
            p2_points=p2_win_point + p1_directfouls;
            this.duration_text = (TextView) findViewById(R.id.duration_p1);
            duration_text.setText(String.valueOf(duration));
            this.p1_points_text = (TextView) findViewById(R.id.p1_points_stats);
            p1_points_text.setText(String.valueOf(p1_points));
            this.p2_points_text = (TextView) findViewById(R.id.p2_points_stats);
            p2_points_text.setText(String.valueOf(p2_points));
            this.p1_first_text = (TextView) findViewById(R.id.p1_firstball_stats);
            p1_first_text.setText(String.valueOf(p1_firstball));
            this.p1_aces_text = (TextView) findViewById(R.id.p1_aces_stats);
            p1_aces_text.setText(String.valueOf(p1_aces));
            this.p1_double_text = (TextView) findViewById(R.id.p1_doublefault_stats);
            p1_double_text.setText(String.valueOf(p1_doublefault));
            p1_first_win = (p1_firstball / p1_points)*100;
            this.p1_first_win_text = (TextView) findViewById(R.id.p1_first_win_stats);
            p1_first_win_text.setText(String.valueOf(p1_first_win +"%"));
            p1_second_win = (p1_secondball/p1_points)*100;
            this.p1_second_win_text = (TextView) findViewById(R.id.p1_second_win_stats);
            p1_second_win_text.setText(String.valueOf(p1_second_win) +"%");
            this.p1_win_text = (TextView) findViewById(R.id.p1_win_stats);
            p1_win_text.setText(String.valueOf(p1_win_point));
            this.p1_fault_text = (TextView) findViewById(R.id.p1_fault);
            p1_fault_text.setText(String.valueOf(p1_directfouls));

            this.p2_first_text = (TextView) findViewById(R.id.p2_firstball_stats);
            p2_first_text.setText(String.valueOf(p2_firstball));
            this.p2_aces_text = (TextView) findViewById(R.id.p2_aces_stats);
            p2_aces_text.setText(String.valueOf(p2_aces));
            this.p2_double_text = (TextView) findViewById(R.id.p2_doublefault_stats);
            p2_double_text.setText(String.valueOf(p2_doublefault));
            p2_first_win = (p2_firstball / p2_points)*100;
            this.p2_first_win_text = (TextView) findViewById(R.id.p2_first_win_stats);
            p2_first_win_text.setText(String.valueOf(p2_first_win +"%"));
            p2_second_win = (p2_secondball/p2_points)*100;
            this.p2_second_win_text = (TextView) findViewById(R.id.p2_second_win_stats);
            p2_second_win_text.setText(String.valueOf(p2_second_win +"%"));
            this.p2_win_text = (TextView) findViewById(R.id.p2_win_stats);
            p2_win_text.setText(String.valueOf(p2_win_point));
            this.p2_fault_text = (TextView) findViewById(R.id.p2_fault);
            p2_fault_text.setText(String.valueOf(p2_directfouls));
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
    public boolean pictures(View view)
    {
        Intent intent = new Intent(this,PicturesActivity.class);
        intent.putExtra("match_id",this.id);
        intent.putExtra("joueur1",this.p1);
        intent.putExtra("joueur2",p2);
        intent.putExtra("date",this.date);
        intent.putExtra("duration",this.duration);
        intent.putExtra("latitude",this.latitude);
        intent.putExtra("longitude",this.longitude);
        startActivity(intent);
        return (true);
    }
}
