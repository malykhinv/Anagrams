package com.malykhinv.anagrams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ERROR_EMPTY_REVERSE_TEXT_FIELD = 0;
    private static final int ERROR_EMPTY_ADD_CHARACTER_TO_IGNORE_FIELD = 1;
    private static final int ERROR_CHARACTER_IS_ALREADY_IGNORED = 2;
    private TextView textIgnoredCharacters;
    private Button buttonAddCharacter;
    private Button buttonReverseWords;
    private EditText editTextToReverse;
    private EditText editTextAddCharacter;
    private ImageView imageExpandCollapse;
    private TextView textResult;
    private TextInputLayout layoutTextToReverse;
    private LinearLayout layoutDescription;
    private android.view.ViewGroup.LayoutParams layoutDescriptionParams;
    private Boolean descriptionCollapsed = true;
    private String ignoredCharacters = "\"\\!#$%&'()*+,-./0123456789:;<=>?@[]^_`{|}~";
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        textIgnoredCharacters = (TextView) findViewById(R.id.textIgnoredCharacters);
        buttonAddCharacter = (Button) findViewById(R.id.buttonAddCharacter);
        buttonAddCharacter.setOnClickListener(this);
        buttonReverseWords = (Button) findViewById(R.id.buttonReverseWords);
        buttonReverseWords.setOnClickListener(this);
        editTextToReverse = (EditText) findViewById(R.id.editTextToReverse);
        editTextAddCharacter = (EditText) findViewById(R.id.editTextAddCharacter);
        imageExpandCollapse = (ImageView) findViewById(R.id.imageExpandCollapse);
        imageExpandCollapse.setOnClickListener(this);
        textResult = (TextView) findViewById(R.id.textResult);
        textIgnoredCharacters = (TextView) findViewById(R.id.textIgnoredCharacters);
        layoutTextToReverse = (TextInputLayout) findViewById(R.id.layoutTextToReverse);
        layoutTextToReverse.setOnClickListener(this);
        layoutDescription = (LinearLayout) findViewById(R.id.layoutDescription);
        layoutDescriptionParams = layoutDescription.getLayoutParams();

        textIgnoredCharacters.setText(ignoredCharacters);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageExpandCollapse:
                if (descriptionCollapsed) expandDescriptionLayout();
                else collapseDescriptionLayout();
                break;
            case R.id.buttonReverseWords:
                if (editTextToReverse.getText().toString().length() > 0) reverseText(editTextToReverse.getText().toString());
                else showError(ERROR_EMPTY_REVERSE_TEXT_FIELD);
                break;
            case R.id.buttonAddCharacter:
                if (editTextAddCharacter.length() > 0) {
                    String characterToIgnore = editTextAddCharacter.getText().toString();
                    if (!ignoredCharacters.contains(characterToIgnore)) addCharacterToIgnoreList(characterToIgnore);
                    else showError(ERROR_CHARACTER_IS_ALREADY_IGNORED);
                } else showError(ERROR_EMPTY_ADD_CHARACTER_TO_IGNORE_FIELD);
                break;
        }
    }

    public void expandDescriptionLayout() {
        layoutDescriptionParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutDescription.setLayoutParams(layoutDescriptionParams);
        imageExpandCollapse.setImageDrawable(getResources().getDrawable(R.drawable.image_collapse));
        descriptionCollapsed = false;
    }

    public void collapseDescriptionLayout() {
        layoutDescriptionParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());
        layoutDescription.setLayoutParams(layoutDescriptionParams);
        imageExpandCollapse.setImageDrawable(getResources().getDrawable(R.drawable.image_expand));
        descriptionCollapsed = true;
    }

    public void reverseText(String text) {
        String[] words = text.split(" ");
        Map<Integer, Character> ignored = new HashMap<>();
        String wordWithoutIgnored;
        String resultText = "";

        for (String word : words) {
            int position = 0;
            wordWithoutIgnored = "";
            ignored.clear();

            // Find ignored characters in word
            for (Character character : word.toCharArray()) {
                if (ignoredCharacters.contains(String.valueOf(character))) {
                    ignored.put(position, character);
                }
                else wordWithoutIgnored += character;
                position++;
            }

            // Reverse clear word
            String reversedWord = new StringBuilder(wordWithoutIgnored).reverse().toString();
            StringBuilder rw = new StringBuilder(reversedWord);

            // Insert ignored characters into reversed word
            for (int i = 0; i < word.length(); i++) {
                if (ignored.containsKey(i)) rw.insert(i, ignored.get(i));
            }
            resultText += rw.toString() + " ";
        }
        textResult.setText(resultText);
    }

    public void addCharacterToIgnoreList(String characterToIgnore) {
        ignoredCharacters += characterToIgnore;
        textIgnoredCharacters.setText(ignoredCharacters);
    }

    public void showError(int errorCode) {
        switch (errorCode) {
            case 0:
                toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_nothing_to_reverse), Toast.LENGTH_SHORT);
                toast.show();
                break;
            case 1:
                toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_nothing_to_add), Toast.LENGTH_SHORT);
                toast.show();
                break;
            case 2:
                toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_this_character_is_already_ignored), Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }
}
