package com.example.filmy;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Film.class}, version = 1)
public abstract class FilmyDataBase extends RoomDatabase {
    public abstract FilmDAO zwrocZwierzeDAO();
}