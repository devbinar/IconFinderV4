package com.devbinar.iconfinderv4.Models;

import androidx.fragment.app.Fragment;

import com.devbinar.iconfinderv4.Activitys.CoreActivity;

public class BackPressedObject {

    Fragment fragment;
    CoreActivity.OnBackPressed onBackPressed;

    public BackPressedObject(Fragment fragment, CoreActivity.OnBackPressed onBackPressed) {
        this.fragment = fragment;
        this.onBackPressed = onBackPressed;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public CoreActivity.OnBackPressed getOnBackPressed() {
        return onBackPressed;
    }
}
