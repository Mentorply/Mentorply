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
import com.example.mentorply.LoginActivity;
import com.example.mentorply.R;
import com.example.mentorply.models.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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