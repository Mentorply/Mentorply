package com.example.mentorply.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//import org.parceler.Parcels;
import com.bumptech.glide.Glide;
import com.example.mentorply.DetailedProgramsActivity;
import com.example.mentorply.R;
import com.example.mentorply.models.Program;
import com.parse.ParseFile;

//import org.parceler.Parcels;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<Program> programs;

    public ProgramAdapter(Context context, List <Program> programs){
        this.context = context;
        this.programs = programs;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_program, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Program program = programs.get(position);
        holder.bind(program);
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    public void clear() {
        programs.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Program> list) {
        programs.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private ImageView ivProfileImage;
        private TextView tvDescription;
        private TextView tvRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvDescription = itemView.findViewById(R.id.tvUserDescription);
            tvRole = itemView.findViewById(R.id.tvRole);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Program program = programs.get(position);
                    Intent i = new Intent(context, DetailedProgramsActivity.class);
                    i.putExtra(Program.class.getSimpleName(), Parcels.wrap(program));
                    context.startActivity(i);

                }
            });

        }

        public void bind(Program program) {
            //Bind the program data to view elements
            tvDescription.setText(program.getDescription());
            tvName.setText(program.getName());
            ParseFile image = program.getImage();
            if (image!=null){
                Glide.with(context).load(program.getImage().getUrl()).into(ivProfileImage);
            }
        }
    }
}
