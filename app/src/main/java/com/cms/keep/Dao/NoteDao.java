package com.cms.keep.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cms.keep.Model.Notes;

import java.util.List;

@androidx.room.Dao
public interface NoteDao {
    @Query("select * from Notes_Table")
    LiveData<List<Notes>> getNote();
    @Query("select * from Notes_Table ORDER BY priority DESC ")
    LiveData<List<Notes>> getHtoL();
    @Query("select * from Notes_Table ORDER BY priority ASC ")
    LiveData<List<Notes>> getLtoH();
    @Query("select * from Notes_Table WHERE title LIKE :query")
    List<Notes> getNote(String query);
    @Insert
    void insertNotes(Notes... note);
    @Query("delete from Notes_Table where id=:id")
    void deleteNote(int id);

    @Query("UPDATE Notes_Table SET title = :title1, subTitle = :subtitle1, body = :body1 WHERE id = :id")
    void updateNotes(int id ,String title1,String subtitle1,String body1);
    @Query("SELECT * FROM Notes_Table WHERE id = :id")
    Notes getNote(int id);

}
