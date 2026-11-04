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

    public double ocena;

    public int rokWydania;

    public Film() {
    }

    public Film(String tytul, String gatunek, double ocena, int rokWydania) {
        this.tytul = tytul;
        this.gatunek = gatunek;
        this.ocena = ocena;
        this.rokWydania = rokWydania;
    }
}
