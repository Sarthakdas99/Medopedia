package com.sarthak.medopedia.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sarthak.medopedia.R;
import com.sarthak.medopedia.fragments.LoginFragment;
import com.sarthak.medopedia.fragments.LoginHomeFragment;
import com.sarthak.medopedia.fragments.SignUpFragment;

import static com.sarthak.medopedia.net_connectivity.Connectivity.isNetworkStatusAvialable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    CoordinatorLayout coordinatorLayout;
    int previousColorTopView = -1;
    int previousColorBottomView = -1;
    private View bigViewRotate;
    private View smallViewRotate;
    private TextView textView;
    private int currentFragmentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //detect internet and show the data
        if (isNetworkStatusAvialable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Internet detected", Toast.LENGTH_SHORT).show();
            //new FetchWebsiteData().execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();

        }
        setContentView(R.layout.activity_login);

        textView = findViewById(R.id.text_view);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        //declaring views
        bigViewRotate = findViewById(R.id.big_view_rotate);
        smallViewRotate = findViewById(R.id.small_view_rotate);
        CardView cardViewLogo = findViewById(R.id.card_view_logo);

        //getting the parameters of the view(bg)  (size setting part)
        ViewGroup.LayoutParams params = bigViewRotate.getLayoutParams();
        params.height = (int) (getResources().getDisplayMetrics().widthPixels * 1.175f);
        bigViewRotate.setLayoutParams(params);

        // animating the bigview(rotating part)
        Animation rotateAnimation = new RotateAnimation(0.0f, 305.0f, 50, 50);
        rotateAnimation.setDuration(0);
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(true);
        bigViewRotate.startAnimation(rotateAnimation);

        // animating the smallview(rotating part)
        rotateAnimation = new RotateAnimation(0.0f, 20.0f, 50, 50);
        rotateAnimation.setDuration(0);
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(true);
        smallViewRotate.startAnimation(rotateAnimation);

        changeViewRotateColor(0);

        //setting card view margins for logo
        RelativeLayout.LayoutParams cardParams = (RelativeLayout.LayoutParams) cardViewLogo.getLayoutParams();
        cardParams.setMargins(0, (int) ((getResources().getDisplayMetrics().widthPixels / 2.75f) - 50 * getResources().getDisplayMetrics().density), 0, 0);
        cardViewLogo.setLayoutParams(cardParams);

        //
        textView.setOnClickListener(this);

        //setting login home fragment at first
        setFragment(0, true);
    }

    private void changeViewRotateColor(int position) {
        if (bigViewRotate == null || smallViewRotate == null) {
            return;
        }
        if (previousColorTopView == -1) {
            previousColorTopView = ContextCompat.getColor(getApplicationContext(), R.color.teal);
        }
        int newColorTopView = 0;
        if (previousColorBottomView == -1) {
            int previousColorBottomView = ContextCompat.getColor(getApplicationContext(), R.color.teal);
        }
        int newColorBottomView = 0;
        switch (position) {
            case 0: {
                newColorTopView = ContextCompat.getColor(getApplicationContext(), R.color.teal);
                newColorBottomView = ContextCompat.getColor(getApplicationContext(), R.color.teal);
                break;
            }
            case 1: {
                newColorTopView = ContextCompat.getColor(getApplicationContext(), R.color.red);
                newColorBottomView = ContextCompat.getColor(getApplicationContext(), R.color.blue);
                break;
            }
            case 2: {
                newColorTopView = ContextCompat.getColor(getApplicationContext(), R.color.blue);
                newColorBottomView = ContextCompat.getColor(getApplicationContext(), R.color.red);
                break;
            }
        }
        ValueAnimator topAnimation = new ValueAnimator();
        topAnimation.setIntValues(previousColorTopView, newColorTopView);
        topAnimation.setEvaluator(new ArgbEvaluator());
        topAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bigViewRotate.getBackground().setColorFilter((Integer) valueAnimator.getAnimatedValue(), PorterDuff.Mode.SRC_ATOP);
                previousColorTopView = (Integer) valueAnimator.getAnimatedValue();
            }
        });
        topAnimation.setDuration(300);
        topAnimation.start();

        ValueAnimator bottomAnimation = new ValueAnimator();
        bottomAnimation.setIntValues(previousColorBottomView, newColorBottomView);
        bottomAnimation.setEvaluator(new ArgbEvaluator());
        bottomAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                smallViewRotate.getBackground().setColorFilter((Integer) valueAnimator.getAnimatedValue(), PorterDuff.Mode.SRC_ATOP);
                previousColorBottomView = (Integer) valueAnimator.getAnimatedValue();
            }
        });
        bottomAnimation.setDuration(300);
        bottomAnimation.start();
    }

    public void setFragment(int position, boolean force) {
        if (currentFragmentPosition != position || force) {
            Fragment fragment;

            switch (position) {
                case 0: {
                    fragment = new LoginHomeFragment();
                    textView.setText(R.string.skip);
                    break;
                }
                case 1: {
                    fragment = new LoginFragment();
                    textView.setText(R.string.sign_up);
                    break;
                }
                case 2: {
                    fragment = new SignUpFragment();
                    textView.setText(R.string.sign_in);
                    break;
                }
                default: {
                    fragment = new LoginHomeFragment();
                    break;
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Slide slideTransition = new Slide(Gravity.END);
                slideTransition.setDuration(500);
                fragment.setEnterTransition(slideTransition);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

            currentFragmentPosition = position;

            changeViewRotateColor(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view: {
                switch (currentFragmentPosition) {
                    case 0: {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case 1: {
                        setFragment(2, false);
                        break;
                    }
                    case 2: {
                        setFragment(1, false);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragmentPosition != 0) {
            setFragment(0, false);
        } else {
            super.onBackPressed();
        }
    }
}
