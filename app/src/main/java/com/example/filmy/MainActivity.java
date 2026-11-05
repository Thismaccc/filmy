package com.example.filmy;

import android.os.Bundle;
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

import com.example.filmy.Film;
import com.example.filmy.FilmyDataBase;
import com.example.filmy.R;

public class MainActivity extends AppCompatActivity {
    private FilmyDataBase filmDatabase;

    private EditText editTextTytul,editTextOcena, getEditTextRokWydania;
    private Spinner spinnerGatunek;
    private Button button;

    private ListView listView;
    private ArrayAdapter<Film> arrayAdapter;
    private List<Film> filmy;


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
        spinnerGatunek = findViewById(R.id.spinner);
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

        listView = findViewById(R.id.filmylist);
        filmy = filmDatabase.zwrocFilmyDAO().zwrocWszystkieFilmy();
        arrayAdapter=new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                filmy);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        filmDatabase.zwrocFilmyDAO().usunZBazy(filmy.get(i));
                        filmy.remove(i);
                        arrayAdapter.notifyDataSetChanged();
                        return false;
                    }
                }
        );

    }
}