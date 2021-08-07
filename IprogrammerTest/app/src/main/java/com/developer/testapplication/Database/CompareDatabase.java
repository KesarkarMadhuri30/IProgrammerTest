package com.developer.testapplication.Database;

import com.developer.testapplication.Model.PhotoListModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities={PhotoListModel.class},version = 1)
public abstract class CompareDatabase extends RoomDatabase {

    public abstract ComparePhotoDao comparePhotoDao();
}