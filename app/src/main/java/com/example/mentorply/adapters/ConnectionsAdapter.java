package com.example.mentorply.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mentorply.ChatActivity;
import com.example.mentorply.R;
import com.example.mentorply.activities.pairing.ChoiceProfileActivity;
import com.example.mentorply.models.Pair;
import com.google.android.material.chip.ChipGroup;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConnectionsAdapter extends RecyclerView.Adapter<ConnectionsAdapter.ViewHolder>  {//implements Filterable
    Context context;
    //List<ParseUser> users;
    //List<ParseUser> usersAll;
    List <Pair> pairs;
    List <Pair> pairsAll;
//
//    //Pass in the context and list of users
//    public ConnectionsAdapter(Context context, List<ParseUser> users) {
//        this.context = context;
//        this.users = users;
//        this.usersAll = users;
//    }
//Pass in the context and list of users
public ConnectionsAdapter(Context context, List<Pair> pairs) {
    this.context = context;
    this.pairs = pairs;
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
        Pair pair = pairs.get(position);
        //bind the user with view holder
        holder.bind(pair);
    }

    @Override
    public int getItemCount() {
        return pairs.size();
    }

    public void clear() {
        pairs.clear();
        notifyDataSetChanged();
    }

    ;

    public void addAll(List<Pair> pairsList) {
        pairs.addAll(pairsList);
        notifyDataSetChanged();
    }

//    @Override
//    public Filter getFilter() {
//        return filter;
//    }
//
//    Filter filter = new Filter() {
//        //run on background thread
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            List <ParseUser> filteredList = new ArrayList<>();
//
//            if (charSequence.toString().isEmpty()||charSequence.length() == 0){
//                filteredList.addAll(pairsAll);
//            }
//            else{
//                for(ParseUser user: usersAll){
//                    if(user.getUsername().toLowerCase().contains(charSequence.toString().toLowerCase())){
//                        filteredList.add(user);
//                    }
////                    else {
////                        List <Tag> tags = user.getList("tags");
////                        for (Tag tag : tags)
////                        if (tag.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
////                            filteredList.add(user);
////                    }
//                }
//            }
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filteredList;
//            return filterResults;
//        }
//        //runs on a UI thread
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            users.clear();
//            users.addAll((Collection<? extends ParseUser>) filterResults.values);
//            notifyDataSetChanged();
//
//        }
//    };

    //Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivProfileImage;
        TextView tvUserDescription;
        TextView tvName;
        TextView tvRole;
        ChipGroup chipsUser;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUserDescription = itemView.findViewById(R.id.tvUserDescription);
            tvName = itemView.findViewById(R.id.tvName);
            //chipsUser = itemView.findViewById(R.id.chipsUser);
            itemView.setOnClickListener(this);
        }

        public void bind(Pair pair) {
            ParseUser user;
            if (pair.getFromUser().equals(ParseUser.getCurrentUser())){
                user = pair.getToUser();
            }
            else{
                user = pair.getFromUser();
            }
            try {
                tvName.setText(user.fetchIfNeeded().getString("username"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            try {
//                //tvUserDescription.setText(user.fetchIfNeeded().getString("description"));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            Glide
                    .with(context)
                    .load(user.getParseFile("profilePicture").getUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .circleCrop()
                    .into(ivProfileImage);

        }
        //        public void setCategoryChips(List<Tag> tags) {
//            for (Tag tag : tags) {
//                Chip mChip = (Chip) itemView.getLayoutInflater().inflate(R.layout.layout_chip_action, null, false);
//                try {
//                    mChip.setText(tag.fetchIfNeeded().getString("name"));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                int paddingDp = (int) TypedValue.applyDimension(
//                        TypedValue.COMPLEX_UNIT_DIP, 10,
//                        getResources().getDisplayMetrics()
//                );
//                mChip.setPadding(paddingDp, 0, paddingDp, 0);
//                mChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                    }
//                });
//                chipsUser.addView(mChip);
//            }
//        }
        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Pair pair = pairs.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, ChatActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Pair.class.getSimpleName(), Parcels.wrap(pair));
                // show the activity
                context.startActivity(intent);

            }
        }
    }
}

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
