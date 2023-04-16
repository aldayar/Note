package com.geeks.notes;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements NoteAdapter.IOnItem {

    RecyclerView recyclerView;
    NoteAdapter adapter;
    Button add;
    private EditText mainEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add = view.findViewById(R.id.add);
        recyclerView = view.findViewById(R.id.recycler);
        mainEditText=view.findViewById(R.id.search_edit);

        adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);


        requireActivity().getSupportFragmentManager().setFragmentResultListener("editNote", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getSerializable("newModel");
                adapter.changeModel(result.getInt("posModel"), note);
            }
        });

        add.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new AddFragment())
                    .commit();
        });


    }

    @Override
    public void delete(int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
        alert.setTitle("Warning");
        alert.setMessage("Are sure to delete the note?");
        alert.setPositiveButton("Delete",(dialogInterface, i) -> {
            adapter.delete(pos);
        });
        alert.setNegativeButton("Cancel",null);
        alert.show();
    }

    @Override
    public void edit(int pos, Note note) {
        if (getArguments()!=null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", note);
            bundle.putInt("position", pos);
            AddFragment addFragment = new AddFragment();
            addFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new AddFragment()).commit();
        }else {
            Toast.makeText(getContext().getApplicationContext(),"The note is empty",Toast.LENGTH_SHORT).show();
        }



    }
}