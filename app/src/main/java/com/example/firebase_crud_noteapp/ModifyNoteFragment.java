package com.example.firebase_crud_noteapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firebase_crud_noteapp.databinding.FragmentModifyNoteBinding;
import com.example.firebase_crud_noteapp.models.Note;

public class ModifyNoteFragment extends Fragment {

    FragmentModifyNoteBinding binding;
    NoteServices services;
    Note note;

    public ModifyNoteFragment(Note note) {
        this.note = note;
        this.services = NoteServices.getInstance();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentModifyNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edtTitle.setText(note.getTitle());
        binding.edtContent.setText(note.getContent());

        binding.btnEdit.setOnClickListener(v -> {
            this.note.setTitle(binding.edtTitle.getText().toString());
            this.note.setContent(binding.edtContent.getText().toString());
            services.updateNote(note);

            getActivity().onBackPressed();
        });

    }
}