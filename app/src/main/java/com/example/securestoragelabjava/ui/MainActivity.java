package com.example.securestoragelabjava.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.securestoragelabjava.R;
import com.example.securestoragelabjava.cache.CacheStore;
import com.example.securestoragelabjava.files.InternalTextStore;
import com.example.securestoragelabjava.files.StudentsJsonStore;
import com.example.securestoragelabjava.model.Student;
import com.example.securestoragelabjava.prefs.AppPrefs;
import com.example.securestoragelabjava.prefs.SecurePrefs;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SecureStorageJava";

    private final List<String> langs =
            Arrays.asList("fr", "en", "ar");

    private EditText etName;
    private EditText etToken;
    private Spinner spLang;
    private Switch swDark;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etToken = findViewById(R.id.etToken);
        spLang = findViewById(R.id.spLang);
        swDark = findViewById(R.id.swDark);
        tvResult = findViewById(R.id.tvResult);

        setupLangSpinner();

        Button btnSavePrefs =
                findViewById(R.id.btnSavePrefs);

        Button btnLoadPrefs =
                findViewById(R.id.btnLoadPrefs);

        Button btnSaveJson =
                findViewById(R.id.btnSaveJson);

        Button btnLoadJson =
                findViewById(R.id.btnLoadJson);

        Button btnClear =
                findViewById(R.id.btnClear);

        btnSavePrefs.setOnClickListener(v -> savePrefs());

        btnLoadPrefs.setOnClickListener(v -> loadPrefsToUi());

        btnSaveJson.setOnClickListener(v -> saveJsonFile());

        btnLoadJson.setOnClickListener(v -> loadJsonFile());

        btnClear.setOnClickListener(v -> clearAll());

        loadPrefsToUi();
    }

    private void setupLangSpinner() {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        langs
                );

        spLang.setAdapter(adapter);
    }

    // Sauvegarder préférences
    private void savePrefs() {

        String name =
                etName.getText().toString().trim();

        String lang =
                langs.get(
                        Math.max(
                                0,
                                spLang.getSelectedItemPosition()
                        )
                );

        String theme =
                swDark.isChecked() ? "dark" : "light";

        boolean ok =
                AppPrefs.save(
                        this,
                        name,
                        lang,
                        theme,
                        false
                );

        String token =
                etToken.getText().toString();

        if (!token.isBlank()) {

            try {

                SecurePrefs.saveToken(this, token);

            } catch (Exception e) {

                tvResult.setText(
                        "Erreur chiffrement token"
                );

                return;
            }
        }

        // Aucun token dans Logcat
        Log.d(
                TAG,
                "Prefs sauvegardées ok="
                        + ok
                        + ", name="
                        + name
                        + ", lang="
                        + lang
                        + ", theme="
                        + theme
        );

        try {

            CacheStore.write(
                    this,
                    "last_ui.txt",
                    "name=" + name
                            + ", lang=" + lang
                            + ", theme=" + theme
            );

        } catch (Exception ignored) {
        }

        tvResult.setText(
                "Sauvegarde prefs terminée.\n"
                        + "name=" + name + "\n"
                        + "lang=" + lang + "\n"
                        + "theme=" + theme + "\n"
                        + "token stocké chiffré."
        );
    }

    // Charger préférences
    private void loadPrefsToUi() {

        AppPrefs.Triple triple =
                AppPrefs.load(this);

        etName.setText(triple.name);

        swDark.setChecked(
                "dark".equals(triple.theme)
        );

        int idx =
                langs.indexOf(triple.lang);

        spLang.setSelection(
                idx >= 0 ? idx : 0
        );

        int tokenLen = 0;

        try {

            String token =
                    SecurePrefs.loadToken(this);

            tokenLen =
                    token == null
                            ? 0
                            : token.length();

        } catch (Exception ignored) {
        }

        tvResult.setText(
                "Chargement prefs terminé.\n"
                        + "name=" + triple.name + "\n"
                        + "lang=" + triple.lang + "\n"
                        + "theme=" + triple.theme + "\n"
                        + "tokenLength=" + tokenLen
        );

        Log.d(
                TAG,
                "Prefs chargées name="
                        + triple.name
                        + ", lang="
                        + triple.lang
                        + ", theme="
                        + triple.theme
                        + ", tokenLength="
                        + tokenLen
        );
    }

    // Sauvegarder JSON
    private void saveJsonFile() {

        List<Student> students =
                Arrays.asList(
                        new Student(1, "Amina", 20),
                        new Student(2, "Omar", 21),
                        new Student(3, "Sara", 19)
                );

        try {

            StudentsJsonStore.save(this, students);

            InternalTextStore.writeUtf8(
                    this,
                    "note.txt",
                    "Sauvegarde JSON effectuée UTF-8."
            );

        } catch (Exception e) {

            tvResult.setText(
                    "Erreur sauvegarde JSON"
            );

            return;
        }

        Log.d(
                TAG,
                "Fichiers internes écrits"
        );

        tvResult.setText(
                "Sauvegarde fichier JSON terminée.\n"
                        + "students="
                        + students.size()
        );
    }

    // Charger JSON
    private void loadJsonFile() {

        List<Student> students =
                StudentsJsonStore.load(this);

        String note;

        try {

            note =
                    InternalTextStore.readUtf8(
                            this,
                            "note.txt"
                    );

        } catch (Exception e) {

            note = "(note.txt absent)";
        }

        StringBuilder sb =
                new StringBuilder();

        sb.append(
                "Chargement JSON terminé.\n"
        );

        sb.append("note=")
                .append(note)
                .append("\n");

        sb.append("students=")
                .append(students.size())
                .append("\n");

        for (Student s : students) {

            sb.append(" - id=")
                    .append(s.id)
                    .append(", name=")
                    .append(s.name)
                    .append(", age=")
                    .append(s.age)
                    .append("\n");
        }

        tvResult.setText(sb.toString());

        Log.d(
                TAG,
                "JSON chargé students="
                        + students.size()
        );
    }

    // Nettoyage complet
    private void clearAll() {

        AppPrefs.clear(this);

        try {

            SecurePrefs.clear(this);

        } catch (Exception ignored) {
        }

        StudentsJsonStore.delete(this);

        InternalTextStore.delete(
                this,
                "note.txt"
        );

        int purged =
                CacheStore.purge(this);

        etName.setText("");

        etToken.setText("");

        swDark.setChecked(false);

        spLang.setSelection(0);

        tvResult.setText(
                "Nettoyage terminé.\n"
                        + "cache purgé="
                        + purged
        );

        Log.d(
                TAG,
                "Nettoyage terminé"
        );
    }
}