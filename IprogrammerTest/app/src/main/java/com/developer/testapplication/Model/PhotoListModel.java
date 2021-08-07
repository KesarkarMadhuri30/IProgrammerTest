package com.developer.testapplication.Model;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="comparephotolist")
public class PhotoListModel {
    @PrimaryKey(autoGenerate = true)
    private int autoid;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    @ColumnInfo(name = "albumId")
    @SerializedName("albumId")
    private int albumId;

    @ColumnInfo(name = "photo_id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "photo_title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "photo_url")
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "photo_thumbnailUrl")
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    public int getAlbumId() {
        return albumId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
