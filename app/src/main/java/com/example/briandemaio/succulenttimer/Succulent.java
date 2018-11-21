package com.example.briandemaio.succulenttimer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "succulent_table")
public class Succulent {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "succulent")
    private String name;

    private int imageResource;
    private int nameId;

    @ColumnInfo(name = "timeId")
    private int timeId;

    @ColumnInfo(name = "expiryTime")
    private long expiryTime;

    public Succulent(@NonNull String name, @NonNull int imageResource, @NonNull long expiryTime) {
        this.name = name;
        this.imageResource = imageResource;
        this.expiryTime = expiryTime;
        this.timeId = (int) expiryTime;
    }

    @Ignore
    public Succulent(int id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Succulent(int nameId, int imageResource) {
        this.nameId = nameId;
        this.imageResource = imageResource;
    }

    public int getId(){return id;}

    public void setId(int id) {
        this.id = id;
    }

    public void setExpiryTime(long expiryTime){this.expiryTime = expiryTime;}

    public long getExpiryTime(){return expiryTime;}

    public int getTimeId(){return timeId;}

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }

    public void setNameId(int nameId) {
        this.nameId=nameId;
    }

    public int getNameId() {
        return nameId;
    }

    public int getImageResource() {
        return imageResource;
    }

}
