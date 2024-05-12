package com.example.blue_books;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

@Entity (tableName = "book",
        primaryKeys ={"book_bookstoreid","book_branchid","book_isbn"},
        foreignKeys={
                @ForeignKey(entity = Branch.class,
                        parentColumns="branch_id", //apo pou pairnw anafora
                        childColumns = "book_branchid", //to ononma tis stilis poy krataw anafora
                        onDelete=ForeignKey.RESTRICT,
                        onUpdate=ForeignKey.CASCADE),
                @ForeignKey(entity = Bookstore.class,
                        parentColumns="bookstore_id",
                        childColumns = "book_bookstoreid",
                        onDelete=ForeignKey.RESTRICT,
                        onUpdate=ForeignKey.CASCADE)

        })
//gia na mi mporw na kataxwrisw sto idio branch 2 fores to idio isbn
public class Book implements Serializable {

    @ColumnInfo(name = "book_isbn")
    private int  bisbn;

    @ColumnInfo(name = "book_name")
    private String bname;
    @ColumnInfo(name = "book_count")
    private int bcount;

    public int getBcount() {
        return bcount;
    }

    public void setBcount(int bcount) {
        this.bcount = bcount;
    }

    @ColumnInfo(name = "book_ekdotis")
    private String bekdotis;

    public String getBekdotis() {
        return bekdotis;
    }

    public void setBekdotis(String bekdotis) {
        this.bekdotis = bekdotis;
    }



    @ColumnInfo(name = "book_price")
    private int bprice;


    @ColumnInfo(name = "book_category")
    private String bcategory;

    @ColumnInfo(name = "book_branchid")
    private int bbranchid;

    @ColumnInfo(name = "book_bookstoreid")
    private int bbookstoreid;
    public Book(int bisbn, String bname,  int bprice, String bcategory, int bbranchid, int bbookstoreid ,String bekdotis, int bcount) {
        this.bisbn = bisbn;
        this.bname = bname;
        this.bprice = bprice;
        this.bcategory = bcategory;
        this.bbranchid=bbranchid;
        this.bbookstoreid=bbookstoreid;
        this.bekdotis=bekdotis;
        this.bcount=bcount;
    }

    public Book() {

    }

    public int getBisbn() {
        return bisbn;
    }

    public void setBisbn(int bisbn) {
        this.bisbn = bisbn;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }



    public int getBprice() {
        return bprice;
    }

    public void setBprice(int bprice) {
        this.bprice = bprice;
    }

    public String getBcategory() {
        return bcategory;
    }

    public void setBcategory(String bcategory) {
        this.bcategory = bcategory;
    }

    public int getBbranchid() {
        return bbranchid;
    }
public void setBbranchid(int bbranchid)
{
    this.bbranchid=bbranchid;
}

    public int getBbookstoreid() {
        return bbookstoreid;
    }

    public void setBbookstoreid(int bbookstoreid) {
        this.bbookstoreid = bbookstoreid;
    }

}
