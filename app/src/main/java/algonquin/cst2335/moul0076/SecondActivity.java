package algonquin.cst2335.moul0076;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button callNumber = findViewById(R.id.CallButton);
        callNumber.setOnClickListener( click -> {
            //button was pressed

        });

        Button changePic = findViewById(R.id.changeImageButton);
        changePic.setOnClickListener( click -> {
            //button was pressed

        });


    }
}