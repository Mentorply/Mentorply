package com.example.mentorply.activities.accounts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mentorply.R;
import com.example.mentorply.activities.MainActivity;
import com.example.mentorply.fragments.SettingsFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = "EditProfileActivity=";

    //image variables
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public final static int PICK_PHOTO_CODE = 1046;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    public Bitmap finalImage;


    private EditText etName;
    private Spinner spinnerCareer;
    private Spinner spinnerInterests;
    private EditText etDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnCaptureImage = findViewById(R.id.btnCaptureImage);
        ivPostImage = findViewById(R.id.ivPostImage);


        spinnerCareer = findViewById(R.id.spinnerCareer);
        spinnerInterests = findViewById(R.id.spinnerInterests);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        ParseUser currentUser = ParseUser.getCurrentUser();
        Glide.with(this).load(currentUser.getParseFile("profilePicture").getUrl()).into(ivPostImage);
        //photoFile = new ParseFile(currentUser.getParseFile("profilePicture"));
        etName.setText(currentUser.getString("name"));
        etDescription.setText(currentUser.getString("description"));

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
                //ParseUser.getCurrentUser().put("profilePicture", takenImage);
            } else { // Result was a failure
                Toast.makeText(getApplicationContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //getting profile image from camera methods
    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future accessf
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getApplicationContext(), "com.codepath.fileprovider.Mentorply", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        switch(item.getItemId())
        {
            case R.id.action_save:
                currentUser.put("name", etName.getText().toString());
                currentUser.put("description", etDescription.getText().toString());
                //final ParseFile photo = new ParseFile(photoFile);
                if (photoFile!=null){
                    currentUser.put("profilePicture", new ParseFile(photoFile));
                }
                currentUser.saveInBackground();
                Intent i = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(i);
            case R.id.action_cancel:
                Intent j = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(j);

                break;
        }
        return true;
    }
}