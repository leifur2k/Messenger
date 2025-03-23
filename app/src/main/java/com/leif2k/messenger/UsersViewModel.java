package com.leif2k.messenger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {

    MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    MutableLiveData<List<User>> users = new MutableLiveData<>();

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;


    public UsersViewModel() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                user.setValue(firebaseUser);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("Users");

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentUser = auth.getCurrentUser();
                List<User> list = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);

                    if (currentUser != null && user != null)
                        if (!currentUser.getUid().equals(user.getId()))
                            list.add(user);
                }

                users.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LogKey", error.getMessage());
            }
        });
    }


    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }


    public void setUserOnline(boolean isOnline) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null)
            usersReference.child(firebaseUser.getUid()).child("online").setValue(isOnline);
    }


    public void logOut() {
        setUserOnline(false);
        auth.signOut();
    }

}
