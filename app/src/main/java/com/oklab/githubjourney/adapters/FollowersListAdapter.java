package com.oklab.githubjourney.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oklab.githubjourney.R;
import com.oklab.githubjourney.activities.UserProfileActivity;
import com.oklab.githubjourney.data.GitHubUserProfileDataEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olgakuklina on 2017-02-05.
 */

public class FollowersListAdapter extends RecyclerView.Adapter<FollowersListAdapter.FollowersListViewHolder> {

    private static final String TAG = FollowersListAdapter.class.getSimpleName();

    private final ArrayList<GitHubUserProfileDataEntry> followersDataEntrylist = new ArrayList<>(1000);
    private final Context context;

    public FollowersListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public FollowersListAdapter.FollowersListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.followers_list_item, parent, false);
        return new FollowersListAdapter.FollowersListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FollowersListAdapter.FollowersListViewHolder holder, int position) {
        GitHubUserProfileDataEntry entry = followersDataEntrylist.get(position);
        holder.populateFollowersViewData(entry);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, " view.getId()" + view.getId());
                String login = followersDataEntrylist.get(position).getLogin();
                String name = followersDataEntrylist.get(position).getName();
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("login_id", login);
                intent.putExtra("name", name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return followersDataEntrylist.size();
    }

    public void add(List<GitHubUserProfileDataEntry> entryList) {
        followersDataEntrylist.addAll(entryList);
        notifyDataSetChanged();
    }

    public void resetAllData() {
        followersDataEntrylist.clear();
        notifyDataSetChanged();
    }

    public class FollowersListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView login;
        private TextView email;
        private TextView location;
        private ImageView avatar;

        public FollowersListViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.followers_user_name);
            login = (TextView) v.findViewById(R.id.followers_login);
            email = (TextView) v.findViewById(R.id.followers_email);
            location = (TextView) v.findViewById(R.id.followers_location);
            avatar = (ImageView) v.findViewById(R.id.followers_avatar_image);
        }

        private void populateFollowersViewData(GitHubUserProfileDataEntry followersDataEntry) {
            Log.v(TAG, "followersDataEntry.getLogin() - " + followersDataEntry.getLogin());
            name.setText(followersDataEntry.getName());
            login.setText(followersDataEntry.getLogin());
            email.setText(followersDataEntry.getEmail());
            location.setText(followersDataEntry.getLocation());
            Picasso pic = Picasso.with(context);
            Log.v(TAG, "path" + followersDataEntry.getImageUri());
            pic.load(followersDataEntry.getImageUri())
                    .fit().centerCrop()
                    .error(R.drawable.octocat)
                    .into(avatar);
        }
    }
}
