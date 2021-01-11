package com.malykhinv.anagrams;

import android.Manifest;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.squareup.spoon.SpoonRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ReverseTextWithIgnoredCharactersTest {

    public static final String additionalIgnoredCharacters = "Zzjjj8m";
    public static final String extendedIgnoredCharacters = "\"\\!#$%&'()*+,-./0123456789:;<=>?@[]^_`{|}~Zzjm";
    public static final String textToReverse = "Password: pass1234WORD_point";
    public static final String reversedText = "drowssaP: tnio1234pDRO_Wssap";

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public SpoonRule spoon = new SpoonRule();

    @Rule
    public GrantPermissionRule runtimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

    @Test
    public void addCharacterToIgnoreList() {
        // Expand "How to use", type characters to ignore, press "Add characters" button
        onView(withId(R.id.imageExpandCollapseArrow)).perform(click());
        onView(withId(R.id.editTextAddIgnoredCharacters)).perform(typeText(additionalIgnoredCharacters), closeSoftKeyboard());
        spoon.screenshot(activityActivityTestRule.getActivity(), "addCharacterToIgnoreList_type_new_characters_to_ignore_into_text_field");
        onView(withId(R.id.buttonAddIgnoredCharacters)).perform(click());
        spoon.screenshot(activityActivityTestRule.getActivity(), "addCharacterToIgnoreList_set_of_ignored_characters_have_been_updated");

        // Check that characters was added
        onView(withId(R.id.textSetOfIgnoredCharacters)).check(matches(withText(extendedIgnoredCharacters)));
    }

    @Test
    public void reverseText() {
        // Enter text to reverse, press "Reverse words" button
        onView(withId(R.id.editTextToReverse)).perform(typeText(textToReverse), closeSoftKeyboard());
        spoon.screenshot(activityActivityTestRule.getActivity(), "reverseText_type_new_text_to_reverse_into_text_field");
        onView(withId(R.id.buttonReverseWords)).perform(click());
        spoon.screenshot(activityActivityTestRule.getActivity(), "reverseText_text_have_been_reversed");

        // Check that the words has been reversed
        onView(withId(R.id.textReversed)).check(matches(withText(reversedText)));
    }
}
