package com.fact.presenter;


import com.fact.presenter.ipresenter.IPresenter;
import com.fact.view.iview.IView;


public abstract class BasePresenter implements IPresenter {

    protected String TAG = getClass().getSimpleName();

    private IView iView;

    public BasePresenter(){

    }

    public BasePresenter(IView iView) {
        this.iView = iView;
        iView.bindPresenter(this);
    }


    @Override
    public void onStartPresenter() {

    }

    @Override
    public void onStopPresenter() {

    }

    @Override
    public void onPausePresenter() {

    }

    @Override
    public void onResumePresenter() {

    }

    @Override
    public void onDestroyPresenter() {

    }
}
