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

public class PackageInformationTest {

    @Rule
    public ActivityTestRule<PackageInformation> testRule= new ActivityTestRule<PackageInformation>(PackageInformation.class);
    private PackageInformation piActivity = null;

    @Before
    public void setUp() throws Exception {
        piActivity = testRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        piActivity=null;
    }

    @Test
    public void checkTextViews(){
        assertNotNull(piActivity.findViewById(R.id.id_pack_inf_destinations));
        assertNotNull(piActivity.findViewById(R.id.id_pack_inf_package));
    }


}