package com.example.myapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {
    @PrimaryKey
    public int id;

    public int presetId;
    public String name;
    public String gender;
    public String fio;
    // TODO: pdfPath

    public Card(int id, int presetId, String name, String gender, String fio) {
        this.id = id;
        this.presetId = presetId;
        this.name = name;
        this.gender = gender;
        this.fio = fio;
    }
}
