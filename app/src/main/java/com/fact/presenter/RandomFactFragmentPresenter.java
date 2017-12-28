package com.fact.presenter;

import android.os.Bundle;

import com.fact.R;
import com.fact.common.Constants;
import com.fact.model.dto.request.BaseRequest;
import com.fact.presenter.ipresenter.IRandomFactFragmentPresenter;
import com.fact.view.iview.IRandomFragmentView;
import com.fact.webservice.ApiClient;
import com.fact.webservice.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RandomFactFragmentPresenter extends BasePresenter implements IRandomFactFragmentPresenter {

    private IRandomFragmentView iRandomFragmentView;
    private BaseRequest request;

    public RandomFactFragmentPresenter(IRandomFragmentView iView) {
        super(iView);
        iRandomFragmentView = iView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
    }

    /**
     * Handing APi Response with default Retrofit Enqueue Listeners
     */

    private void doRandomFactApiCall(long taskId, BaseRequest request) {
        iRandomFragmentView.showProgressbar();
        Call<ResponseBody> tCall = ApiClient.getClient().create(ApiInterface.class).performRandomFact(request.getCategory());
        tCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == Constants.InternalHttpCode.SUCCESS_CODE) {
                        try {
                            iRandomFragmentView.sendResponse(response.body().string());
                            iRandomFragmentView.dismissProgressbar();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (response.code() == Constants.InternalHttpCode.FAILURE_CODE) {
                        iRandomFragmentView.sendResponse(response.code() + " No Data");
                        iRandomFragmentView.dismissProgressbar();
                    } else if (response.code() == Constants.InternalHttpCode.SERVER_ERROR) {
                        iRandomFragmentView.sendResponse(response.code() + " Server Error");
                        iRandomFragmentView.dismissProgressbar();
                    } else {
                        iRandomFragmentView.sendResponse(String.valueOf(response.errorBody()));
                        iRandomFragmentView.dismissProgressbar();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                iRandomFragmentView.sendResponse(t.getLocalizedMessage());
                iRandomFragmentView.dismissProgressbar();
            }
        });
    }

    /**
     * Checking Internet Connection and Setting Request Parameters to Performing Api call
     */

    @Override
    public void setCategoryApiCall(String category) {
        if (!iRandomFragmentView.getCodeSnippet().hasNetworkConnection()) {
            iRandomFragmentView.getCodeSnippet().showNetworkMessage(iRandomFragmentView.getActivity().findViewById(R.id.randomContainer));
        } else {
            request = new BaseRequest();
            request.setCategory(category.toLowerCase());
            doRandomFactApiCall(1, request);
        }
    }
}
