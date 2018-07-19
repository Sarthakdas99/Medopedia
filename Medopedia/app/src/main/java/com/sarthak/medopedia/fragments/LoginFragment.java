package com.sarthak.medopedia.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sarthak.medopedia.R;
import com.sarthak.medopedia.activities.MainActivity;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {

    TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSignIn;
    TextView textViewForgotPassword;
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, null);

        textInputEditTextEmail = rootView.findViewById(R.id.edit_text_email);
        textInputEditTextPassword = rootView.findViewById(R.id.edit_text_password);
        buttonSignIn = rootView.findViewById(R.id.sign_in_button);
        textViewForgotPassword = rootView.findViewById(R.id.text_view_forgot_pass);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignIn.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                login();
                break;

            case R.id.text_view_forgot_pass:

                break;
        }
    }

    private void login() {
        String email = textInputEditTextEmail.getText().toString();
      //  Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        String password = textInputEditTextPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Enter data", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                Objects.requireNonNull(getActivity()).finish();
                            } else
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

}

