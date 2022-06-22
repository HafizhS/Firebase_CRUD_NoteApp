package com.example.firebase_crud_noteapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.firebase_crud_noteapp.databinding.ActivityMainBinding;
import com.example.firebase_crud_noteapp.models.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ArrayList<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        NoteAdapter adapter = new NoteAdapter(this, noteList, new NoteAdapter.NoteAdapterListener() {
            @Override
            public void onEditClicked(Note note) {
                ModifyNoteFragment fragment = new ModifyNoteFragment(note);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .add(android.R.id.content, fragment)
                        .commit();
            }

            @Override
            public void onClicked(Note note) {
                DetailNoteFragment fragment = new DetailNoteFragment(note);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .add(android.R.id.content, fragment)
                        .commit();
            }
        });

        binding.fab.setOnClickListener(v -> {
            AddNoteFragment fragment = new AddNoteFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(android.R.id.content, fragment)
                    .commit();
        });

        reference.child("note").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noteList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Note note = dataSnapshot.getValue(Note.class);
                    noteList.add(note);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getFragments().size() != 0) {
            Fragment fragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .remove(fragment)
                    .commit();

            return;
        }

        super.onBackPressed();

    }
}