package com.sewamobil.sewamobil.menu.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.profile.ProfileActivity;
import com.sewamobil.sewamobil.menu.rentcar.RentCarActivity;
import com.sewamobil.sewamobil.menu.wisata.WisataActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.utils.BottomNavDisable;

public class MainActivity extends ActivityGeneral implements BottomNavigationView.OnNavigationItemSelectedListener{

    String TAG = "MainActivity";

    @BindView(R.id.nav) protected BottomNavigationView navigation;
    Fragment fragment = null;
    int active_fragment = 0;
    int after_active_fragment = 0;

    Fragment rentFragment = RentCarActivity.newInstance();
    Fragment wisataFragment = WisataActivity.newInstance();
    Fragment profilFragment = ProfileActivity.newInstance();
    FragmentManager mFragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BottomNavDisable.disableShiftMode(navigation);

        if (savedInstanceState != null) {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
        } else {
            fragment = rentFragment;
        }
        initializeNavFragment(fragment);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: "+item.getItemId());
        switch (item.getItemId()) {
            case R.id.navigation_rent:
                initActiveFragment(0);
                fragment = rentFragment;
                break;
            case R.id.navigation_wisata:
                initActiveFragment(1);
                fragment = wisataFragment;
                break;
            case R.id.navigation_profil:
                initActiveFragment(2);
                fragment = profilFragment;
                break;
        }
        initializeNavFragment(fragment);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "fragment", fragment);
        }
    }

    private void initializeNavFragment(Fragment curFragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mFragmentManager.findFragmentByTag(curFragment.getClass().getSimpleName()) == null) {
            transaction.add(R.id.contentContainer, curFragment, curFragment.getClass().getSimpleName());
        }

        Fragment tagMain = mFragmentManager.findFragmentByTag(rentFragment.getClass().getSimpleName());
        Fragment tagWisata = mFragmentManager.findFragmentByTag(wisataFragment.getClass().getSimpleName());
        Fragment tagProfil = mFragmentManager.findFragmentByTag(profilFragment.getClass().getSimpleName());
        hideFragment(transaction, tagMain, tagWisata, tagProfil);
        showFragment(curFragment, transaction, tagMain, tagWisata, tagProfil);

        after_active_fragment = active_fragment;
        transaction.commitAllowingStateLoss();
    }

    private void initActiveFragment(int a){
        active_fragment = a;
    }

    private void showFragment(Fragment curFragment, FragmentTransaction transaction, Fragment tagMain, Fragment tagWisata, Fragment tagProfil) {
        if (curFragment.getClass().getSimpleName().equals(rentFragment.getClass().getSimpleName())) {
            if (tagMain != null) {
                transaction.show(tagMain);
            }
        }


        if (curFragment.getClass().getSimpleName().equals(wisataFragment.getClass().getSimpleName())) {
            if (tagWisata != null) {
                transaction.show(tagWisata);
            }
        }

        if (curFragment.getClass().getSimpleName().equals(profilFragment.getClass().getSimpleName())) {
            if (tagProfil != null) {
                transaction.show(tagProfil);
            }
        }
    }

    private void hideFragment(FragmentTransaction transaction, Fragment tagMain, Fragment tagWisata, Fragment tagProfil) {
        if (tagMain != null) {
//            initAnimNav(transaction);
            transaction.hide(tagMain);
        }

        if (tagWisata != null) {
//            initAnimNav(transaction);
            transaction.hide(tagWisata);
        }

        if (tagProfil != null) {
//            initAnimNav(transaction);
            transaction.hide(tagProfil);
        }
    }
}
