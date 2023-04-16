package com.geeks.notes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class AddFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 1;

    private String imageUri, textTitle;
    private EditText editDesc, editTitle, editDate;
    private MaterialButton addButton;
    private String inputtedDate, inputtedDesc, inputtedTitle;
    private CardView cardView;
    private String image;
    Bundle bundle=new Bundle();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardView = view.findViewById(R.id.cardview);
        addButton = view.findViewById(R.id.add_btn);
        editTitle = view.findViewById(R.id.edit_title);
        editDesc = view.findViewById(R.id.edit_desc);
        editDate = view.findViewById(R.id.edit_date);

        //HomeWork№4
        if (getArguments() != null) {
            Note note = (Note) getArguments().getSerializable("key");
            editDate.setText(note.getDate());
            editTitle.setText(note.getTitle());
            editDesc.setText(note.getDesc());

            addButton.setText("Edit");
            addButton.setOnClickListener(v -> {

                inputtedDate = editDate.getText().toString();
                inputtedTitle = editTitle.getText().toString();
                inputtedDesc = editDesc.getText().toString();

                Note editedModel = new Note(image, inputtedTitle, inputtedDesc, inputtedDate);
                bundle.putSerializable("newModel", editedModel);
                int posModel = getArguments().getInt("position");
                bundle.putInt("posModel", posModel);
                Toast.makeText(getContext().getApplicationContext(),"The note successfully edited",Toast.LENGTH_SHORT);
                requireActivity().getSupportFragmentManager().setFragmentResult("editNote", bundle);
            });
        } else {
            addButton.setOnClickListener(v2 -> {
                String inputtedDate = editDate.getText().toString();
                String inputtedTitle = editTitle.getText().toString();
                String inputtedDesc = editDesc.getText().toString();

                Note addModel= new Note(image,inputtedTitle,inputtedDesc,inputtedDate);
                bundle.putSerializable("addModel",addModel);
                Toast.makeText(getContext().getApplicationContext(),"The note successfully added", Toast.LENGTH_SHORT);
                requireActivity().getSupportFragmentManager().setFragmentResult("addNote",bundle);

            });

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                image = data.getData().toString();
                cardView.setBackground(Drawable.createFromPath(image));
            }else {
                Toast.makeText(getContext().getApplicationContext(),"Nothing was chosen",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

