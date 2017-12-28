package com.fact.view.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fact.presenter.ipresenter.IPresenter;
import com.fact.util.CodeSnippet;
import com.fact.util.CustomProgressbar;
import com.fact.view.iview.IView;
import com.library.CustomException;
import com.library.ExceptionTracker;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements IView {

    protected String TAG = getClass().getSimpleName();
    protected View mParentView;
    protected CodeSnippet mCodeSnippet;
    ProgressDialog pDialog;
    private IPresenter iPresenter;
    private CustomProgressbar mCustomProgressbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        injectViews();
    }

    private void injectViews() {
        ButterKnife.bind(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (iPresenter != null) iPresenter.onStartPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (iPresenter != null) iPresenter.onStopPresenter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (iPresenter != null) iPresenter.onPausePresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iPresenter != null) iPresenter.onResumePresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPresenter != null) iPresenter.onDestroyPresenter();
    }

    public void bindPresenter(IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    private CustomProgressbar getProgressBar() {
        if (mCustomProgressbar == null) {
            mCustomProgressbar = new CustomProgressbar(this);
        }
        return mCustomProgressbar;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(CustomException e) {
        Toast.makeText(this, e.getException(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressbar() {
        getProgressBar().show();
    }


    @Override
    public void dismissProgressbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getProgressBar().dismissProgress();
                } catch (Exception e) {
                    ExceptionTracker.track(e);
                }
            }
        });
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }


    @Override
    public CodeSnippet getCodeSnippet() {
        if (mCodeSnippet == null) {
            mCodeSnippet = new CodeSnippet(getActivity());
            return mCodeSnippet;
        }
        return mCodeSnippet;
    }

    protected abstract int getLayoutId();
}