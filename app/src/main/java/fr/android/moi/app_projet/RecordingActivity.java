package fr.android.moi.app_projet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordingActivity extends AppCompatActivity {
    private static final int TAKE_PICTURE = 1;
    private static final int CHOOSE_PICTURE = 2;
    private String photoLocation = "";

    DataBaseSQLite dataBaseSQLite;

    public ImageView checkboxP1;
    public ImageView checkboxP2;
    boolean serviceP1;
    public TextView PlayerEngaging;

    public TextView pointsP1;
    public TextView pointsP2;
    public TextView set1P1;
    public TextView set1P2;
    public TextView set2P1;
    public TextView set2P2;
    public TextView set3P1;
    public TextView set3P2;
    public TextView player1;
    public TextView player2;
    boolean set1;

    int firstBallP1cpt;
    int firstBallP2cpt;
    int secondBallP1;
    int secondBallP2;
    int doubleFaultP1;
    int doubleFaultP2;
    int aceP1cpt;
    int aceP2cpt;
    int directFaultP1cpt;
    int directFaultP2cpt;
    int provoFaultP1cpt;
    int provoFaultP2cpt;
    int pointsWinP1cpt;
    int pointsWinP2cpt;
    private String player_1_name;
    private String player_2_name;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        this.checkboxP1 = (ImageView) findViewById(R.id.imageView32);
        this.checkboxP2 = (ImageView) findViewById(R.id.imageView33);

        this.pointsP1 = (TextView) findViewById(R.id.textView16);
        this.pointsP2 = (TextView) findViewById(R.id.textView18);
        this.set1P1 = (TextView) findViewById(R.id.textView20);
        this.set1P2 = (TextView) findViewById(R.id.textView22);
        this.set2P1 = (TextView) findViewById(R.id.textView21);
        this.set2P2 = (TextView) findViewById(R.id.textView23);
        this.player1 = (TextView) findViewById(R.id.recording_joueur1);
        this.player2 = (TextView) findViewById(R.id.recording_joueur2);

        //this.set3P1 = (TextView) findViewById(R.id.textView23);
        //this.set3P2 = (TextView) findViewById(R.id.textView23);
        this.PlayerEngaging = (TextView) findViewById(R.id.textView13);

        serviceP1 = true;
        set1 = true;
        firstBallP1cpt = 0;
        firstBallP2cpt = 0;
        secondBallP1 = 0;
        secondBallP2 = 0;
        doubleFaultP1 = 0;
        doubleFaultP2 = 0;
        aceP1cpt = 0;
        aceP2cpt = 0;
        directFaultP1cpt = 0;
        directFaultP2cpt = 0;
        provoFaultP1cpt = 0;
        provoFaultP2cpt = 0;
        pointsWinP1cpt = 0;
        pointsWinP2cpt = 0;
        Bundle before = getIntent().getExtras();
        player_1_name="";
        player_2_name = "";
        latitude = 0.0;
        longitude = 0.0;
        if(before != null) {
            player_1_name = before.getString("player1");
            player_2_name = before.getString("player2");
            latitude = Double.valueOf(before.getString("latitude"));
            longitude = Double.valueOf(before.getString("longitude"));
            player1.setText(player_1_name);
            player2.setText(player_2_name);
        }
        dataBaseSQLite = new DataBaseSQLite(this);
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

    public boolean previous_games(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return (true);
    }

    public boolean picture(View view) {
        //PHOTO FUNCTION
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, TAKE_PICTURE);
        }
        return (true);
    }
    public boolean picture_add(View view)
    {
        //Picture from gallery
        Intent Gallerychoose = new Intent(Intent.ACTION_PICK);
        Gallerychoose.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        //EXTRA
        Gallerychoose.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        //STATT ACTIVITY
        startActivityForResult(Gallerychoose, CHOOSE_PICTURE);
        return (true);
    }
    public boolean finish(View view)
    {
        //*************SAVE FUNCION INTO DATABASE+SQQLITE*************//

        //Creation location in bdd

        //Creation Stats each player in bdd

        //Creation score each player in bdd

        //Creation new match in bdd


        //Returning Home page
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        return (true);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    System.out.println(selectedImage);
                    photoLocation = selectedImage.toString();
                    //addPicture
                    break;

                case TAKE_PICTURE:
                    Uri takeImage = data.getData();
                    System.out.println(takeImage);
                    //addPicture
                    break;
            }
        }
    }

    public void myClickHandler(View view) {
        //String running = String.valueOf(this.operation.getText());
        int cptPoints;

        //Condition pour passer du set 1 au set 2
        if (set1 == true) {
            int scoreP1 = Integer.parseInt(String.valueOf(set1P1.getText()));
            int scoreP2 = Integer.parseInt(String.valueOf(set1P2.getText()));

            if (((scoreP1 - scoreP2 >= 2) && scoreP1 >= 6) || ((scoreP2 - scoreP1 >= 2) && scoreP2 >= 6)) {
                set1 = false;
            }
        }

        switch (view.getId()) {
            case R.id.imageView32:
                if (serviceP1 == false) {
                    checkboxP1.setImageResource(R.drawable.red_box);
                    checkboxP2.setImageResource(R.drawable.empty_red_box);
                    serviceP1 = true;
                    this.PlayerEngaging.setText("Service : Joueur 1");
                }
                break;

            case R.id.imageView33:
                if (serviceP1 == true) {
                    checkboxP1.setImageResource(R.drawable.empty_red_box);
                    checkboxP2.setImageResource(R.drawable.red_box);
                    serviceP1 = false;
                    this.PlayerEngaging.setText("Service : Joueur 2");
                }
                break;

            case R.id.imageView34:
                if (serviceP1 == true) {
                    firstBallP1cpt += 1;
                } else {
                    firstBallP2cpt += 1;
                }
                break;

            case R.id.imageView30:
                if (serviceP1 == true) {
                    secondBallP1 += 1;
                } else {
                    secondBallP2 += 1;
                }
                break;

            case R.id.imageView29:
                if (serviceP1 == true) {
                    doubleFaultP1 += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                    cptPoints += 15;
                    this.pointsP2.setText(cptPoints);
                } else {
                    doubleFaultP2 += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                    cptPoints += 15;
                    this.pointsP1.setText(cptPoints);
                }
                break;

            case R.id.imageView31:
                if (serviceP1 == true) {
                    aceP1cpt += 1;
                    firstBallP1cpt += 1;
                    pointsWinP1cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                    cptPoints += 15;
                    this.pointsP1.setText(cptPoints);
                } else {
                    aceP2cpt += 1;
                    firstBallP2cpt += 1;
                    pointsWinP2cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                    cptPoints += 15;
                    this.pointsP2.setText(cptPoints);
                }
                break;

            case R.id.imageView28:
                if (serviceP1 == true) {
                    aceP1cpt += 1;
                    secondBallP1 += 1;
                    pointsWinP1cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                    cptPoints += 15;
                    this.pointsP1.setText(cptPoints);
                } else {
                    aceP2cpt += 1;
                    secondBallP2 += 1;
                    pointsWinP2cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                    cptPoints += 15;
                    this.pointsP2.setText(cptPoints);
                }
                break;

            case R.id.imageView22:
                pointsWinP1cpt += 1;

                cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                cptPoints += 15;
                this.pointsP1.setText(cptPoints);
                break;

            case R.id.imageView26:
                pointsWinP2cpt += 1;

                cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                cptPoints += 15;
                this.pointsP2.setText(cptPoints);
                break;

            case R.id.imageView25:
                pointsWinP2cpt += 1;
                directFaultP1cpt += 1;

                cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                cptPoints += 15;
                this.pointsP2.setText(cptPoints);
                break;

            case R.id.imageView23:
                pointsWinP1cpt += 1;
                directFaultP2cpt += 1;

                cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                cptPoints += 15;
                this.pointsP1.setText(cptPoints);
                break;

            case R.id.imageView21:
                pointsWinP1cpt += 1;
                provoFaultP1cpt += 1;

                cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                cptPoints += 15;
                this.pointsP1.setText(cptPoints);
                break;

            case R.id.imageView15:
                pointsWinP2cpt += 1;
                provoFaultP2cpt += 1;

                cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                cptPoints += 15;
                this.pointsP2.setText(cptPoints);
                break;
        }

        //Pas de gestion des avantages pour l'instant ...
        int pointsP1cpt = Integer.parseInt(String.valueOf(pointsP1.getText()));
        int pointsP2cpt = Integer.parseInt(String.valueOf(pointsP2.getText()));

        if (pointsP1cpt > 45 && pointsP2cpt <= 45) {
            this.pointsP1.setText("0");
            this.pointsP2.setText("0");

            if (set1 == true) {
                int scoreP1 = Integer.parseInt(String.valueOf(set1P1.getText()));
                scoreP1 += 1;
                this.set1P1.setText(scoreP1);
            } else {
                int scoreP1 = Integer.parseInt(String.valueOf(set2P1.getText()));
                scoreP1 += 1;
                this.set2P1.setText(scoreP1);
            }
        } else if (pointsP2cpt > 45 && pointsP1cpt <= 45) {
            this.pointsP1.setText("0");
            this.pointsP2.setText("0");

            if (set1 == true) {
                int scoreP2 = Integer.parseInt(String.valueOf(set1P2.getText()));
                scoreP2 += 1;
                this.set1P2.setText(scoreP2);
            } else {
                int scoreP2 = Integer.parseInt(String.valueOf(set2P2.getText()));
                scoreP2 += 1;
                this.set2P2.setText(scoreP2);
            }
        }
    }
}
