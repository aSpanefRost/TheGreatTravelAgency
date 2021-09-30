package com.ardee.thegreattravelagency;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TravelPackagesTest {

    @Rule
    public ActivityTestRule<TravelPackages> testRule= new ActivityTestRule<TravelPackages>(TravelPackages.class);
    private TravelPackages tpActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(AddTravelPackage.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        tpActivity = testRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        tpActivity = null;
    }

    @Test
    public void check(){
       assertNotNull(tpActivity.findViewById(R.id.tv_name));
       assertNotNull(tpActivity.findViewById(R.id.tv_available));
       assertNotNull(tpActivity.findViewById(R.id.tv_spaces));

       onView(withId(R.id.floatingActionButton)).perform(click());
       Activity addTravelPackageActivity=getInstrumentation().
               waitForMonitorWithTimeout(monitor,1000);
       assertNotNull(addTravelPackageActivity);
       addTravelPackageActivity.finish();
    }

    @Test
    public void storeDataInArrays() {
        System.out.println("Number of current Packages: "+tpActivity.package_id.size());
        assertNotEquals(tpActivity.package_id.size(),0);
    }
}