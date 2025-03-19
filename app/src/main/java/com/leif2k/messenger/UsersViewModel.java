package com.leif2k.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersViewModel extends ViewModel {

    MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private FirebaseAuth auth;


    public UsersViewModel() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                user.setValue(firebaseUser);
            }
        });
    }


    public LiveData<FirebaseUser> getUser() {
        return user;
    }


    public void logOut() {
        auth.signOut();
    }
}
