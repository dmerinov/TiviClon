package com.example.tiviclon.views


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.example.tiviclon.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CompleteFlowTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION"
        )

    @Test
    fun completeFlowTest() {
        val actionMenuItemView = onView(
            allOf(
                withId(R.id.it_login), withContentDescription("inicia sesion"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val materialButton = onView(
            allOf(
                withId(android.R.id.button3), withText("Registrarse"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.buttonPanel),
                        0
                    ),
                    0
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.et_username),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_form_container),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("usuario"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.et_password),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_form_container),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("usuario123"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.et_repeat_password),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_form_container),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("usuario123"), closeSoftKeyboard())

        val editText = onView(
            allOf(
                withId(R.id.et_password), withText("usuario123"),
                withParent(
                    allOf(
                        withId(R.id.cl_form_container),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        editText.check(matches(withText("usuario123")))

        val editText2 = onView(
            allOf(
                withId(R.id.et_repeat_password), withText("usuario123"),
                withParent(
                    allOf(
                        withId(R.id.cl_form_container),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        editText2.check(matches(withText("usuario123")))

        val materialButton2 = onView(
            allOf(
                withId(R.id.bt_register), withText("Confirmar Registro"),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_form_container),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.tv_logging_warning), withText("haz login para ver tu biblioteca"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val actionMenuItemView2 = onView(
            allOf(
                withId(R.id.it_login), withContentDescription("inicia sesion"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        actionMenuItemView2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("Entrar"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val button = onView(
            allOf(
                withId(R.id.bt_login), withText("LOG IN"),
                withParent(
                    allOf(
                        withId(R.id.cl_form_container),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.et_username),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_form_container),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("usuario"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.et_password),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_form_container),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("usuario123"), closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.bt_login), withText("Log In"),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_form_container),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.action_discover), withContentDescription("Descubre"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavBar),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val cardView = onView(
            allOf(
                withId(R.id.cv_Item),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.rv_show_list_location),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.bt_fav_toggle),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        appCompatImageView.perform(scrollTo(), click())

        pressBack()

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.action_library), withContentDescription("Biblioteca"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavBar),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val viewGroup = onView(
            allOf(
                withId(R.id.cl_item),
                withParent(
                    allOf(
                        withId(R.id.rv_show_list),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_show_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView2 = onView(
            allOf(
                withId(R.id.tv_title), withText("Titulo: The Flash"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Titulo: The Flash")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
