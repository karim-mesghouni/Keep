package com.cms.keep.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cms.keep.Model.Notes;
import com.cms.keep.Repository.NotesRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotesViewModel extends ViewModel {
    private NotesRepository repository;
    private LiveData<List<Notes>> allNotes;
//    public NotesViewModel(@NonNull @NotNull Application application) {
//        super(application);
//        repository = new NotesRepository(application);
//        allNotes = repository.getAllNotes();
//    }
    public void init(Application application){
        repository = new NotesRepository(application);
        allNotes = repository.getAllNotes();
    }
    public void insertNote(Notes note){
        repository.insertNote(note);
    }
    public void deleteNote(int id){
        repository.deleteNote(id);
    }
    public void updateNote(int id,Notes note){
        repository.updateNote(id,note);
    }
    public LiveData<List<Notes>> getHtoL(){
        return repository.getHtoL();
    }
    public LiveData<List<Notes>> getLtoH(){
        return repository.getLtoH();
    }
    public LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }
    public List<Notes> getNotes(String query){
        return repository.getNotes(query);
    }
    public Notes getNote(int id){
        return repository.getNote(id);
    }

}
