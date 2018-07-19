package com.sarthak.medopedia.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sarthak.medopedia.R;
import com.sarthak.medopedia.activities.LoginActivity;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    TextInputEditText textInputEditTextEmail, textInputEditTextPassword, textInputEditTextName;
    Button buttonSignUp;
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, null);
        textInputEditTextEmail = rootView.findViewById(R.id.edit_text_email);
        textInputEditTextName = rootView.findViewById(R.id.edit_text_name);
        textInputEditTextPassword = rootView.findViewById(R.id.edit_text_password);
        buttonSignUp = rootView.findViewById(R.id.sign_up_button);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        String name = textInputEditTextName.getText().toString();
        String email = textInputEditTextEmail.getText().toString();
        String password = textInputEditTextPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Enter data", Toast.LENGTH_SHORT).show();
        } else

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                if (getActivity() != null) {
                                    ((LoginActivity) getActivity()).setFragment(1, false);
                                }
                            } else
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
    }
}
