package com.fact.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.fact.R;
import com.fact.view.fragment.QuestFactFragment;
import com.fact.view.fragment.RandomFactFragment;
import com.fact.view.iview.IHomeActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements IHomeActivityView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.switch_selection)
    SwitchCompat mSwitchFact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        randomFragmentTransaction();
        mSwitchFact.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.isChecked()) {
            questFragmentTransaction();
        } else {
            randomFragmentTransaction();
        }
    }

    private void randomFragmentTransaction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RandomFactFragment randFact = new RandomFactFragment();
        fragmentTransaction.replace(R.id.fact_container, randFact);
        fragmentTransaction.commit();
    }

    private void questFragmentTransaction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        QuestFactFragment questFact = new QuestFactFragment();
        fragmentTransaction.replace(R.id.fact_container, questFact);
        fragmentTransaction.commit();
    }
}
