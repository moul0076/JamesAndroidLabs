package algonquin.cst2335.moul0076;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SecondActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();

        /* Setting the top text veiw to Welcome back email Address */
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView titleText = findViewById(R.id.secondActivityLabel);
        //Warning Do not Concatenate text displayed with 'setText'. Use resource string with placeholders
        //titleText.setText("Welcome back " + emailAddress);
        String tempText = "Welcome back " + emailAddress;
        titleText.setText(tempText);
        /* */

        /* Get the phone number from the phoneNumberInput and use it
         * to make a phone call
         **/
        EditText phoneNumber = findViewById(R.id.phoneNumberInput);
        Button callNumber = findViewById(R.id.CallButton);
        callNumber.setOnClickListener( click ->
        {
            //button was pressed
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber));
        });
        /* */
        String filename = "Picture.png";
        ImageView profilepic = findViewById(R.id.profileImage);
        /* testing if a file exist */

        File file = new File( getFilesDir(), filename);
        /* */
        if(file.exists())
        {
            //avoid Hard codeing "/data/user/0/algonquin.cst2335.moul0076/files/Picture.png"
            //done to avoid an endless sea of NullPointerExceptions
            Bitmap theImage = BitmapFactory.decodeFile("/data/user/0/algonquin.cst2335.moul0076/files/Picture.png");
            if (theImage != null)
            {
                profilepic.setImageBitmap(theImage);
            }
        }
        /* */

        /* Use the camera to take a picture and set it as the profile picture */
        //ImageView profileImage = findViewById(R.id.profileImage);
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult
                (
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>()
                        {
                            @Override
                            public void onActivityResult(ActivityResult result)
                            {
                                if (result.getResultCode() == Activity.RESULT_OK)
                                {
                                    Log.i("Got Bitmap","image");
                                    Intent data = result.getData();

                                    File whereAmI = getFilesDir();

                                    /* getParcelableExtra may throw exception NullPointerException,
                                    adding try + catch disables code afterwards */
                                    Bitmap thumbnail = data.getParcelableExtra("data");

                                    try { profilepic.setImageBitmap ( thumbnail);

                                    }catch (NullPointerException e){
                                        Log.w("nullpointer", "for some reason the file is null");
                                    }


                                    try{ FileOutputStream file = openFileOutput(filename, Context.MODE_PRIVATE);

                                        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, file);
                                        file.flush();
                                        file.close();

                                    } catch (FileNotFoundException e){
                                        Log.w("FileNotFoundException", "can not output PNG");
                                        e.printStackTrace();

                                    } catch (IOException e) {
                                        Log.w("IOException", "can not output PNG");
                                        e.printStackTrace();
                                    }

                                    /*
                                    FileOutputStream fOut = null;
                                    try { fOut = openFileOutput(filename, Context.MODE_PRIVATE);
                                        Log.i("bitmap","Output bitmap");
                                        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                        fOut.flush();
                                        fOut.close();
                                    }
                                    catch (IOException e) //it does not like the FileNotFoundException, it wants the IOException instead
                                    { e.printStackTrace();
                                    }
                                    */

                                }
                                else if(result.getResultCode() == Activity.RESULT_CANCELED)
                                {
                                    Log.i("Got Bitmap","User refused the image");
                                }
                            }
                        }
                );
        /* */
        Button changePic = findViewById(R.id.changeImageButton);
        changePic.setOnClickListener( click ->
        {
            //button was clicked

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



            cameraResult.launch(cameraIntent);
            //startActivity(cameraIntent);

        });


    }
}