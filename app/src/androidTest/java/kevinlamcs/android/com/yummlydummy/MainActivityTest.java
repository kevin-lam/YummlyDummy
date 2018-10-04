package kevinlamcs.android.com.yummlydummy;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.squareup.rx2.idler.Rx2Idler;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.reactivex.plugins.RxJavaPlugins;
import kevinlamcs.android.com.yummlydummy.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        RxJavaPlugins.setInitIoSchedulerHandler(
                Rx2Idler.create("RxJava 2.x IO Scheduler")
        );
    }

    @Test
    public void testSearchShouldShowRecipe() {
        // Focus search bar
        onView(withId(R.id.search_bar)).perform(click());

        // Type search query and press enter
        onView(withId(R.id.search_bar)).perform(typeText("Asian\n"));

        // Check that recipe list is not empty
        RecyclerView recipeListing = mainActivityActivityTestRule.getActivity().findViewById(R.id.recipe_listing);
        assertThat(recipeListing.getAdapter().getItemCount(), is(not(0)));
    }
}
