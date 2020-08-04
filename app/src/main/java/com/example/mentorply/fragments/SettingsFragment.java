package com.example.mentorply.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mentorply.activities.accounts.LoginActivity;
import com.example.mentorply.R;
import com.example.mentorply.models.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    ImageView ivProfileImage;
    TextView tvName;
    TextView tvDescription;
    ChipGroup chipsPrograms;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        tvName = view.findViewById(R.id.tvName);
        tvDescription = view.findViewById(R.id.tvUserDescription);
        chipsPrograms = view.findViewById(R.id.chipsPrograms);

        ParseUser currentUser = ParseUser.getCurrentUser();
        tvName.setText(currentUser.getString("username"));
        tvDescription.setText(currentUser.getString("description"));
        if (currentUser.getParseFile("profilePicture")!=null){
            Glide.with(this).load(currentUser.getParseFile("profilePicture").getUrl()).into(ivProfileImage);
        }
        List <Tag> tags = currentUser.getList("tags");
        if (tags!=null)
            setCategoryChips(tags);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    public void setCategoryChips(List<Tag> tags) {
        for (Tag tag : tags) {
            Chip mChip = (Chip) this.getLayoutInflater().inflate(R.layout.layout_chip_action, null, false);
            try {
                mChip.setText(tag.fetchIfNeeded().getString("name"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            mChip.setPadding(paddingDp, 0, paddingDp, 0);
            mChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                }
            });
            chipsPrograms.addView(mChip);
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}