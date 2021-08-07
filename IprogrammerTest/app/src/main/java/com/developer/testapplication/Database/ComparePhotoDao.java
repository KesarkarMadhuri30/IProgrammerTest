package com.developer.testapplication.Database;

import com.developer.testapplication.Model.PhotoListModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ComparePhotoDao {
    @Insert
    public void addPhotoData(PhotoListModel photoDataModel);

    @Query("SELECT * FROM comparephotolist WHERE photo_id=:id")
    List<PhotoListModel> getParticularData(int id);

    @Query("DELETE FROM comparephotolist WHERE photo_id=:id")
    void deleteBymatchId(int id);

    @Query("select * from comparephotolist")
    List<PhotoListModel> getCompareData();
}
