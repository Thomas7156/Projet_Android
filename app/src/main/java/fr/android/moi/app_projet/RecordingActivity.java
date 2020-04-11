package fr.android.moi.app_projet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordingActivity extends AppCompatActivity {
    private static final int TAKE_PICTURE = 1;
    private static final int CHOOSE_PICTURE = 2;
    private String photoLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
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
    public boolean picture(View view)
    {
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
        //SAVE FUNCION INTO DATABASE+SQQLITE
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
                    photoLocation = selectedImage.toString();
                    //addPicture
                    break;

                case TAKE_PICTURE:
                    photoLocation = "camera";
                    //addPicture
                    break;
            }
        }
    }
}
