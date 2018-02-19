package in.bcs.codingexercise.activities;

import android.content.Intent;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeoutException;

import in.bcs.codingexercise.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by saran on 2/19/2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FactsRecyclerActivityTest {
    @Rule
    public ActivityTestRule<FactsRecyclerActivity> mActivityRule = new ActivityTestRule<>(
            FactsRecyclerActivity.class);

    /**
     * Generic method to check if the view has values equals to the param
     *
     * @param content value to be compared
     * @return
     */
    Matcher<View> hasValueEqualTo(final String content) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Has EditText/TextView the value:  " + content);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView)) {
                    return false;
                }
                if (view != null) {
                    String text = null;
                    if (view instanceof TextView) {
                        text = ((TextView) view).getText().toString();
                    }

                    return (text.equalsIgnoreCase(content));
                }
                return false;
            }
        };
    }

    /**
     * Testing Internet Connection
     */
    @Test
    public void testInternetConnection() {
        onView(withId(R.id.emptyTextView))
                .check(matches(hasValueEqualTo(mActivityRule.getActivity().getString(R.string.no_internet))));
    }

    /**
     * On Menu item clicked in action bar
     *
     * @throws Exception
     */
    @Test
    public void onOptionsItemSelected() throws Exception {
        onView(withId(R.id.menu_refresh))
                .perform(click());
        int item_count = mActivityRule.getActivity().getItemsCount();
        int rv_count = getRVcount();
        assertEquals(item_count, rv_count);
    }

    /**
     * Testing pull to refresh
     */
    @Test
    public void testPullToRefresh() {

        onView(withId(R.id.pullToRefresh))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));
        int item_count = mActivityRule.getActivity().getItemsCount();
        int rv_count = getRVcount();
        assertEquals(item_count, rv_count);
    }


    @Test
    public void checkRecyclerViewIsLoaded() {
        int item_count = getRVcount();
        assertEquals(item_count, mActivityRule.getActivity().getItemsCount());

    }

    private int getRVcount() {
        onView(ViewMatchers.withId(R.id.factsRecyclerView)).check(matches(isDisplayed()));

        onView(withId(R.id.factsRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(((RecyclerView) mActivityRule.getActivity().findViewById(R.id.factsRecyclerView)).getAdapter().getItemCount() - 1));
        return ((RecyclerView) mActivityRule.getActivity().findViewById(R.id.factsRecyclerView)).getAdapter().getItemCount();
    }

    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }
}