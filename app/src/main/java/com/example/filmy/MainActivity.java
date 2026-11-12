package com.example.filmy;

import android.os.Bundle;
import android.annotation.SuppressLint;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.filmy.Film;
import com.example.filmy.FilmyDataBase;
import com.example.filmy.R;

public class MainActivity extends AppCompatActivity {
    private FilmyDataBase filmDatabase;

    private EditText editTextTytul,editTextOcena, getEditTextRokWydania;
    private Spinner spinnerGatunek;
    private Button button, buttonAktualizacja;
    private ListView listView;
    private ArrayAdapter<Film> arrayAdapter;
    private List<Film> filmy;
    private Film kliknietyFilm;

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

        buttonAktualizacja=findViewById(R.id.buttonAktualizuj);
        editTextTytul = findViewById(R.id.editTextText);
        editTextOcena = findViewById(R.id.editTextNumber);
        spinnerGatunek = findViewById(R.id.spinner);
        getEditTextRokWydania = findViewById(R.id.editTextNumber2);
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
                        filmy.add(film);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
        );

        listView = findViewById(R.id.filmylist);
        filmy = filmDatabase.zwrocFilmyDAO().zwrocWszystkieFilmy();
        arrayAdapter=new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                filmy);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        kliknietyFilm = filmy.get(i);
                        editTextTytul.setText(kliknietyFilm.tytul);

                        getEditTextRokWydania.setText(String.valueOf(kliknietyFilm.rokWydania));

                        ArrayList<String> gatunki = new ArrayList<String>(
                                Arrays.asList("Sci-Fi", "Romans", "Akcji", "Komedia")
                        );
                        spinnerGatunek.setSelection(gatunki.indexOf(kliknietyFilm.gatunek));
                        editTextOcena.setText(String.valueOf(kliknietyFilm.ocena));
                    }
                }
        );
        buttonAktualizacja.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        kliknietyFilm.tytul = editTextTytul.getText().toString();
                        kliknietyFilm.gatunek = spinnerGatunek.getSelectedItem().toString();
                        kliknietyFilm.rokWydania = Integer.parseInt(getEditTextRokWydania.getText().toString());
                        kliknietyFilm.ocena = Integer.parseInt(editTextOcena.getText().toString());


                        filmDatabase.zwrocFilmyDAO().zaktualizujFilm(kliknietyFilm);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
        );

    }
}