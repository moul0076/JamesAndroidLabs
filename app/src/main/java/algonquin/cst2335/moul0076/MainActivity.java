/* James Android Lab
 * Author: James Mouland
 * ID: 040418260
 * For: Eric Torunski
 * This is the lab for 21F_CST2335_010 Mobile Graphical Interface Programing
 * This git is the Week 2 branch on GIT hub
 */

package algonquin.cst2335.moul0076;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myText = findViewById(R.id.textview);

        myText.setText("Java put this here");

        Button btn = findViewById(R.id.mybutton);
        //btn.setText("the view was previous" + oldText);

        EditText myedit = findViewById(R.id.myEditText);
        String editString = myedit.getText().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myText.setText("edit text has " + myedit.getText() );
            }
        });



    }

}
