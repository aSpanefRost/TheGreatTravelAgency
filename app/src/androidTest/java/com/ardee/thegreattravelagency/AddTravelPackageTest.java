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

public class AddTravelPackageTest {

    @Rule
    public ActivityTestRule<AddTravelPackage> testRule= new ActivityTestRule<AddTravelPackage>(AddTravelPackage.class);
    private AddTravelPackage adpActivity = null;

    @Before
    public void setUp() throws Exception {
       adpActivity = testRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        adpActivity=null;
    }

    @Test
    public void checkTextViews(){
        assertNotNull(adpActivity.findViewById(R.id.editTextName));
        assertNotNull(adpActivity.findViewById(R.id.editTextCapacity));
    }

}