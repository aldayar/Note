package com.geeks.notes;

import android.app.AlertDialog;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> list = new ArrayList<>();
    private IOnItem listener;

    public NoteAdapter(IOnItem listener) {
        this.listener = listener;
    }

    public void setList(List<Note> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addNote(Note note){
        list.add(note);
        notifyDataSetChanged();
    }
    public void delete(int pos){
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public Note getItem(int pos){
        return list.get(pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;
        private TextView desc;
        private ImageView delete;
        private ImageView editNote;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.item_title);
            desc = itemView.findViewById(R.id.item_desc);
            delete = itemView.findViewById(R.id.delete);
            editNote=itemView.findViewById(R.id.editNote);
        }

        void bind(int position) {
            title.setText(list.get(position).getTitle());
            desc.setText(list.get(position).getDesc());
            Glide.with(itemView)
                    .load(list.get(position).getImage())
                    .transform(new CenterCrop(), new RoundedCorners(25))
                    .into(imageView);

            delete.setOnClickListener(v -> {
                listener.delete(getAdapterPosition());
            });
            editNote.setOnClickListener(v->{
                Note note = list.get(position);
                listener.edit(getAdapterPosition(),note);
            });
        }
    }

    interface  IOnItem{
        void delete(int pos );
        void edit (int pos,Note note);
    }
}

