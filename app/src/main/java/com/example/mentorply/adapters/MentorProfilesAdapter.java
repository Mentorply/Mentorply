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
import com.example.mentorply.ProfileActivity;
import com.example.mentorply.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class MentorProfilesAdapter extends RecyclerView.Adapter<MentorProfilesAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<ParseUser> mentors;

    public MentorProfilesAdapter(Context context, List <ParseUser> mentors){
        this.context = context;
        this.mentors = mentors;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParseUser mentor = mentors.get(position);
        holder.bind(mentor);
    }

    @Override
    public int getItemCount() {
        return mentors.size();
    }

    public void clear() {
        mentors.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ParseUser> list) {
        mentors.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private ImageView ivProfileImage;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvName);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvDescription = itemView.findViewById(R.id.tvUserDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ParseUser mentor = mentors.get(position);
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra(ParseUser.class.getSimpleName(), Parcels.wrap(mentor));
                    context.startActivity(i);

                }
            });

        }

        public void bind(ParseUser mentor) {
            //Bind the mentor data to view elements
            tvDescription.setText(mentor.getString("description"));
            tvUsername.setText(mentor.getString("username"));
            ParseFile image = mentor.getParseFile("profilePicture");
            if (image!=null){
                Glide.with(context).load(mentor.getParseFile("profilePicture").getUrl()).into(ivProfileImage);
            }
        }
    }
}
