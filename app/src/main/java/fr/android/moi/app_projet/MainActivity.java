    package fr.android.moi.app_projet;

    import android.content.Intent;
    import android.content.res.Resources;
    import android.database.Cursor;
    import android.graphics.Color;
    import android.os.Bundle;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.constraintlayout.widget.ConstraintLayout;
    import androidx.constraintlayout.widget.ConstraintSet;

    import android.util.Log;
    import android.util.TypedValue;
    import android.view.View;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.ImageView;
    import android.widget.TextView;

    import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {
    // Database
    DataBaseSQLite dataBaseSQLite;
    private int nombre_match;
    private int buffer_id_rect, buffer_id_text_players, getBuffer_id_text_infos, buffer_id_delete;
    public ArrayList<Match> AllMatchs = new ArrayList<>();


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
        LoadLastMatchs();
        Log.e("ombre match", String.valueOf(nombre_match));
        Log.e("liste machts", String.valueOf(AllMatchs.size()));

        /*final Match new_match = new Match();
        new_match.id = 1;
        new_match.p1 = "Tom";
        new_match.p2 = "Thomas";
        new_match.duration = 2;
        new_match.date = "30/04/2019";
        new_match.latitude = 4000.2;
        new_match.longitude = 200.3;
        new_match.p1_first = 2;
        new_match.p1_second = 2;
        new_match.p2_first = 2;
        new_match.p2_second = 2;
        new_match.p1_points = 2;
        new_match.p1_doubleFaults = 2;
        new_match.p1_firstball = 2;
        new_match.p1_secondball = 2;
        new_match.p1_aces = 2;
        new_match.p1_directfouls = 2;
        new_match.p2_points = 2;
        new_match.p2_doubleFaults = 2;
        new_match.p2_firstball = 2;
        new_match.p2_secondball = 2;
        new_match.p2_aces = 2;
        new_match.p2_directfouls = 2;
        new_match.p1_doublefault = 2;
        new_match.p2_doublefault = 2; */

        //Ajouter les infos
        // ex : new_match.id = id_database;
    }

    public void LoadLastMatchs() {
        Cursor elem = dataBaseSQLite.getAllMatchs();
        elem.moveToFirst();

        for (int i = 0; i < elem.getCount(); i++) {
            final Match new_match = new Match(elem);
            AllMatchs.add(new_match);

            //Ajouter les infos
            // ex : new_match.id = id_database;
            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout_content_main);
            Log.e("VERIF LAYOUT", layout.toString());
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
            ConstraintLayout.LayoutParams prams_rect = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            //LAYOUT : DERNIER ELEMENT
            prams_rect.startToStart = layout.getId();
            prams_rect.endToEnd = layout.getId();
            //dernier élément ajouté (utilisé buffer_id_rect)
            if(nombre_match==0) {
                prams_rect.topToBottom = R.id.imageView2;
            }
            else
            {
                prams_rect.topToBottom = buffer_id_rect;
            }
            prams_rect.horizontalBias = (float) 0.498;
            layout.addView(rect, -10000, prams_rect);
            buffer_id_rect = rect.getId();

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
            ConstraintLayout.LayoutParams prams_delete = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            //LAYOUT SUR LES COTES

            //LAYOUT : DERNIER ELEMENT
            float density = this.getResources().getDisplayMetrics().density;
            prams_delete.startToStart = buffer_id_rect;
            prams_delete.endToEnd = buffer_id_rect;
            prams_delete.topToTop = buffer_id_rect;
            prams_delete.bottomToBottom = buffer_id_rect;
            prams_delete.setMarginStart((int) (312 * density));
            prams_delete.setMarginEnd((int) (48 * density));
            prams_delete.leftMargin = (int) (312 * density);
            prams_delete.rightMargin = (int) (int) (48 * density);
            prams_delete.bottomMargin = (int) (66 * density);
            prams_delete.topMargin = (int) (54 * density);
            layout.addView(delete, -9999, prams_delete);
            buffer_id_delete = delete.getId();

            //TEXT VIEW PLAYERS
            TextView players = new TextView(this);
            players.setText(new_match.p1 + " VS " + new_match.p2 + " - " +new_match.date);
            players.setId(View.generateViewId());
            players.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            players.setTextColor(Color.parseColor("#30344B"));
            players.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Fonction pour ajouter un match
                    previous_game(view, new_match);
                }
            });

            //LAYOUT POUR LE TEXT VIEW PLAYERS
            ConstraintLayout.LayoutParams prams_players = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            //LAYOUT SUR LES COTES
            //LAYOUT : DERNIER ELEMENT
            prams_players.startToStart = buffer_id_rect;
            prams_players.setMarginStart((int) (53 * density));
            prams_players.topMargin = (int) (52 * density);
            prams_players.topToTop = buffer_id_rect;
            layout.addView(players, -9998, prams_players);
            buffer_id_text_players = players.getId();

            ///TEXT VIEW INFOS
            TextView infos = new TextView(MainActivity.this);
            long minutes = ((long)new_match.duration / 1000) / 60;
            long seconds = ((long)new_match.duration / 1000) % 60;
            if(new_match.duration<60000) {
                infos.setText(seconds + "s" +" - GPS : " + new_match.latitude + ", " + new_match.longitude);
            }
            else
            {
                infos.setText(minutes + "m"+ seconds +"s" + " - GPS : " + new_match.latitude + ", " + new_match.longitude);
            }
            infos.setTextColor(Color.parseColor("#707070"));
            infos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            //LAYOUT POUR LE TEXT VIEW INFOS
            ConstraintLayout.LayoutParams prams_infos = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            //LAYOUT SUR LES COTES
            //LAYOUT : DERNIER ELEMENT
            prams_infos.horizontalBias = (float) 0.0;
            prams_infos.verticalBias = (float) 0.0;
            prams_infos.bottomToBottom = buffer_id_rect;
            prams_infos.topToBottom = buffer_id_text_players;
            prams_infos.topMargin = (int) (8 * density);
            prams_infos.startToStart = buffer_id_text_players;
            layout.addView(infos, -9997, prams_infos);
            getBuffer_id_text_infos = infos.getId();
    //        layout.addView(players);
    //        layout.addView(delete);
            //      layout.addView(layout_players);
            //    layout.addView(layout_infos);
            nombre_match++;
            Log.e("verif", "OKKKK");

            elem.moveToNext();
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
        intent.putExtra("p2_first", new_match.p2_first);
        intent.putExtra("p2_second", new_match.p2_second);
        intent.putExtra("p1_points", new_match.p1_points);
        intent.putExtra("p1_doubleFaults", new_match.p1_doubleFaults);
        intent.putExtra("p1_firstball", new_match.p1_firstball);
        intent.putExtra("p1_secondball", new_match.p1_secondball);
        intent.putExtra("p1_aces", new_match.p1_aces);
        intent.putExtra("p1_double_fault", new_match.p1_doubleFaults);
        intent.putExtra("p1_directfouls", new_match.p1_directfouls);
        intent.putExtra("p2_points", new_match.p2_points);
        intent.putExtra("p2_doubleFaults", new_match.p2_doubleFaults);
        intent.putExtra("p2_firstball", new_match.p2_firstball);
        intent.putExtra("p2_secondball", new_match.p2_secondball);
        intent.putExtra("p2_aces", new_match.p2_aces);
        intent.putExtra("p2_double_fault", new_match.p2_doubleFaults);
        intent.putExtra("p2_directfouls", new_match.p2_directfouls);
        startActivity(intent);
        return (true);
    }

    public class Match {
        public int id;
        public String p1, p2;
        public int duration;
        public String date;
        public String latitude, longitude;
        public int p1_first, p1_second;
        public int p2_first, p2_second;
        public int p1_points, p1_firstball, p1_secondball, p1_aces, p1_doubleFaults, p1_directfouls;
        public int p2_points, p2_firstball, p2_secondball, p2_aces, p2_doubleFaults, p2_directfouls;

        public Match(Cursor elem) {
            id = elem.getInt(0);
            p1 = elem.getString(1);
            p2 = elem.getString(2);
            duration = elem.getInt(3);
            date = elem.getString(4);

            Cursor location = dataBaseSQLite.getLocationByID(elem.getInt(5));
            location.moveToFirst();
            latitude = location.getString(1);
            longitude = location.getString(2);

            Cursor scoreP1 = dataBaseSQLite.getScoreByID(elem.getInt(6));
            scoreP1.moveToFirst();
            p1_first = scoreP1.getInt(1);
            p1_second = scoreP1.getInt(2);

            Cursor scoreP2 = dataBaseSQLite.getScoreByID(elem.getInt(7));
            scoreP2.moveToFirst();
            p2_first = scoreP2.getInt(1);
            p2_second = scoreP2.getInt(2);

            Cursor statsP1 = dataBaseSQLite.getStatisticsByID(elem.getInt(8));
            statsP1.moveToFirst();
            p1_points = statsP1.getInt(1);
            p1_firstball = statsP1.getInt(2);
            p1_secondball = statsP1.getInt(3);
            p1_aces = statsP1.getInt(4);
            p1_doubleFaults = statsP1.getInt(5);
            p1_directfouls = statsP1.getInt(6);

            Cursor statsP2 = dataBaseSQLite.getStatisticsByID(elem.getInt(9));
            statsP2.moveToFirst();
            p2_points = statsP2.getInt(1);
            p2_firstball = statsP2.getInt(2);
            p2_secondball = statsP2.getInt(3);
            p2_aces = statsP2.getInt(4);
            p2_doubleFaults = statsP2.getInt(5);
            p2_directfouls = statsP2.getInt(6);
        }
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
