package com.example.firebase_crud_noteapp;

import androidx.annotation.NonNull;

import com.example.firebase_crud_noteapp.models.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoteServices {

    private static NoteServices _instance = null;
    private final DatabaseReference reference;

    public NoteServices() {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static NoteServices getInstance() {
        if (_instance == null) {
            _instance = new NoteServices();
        }
        return _instance;
    }

    public void getNotes(ArrayList<Note> list, NoteServiceCallback callback) {
        reference.child("note").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Note note = snapshot.getValue(Note.class);
                list.add(note);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        callback.callback();
    }

    public void addNote(String title, String content) {
        DatabaseReference ref = reference.child("note").push();
        Note note = new Note(ref.getKey(), title, content);
        ref.setValue(note);
    }

    public void updateNote(Note note) {
        reference.child("note").child(note.getUid()).setValue(note);
    }

    public void removeNote(String uid) {
        reference.child("note").child(uid).removeValue();
    }

    public void removeNote(Note note) {
        reference.child("note").child(note.getUid()).removeValue();
    }

    public interface NoteServiceCallback {
        void callback();
    }

}
