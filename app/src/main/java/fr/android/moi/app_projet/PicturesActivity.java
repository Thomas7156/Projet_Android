package fr.android.moi.app_projet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class PicturesActivity extends AppCompatActivity {
    private int id,duration;
    private String p1,p2,date;
    private Double latitude,longitude;
    private TextView title;
    DataBaseSQLite dataBaseSQLite;
    private static final int TAKE_PICTURE = 1;
    private static final int CHOOSE_PICTURE = 2;
    private String photo_take_path;
    public ArrayList<PicturesActivity.Pictures> AllPictures = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        dataBaseSQLite = new DataBaseSQLite(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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
        LoadLastPictures();
    }

    public void LoadLastPictures() {
        Cursor elem = dataBaseSQLite.getPictureByID(id);
        elem.moveToFirst();
        System.out.println("elem");
        System.out.println(elem);
        LinearLayout grid_pic = (LinearLayout) findViewById(R.id.grid_pics);
        System.out.println("grid_pic");
        System.out.println(grid_pic);


        for (int i = 0; i < elem.getCount(); i++) {
            final PicturesActivity.Pictures new_picture = new PicturesActivity.Pictures(elem);
            AllPictures.add(new_picture);
            File imgFile = new File(new_picture.path);
            System.out.println("img_file");
            System.out.println(imgFile);
            System.out.println("new_pic_id");
            System.out.println(new_picture.id);
            System.out.println("new_pic_path");
            System.out.println(new_picture.path);
            System.out.println("new_pic_id_match");
            System.out.println(new_picture.id_match);
            System.out.println("OK");
                ImageView myImage = new ImageView(this);
                myImage.setAdjustViewBounds(true);
                myImage.setMaxHeight(400);
                myImage.setMaxWidth(400);
                LinearLayout.LayoutParams prms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                myImage.setImageURI(Uri.parse(new_picture.path));
                myImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Fonction pour ajouter un match
                        open_picture(view, new_picture);
                    }
                });
                grid_pic.addView(myImage, prms);
                ImageView delete = new ImageView(this);
                delete.setImageResource(R.drawable.accueil_delete_match);
                delete.setContentDescription("@string/delete");
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Fonction pour ajouter un match
                        delete_picture(new_picture);
                    }
                });
                grid_pic.addView(delete);
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
    public boolean previous_games(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        return (true);
    }

    public class Pictures {
        public int id;
        public String path;
        public int id_match;

        public Pictures(Cursor elem) {
            id = elem.getInt(0);
            path = elem.getString(1);
            id_match = elem.getInt(2);
        }
    }
    public boolean open_picture (View view, PicturesActivity.Pictures new_picture) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(new_picture.path), "image/*");
        startActivity(intent);
        return (true);
    }
    public boolean delete_picture(PicturesActivity.Pictures deleted_picture) {
        //supprimer matchs
        dataBaseSQLite.deletePictures(deleted_picture.id);
        //Recharger le layout
        reload();
        return (true);
    }
    public boolean take_picture(View view)
    {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();

                    //addPicture
                    dataBaseSQLite.addPicture(selectedImage.toString(), id);
                    reload();

                    break;

                case TAKE_PICTURE:
                    galleryAddPic();
                    dataBaseSQLite.addPicture(photo_take_path, id);
                    reload();
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
    private boolean reload()
    {
        //Recharger le layout
        Intent intent = new Intent(this, PicturesActivity.class);
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
