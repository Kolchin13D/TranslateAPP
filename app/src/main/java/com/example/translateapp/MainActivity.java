package com.example.translateapp;

import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.NaturalLanguageTranslateRegistrar;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MainActivity extends AppCompatActivity {

    private Spinner firstLangSpinner, secondLangSpinner;
    private EditText enterText;
    private Button translate;
    private TextView translatedText;

    String[] fLanguages = {"First language", "English", "Russian", "Polska"};

    //String[] sLanguages = {"Second language", "English", "Russian", "Polska"};
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

        //  spinner 2
        //ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.spinner_item, sLanguages);
        //adapter2.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        secondLangSpinner.setAdapter(adapter1);

        secondLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secondLangCode = GetLangCode(fLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // translate button
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translatedText.setText("");

                if (enterText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter text!", Toast.LENGTH_SHORT).show();
                } else if (firstLangCode.isEmpty() || secondLangCode.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Select language!", Toast.LENGTH_SHORT).show();
                } else {
                    TranslateText(firstLangCode, secondLangCode, enterText.getText().toString());
                }
            }
        });

    }

    private void TranslateText(String firstLangCode, String secondLangCode, String string) {

        try {
            TranslatorOptions options = new TranslatorOptions.Builder()
                    .setSourceLanguage(firstLangCode)
                    .setTargetLanguage(secondLangCode)
                    .build();

            Translator translator = Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder().build();

            translator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            translatedText.setText("Translating...");

                            translator.translate(string)
                                    .addOnSuccessListener(new OnSuccessListener<String>() {
                                        @Override
                                        public void onSuccess(String s) {
                                            translatedText.setText(s);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Fail to translate!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Fail to load language!", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }


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