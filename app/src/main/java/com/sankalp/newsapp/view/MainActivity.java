package com.sankalp.newsapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sankalp.newsapp.R;
import com.sankalp.newsapp.viewmodel.FeedsViewModel;


public class MainActivity extends AppCompatActivity {

    private FeedsViewModel feedsViewModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViewModel();
        showHomeScreenFragment();
        toolbar= findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    private void showHomeScreenFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out)
                .replace(R.id.base_frame_container,
                        HomeScreenFragment.NewInstance(),
                        HomeScreenFragment.class.getName())
                .addToBackStack(HomeScreenFragment.class.getName())
                .commit();
    }

    public void showCountryNewsFragment(String countryCode , String countryName) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.base_frame_container,
                        CountryNewsFragment.NewInstance(countryCode ,countryName),
                        CountryNewsFragment.class.getName())
                .addToBackStack(CountryNewsFragment.class.getName())
                .commit();
    }

    public void showCountryNewsDescriptionFragment(Bundle articleInfo) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.base_frame_container,
                        NewsDescriptionFragment.NewInstance(articleInfo),
                        NewsDescriptionFragment.class.getName())
                .addToBackStack(NewsDescriptionFragment.class.getName())
                .commit();
    }

    private void initViewModel() {
        feedsViewModel = ViewModelProviders.of(this).get(FeedsViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public FeedsViewModel getMainViewModel() {
        return feedsViewModel;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }
    public Toolbar getToolbar(){
        return toolbar;
    }

    public interface IFragmentInteractionListener {

    }

}
