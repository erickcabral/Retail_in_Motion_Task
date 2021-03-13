package ie.toxodev.retailinmotiontask

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertTextColorIs
import com.schibsted.spain.barista.internal.performActionOnView
import ie.toxodev.retailinmotiontask.activities.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewForecastFeatures {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Thread.sleep(1000)
    }

    private fun contTextInfoHasText(@IdRes inclId: Int, @IdRes tvId: Int, text: String) {
        onView(
            allOf(
                isDescendantOfA(
                    withId(inclId)
                ),
                withId(tvId)
            )
        )
            .check(matches(withText(text)))
    }

    private fun findContTextInfo(@IdRes inclId: Int, @IdRes tvId: Int): ViewInteraction {
        return onView(
            allOf(
                isDescendantOfA(
                    withId(inclId)
                ),
                withId(tvId)
            )
        )
    }

    private fun clearTramList() {
        //TODO()
    }

    @Test
    fun display_line_status() {
        assertNotDisplayed(R.id.tvLineStatus, "")
        assertTextColorIs(R.id.tvLineStatus, android.R.color.holo_green_dark)
    }

    @Test
    fun assert_text_containers_are_initialized() {
        contTextInfoHasText(R.id.inclStation, R.id.tvContLabel, "Station")
        contTextInfoHasText(R.id.inclStationAbv, R.id.tvContLabel, "Station Abv")
        contTextInfoHasText(R.id.inclDirection, R.id.tvContLabel, "Direction")
    }

    @Test
    fun assert_station_abv_container_has_text_info() {
        findContTextInfo(R.id.inclStationAbv, R.id.contTextInfo).check(
            matches(not(withText("")))
        )
    }

    @Test
    fun assert_station_container_has_text_info() {
        findContTextInfo(R.id.inclStation, R.id.contTextInfo).check(
            matches(not(withText("")))
        )
    }

    @Test
    fun assert_direction_container_has_text_info() {
        findContTextInfo(R.id.inclDirection, R.id.contTextInfo).check(
            matches(not(withText("")))
        )
    }

    @Test
    fun display_requested_time_info() {
        findContTextInfo(R.id.inclCurrentTime, R.id.tvContLabel).check(
            matches(withText("Requested Time"))
        )
        findContTextInfo(R.id.inclCurrentTime, R.id.contTextInfo).check(
            matches(not(withText("")))
        )
    }


    //================ RECYCLER TEST ================== //

    @Test
    fun recycler_view_has_items_progress_not_displayed() {
        assertNotDisplayed(R.id.progressBar)
        onView(withId(R.id.recyclerTramInfo)).check(
            matches(hasMinimumChildCount(1))
        )
    }

    // =================== RENEW BUTTON TESTS =================== //
    @Test
    fun display_progress_when_update_button_clicked() {
        performActionOnView(withId(R.id.floatBtnUpdate), click())

        onView(withId(R.id.recyclerTramInfo)).check(
            matches(hasChildCount(0))
        )
        assertDisplayed(R.id.progressBar)
    }
}