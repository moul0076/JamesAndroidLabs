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
import android.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
            try {
                String cityName = cityText.getText().toString();
                String temp = URLEncoder.encode(cityName,"UTF-8");
                //My API key is d6acb109ae5d19a9953c652a8ecf682e  keeps saying invalid key
                //Class API key is 7e943c97096a9784391a981c4d878b22
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName,"UTF-8")
                        +"&appid=d6acb109ae5d19a9953c652a8ecf682e&units=metric&mode=xml";

                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                /**
                 * XML parser
                 */
                /*
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(in, "UTF-8");
                */
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( in  , "UTF-8");


                String descriptionXML = null;
                String iconName = null;
                String currentXML = null;
                String minXML = null;
                String maxXML = null;
                String humidityXML = null;

                int eventType = xpp.next();

                while (xpp.next() != XmlPullParser.END_DOCUMENT)
                {
                    switch ( xpp.getEventType() )
                    {
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("temperature"))
                            {
                                currentXML = xpp.getAttributeValue(null, "value");
                                minXML = xpp.getAttributeValue(null, "min");
                                maxXML = xpp.getAttributeValue(null, "max");
                            }
                            else if (xpp.getName().equals("weather"))
                            {
                                descriptionXML = xpp.getAttributeValue(null, "value");
                                iconName = xpp.getAttributeValue(null, "icon");
                            }
                            else if (xpp.getName().equals("humidity"))
                            {
                                humidityXML = xpp.getAttributeValue(null, "humidity");
                            }
                            break;
                        case XmlPullParser.END_TAG:

                            break;
                        case XmlPullParser.TEXT:

                            break;
                    }
                }

                String text = (new BufferedReader(
                        new InputStreamReader(in, StandardCharsets.UTF_8)))
                        .lines()
                        .collect(Collectors.joining("\n"));

                try {
                    //JSONObject theDocument = new JSONObject(text);
                    //JSONArray weatherArray = theDocument.getJSONArray ("weather");
                    //JSONObject position0 = weatherArray.getJSONObject(0);

                    //String description = position0.getString("description");
                    //String iconName = position0.getString("icon");

                    //JSONObject mainObject = theDocument.getJSONObject("main");

                    //double current = mainObject.getDouble("temp");
                    //double min = mainObject.getDouble("temp_min");
                    //double max = mainObject.getDouble("temp_max");
                    //double humidity = mainObject.getInt("humidity");

                    Bitmap image = null;
                    URL imgUrl = new URL("https://openweathermap.org/img/w/"+ iconName +".png");
                    HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200)
                    {
                        image = BitmapFactory.decodeStream(connection.getInputStream());

                    }

                    final Bitmap mainImage = image;
                    final String current = currentXML;
                    final String min = minXML;
                    final String max = maxXML;
                    final String humidity = humidityXML;
                    final String description = descriptionXML;


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

                        tv = findViewById(R.id.humidity);
                        tv.setText("The humidity is " + humidity);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.description);
                        tv.setText( description );
                        tv.setVisibility(View.VISIBLE);

                        ImageView iv = findViewById(R.id.icon);
                        iv.setImageBitmap(mainImage);
                        iv.setVisibility(View.VISIBLE);
                    });


                }catch (UnsupportedEncodingException e)
                {
                    Log.e("JSON error", "UnsupportedEncodingException");
                }
                /* */





            }
            catch (IOException ioe)
            {
                Log.e("Connection Error", ioe.getMessage());
            }
            catch (XmlPullParserException e)
            {
                e.printStackTrace();
            }
        } );
        });
    }
}