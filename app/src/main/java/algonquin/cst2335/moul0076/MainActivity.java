package algonquin.cst2335.moul0076;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/** Week 09: Connect to a server
 *  CST2335_010 Mobile Graphical Interface
 *
 * @author James Mouland
 * @version 1.0
 */

/**
 * What is needed to connect to a Server
 * URL url = new URL("The server URL");
 * HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 * InputStream in = new BufferedInputStream(urlConnection.getInputStream());
 */
public class MainActivity extends AppCompatActivity {

    boolean valid = false;
    private String stringURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forecastBtn = findViewById(R.id.forecastButton);
        EditText cityText = findViewById(R.id.cityTextField);

        forecastBtn.setOnClickListener( (click) ->
        {
            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute( () ->
        {
            /* This runs in a separate thread */
            try
            {
                String cityName = cityText.getText().toString();
                String temp = URLEncoder.encode(cityName,"UTF-8");
                //My API key is d6acb109ae5d19a9953c652a8ecf682e  keeps saying invalid key
                //Class API key is 7e943c97096a9784391a981c4d878b22
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName,"UTF-8")
                        +"&appid=7e943c97096a9784391a981c4d878b22&units=metric";

                URL url = new URL(stringURL);
                //URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                String text = (new BufferedReader(
                        new InputStreamReader(in, StandardCharsets.UTF_8)))
                        .lines()
                        .collect(Collectors.joining("\n"));


            }
            catch (IOException ioe)
            {
                Log.e("Connection Error", ioe.getMessage());
            }
        } );
        });


    }


}