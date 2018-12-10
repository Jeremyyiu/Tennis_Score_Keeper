package com.example.jeremy.tennisscorekeeperapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    public static final String GAME_PREFERENCES = "GamePrefs";
    public static final int BEST_OF_3_SETS = 3;
    public static final int BEST_OF_5_SETS = 5;

    @BindView(R.id.gameLength_spinner)
    Spinner spinner;

    @BindView(R.id.player1name)
    EditText player1Name;

    @BindView(R.id.player2name)
    EditText player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        ButterKnife.bind(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gameLength_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    /**
     * Acquires player name inputs as well as the match length and stores them to be used in the main activity page.
     *
     * @param view - to update changes on the screen
     */
    public void startBtnClick(View view) {
        SharedPreferences settings = this.getSharedPreferences(GAME_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        /* Checks if the given player names are not empty and the same */
        if (!player1Name.getText().toString().equals("") && player1Name.getText().toString().equals(player2Name.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(), "Both player names cannot be the same name.", Toast.LENGTH_SHORT);
            toast.show();
        }
        /* Checks if the given player names are empty */
        else if (player1Name.getText().toString().equals("") || player2Name.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter player names", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            editor.putString("player1name", capitaliseName(player1Name.getText().toString())); //places player name input into shared storage
            editor.putString("player2name", capitaliseName(player2Name.getText().toString()));
            editor.putInt("MatchLength", getMatchLength()); //places match length into shared storage
            editor.apply();

            Intent i = new Intent(StartActivity.this, MainActivity.class); //launches the main activity
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
        }
    }

    /**
     * Capitalises the first character of input string name and returns the updated string
     *
     * @param name - name string passed as a parameter
     * @return - returns the string with the first character capitalised
     */
    public String capitaliseName(String name) {
        StringBuilder sb = new StringBuilder(name);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    /**
     * Acquires the match length selected by the user in the spinner
     *
     * @return match length as integer
     */
    public int getMatchLength() {
        if (spinner.getSelectedItem().toString().contains("3")) {
            return BEST_OF_3_SETS; //if user selected best of 3 sets, the function returns the constant containing the value "3".
        } else if (spinner.getSelectedItem().toString().contains("5")) {
            return BEST_OF_5_SETS; //if user selected best of 5 sets, the function returns the constant containing the value "5".
        }
        return BEST_OF_3_SETS; //returns BEST_OF_3_SETS if nothing was selected.
    }

    public class SpinnerActivity extends StartActivity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            /* Upon item selection, the selected item is retrieved and then added to the shared preference */
            parent.getItemAtPosition(pos);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
}

