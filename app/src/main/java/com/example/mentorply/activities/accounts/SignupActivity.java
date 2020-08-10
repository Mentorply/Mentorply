package com.example.mentorply.activities.accounts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentorply.activities.MainActivity;
import com.example.mentorply.R;
import com.example.mentorply.models.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";

//    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
//    public final static int PICK_PHOTO_CODE = 1046;
    private EditText etUsername;
    private EditText etPassword;
//    private ChipGroup chipsCareers;
//    private ChipGroup chipsInterests;
    private TextView tvBackToLogin;
    //adding new stuff
    private Button btnSignup;

//    private Button btnCaptureImage;
//    private ImageView ivPostImage;

//    private File photoFile;
//    public String photoFileName = "photo.jpg";
//    //public Bitmap finalImage;
//    private List <Tag> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        SpannableString content = new SpannableString("Return to Login");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvBackToLogin.setText(content);

//        btnCaptureImage = findViewById(R.id.btnCaptureImage);
//        ivPostImage = findViewById(R.id.ivPostImage);
//        btnSelectImage = findViewById(R.id.btnSelectImage);


//        chipsCareers = findViewById(R.id.chipsCareers);
//        chipsInterests = findViewById(R.id.chipsInterests);

//        setChipGroups("anime", chipsCareers);
//        setChipGroups("anime?", chipsInterests);

//        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchCamera();
//            }
//        });
//
//        btnSelectImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onPickPhoto(view);
//            }
//        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "onclick signup button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                //loginUser(username,password);
//                tags = new ArrayList<Tag>();
//                for (int i=0; i<chipsCareers.getChildCount();i++){
//                    Chip chip = (Chip)chipsCareers.getChildAt(i);
//                    if (chip.isChecked()){
//                        final Tag tag = new Tag();
//                        tag.setName((String) chip.getText());
//                        tag.setCategory("anime");
//                        tag.saveInBackground(new SaveCallback() {
//                            public void done(ParseException e) {
//                                // If successful add file to user and signUpInBackground
//                                if(null == e)
//                                    tags.add(tag);
//                            }
//                        });
//
//
//                    }
//                }
//                for (int i=0; i<chipsInterests.getChildCount();i++){
//                    Chip chip = (Chip)chipsInterests.getChildAt(i);
//                    if (chip.isChecked()){
//                        final Tag tag = new Tag();
//                        tag.setName((String) chip.getText());
//                        tag.setCategory("anime?");
//                        tag.saveInBackground(new SaveCallback() {
//                            public void done(ParseException e) {
//                                // If successful add file to user and signUpInBackground
//                                if(null == e)
//                                    tags.add(tag);
//                            }
//                        });
//                    }
//                }
//                if (photoFile == null) {
//                    Log.e(TAG, "Attempt to post invalid image");
//                    return;
//                }
//                final ParseFile photo = new ParseFile(photoFile);
//                photo.saveInBackground(new SaveCallback() {
//                    public void done(ParseException e) {
//                        // If successful add file to user and signUpInBackground
//                        if(null == e)
//                            signupUser(photo);
//                    }
//                });
//            }
//        });
                                             signupUser();
                                         }
                                     });
        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onclick back to login button");
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

//    private void setChipGroups(final String category, final ChipGroup cg) {
//        // Define the class we would like to query
//        final ParseQuery<Tag> query = ParseQuery.getQuery(Tag.class);
//        // Define our query conditions
//        query.whereEqualTo("category", category);
//        // Execute the find asynchronously
//        query.findInBackground(new FindCallback<Tag>() {
//            public void done(List<Tag> itemList, ParseException e) {
//                if (e == null) {
//                    // Access the array of results here
//                    //String firstItemId = itemList.get(0).getObjectId();
//                    //Toast.makeText(TodoItemsActivity.this, firstItemId, Toast.LENGTH_SHORT).show();
//                    setCategoryChips(itemList, cg);
//
//                } else {
//                    Log.d("item", "Error: " + e.getMessage());
//                }
//            }
//        });
//    }
//ParseFile photo
    private void signupUser() {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        user.setUsername(username);
        user.setPassword(password);
//        user.put("tags", tags);
//        user.put("profilePicture",photo);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Intent i = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(i);
                    Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    etUsername.setText("");
                    etPassword.setText("");
                    //ivPostImage.setImageResource(0);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Toast.makeText(SignupActivity.this, "Issue with signup", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Issue with signup", e);
                    return;

                }
            }
        });

    }
//    public void setCategoryChips(List<Tag> tags, ChipGroup cg) {
//        for (Tag tag : tags) {
//            Chip mChip = (Chip) this.getLayoutInflater().inflate(R.layout.layout_chip_filter, null, false);
//            try {
//                mChip.setText(tag.fetchIfNeeded().getString("name"));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            int paddingDp = (int) TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP, 10,
//                    getResources().getDisplayMetrics()
//            );
//            mChip.setPadding(paddingDp, 0, paddingDp, 0);
//            mChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                }
//            });
//            cg.addView(mChip);
//        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {
//            if (resultCode == RESULT_OK) {
//                // by this point we have the camera photo on disk
//                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                // RESIZE BITMAP, see section below
//                // Load the taken image into a preview
//                ivPostImage.setImageBitmap(takenImage);
//            } else { // Result was a failure
//                Toast.makeText(getApplicationContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else if ((data != null) && requestCode == PICK_PHOTO_CODE) {
//            Uri photoUri = data.getData();
//
//            // Load the image located at photoUri into selectedImage
//            Bitmap selectedImage = loadFromUri(photoUri);
//            // Load the selected image into a preview
//            ivPostImage.setImageBitmap(selectedImage);
//
//        }
//    }
//
//    //getting profile image from camera methods
//    private void launchCamera() {
//        // create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Create a File reference for future accessf
//        photoFile = getPhotoFileUri(photoFileName);
//
//        // wrap File object into a content provider
//        // required for API >= 24
//        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
//        Uri fileProvider = FileProvider.getUriForFile(getApplicationContext(), "com.codepath.fileprovider.Mentorply", photoFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
//        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
//
//    }
//
//    // Returns the File for a photo stored on disk given the fileName
//    public File getPhotoFileUri(String fileName) {
//        // Get safe storage directory for photos
//        // Use `getExternalFilesDir` on Context to access package-specific directories.
//        // This way, we don't need to request external read/write runtime permissions.
//        File mediaStorageDir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
//            Log.d(TAG, "failed to create directory");
//        }
//
//        // Return the file target for the photo based on filename
//        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//        return file;
//    }
//
//    // Trigger gallery selection for a photo
//    public void onPickPhoto(View view) {
//        // Create intent for picking a photo from the gallery
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Bring up gallery to select a photo
//            startActivityForResult(intent, PICK_PHOTO_CODE);
//        }
//    }
//
//    public Bitmap loadFromUri(Uri photoUri) {
//        Bitmap bitmap = null;
//        try {
//            // check version of Android on device
//            if(Build.VERSION.SDK_INT > 27){
//                // on newer versions of Android, use the new decodeBitmap method
//                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
//                bitmap = ImageDecoder.decodeBitmap(source);
//            } else {
//                // support older versions of Android by using getBitmap
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
//
}