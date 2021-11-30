package algonquin.cst2335.moul0076;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.*;

import org.json.JSONArray;
import org.json.JSONException;
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

        //JSONObject theDocument;

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
                //JSONArray theArray = new JSONArray( text );
                try {
                    JSONObject theDocument = new JSONObject(text);
                    JSONArray weatherArray = theDocument.getJSONArray ("weather");
                    JSONObject position0 = weatherArray.getJSONObject(0);

                    String description = position0.getString("description");
                    String iconName = position0.getString("icon");

                    JSONObject mainObject = theDocument.getJSONObject("main");

                    double current = mainObject.getDouble("temp");
                    double min = mainObject.getDouble("temp_min");
                    double max = mainObject.getDouble("temp_max");
                    double humitidy = mainObject.getInt("humidity");



                    Bitmap image = null;
                    URL imgUrl = new URL("https://openweathermap.org/img/w/"+ iconName +".png");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200)
                    {
                        image = BitmapFactory.decodeStream(connection.getInputStream());

                        ImageView iv = findViewById(R.id.icon);
                        iv.setImageBitmap(image);
                    }

                    FileOutputStream fOUt = null;
                    try {
                        fOUt = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                        image.compress(Bitmap.CompressFormat.PNG, 100, fOUt);
                        fOUt.flush();
                        fOUt.close();
                    } catch ( FileNotFoundException e){
                        e.printStackTrace();
                    }catch ( NullPointerException e){
                        e.printStackTrace();
                    }

                    runOnUiThread( (  )  -> {
                        TextView tv = findViewById(R.id.temp);
                        tv.setText("The current temperature is " + current);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.minTemp);
                        tv.setText("The minimum temperature is " + min);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.maxTemp);
                        tv.setText("The maximum temperature is " + max);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.humitidy);
                        tv.setText("The minimum temperature is " + humitidy);
                        tv.setVisibility(View.VISIBLE);

                    //    ImageView iv = findViewById(R.id.icon);
                    //    iv.setImageBitmap(image);
                    //    iv.setVisibility(View.VISIBLE);
                    });




                }catch (JSONException je)
                {
                    Log.e("JSON error", je.getMessage());
                }






            }
            catch (IOException ioe)
            {
                Log.e("Connection Error", ioe.getMessage());
            }
        } );
        });


    }


}