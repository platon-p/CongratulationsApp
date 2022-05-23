package com.example.myapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Card implements Serializable {
    @PrimaryKey
    public int id;

    public int presetId;
    public String name;
    public String gender;
    public String fio;
}
