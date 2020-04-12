package fr.android.moi.app_projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordingActivity extends AppCompatActivity {
    DataBaseSQLite dataBaseSQLite;
    public ImageView checkboxP1;
    public ImageView checkboxP2;
    boolean serviceP1;

    public TextView pointsP1;
    public TextView pointsP2;
    public TextView set1P1;
    public TextView set1P2;
    public TextView set2P1;
    public TextView set2P2;
    public TextView set3P1;
    public TextView set3P2;
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
        //this.set3P1 = (TextView) findViewById(R.id.textView23);
        //this.set3P2 = (TextView) findViewById(R.id.textView23);

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
        return (true);
    }

    public boolean finish(View view) {
        //SAVE FUNCION INTO DATABASE+SQLITE
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return (true);
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
                }
                break;

            case R.id.imageView33:
                if (serviceP1 == true) {
                    checkboxP1.setImageResource(R.drawable.empty_red_box);
                    checkboxP2.setImageResource(R.drawable.red_box);
                    serviceP1 = false;
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
