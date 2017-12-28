package com.fact.view.iview;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.fact.presenter.ipresenter.IPresenter;
import com.fact.util.CodeSnippet;
import com.library.CustomException;

public interface IView {

    void showMessage(String message);

    void showMessage(int resId);

    void showMessage(CustomException e);

    void showProgressbar();

    void dismissProgressbar();

    FragmentActivity getActivity();

    void bindPresenter(IPresenter iPresenter);

    CodeSnippet getCodeSnippet();

}
