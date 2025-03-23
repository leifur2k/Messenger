package com.leif2k.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends ViewModel {

    private MutableLiveData<Boolean> isSentReset = new MutableLiveData<>(false);
    private MutableLiveData<String> isError = new MutableLiveData<>();

    private FirebaseAuth auth;


    public ResetPasswordViewModel() {
        auth = FirebaseAuth.getInstance();
    }


    public LiveData<Boolean> getIsSentReset() {
        return isSentReset;
    }

    public LiveData<String> getIsError() {
        return isError;
    }


    public void resetPass(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        isSentReset.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isError.setValue(e.getMessage());
                    }
                });
    }


}
