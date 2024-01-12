package com.ariqhikari.githubuser.ui.splashscreen

import com.ariqhikari.githubuser.R
import org.junit.Before
import org.junit.Test
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

class SplashScreenActivityTest {
    private val dummyText = "GitHub User"

    @Before
    fun setup(){
        ActivityScenario.launch(SplashScreenActivity::class.java)
    }

    @Test
    fun assertGetView() {
        onView(withId(R.id.ivGithub)).check(matches(isDisplayed()))
        onView(withId(R.id.tvGithub)).check(matches(isDisplayed()))
        onView(withId(R.id.tvGithub)).check(matches(withText(dummyText)))
    }
}