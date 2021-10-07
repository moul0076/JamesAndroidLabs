package algonquin.cst2335.moul0076;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";


    @Override // called first
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        Log.i( TAG, "In onCreate() - Loading Widgets" );

        setContentView(R.layout.activity_main);

        EditText logninEmail = new EditText();
        loginEmail = findViewById(R.id.EmailTextBox);
        EditText loginPass - new EditText();
        loginPass = findViewById(R.id.PassTextBox);
        Button loginButton = findViewById(R.id.LoginButton);

        loginButton.setOnClicklistener(( click ) ->
        {

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
        });

    }

    @Override //screen is visible but not responding
    protected void onStart() {
        super.onStart();
        Log.i( TAG, "In onStart()" );
    }

    @Override //screen is visibile but responding
    protected void onResume() {
        super.onResume();
        Log.i( TAG, "In onResume()" );
    }

    @Override //screen is visible but not responding
    protected void onPause() {
        super.onPause();
        Log.i( TAG, "In onPause" );
    }

    @Override //screen is not visible
    protected void onStop() {
        super.onStop();
        Log.i( TAG, "In onStop" );
    }

    @Override //garbage collected
    protected void onDestroy() {
        super.onDestroy();
        Log.i( TAG, "In onDestroy" );
    }
}

/*

 */