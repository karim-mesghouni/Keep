 package com.cms.keep.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cms.keep.Dao.NoteDao;
import com.cms.keep.DataBase.NotesDataBase;
import com.cms.keep.Model.Notes;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotesRepository {
    public NoteDao notesDao;
    private  LiveData<List<Notes>> allNotes;
    public NotesRepository(Application application){
        NotesDataBase dataBase = NotesDataBase.getInstance(application);
        notesDao = dataBase.noteDao();
        allNotes = notesDao.getNote();


    }
    public void insertNote(Notes note){

        new InsertAsyncTask(notesDao).execute(note);
    }
    public void updateNote(int id , @NotNull Notes notes){
        new UpdateAsyncTask(notesDao).execute(notes);
    }
    public void deleteNote(int id){
        new DeleteAsyncTask(notesDao).execute(id);
    }
    public  LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }
    public  List<Notes> getNotes(String query){
        return notesDao.getNote(query);
    }
    public LiveData<List<Notes>> getHtoL(){return  notesDao.getHtoL();}
    public LiveData<List<Notes>> getLtoH(){return  notesDao.getLtoH();}
    public Notes getNote(int id){return notesDao.getNote(id);}
    private static class InsertAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao dao;
        public InsertAsyncTask(NoteDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Notes... notes) {
            dao.insertNotes(notes[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao dao;
        public UpdateAsyncTask(NoteDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Notes... notes) {
            dao.updateNotes(notes[0].getId(),notes[0].getTitle(),notes[0].getSubTitle(),notes[0].getBody());
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<Integer,Void,Void>{
        private NoteDao dao;
        public DeleteAsyncTask(NoteDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            dao.deleteNote(integers[0]);
            return null;
        }
    }

}
