package com.example.firebase_crud_noteapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firebase_crud_noteapp.databinding.FragmentDetailNoteBinding;
import com.example.firebase_crud_noteapp.models.Note;

public class DetailNoteFragment extends Fragment {

    FragmentDetailNoteBinding binding;
    Note note;

    public DetailNoteFragment(Note note) {
        this.note = note;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvTitle.setText(note.getTitle());
        binding.tvContent.setText(note.getContent());

        binding.getRoot().setOnClickListener(v -> {
            getActivity().onBackPressed();
        });


    }
}