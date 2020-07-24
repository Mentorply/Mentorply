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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.mentorply.ProfileActivity;
import com.example.mentorply.R;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ViewHolder> {
    Context context;
    List<ParseUser> users;

    //Pass in the context and list of users
    public ChoicesAdapter(Context context, List<ParseUser> users) {
        this.context = context;
        this.users = users;
    }

    //For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choice, parent, false);
        return new ViewHolder(view);
    }

    //Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data at the position
        ParseUser user = users.get(position);
        //bind the user with view holder
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void clear() {
        users.clear();
        notifyDataSetChanged();
    }

    ;

    public void addAll(List<ParseUser> userList) {
        users.addAll(userList);
        notifyDataSetChanged();
    }

    //Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivProfileImage;
        TextView tvUserDescription;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUserDescription = itemView.findViewById(R.id.tvUserDescription);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        public void bind(ParseUser user) {
            try {
                tvName.setText(user.fetchIfNeeded().getString("username"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                tvUserDescription.setText(user.fetchIfNeeded().getString("description"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Glide.with(context)
                    .load(user.getParseFile("profilePicture"))
                    .transform(new CircleCrop())
                    .into(ivProfileImage);
            //If there's an image
           /* if (user.userImageUrls.size() > 0) {
                //Set media
                Glide.with(context)
                        .load(user.userImageUrls.get(0))
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .into(ivParseUserImage);

                //Recovers visibility on a recycled item after it had been toggled off
                ivParseUserImage.setVisibility(View.VISIBLE);
            } else {
                //No image? Hide the view.
                ivParseUserImage.setVisibility(View.GONE);
            }
*/

           /* //show the image for the user if there is an actual image that exists
            if (user.userImageUrl==null){
                 ivParseUserImage.setVisibility(View.GONE);
            }
            else{
                ivParseUserImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(user.userImageUrl).into(ivParseUserImage);
            }
*/
        }


        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                ParseUser user = users.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, ProfileActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(ParseUser.class.getSimpleName(), Parcels.wrap(user));
                // show the activity
                context.startActivity(intent);

            }
        }
    }
}

