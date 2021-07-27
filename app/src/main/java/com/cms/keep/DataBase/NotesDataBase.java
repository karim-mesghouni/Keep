package com.cms.keep.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cms.keep.Dao.NoteDao;
import com.cms.keep.Model.Notes;

import org.jetbrains.annotations.NotNull;

@Database(entities = Notes.class,version = 1)
public abstract class NotesDataBase extends RoomDatabase {
    private static NotesDataBase instance;
    public abstract NoteDao noteDao();

    public static synchronized NotesDataBase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , NotesDataBase.class, "Note_Database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new insertData(instance).execute();
        }
    };
    private static class insertData extends AsyncTask<Void,Void,Void>{
        private NoteDao dao;

        public insertData(NotesDataBase db) {
            this.dao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insertNotes(new Notes("title","subtile","body","date","1"));
            return null;
        }
    }
}
