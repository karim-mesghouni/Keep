package com.cms.keep.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cms.keep.Listenrs.NoteOnClickListener;
import com.cms.keep.Model.Notes;
import com.cms.keep.R;
import com.cms.keep.databinding.CardNoteBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{
    private List<Notes> notesList;
    private Context context;
    private NoteOnClickListener listener;


    public NotesAdapter(List<Notes> notesList, Context context ,NoteOnClickListener listener) {
        this.notesList = notesList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CardNoteBinding binding = CardNoteBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotesAdapter.ViewHolder holder, int position) {
      Notes note = notesList.get(position);
      String title = note.getTitle();
      String subTitle = note.getSubTitle();
      String priority = note.getPriority();
      String date = note.getDate();

      if (priority.equals("1"))
          holder.binding.cardPriority.setBackgroundResource(R.drawable.circl_green);
      else if (priority.equals("2"))
          holder.binding.cardPriority.setBackgroundResource(R.drawable.circl_yellow);
      else if (priority.equals("3"))
          holder.binding.cardPriority.setBackgroundResource(R.drawable.circl_red);
      else
          holder.binding.cardPriority.setBackgroundResource(0);
      holder.binding.cardTitle.setText(title);
      holder.binding.cardSubTitle.setText(subTitle);
      holder.binding.cardDate.setText(date);
      holder.id = note.getId();
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardNoteBinding binding;
        int id;
        public ViewHolder(@NonNull @NotNull View itemView,CardNoteBinding binding) {
            super(itemView);
            this.binding = binding;
            itemView.setOnClickListener(v -> {
                listener.onClickListener(id);
              //  Log.d("tag",""+id);
            });

        }

    }
}
