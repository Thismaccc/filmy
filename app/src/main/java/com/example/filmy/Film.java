package com.example.filmy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "filmy")
public class Film {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String tytul;

    @ColumnInfo(name = "gatunek_filmu")
    public String gatunek;

    public int ocena;

    public int rokWydania;

    public Film() {
    }

    public Film(String tytul, String gatunek, int ocena, int rokWydania) {
        this.tytul = tytul;
        this.gatunek = gatunek;
        this.ocena = ocena;
        this.rokWydania = rokWydania;
    }

    @Override
    public String toString() {
        return
                " tytul = '" + tytul + '\'' +
                ", gatunek = '" + gatunek + '\'' +
                ", ocena = " + ocena +
                ", rokWydania = " + rokWydania +
                '}';
    }
}
