package com.example.blue_books;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "branch",
        primaryKeys ={"branch_id" },
        foreignKeys={

                @ForeignKey(entity = Bookstore.class,
                        parentColumns="bookstore_id",
                        childColumns = "branch_bookstoreid",
                        onDelete=ForeignKey.RESTRICT,
                        onUpdate=ForeignKey.CASCADE)

        })


public class Branch  implements Serializable {

    @ColumnInfo(name = "branch_id")
    private int  brid;

    @NonNull @ColumnInfo(name = "branch_name")
    private String  brname;

    @NonNull @ColumnInfo(name = "branch_afm")
    private int   afm;

    @NonNull @ColumnInfo(name = "branch_tel")
    private String   tel;

    @NonNull @ColumnInfo(name = "branch_adr")
    private String  adr;


    @ColumnInfo(name = "branch_bookstoreid")
    private int brbooksotreid;


    public Branch(int brid, @NonNull String brname, int afm, @NonNull String tel, @NonNull String adr, int brbooksotreid ) {
        this.brid = brid;
        this.brname = brname;
        this.afm = afm;
        this.tel = tel;
        this.adr = adr;
this.brbooksotreid=brbooksotreid;
    }

    public Branch() {

    }


    public int getBrbooksotreid() {
        return brbooksotreid;
    }

    public void setBrbooksotreid(int brbooksotreid) {
        this.brbooksotreid = brbooksotreid;
    }

    public int getBrid() {
        return brid;
    }

    public void setBrid(int brid) {
        this.brid = brid;
    }

    @NonNull
    public String getBrname() {
        return brname;
    }

    public void setBrname(@NonNull String brname) {
        this.brname = brname;
    }

    public int getAfm() {
        return afm;
    }

    public void setAfm(int afm) {
        this.afm = afm;
    }

    @NonNull
    public String getTel() {
        return tel;
    }

    public void setTel(@NonNull String tel) {
        this.tel = tel;
    }

    @NonNull
    public String getAdr() {
        return adr;
    }

    public void setAdr(@NonNull String adr) {
        this.adr = adr;
    }


}
