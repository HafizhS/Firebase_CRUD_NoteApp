package com.example.firebase_crud_noteapp;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firebase_crud_noteapp.databinding.FragmentAddNoteBinding;

public class AddNoteFragment extends Fragment {

    FragmentAddNoteBinding binding;
    NoteServices services;

    public AddNoteFragment() {
        services = NoteServices.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAdd.setOnClickListener(v -> {
            String title = binding.edtTitle.getText().toString();
            String content = binding.edtContent.getText().toString();
            services.addNote(title, content);

            getActivity().onBackPressed();
        });

    }
}