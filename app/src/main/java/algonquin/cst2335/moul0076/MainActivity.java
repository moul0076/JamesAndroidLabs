package algonquin.cst2335.moul0076;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Switch;
import android.widget.ImageView;
import android.view.animation.RotateAnimation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Animation;

import android.os.Bundle;
//RELATIVE_TO_SELF
//symbol:   variable Animation
public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.flagveiw);
        sw = findViewById(R.id.spin_switch);

        sw.setOnCheckedChangeListener( (btn, isChecked) ->
        {
            if (isChecked)
            {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                imgView.startAnimation(rotate);
            }
            else
            {
                imgView.clearAnimation();
            }
        });
    }
}