package com.example.firebase_crud_noteapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_crud_noteapp.databinding.ItemNoteBinding;
import com.example.firebase_crud_noteapp.models.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    ArrayList<Note> list;
    Activity activity;

    NoteAdapterListener noteAdapterListener;

    public NoteAdapter(Activity activity, ArrayList<Note> list, NoteAdapterListener noteAdapterListener) {
        this.list = list;
        this.activity = activity;
        this.noteAdapterListener = noteAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = list.get(position);
        holder.binding.title.setText(note.getTitle());
        holder.binding.previewContent.setText(note.getContent());

        holder.binding.viewNormal.setOnLongClickListener(v -> {
            if (holder.binding.viewNormal.getVisibility() == View.VISIBLE) {
                holder.toggleView();
            }
            return true;
        });

        holder.binding.viewNormal.setOnClickListener(v -> {
            noteAdapterListener.onClicked(note);
        });

        holder.binding.btnEdit.setOnClickListener(v -> {
            noteAdapterListener.onEditClicked(note);
            holder.toggleView();
        });

        holder.binding.btnBack.setOnClickListener(v -> {
            holder.toggleView();
        });

        holder.binding.btnDelete.setOnClickListener(v -> {
            list.remove(note);
            notifyItemRemoved(position);
            NoteServices.getInstance().removeNote(note);
            holder.toggleView();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface NoteAdapterListener {
        void onEditClicked(Note note);

        void onClicked(Note note);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        ItemNoteBinding binding;

        public ViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void toggleView() {
            binding.viewNormal.setVisibility(binding.viewNormal.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            binding.viewDelete.setVisibility(binding.viewDelete.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        }
    }
}
