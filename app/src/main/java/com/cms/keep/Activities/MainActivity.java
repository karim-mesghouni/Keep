package com.cms.keep.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cms.keep.Adapter.NotesAdapter;
import com.cms.keep.Listenrs.NoteOnClickListener;
import com.cms.keep.Model.Notes;
import com.cms.keep.R;
import com.cms.keep.ViewModel.NotesViewModel;
import com.cms.keep.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NotesViewModel viewModel;
    private NotesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.mainToolBar);

        binding.mainRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        viewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        viewModel.init(getApplication());
        noFilter();
        binding.mainFloatButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,AddUpdateNotes.class).putExtra("id",-1));

        });
        binding.noFilter.setOnClickListener(v -> {
            binding.noFilter.setBackgroundResource(R.drawable.rec_03);
            binding.htolFilter.setBackgroundResource(R.drawable.rec_02);
            binding.ltohFilter.setBackgroundResource(R.drawable.rec_02);
            showData(1);
        });
        binding.ltohFilter.setOnClickListener(v -> {
            binding.noFilter.setBackgroundResource(R.drawable.rec_02);
            binding.htolFilter.setBackgroundResource(R.drawable.rec_02);
            binding.ltohFilter.setBackgroundResource(R.drawable.rec_03);
            showData(3);
        });
        binding.htolFilter.setOnClickListener(v ->{
            binding.noFilter.setBackgroundResource(R.drawable.rec_02);
            binding.htolFilter.setBackgroundResource(R.drawable.rec_03);
            binding.ltohFilter.setBackgroundResource(R.drawable.rec_02);
            showData(2);
        });

    }
   private void showData(int pri){
        switch (pri){
            case 1 :
                noFilter();
                break;
            case 2:
                HtoL();
                break;
            case 3 :
                LtoH();
                break;
        }
   }

    private void LtoH() {
        viewModel.getLtoH().observe(this, notes -> {
            setData(notes);
        });
    }

    private void HtoL() {
        viewModel.getHtoL().observe(this,notes -> {
            setData(notes);
        });
    }

    private void noFilter() {
        viewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setData(notes);
            }
        });
    }

    private void setData(List<Notes> notes){
        adapter = new NotesAdapter(notes, this, new NoteOnClickListener() {
            @Override
            public void onClickListener(int id) {
                Intent intent = new Intent(MainActivity.this,AddUpdateNotes.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });
        //adapter.notifyDataSetChanged();
        binding.mainRecyclerView.setAdapter(adapter);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        androidx.appcompat.widget.SearchView search = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.main_menu_search_view).getActionView();
        search.setSubmitButtonEnabled(true);
        search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Notes> note = viewModel.getNotes("%"+query+"5");
                setData(note);
                //Log.d("tag",note.get(1).getTitle());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Notes> note = viewModel.getNotes("%"+newText+"%");
                setData(note);
                return true;
            }
        });
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                noFilter();
                return true;
            }
        });

        return true;
    }

    private void ApplySearch(androidx.appcompat.widget.SearchView search) {
        search.setSubmitButtonEnabled(true);
        search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Notes> note = viewModel.getNotes(query);
                setData(note);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Notes> note = viewModel.getNotes(newText);
                setData(note);
                return true;
            }
        });
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                noFilter();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_menu_search_view:
                return true;


        }
        return true;
    }
}