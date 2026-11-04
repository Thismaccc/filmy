package com.example.filmy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface FilmDAO {

    @Insert
    void wstawDoBazy(Film film);

    @Insert
    void wstawWiecejFilmow(Film... filmy);

    @Delete
    void usunZBazy(Film film);

    @Update
    void zaktualizujFilm(Film film);

    @Query("SELECT * FROM filmy")
    List<Film> zwrocWszystkieFilmy();

    @Query("SELECT tytul FROM filmy WHERE ocena > :minOcena")
    List<String> zwrocTytulyFilmowZOcenaWyzszaNiz(double minOcena);
}