package algonquin.cst2335.moul0076;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.*;

import java.util.Locale;

/** Week 06: Unit Test
 *  CST2335_010 Mobile Graphical Interface
 *  This is the week 6 lab for mobile graphical interface
 *  The requirements for the this program are to perform a
 *  check on a passwords complexity and show understanding of
 *  how to write JavaDocs
 *
 * @author James Mouland
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The bollean Variable valid is used to store if the
     * checked is complex enough
     * a True value indicates enough complexity
     * a False value indicated not enough complexity
     */
    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * The variable tv holds the TextVeiw id textView
         * This text field provides the starting information
         * as well as will be used to provided the feed to the
         * user on the password complexity
         */
        TextView tv = findViewById(R.id.textView);

        /**
         * The variable et holds the EditText id editText
         * This is the text feild where the user will enter the
         * password they wish to check for complexity
         */
        EditText et = findViewById(R.id.editText);

        /**
         * The variable btn holds the Button id button
         * This button will be activate the function
         * checkPasswordComplexity(String string)
         */
        Button btn = findViewById(R.id.button);

        /**
         * this button purpose is to activate the
         * method checkPasswordComplexity(String string)
         */
        btn.setOnClickListener( clk ->
        {
            String password = et.getText().toString();

            valid = checkPasswordComplexity(password);

            if (valid == true)
            {
                tv.setText("Your password is complex enough");
            }
            else
            {
                tv.setText("You shall not pass!");
            }
        });


    }

    /** This function will check the complexity of the password
     * if the password is found to missing an uppercase, lowercase, digit or special
     * character. The user will recieve a popup notification that corosponds to the missing
     * complexity. Checking each one in order.
     *
     * @param pw The String object that we are checking
     * @return Returns true if the string checked is complex enough
     */
    public boolean checkPasswordComplexity(String pw)
    {
        /**
         * The boolean vairables for foundUpperCase, foundLowerCase, foundNumber, foundSpecial
         * are used to store True or False for if an UpperCase, LowerCase, Number or Special character
         * was found in the string checked
         */
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        /**
         * Declaring the lenght of the Toast as short
         */
        int duration = Toast.LENGTH_SHORT;

        /**
         * Setting the boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial to false
         */
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        /**
         * Declairing the currentChar as character.
         */
        char currentChar;

        for (int f=0; f < pw.length(); f++)
        {
            currentChar = pw.charAt(f);

            if ( Character.isUpperCase(currentChar) )
            {
                foundUpperCase = true;
            }
            else if(Character.isLowerCase(currentChar))
            {
                foundLowerCase = true;
            }
            else if(Character.isDigit(currentChar))
            {
                foundNumber = true;
            }
            else if(isSpecialCharacter(currentChar))
            {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase)
        {
            Toast.makeText(MainActivity.this, "You are missing an upper case letter", duration).show();
            return false;
        }
        else if (!foundLowerCase)
        {
            Toast.makeText( MainActivity.this, "You are missing a lower case letter", duration ).show();
            return false;
        }
        else if (!foundNumber)
        {
            Toast.makeText( MainActivity.this, "You are missing a numeric digit", duration ).show();
            return false;
        }
        else if (!foundSpecial)
        {
            Toast.makeText( MainActivity.this, "You are missing a special character #$%^&*!@?", duration ).show();
            return false;
        } else
            return true; //only get here if they're all true

    }

    /** This is a function that will check a single character if one of the following
     * special characters #$%^&*!@? is present.
     *
     * @param c The character to be checked
     * @return returns true if one of the special characters is present, returns false if no
     * special character is found
     */
    boolean isSpecialCharacter(char c)
    {
        switch (c)
        {
            case '!':
            case '@':
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '?':
                return true;
            default:
                return false;
        }
    }

}