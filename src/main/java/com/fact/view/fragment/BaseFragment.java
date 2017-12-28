package com.fact.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fact.presenter.ipresenter.IPresenter;
import com.fact.util.CodeSnippet;
import com.fact.view.iview.IView;
import com.library.CustomException;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements IView {

    protected String TAG = getClass().getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @Override
    public void bindPresenter(IPresenter iPresenter) {
    }

    @Override
    public void showMessage(String message) {
        ((IView) getActivity()).showMessage(message);
    }

    @Override
    public void showMessage(int resId) {
        ((IView) getActivity()).showMessage(resId);
    }

    @Override
    public void showMessage(CustomException e) {
        ((IView) getActivity()).showMessage(e);
    }

    @Override
    public void showProgressbar() {
        ((IView) getActivity()).showProgressbar();
    }

    @Override
    public void dismissProgressbar() {
        try {
            ((IView) getActivity()).dismissProgressbar();
        } catch (Exception e) {

        }

    }

    @Override
    public CodeSnippet getCodeSnippet() {
        return ((IView) getActivity()).getCodeSnippet();
    }

    protected abstract int getLayoutId();

}
