package fr.android.moi.app_projet;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Database
    DataBaseSQLite dataBaseSQLite;
    private int nombre_match;
    private int buffer_id_rect, buffer_id_text_players, getBuffer_id_text_infos, buffer_id_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre_match = 0;
        buffer_id_rect = 0;
        buffer_id_text_players = 0;
        getBuffer_id_text_infos = 0;
        buffer_id_delete = 0;
        dataBaseSQLite = new DataBaseSQLite(this);
        //Search all saved matches
        //For each one create a New ImageView, Text Description and Button Delete + Function to see previous game (with intent putExtra) and Function to delete the game
        //Need to retrieve every info
        //Ex : new_match_id : match_database_id;
        //Pour chaque match
        final Match new_match = new Match();
        //Ajouter les infos
        // ex : new_match.id = id_database;
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout_content_main);
        ImageView rect = new ImageView(this);
        rect.setImageResource(R.drawable.match_precedent_contour);
        //On définit l'affichage du rect
        rect.setId(View.generateViewId());
        rect.setContentDescription("@string/border_match");
        rect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fonction pour ajouter un match
                previous_game(view, new_match);
            }
        });
        //LAYOUT POUR LE RECTANGLE
        ConstraintLayout.LayoutParams prams_rect = new ConstraintLayout.LayoutParams(rect.getWidth(), rect.getHeight());
        ConstraintLayout layout_rect = new ConstraintLayout(this);
        layout_rect.setLayoutParams(prams_rect);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout_rect);
        //LAYOUT SUR LES COTES
        constraintSet.connect(rect.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START, 0);
        constraintSet.connect(rect.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END, 0);
        //LAYOUT : DERNIER ELEMENT
        constraintSet.connect(rect.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 0);
        constraintSet.setHorizontalBias(rect.getId(), (float) 0.498);
        constraintSet.applyTo(layout_rect);
        buffer_id_rect = rect.getId();
        //TEXT VIEW PLAYERS
        TextView players = new TextView(this);
        players.setText(new_match.p1 + " VS " + new_match.p2);
        //LAYOUT POUR LE TEXT VIEW PLAYERS
        ///TEXT VIEW INFOS
        TextView infos = new TextView(MainActivity.this);
        infos.setText(new_match.date + " " + new_match.duration + " " + new_match.latitude + ", " + new_match.longitude);
        //LAYOUT POUR LE TEXT VIEW INFOS
        ///DELETE BOUTON ADD
        ImageView delete = new ImageView(this);
        delete.setImageResource(R.drawable.accueil_delete_match);
        //On définit l'affichage du rect
        delete.setId(View.generateViewId());
        delete.setContentDescription("@string/delete");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fonction pour ajouter un match
                supprimer_match(new_match);
            }
        });

        //LAYOUT POUR LE DELETE BUTTON
        ConstraintLayout.LayoutParams prams_delete = new ConstraintLayout.LayoutParams(delete.getWidth(), delete.getHeight());
        ConstraintLayout layout_delete = new ConstraintLayout(this);
        layout_delete.setLayoutParams(prams_delete);
        ConstraintSet constraintSet_delete = new ConstraintSet();
        constraintSet.clone(layout_delete);
        //LAYOUT SUR LES COTES
        constraintSet.connect(delete.getId(), ConstraintSet.START, rect.getId(), ConstraintSet.START, 312);
        constraintSet.connect(delete.getId(), ConstraintSet.END, rect.getId(), ConstraintSet.END, 48);
        //LAYOUT : DERNIER ELEMENT
        constraintSet.connect(delete.getId(), ConstraintSet.TOP, rect.getId(), ConstraintSet.TOP, 54);
        constraintSet.connect(delete.getId(), ConstraintSet.BOTTOM, rect.getId(), ConstraintSet.BOTTOM, 66);
        constraintSet.applyTo(layout_delete);
        buffer_id_delete = delete.getId();
        nombre_match++;
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

    public boolean new_game(View view) {
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
        return (true);
    }

    public boolean menu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        return (true);
    }

    public boolean previous_game(View view, Match new_match) {
        Intent intent = new Intent(this, StatistiquesActivity.class);
        //On transmet les infos du match correspondants
        intent.putExtra("id", new_match.id);
        intent.putExtra("p1", new_match.p1);
        intent.putExtra("p2", new_match.p2);
        intent.putExtra("duration", new_match.duration);
        intent.putExtra("date", new_match.date);
        intent.putExtra("latitude", new_match.latitude);
        intent.putExtra("longitude", new_match.longitude);
        intent.putExtra("p1_first", new_match.p1_first);
        intent.putExtra("p1_second", new_match.p1_second);
        intent.putExtra("p1_third", new_match.p1_third);
        intent.putExtra("p1_forth", new_match.p1_forth);
        intent.putExtra("p1_fifth", new_match.p1_fifth);
        intent.putExtra("p2_first", new_match.p2_first);
        intent.putExtra("p2_second", new_match.p2_second);
        intent.putExtra("p2_third", new_match.p2_third);
        intent.putExtra("p2_forth", new_match.p2_forth);
        intent.putExtra("p2_fifth", new_match.p2_fifth);
        intent.putExtra("p1_points", new_match.p1_points);
        intent.putExtra("p1_sets", new_match.p1_sets);
        intent.putExtra("p1_firstball", new_match.p1_firstball);
        intent.putExtra("p1_secondball", new_match.p1_secondball);
        intent.putExtra("p1_aces", new_match.p1_aces);
        intent.putExtra("p1_directfouls", new_match.p1_directfouls);
        intent.putExtra("p2_points", new_match.p2_points);
        intent.putExtra("p2_sets", new_match.p2_sets);
        intent.putExtra("p2_firstball", new_match.p2_firstball);
        intent.putExtra("p2_secondball", new_match.p2_secondball);
        intent.putExtra("p2_aces", new_match.p2_aces);
        intent.putExtra("p2_directfouls", new_match.p2_directfouls);
        startActivity(intent);
        return (true);
    }

    public class Match {
        public int id;
        public String p1, p2;
        public int duration;
        public String date;
        public double latitude, longitude;
        public int p1_first, p1_second, p1_third, p1_forth, p1_fifth;
        public int p2_first, p2_second, p2_third, p2_forth, p2_fifth;
        public int p1_points, p1_sets, p1_firstball, p1_secondball, p1_aces, p1_directfouls;
        public int p2_points, p2_sets, p2_firstball, p2_secondball, p2_aces, p2_directfouls;
    }

    public boolean supprimer_match(Match deleted_match) {
        //supprimer matchs
        dataBaseSQLite.deleteMatch(deleted_match.id);

        //Recharger le layout
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return (true);
    }
}
