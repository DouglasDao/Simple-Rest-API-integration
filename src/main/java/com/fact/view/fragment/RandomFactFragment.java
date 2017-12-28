package com.fact.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.fact.R;
import com.fact.presenter.RandomFactFragmentPresenter;
import com.fact.presenter.ipresenter.IRandomFactFragmentPresenter;
import com.fact.view.iview.IRandomFragmentView;

import butterknife.BindView;
import butterknife.OnClick;


public class RandomFactFragment extends BaseFragment implements IRandomFragmentView, AdapterView.OnItemSelectedListener {


    public String category;
    IRandomFactFragmentPresenter iRandomFactFragmentPresenter;
    @BindView(R.id.sp_select_category)
    Spinner spCategory;

    /**
     *  Passing controls to presenter to perform app logic
     * */


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iRandomFactFragmentPresenter = new RandomFactFragmentPresenter(this);
        iRandomFactFragmentPresenter.onCreatePresenter(getArguments());
        setRandomFacts();
    }

    /**
     *  Setting Drop down for User Selection
     * */

    private void setRandomFacts() {
        ArrayAdapter randomAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        randomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setPrompt("Select Category");
        spCategory.setAdapter(randomAdapter);
        spCategory.setOnItemSelectedListener(this);
    }

    /**
     * All Response in Alert Dialog
     */

    @Override
    public void sendResponse(String response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(category);
        builder.setMessage(response);
        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     *  Getting Layout XML file for Respective Fragment
     * */

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_random_fact;
    }

    /**
     * Handling Toogle Widget Functionality
     */

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView currentCategory = (TextView) view;
        category = currentCategory.getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /**
     *  Setting Parameters for an API call
     * */

    @OnClick(R.id.bt_fetch_rand_fact)
    void getRandomFact() {
        iRandomFactFragmentPresenter.setCategoryApiCall(category);
    }
}
