package com.sarthak.medopedia.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sarthak.medopedia.R;
import com.sarthak.medopedia.activities.LoginActivity;

public class LoginHomeFragment extends Fragment implements View.OnClickListener {

    private Button signInButton;
    private Button signUpButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_home, null);

        signInButton = rootView.findViewById(R.id.sign_in_button);
        signUpButton = rootView.findViewById(R.id.sign_up_button);

        signInButton.setOnClickListener(LoginHomeFragment.this);
        signUpButton.setOnClickListener(LoginHomeFragment.this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button: {
                if (getActivity() != null) {
                    ((LoginActivity) getActivity()).setFragment(1, false);
                }
                break;
            }
            case R.id.sign_up_button: {
                if (getActivity() != null) {
                    ((LoginActivity) getActivity()).setFragment(2, false);
                }
                break;
            }
        }
    }
}