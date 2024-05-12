package com.example.blue_books;


import static android.text.TextUtils.TruncateAt.END;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface MyDaoBookstore {
    @Insert
    void InsertBook (Book book);

    @Insert
    void InsertBookstore(Bookstore bookstore);

    @Insert
    void InsertBranch(Branch branch);

    @Update
    void UpdateBook(Book book);

    @Update
    void UpdateBookstore(Bookstore bookstore);

    @Update
    void UpdateBranch(Branch branch);

    @Delete
    void DeleteBranch(Branch branch);

    @Delete
    void DeleteBookstore(Bookstore bookstore);

    @Delete
    void DeleteBook(Book book);

    @Query("select *  from bookstore")
    List<Bookstore> getBookstores();

    @Query("select *  from branch")
    List<Branch> getBranches();

    @Query("select *  from book")
    List<Book> getBooks();



    @Query("select bookstore_name  from bookstore")

    List<String> getBookstoresNames();

    @Query("select branch_name  from branch")

    List<String> getBranchesNames();


    @Query("SELECT DISTINCT bookstore_id  " +
            "FROM Bookstore   " +
            "WHERE bookstore_name = :inputname ")
    public int getBookstoreId(String inputname);

    @Query("SELECT DISTINCT branch_bookstoreid  " +
            "FROM Branch   " +
            "WHERE branch_id = :inputid ")
    public int getBookstoreIdFromBranchId(String inputid);

    @Query("SELECT DISTINCT branch_id  " +
            "FROM Branch   " +
            "WHERE branch_name = :inputname ")
    public int getBranchId(String inputname);

//CHECK EXISTANCE
    @Query("SELECT EXISTS(SELECT* FROM bookstore WHERE bookstore_id = :bookstoreId)")
    Boolean bs_exists(int bookstoreId);


    @Query("SELECT EXISTS(SELECT* FROM branch WHERE branch_id = :input)")

    Boolean branch_exists(int input);
    @Query("SELECT EXISTS(SELECT* FROM book WHERE book_isbn = :input)")

    Boolean book_exists(int input);
    @Query("SELECT DISTINCT bookstore_name  " +
            "FROM bookstore   " +
            "WHERE bookstore_id = :inputid ")
    public String getBookstoreName(String inputid);

    @Query("SELECT DISTINCT branch_name  " +
            "FROM branch   " +
            "WHERE branch_id = :inputid ")
    public String getBranchName(String inputid);
    @Query("SELECT DISTINCT book_isbn  " +
            "FROM book   " +
            "WHERE book_name = :inputname ")
    public int getBooksIsbn_withName(String inputname);
    @Query("SELECT DISTINCT book_category  " +
            "FROM book    " +
            "WHERE book_name = :inputname ")
    public String getBooksCategory_withName(String inputname);
    @Query("SELECT DISTINCT book_count  " +
            "FROM book   " +
            "WHERE book_name = :inputname ")
    public int getBooksCount_withName(String inputname);

    @Query("SELECT DISTINCT book_name  " +
            "FROM book   " +
            "WHERE book_isbn = :inputid ")
    public String getBooksNames(String inputid);

    @Query("SELECT DISTINCT book_count  " +
            "FROM book   " +
            "WHERE book_isbn = :inputid ")
    public int getBooksCount(int inputid);

    @Query("UPDATE book " +
            "SET book_count = :inputcount  " +
            "WHERE book_name = :inputname ")
    public void UpdateBooksCountwithName(String inputname, int inputcount);
    @Query("select book_name  from book")
    List<String> getBookNames();
    @Query("SELECT DISTINCT book_price  " +
            "FROM book   " +
            "WHERE book_isbn = :inputid ")
    public int getBookPrice(int inputid);
    @Query("SELECT DISTINCT book_price  " +
            "FROM book   " +
            "WHERE book_name = :inputname ")
    public int getBookPrice_withName(String inputname);

    @Query("SELECT DISTINCT branch_name  " +
            "FROM branch   " +
            "WHERE branch_bookstoreid = :bsid ")
    List<String> getBranchesNamesThatBelongToBookstore(int bsid);


    @Query("DELETE  FROM branch   " +
            "WHERE branch_bookstoreid > 0 ")
    public void DeleteAllBranches();

    @Query("SELECT * FROM branch ORDER BY branch_name ASC")
     List<Branch> getBranchesSortByAscLastName();
    @Query("SELECT * FROM branch ORDER BY branch_name DESC")
    List<Branch> getBranchesSortByDescLastName();


    @Query("SELECT COUNT(DISTINCT branch_name) FROM branch ")
    int getBranchesCount();

    @Query("SELECT COUNT(DISTINCT book_name) FROM book ")
 int getBooksCount();

    @Query("SELECT COUNT(DISTINCT bookstore_name) FROM bookstore ")
    int getBookstoreCount();


}
