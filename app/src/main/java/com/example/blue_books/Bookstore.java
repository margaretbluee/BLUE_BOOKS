package com.example.blue_books;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity (tableName = "bookstore")

public class Bookstore implements Serializable {



    public Bookstore(int bsid,   String bsname ) {
        this.bsid = bsid;
        this.bsname = bsname;

    }



    @NonNull    @PrimaryKey
    @ColumnInfo(name = "bookstore_id")
    private int  bsid;

    @NonNull @ColumnInfo(name = "bookstore_name")
    private String  bsname;

    public Bookstore() {

    }


    public int getBsid() {
        return bsid;
    }

    public void setBsid(int bsid) {
        this.bsid = bsid;
    }

    @NonNull
    public String getBsname() {
        return bsname;
    }

    public void setBsname(@NonNull String bsname) {
        this.bsname = bsname;
    }



}
