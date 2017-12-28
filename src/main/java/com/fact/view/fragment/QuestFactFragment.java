package com.fact.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fact.R;
import com.fact.presenter.QuestFactFragmentPresenter;
import com.fact.presenter.ipresenter.IQuestFactFragmentPresenter;
import com.fact.view.iview.IQuestFactFragmentView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestFactFragment extends BaseFragment implements IQuestFactFragmentView, AdapterView.OnItemSelectedListener {

    public String category;
    @BindView(R.id.sp_quest_select_category)
    Spinner spCategory;
    @BindView(R.id.input_digit)
    EditText mInputDigit;
    @BindView(R.id.input_date)
    EditText mInputDate;
    DatePickerDialog datePickerDialog;
    private IQuestFactFragmentPresenter iQuestFactFragmentPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iQuestFactFragmentPresenter = new QuestFactFragmentPresenter(this);
        iQuestFactFragmentPresenter.onCreatePresenter(getArguments());
        mInputDate.setText("");
        mInputDigit.setText("");
        mInputDate.setKeyListener(null);
        setSpecificQuestFacts();
    }

    /**
     * Setting Drop down for User Selection
     */

    private void setSpecificQuestFacts() {
        ArrayAdapter randomAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        randomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setPrompt("Select Category");
        spCategory.setAdapter(randomAdapter);
        spCategory.setOnItemSelectedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quest_fact;
    }

    /**
     * Handling Switch Compact Widget Functionality
     */

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 2) {
            mInputDate.setVisibility(View.VISIBLE);
            mInputDigit.setVisibility(View.GONE);
            mInputDate.setText("");
            mInputDigit.setText("");
        } else {
            mInputDigit.setVisibility(View.VISIBLE);
            mInputDate.setVisibility(View.GONE);
            mInputDate.setText("");
            mInputDigit.setText("");
        }
        TextView currentCategory = (TextView) view;
        category = currentCategory.getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /**
     * Setting Parameters for an API call
     */

    @OnClick(R.id.bt_fetch_quest_fact)
    void getSpecificFact() {
        if (category.equals("Date")) {
            if (validDate()) {
                iQuestFactFragmentPresenter.setApiCall(category, mInputDate.getText().toString());
                mInputDate.setText("");
                mInputDigit.setText("");
            }
        } else {
            if (validDigit()) {
                iQuestFactFragmentPresenter.setApiCall(category, mInputDigit.getText().toString());
                mInputDate.setText("");
                mInputDigit.setText("");
            }
        }
    }

    /**
     * Performing Validation for Required Fields
     */

    private boolean validDate() {
        if (TextUtils.isEmpty(mInputDate.getText())) {
            mInputDate.setError("Mandatory to enter");
            return false;
        }
        return true;
    }

    private boolean validDigit() {
        if (TextUtils.isEmpty(mInputDigit.getText())) {
            mInputDigit.setError("Mandatory to enter");
            return false;
        }
        return true;
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
     * Calender Popup for Date Input
     */

    @OnClick(R.id.input_date)
    void getDate() {
        final Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mInputDate.setText(String.valueOf((month + 1) + "/" + day));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
