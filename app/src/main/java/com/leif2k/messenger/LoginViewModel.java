package com.leif2k.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<FirebaseUser> isLogged = new MutableLiveData<>();
    private MutableLiveData<String> isException = new MutableLiveData<>();

    private FirebaseAuth auth;


    public LoginViewModel() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                isLogged.setValue(firebaseUser);
            }
        });
    }


    public LiveData<FirebaseUser> getIsLogged() {
        return isLogged;
    }

    public LiveData<String> getIsException() {
        return isException;
    }


    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isException.setValue(e.getMessage());
                    }
                });
    }

}
