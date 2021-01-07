package com.malykhinv.anagrams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ERROR_EMPTY_REVERSE_TEXT_FIELD = 0;
    private static final int ERROR_EMPTY_ADD_CHARACTER_TO_IGNORE_FIELD = 1;
    private static final int ERROR_CHARACTER_IS_ALREADY_IGNORED = 2;
    private TextView textIgnoredCharacters;
    private EditText editTextToReverse;
    private EditText editTextAddCharacter;
    private ImageView imageExpandCollapse;
    private TextView textResult;
    private LinearLayout layoutDescription;
    private android.view.ViewGroup.LayoutParams layoutDescriptionParams;
    private Boolean descriptionCollapsed = true;
    private String ignoredCharacters = "\"\\!#$%&'()*+,-./0123456789:;<=>?@[]^_`{|}~";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextToReverse = findViewById(R.id.editTextToReverse);
        editTextAddCharacter = findViewById(R.id.editTextAddIgnoredCharacters);
        imageExpandCollapse = findViewById(R.id.imageExpandCollapseArrow);
        imageExpandCollapse.setOnClickListener(this);
        textResult = findViewById(R.id.textReversed);
        textIgnoredCharacters = findViewById(R.id.textSetOfIgnoredCharacters);
        textIgnoredCharacters.setText(ignoredCharacters);
        layoutDescription = findViewById(R.id.layoutAppDescription);
        layoutDescriptionParams = layoutDescription.getLayoutParams();
        findViewById(R.id.buttonAddIgnoredCharacters).setOnClickListener(this);
        findViewById(R.id.buttonReverseWords).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // Expand/collapse
        if (v.getId() == R.id.imageExpandCollapseArrow) {
            if (descriptionCollapsed) expandDescriptionLayout();
            else collapseDescriptionLayout();
        }

        // Reverse text
        if (v.getId() == R.id.buttonReverseWords) {
            if (editTextToReverse.getText().toString().length() > 0) reverseText(editTextToReverse.getText().toString());
            else showError(ERROR_EMPTY_REVERSE_TEXT_FIELD);
        }

        // Add ignored characters
        if (v.getId() == R.id.buttonAddIgnoredCharacters) {
            if (editTextAddCharacter.length() > 0) {
                String characterToIgnore = editTextAddCharacter.getText().toString();
                if (!ignoredCharacters.contains(characterToIgnore)) addCharacterToIgnoreList(characterToIgnore);
                else showError(ERROR_CHARACTER_IS_ALREADY_IGNORED);
            } else showError(ERROR_EMPTY_ADD_CHARACTER_TO_IGNORE_FIELD);
        }
    }

    public void expandDescriptionLayout() {
        layoutDescriptionParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutDescription.setLayoutParams(layoutDescriptionParams);
        imageExpandCollapse.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_collapse));
        descriptionCollapsed = false;
    }

    public void collapseDescriptionLayout() {
        layoutDescriptionParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());
        layoutDescription.setLayoutParams(layoutDescriptionParams);
        imageExpandCollapse.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_expand));
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
        for (int i = 0; i < characterToIgnore.length(); i++) {
            if (!ignoredCharacters.contains(String.valueOf(characterToIgnore.charAt(i)))) {
                ignoredCharacters += characterToIgnore.charAt(i);
            }
        }
        textIgnoredCharacters.setText(ignoredCharacters);
    }

    public void showError(int errorCode) {
        switch (errorCode) {
            case 0:
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_nothing_to_reverse), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_nothing_to_add), Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_this_character_is_already_ignored), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
