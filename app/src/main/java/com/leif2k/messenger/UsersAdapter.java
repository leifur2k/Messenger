package com.leif2k.messenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<User> users = new ArrayList<>();
    private OnUserClickListener onUserClickListener;


    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        holder.textViewUserInfo.setText(String.format("%s %s, %s", user.getName(), user.getLastName(), user.getAge()));

        if (user.getOnline())
            holder.viewCircleStatus.setBackgroundResource(R.drawable.circle_green);
        else
            holder.viewCircleStatus.setBackgroundResource(R.drawable.circle_red);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickListener != null) {
                    onUserClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    interface OnUserClickListener {
        void onUserClick(User user);
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewUserInfo;
        private final View viewCircleStatus;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserInfo = itemView.findViewById(R.id.textViewUserInfo);
            viewCircleStatus = itemView.findViewById(R.id.viewCircleStatus);
        }

    }
}
