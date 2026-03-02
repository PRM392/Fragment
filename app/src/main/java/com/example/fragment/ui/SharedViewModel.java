package com.example.fragment.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    // ---- Role constants ----
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CUSTOMER = "CUSTOMER";

    // ---- Shared counter (legacy demo) ----
    private final MutableLiveData<Integer> counter = new MutableLiveData<>(0);

    public LiveData<Integer> getCounter() {
        return counter;
    }

    public void incrementCounter() {
        if (counter.getValue() != null) {
            counter.setValue(counter.getValue() + 1);
        }
    }

    // ---- User role ----
    private final MutableLiveData<String> userRole = new MutableLiveData<>(null);

    public LiveData<String> getUserRole() {
        return userRole;
    }

    public void setUserRole(String role) {
        userRole.setValue(role);
    }

    public String getCurrentRole() {
        return userRole.getValue();
    }
}
