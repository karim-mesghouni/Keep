package com.cms.keep.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cms.keep.Model.Notes;
import com.cms.keep.R;
import com.cms.keep.ViewModel.NotesViewModel;
import com.cms.keep.databinding.ActivityAddUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;

public class AddUpdateNotes extends AppCompatActivity {
    private ActivityAddUpdateNotesBinding binding;
    private NotesViewModel viewModel;
    private boolean load = true;
//    private int comeFrom = -1;
    private int id;

    //private final String check = "\\u2713";
    private String title,subtitle,body,priority;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        load = true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.editToolBar);
        viewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        priority = "0";
        id = getIntent().getIntExtra("id",0);
        Log.d("Tad",id+"");
        if (!(id == -1)){
            loadData(id);
        }


        binding.mainFloatButton.setOnClickListener(v -> {
            inputData();
        });
        binding.priorityGreen.setOnClickListener(v -> {
            binding.priorityGreen.setText(R.string.done_mark);
            binding.priorityRed.setText("");
            binding.priorityYellow.setText("");
            priority = "1";
        });
        binding.priorityRed.setOnClickListener(v -> {
            binding.priorityGreen.setText("");
            binding.priorityRed.setText(R.string.done_mark);
            binding.priorityYellow.setText("");
            priority = "3";
        });
        binding.priorityYellow.setOnClickListener(v -> {
            binding.priorityGreen.setText("");
            binding.priorityRed.setText("");
            binding.priorityYellow.setText(R.string.done_mark);
            priority = "2";
        });
        binding.addNoteED.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.KEYCODE_1)
                    finish();
                return false;
            }
        });
//        binding.addNoteED.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//               final String c = (String) s;
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.addNoteED.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_NULL
//                        && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    Toast.makeText(AddUpdateNotes.this, "clicked", Toast.LENGTH_SHORT).show();
//
//                }
//                return true;
//            }
//        });

    }

    private void loadData(int id) {
//           if (load == false)
//                 return;

            Notes note = viewModel.getNote(id);
            if (note.getPriority().equals("1"))
                binding.priorityGreen.setText(R.string.done_mark);
            else if (note.getPriority().equals("2"))
                binding.priorityYellow.setText(R.string.done_mark);
            else
                binding.priorityRed.setText(R.string.done_mark);

            binding.addTitleED.setText(note.getTitle());
            binding.addSubTitleED.setText(note.getSubTitle());
            binding.addNoteED.setText(note.getBody());
            priority = note.getPriority();




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        MenuItem delete = menu.findItem(R.id.delete);
        if (id == -1)
            delete.setVisible(false);
        return true;
    }
    private void inputData(){
        title = binding.addTitleED.getText().toString();
        subtitle = binding.addSubTitleED.getText().toString();
        body = binding.addNoteED.getText().toString();
          if (priority.equals("0")){
              Toast.makeText(this, "You should check priority", Toast.LENGTH_SHORT).show();
              return;
          }

          if (title.equals(""))
              title = " ";
          if (subtitle.equals(""))
              subtitle = " ";

          if (body.equals(""))
              ignore();
          else
             createNote(title,subtitle,body);
    }

    private void createNote(String title, String subtitle, String body) {
        Notes note = new Notes();
        note.setTitle(title);
        note.setSubTitle(subtitle);
        note.setBody(body);
        note.setDate(getDate().toString());
        note.setPriority(priority);

        if (id == -1){
            viewModel.insertNote(note);
            Toast.makeText(this, "Note is created", Toast.LENGTH_SHORT).show();
        }
        else {
            viewModel.updateNote(id, note);
            Toast.makeText(this, "Note is updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void ignore() {
        finish();
        Toast.makeText(getApplicationContext(), "Empty note ignored", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (binding.addNoteED.getText().toString().equals(""))
            super.onBackPressed();
        //else


    }

    public CharSequence getDate() {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("d MMM yyyy",date.getTime());
        return sequence;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                showBottomDialog();
                break;
        }
        return true;
    }
    private void showBottomDialog(){
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this,R.style.BottomSheetStyle);
        View view = LayoutInflater.from(AddUpdateNotes.this).inflate(R.layout.delete_bottom_sheet,(LinearLayout)findViewById(R.id.linear));
        sheetDialog.setContentView(view);
        TextView yes , no;
        yes = view.findViewById(R.id.yes_button);
        no = view.findViewById(R.id.no_button);
        yes.setOnClickListener(v ->{
            viewModel.deleteNote(id);
            // load = false;
            finish();
        });
        no.setOnClickListener(v -> {
            sheetDialog.dismiss();
        });
        sheetDialog.show();
    }
}