package algonquin.cst2335.moul0076;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();

        /** Setting the top text veiw to Welcome back email Address */
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView titleText = findViewById(R.id.secondActivityLabel);
        titleText.setText("Welcome back " + emailAddress);


        /** Get the phone number from the phoneNumberInput and use it
         * to make a phone call
         */
        EditText phoneNumber = findViewById(R.id.phoneNumberInput);
        Button callNumber = findViewById(R.id.CallButton);
        callNumber.setOnClickListener( click ->
        {
            //button was pressed
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber));
        });

        /** Use the camera to take a picture and set it as the profile picture */
        ImageView profileImage = findViewById(R.id.profileImage);
        /**
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

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            profileImage.setImageBitmap ( thumbnail);
                        }
                    }
                }
        );
        cameraResult.launch(cameraIntent);
         **/

        Button changePic = findViewById(R.id.changeImageButton);
        changePic.setOnClickListener( click ->
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivity(next);

        });


    }
}