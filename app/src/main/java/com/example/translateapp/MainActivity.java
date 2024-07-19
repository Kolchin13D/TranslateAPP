package com.example.translateapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.nl.translate.TranslateLanguage;

public class MainActivity extends AppCompatActivity {

    private Spinner firstLangSpinner, secondLangSpinner;
    private EditText enterText;
    private Button translate;
    private TextView translatedText;

    String[] fLanguages = {"from", "English", "Russian", "Polska"};
    String[] sLanguages = {"to", "English", "Russian"};
    private static final int REQUEST_CODE = 1;
    String langCode, firstLangCode, secondLangCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        firstLangSpinner = findViewById(R.id.firstLangSpinner);
        secondLangSpinner = findViewById(R.id.secondLangSpinner);
        enterText = findViewById(R.id.textET);
        translate = findViewById(R.id.translateBtn);
        translatedText = findViewById(R.id.textTV);


        // spinner 1
        ArrayAdapter adapter1 = new ArrayAdapter(this, R.layout.spinner_item, fLanguages);
        adapter1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        firstLangSpinner.setAdapter(adapter1);

        firstLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                firstLangCode = GetLangCode(fLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.spinner_item, sLanguages);
        adapter2.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        secondLangSpinner.setAdapter(adapter2);

        secondLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secondLangCode = GetLangCode(sLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private String GetLangCode(String language) {
        String languageCode = " ";

        switch (language) {
            case "English":
                languageCode = TranslateLanguage.ENGLISH;
                break;
            case "Russian":
                languageCode = TranslateLanguage.RUSSIAN;
                break;
            case "Polska":
                languageCode = TranslateLanguage.POLISH;
                break;
            default:
                languageCode = "";
        }

        return languageCode;
    }
}