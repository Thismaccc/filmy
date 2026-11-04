package com.example.filmy;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.filmy.Film;
import com.example.filmy.FilmyDataBase;
import com.example.filmy.R;

public class MainActivity extends AppCompatActivity {
    private FilmyDataBase filmDatabase;

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

        filmDatabase = Room.databaseBuilder(
                getApplicationContext(),
                FilmyDataBase.class,
                "filmy_db"
        ).allowMainThreadQueries().build();

        filmDatabase.zwrocZwierzeDAO().wstawDoBazy(
                new Film("Incepcja", "Sci-Fi", 6, 2008)
        );
    }
}