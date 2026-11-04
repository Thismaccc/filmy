package com.example.filmy;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.filmy.Film;
import com.example.filmy.FilmyDataBase;
import com.example.filmy.R;

public class MainActivity extends AppCompatActivity {
    private FilmyDataBase filmDatabase;

    private EditText editTextTytul,editTextOcena, getEditTextRokWydania;
    private Spinner spinnerGatunek;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        editTextTytul = findViewById(R.id.editTextText);
        editTextOcena = findViewById(R.id.editTextNumber);
        getEditTextRokWydania= findViewById(R.id.editTextNumber2);
        button = findViewById(R.id.button);


        filmDatabase = Room.databaseBuilder(
                getApplicationContext(),
                FilmyDataBase.class,
                "filmy_db"
        ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        filmDatabase.zwrocFilmyDAO().wstawDoBazy(
                new Film("Incepcja", "Sci-Fi", 6, 2008)
        );


        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Film film = new Film(
                                editTextTytul.getText().toString(),
                                spinnerGatunek.getSelectedItem().toString(),
                                Integer.parseInt(editTextOcena.getText().toString()),
                                Integer.parseInt(getEditTextRokWydania.getText().toString())
                        );
                        filmDatabase.zwrocFilmyDAO().wstawDoBazy(film);
                    }
                }
        );
    }
}