    package fr.android.moi.app_projet;

    import android.app.Activity;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.os.Environment;
    import android.os.StrictMode;
    import android.os.SystemClock;
    import android.provider.MediaStore;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Chronometer;
    import android.widget.ImageView;
    import android.widget.TextView;

    import org.w3c.dom.Text;

    import androidx.appcompat.app.AppCompatActivity;

    import java.io.File;
    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    //Code from https://stackoverflow.com/questions/41777836/using-camera-to-take-photo-and-save-to-gallery

    public class RecordingActivity extends AppCompatActivity {
        private static final int TAKE_PICTURE = 1;
        private static final int CHOOSE_PICTURE = 2;
        public ArrayList<String> photoLocation = new ArrayList<>();

        public Chronometer chrono;
        public String date;
        private int number_pictures;
        private String photo_take_path;

        public DataBaseSQLite dataBaseSQLite;

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

        int scoreSet1P1;
        int scoreSet1P2;
        int scoreSet2P1;
        int scoreSet2P2;
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
        public String player_1_name;
        public String player_2_name;
        public String latitude;
        public String longitude;
        private TextView n_pictures;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recording);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            number_pictures = 0;

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

            this.pointsP1.setText("0");
            this.pointsP2.setText("0");
            this.set1P1.setText("0");
            this.set1P2.setText("0");
            this.set2P1.setText("0");
            this.set2P2.setText("0");

            //this.set3P1 = (TextView) findViewById(R.id.textView23);
            //this.set3P2 = (TextView) findViewById(R.id.textView23);
            this.PlayerEngaging = (TextView) findViewById(R.id.textView13);

            chrono = new Chronometer(this);

            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            date = df.format(currentTime);

            serviceP1 = true;
            set1 = true;
            scoreSet1P1 = 0;
            scoreSet1P2 = 0;
            scoreSet2P1 = 0;
            scoreSet2P2 = 0;
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
            player_1_name = "";
            player_2_name = "";
            latitude = "";
            longitude = "";
            if (before != null) {
                player_1_name = before.getString("player1");
                player_2_name = before.getString("player2");
                latitude = before.getString("latitude");
                longitude = before.getString("longitude");
                player1.setText(player_1_name);
                player2.setText(player_2_name);
                this.PlayerEngaging.setText("SERVICE : "+ player_1_name);
            }

            dataBaseSQLite = new DataBaseSQLite(this);
            n_pictures = ((TextView) findViewById(R.id.number_pictures_recording));
            n_pictures.setText(" "+number_pictures);

            new startChrono().execute();
        }

        private class startChrono extends AsyncTask<String, String, Integer> {
            protected Integer doInBackground(String... rslt) {

                chrono.start();
                chrono.setBase(SystemClock.elapsedRealtime());

                return 0;
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

        /*public boolean previous_games(View view) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return (true);
        }*/

        public boolean picture(View view) {
            //PHOTO FUNCTION
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, TAKE_PICTURE);
                }
            }
            return (true);
        }

        public boolean picture_add(View view) {
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

        public boolean finish(View view) {
            new saveDataOnBDD().execute();

            //Returning Home page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return (true);
        }

        private class saveDataOnBDD extends AsyncTask<String, String, Integer> {
            protected Integer doInBackground(String... rslt) {

                //Stop and get the duration
                long tmp = chrono.getBase();
                chrono.stop();
                String duration = String.valueOf(SystemClock.elapsedRealtime() - tmp);

                //*************SAVE FUNCION INTO DATABASE+SQQLITE*************//

                //Creation location in bdd
                dataBaseSQLite.addLocation(latitude, longitude);
                int idLocationMatch = dataBaseSQLite.getIDLastLocation();

                //Creation Stats each player in bdd
                dataBaseSQLite.addStatistics(pointsWinP1cpt, aceP1cpt, firstBallP1cpt, secondBallP1, directFaultP1cpt, doubleFaultP1);
                int idStatsP1 = dataBaseSQLite.getIDLastStats();

                dataBaseSQLite.addStatistics(pointsWinP2cpt, aceP2cpt, firstBallP2cpt, secondBallP2, directFaultP2cpt, doubleFaultP2);
                int idStatsP2 = dataBaseSQLite.getIDLastStats();

                //Creation score each player in bdd
                dataBaseSQLite.addScore(scoreSet1P1, scoreSet2P1);
                int idScoreP1 = dataBaseSQLite.getIDLastScore();

                dataBaseSQLite.addScore(scoreSet1P2, scoreSet2P2);
                int idScoreP2 = dataBaseSQLite.getIDLastScore();

                //Creation new match in bdd
                dataBaseSQLite.addMatch(player_1_name, player_2_name, duration, date, idLocationMatch, idScoreP1, idScoreP2, idStatsP1, idStatsP2);
                int idMatch = dataBaseSQLite.getIDLastMatch();

                //Add pictures of the match
                //FAIRE LA BOUCLE FOR QUI PARCOURE TOUTES LES PHOTOS ET LES AJOUTER UNE PAR UNE
                for(int i = 0; i < photoLocation.size(); i++){
                    dataBaseSQLite.addPicture(photoLocation.get(i), idMatch);
                }

                return 0;
            }
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case CHOOSE_PICTURE:
                        //data.getData returns the content URI for the selected Image
                        Uri selectedImage = data.getData();

                        //addPicture
                        photoLocation.add(selectedImage.toString());
                        number_pictures++;
                        n_pictures.setText(" "+number_pictures);
                        break;

                    case TAKE_PICTURE:
                        galleryAddPic();
                        number_pictures++;
                        n_pictures.setText(" "+number_pictures);
                        photoLocation.add(photo_take_path);
                        //addPicture
                        //photoLocation.add(takeImage.toString());
                        break;
                }
            }
        }
        private File createImageFile() throws IOException {
            File storageDir = Environment.getExternalStorageDirectory();
            File image = File.createTempFile(
                    "example",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            photo_take_path = image.getAbsolutePath();
            return image;
        }


        private void galleryAddPic() {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(photo_take_path);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        }
        public boolean previous_games(View view)
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            return (true);
        }
        public void myClickHandler(View view) {
            int cptPoints;

            //Condition pour passer du set 1 au set 2
            if (set1) {
                if (((scoreSet1P1 - scoreSet1P2 >= 2) && scoreSet1P1 >= 6) || ((scoreSet1P2 - scoreSet1P1 >= 2) && scoreSet1P2 >= 6)) {
                    set1 = false;
                }
            }

            switch (view.getId()) {
                case R.id.imageView32:
                    if (!serviceP1) {
                        checkboxP1.setImageResource(R.drawable.red_box);
                        checkboxP2.setImageResource(R.drawable.empty_red_box);
                        serviceP1 = true;
                        this.PlayerEngaging.setText("SERVICE : "+ player_1_name);
                    }
                    break;

                case R.id.imageView33:
                    if (serviceP1) {
                        checkboxP1.setImageResource(R.drawable.empty_red_box);
                        checkboxP2.setImageResource(R.drawable.red_box);
                        serviceP1 = false;
                        this.PlayerEngaging.setText("SERVICE : "+ player_2_name);
                    }
                    break;

                case R.id.imageView34:
                    if (serviceP1) {
                        firstBallP1cpt += 1;
                    } else {
                        firstBallP2cpt += 1;
                    }
                    break;

                case R.id.imageView30:
                    if (serviceP1) {
                        secondBallP1 += 1;
                    } else {
                        secondBallP2 += 1;
                    }
                    break;

                case R.id.imageView29:
                    if (serviceP1) {
                        doubleFaultP1 += 1;

                        cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                        cptPoints += 15;
                        this.pointsP2.setText(String.valueOf(cptPoints));
                    } else {
                        doubleFaultP2 += 1;

                        cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                        cptPoints += 15;
                        this.pointsP1.setText(String.valueOf(cptPoints));
                    }
                    break;

                case R.id.imageView31:
                    if (serviceP1) {
                        aceP1cpt += 1;
                        firstBallP1cpt += 1;
                        pointsWinP1cpt += 1;

                        cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                        cptPoints += 15;
                        this.pointsP1.setText(String.valueOf(cptPoints));
                    } else {
                        aceP2cpt += 1;
                        firstBallP2cpt += 1;
                        pointsWinP2cpt += 1;

                        cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                        cptPoints += 15;
                        this.pointsP2.setText(String.valueOf(cptPoints));
                    }
                    break;

                case R.id.imageView28:
                    if (serviceP1) {
                        aceP1cpt += 1;
                        secondBallP1 += 1;
                        pointsWinP1cpt += 1;

                        cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                        cptPoints += 15;
                        this.pointsP1.setText(String.valueOf(cptPoints));
                    } else {
                        aceP2cpt += 1;
                        secondBallP2 += 1;
                        pointsWinP2cpt += 1;

                        cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                        cptPoints += 15;
                        this.pointsP2.setText(String.valueOf(cptPoints));
                    }
                    break;

                case R.id.imageView22:
                    pointsWinP1cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                    cptPoints += 15;
                    this.pointsP1.setText(String.valueOf(cptPoints));
                    break;

                case R.id.imageView26:
                    pointsWinP2cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                    cptPoints += 15;
                    this.pointsP2.setText(String.valueOf(cptPoints));
                    break;

                case R.id.imageView25:
                    pointsWinP2cpt += 1;
                    directFaultP1cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                    cptPoints += 15;
                    this.pointsP2.setText(String.valueOf(cptPoints));
                    break;

                case R.id.imageView23:
                    pointsWinP1cpt += 1;
                    directFaultP2cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                    cptPoints += 15;
                    this.pointsP1.setText(String.valueOf(cptPoints));
                    break;

                case R.id.imageView21:
                    pointsWinP1cpt += 1;
                    provoFaultP1cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP1.getText()));
                    cptPoints += 15;
                    this.pointsP1.setText(String.valueOf(cptPoints));
                    break;

                case R.id.imageView15:
                    pointsWinP2cpt += 1;
                    provoFaultP2cpt += 1;

                    cptPoints = Integer.parseInt(String.valueOf(pointsP2.getText()));
                    cptPoints += 15;
                    this.pointsP2.setText(String.valueOf(cptPoints));
                    break;
            }

            //Pas de gestion des avantages pour l'instant ...
            int pointsP1cpt = Integer.parseInt(String.valueOf(pointsP1.getText()));
            int pointsP2cpt = Integer.parseInt(String.valueOf(pointsP2.getText()));

            if (pointsP1cpt > 45 && pointsP2cpt <= 45) {
                this.pointsP1.setText("0");
                this.pointsP2.setText("0");

                if (set1) {
                    scoreSet1P1 += 1;
                    this.set1P1.setText(String.valueOf(scoreSet1P1));
                } else {
                    scoreSet2P1 += 1;
                    this.set2P1.setText(String.valueOf(scoreSet2P1));
                }
            } else if (pointsP2cpt > 45 && pointsP1cpt <= 45) {
                this.pointsP1.setText("0");
                this.pointsP2.setText("0");

                if (set1) {
                    scoreSet1P2 += 1;
                    this.set1P2.setText(String.valueOf(scoreSet1P2));
                } else {
                    scoreSet2P2 += 1;
                    this.set2P2.setText(String.valueOf(scoreSet2P2));
                }
            }

            //End game detection
            if (((scoreSet2P1 - scoreSet2P2 >= 2) && scoreSet2P1 >= 6) || ((scoreSet2P2 - scoreSet2P1 >= 2) && scoreSet2P2 >= 6)) {
                this.finish(null);
            }
        }
    }
