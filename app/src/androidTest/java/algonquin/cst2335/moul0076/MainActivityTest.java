package algonquin.cst2335.moul0076;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest()
    {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type password in textField
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingUpperCase()
    {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type password in textField
        appCompatEditText.perform(replaceText("password12345!"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingLowerCase()
    {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type password in textField
        appCompatEditText.perform(replaceText("PASSWORD12345!"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingDigitCase()
    {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type password in textField
        appCompatEditText.perform(replaceText("PaSsWoRd!"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingSpecial()
    {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type password in textField
        appCompatEditText.perform(replaceText("PaSsWoRd12345"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testComplexEnoughPassword()
    {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type password in textField
        appCompatEditText.perform(replaceText("PaSsWoRd12345!"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("Your password is complex enough")));
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>()
        {
            @Override
            public void describeTo(Description description)
            {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view)
            {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
