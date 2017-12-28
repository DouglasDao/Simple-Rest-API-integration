package com.fact.presenter;

import android.os.Bundle;

import com.fact.R;
import com.fact.common.Constants;
import com.fact.model.dto.request.BaseRequest;
import com.fact.presenter.ipresenter.IQuestFactFragmentPresenter;
import com.fact.view.iview.IQuestFactFragmentView;
import com.fact.webservice.ApiClient;
import com.fact.webservice.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestFactFragmentPresenter extends BasePresenter implements IQuestFactFragmentPresenter {

    private IQuestFactFragmentView iQuestFactFragmentView;
    private BaseRequest request;

    public QuestFactFragmentPresenter(IQuestFactFragmentView iView) {
        iQuestFactFragmentView = iView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
    }

    /**
     * Handing APi Response with default Retrofit Enqueue Listeners
     */

    private void doSpecificFactApiCall(long taskId, BaseRequest request) {
        iQuestFactFragmentView.showProgressbar();
        Call<ResponseBody> tCall = ApiClient.getClient().create(ApiInterface.class).performSpecificFact(request.getInput(), request.getCategory());
        tCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == Constants.InternalHttpCode.SUCCESS_CODE) {
                        try {
                            iQuestFactFragmentView.sendResponse(response.body().string());
                            iQuestFactFragmentView.dismissProgressbar();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (response.code() == Constants.InternalHttpCode.FAILURE_CODE) {
                        iQuestFactFragmentView.sendResponse(response.code() + " No Data");
                        iQuestFactFragmentView.dismissProgressbar();
                    } else if (response.code() == Constants.InternalHttpCode.SERVER_ERROR) {
                        iQuestFactFragmentView.sendResponse(response.code() + " Server Error");
                        iQuestFactFragmentView.dismissProgressbar();
                    } else {
                        iQuestFactFragmentView.sendResponse(String.valueOf(response.errorBody()));
                        iQuestFactFragmentView.dismissProgressbar();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                iQuestFactFragmentView.sendResponse(t.getLocalizedMessage());
                iQuestFactFragmentView.dismissProgressbar();
            }
        });
    }


    /**
     * Checking Internet Connection and Setting Request Parameters to Performing Api call
     */

    @Override
    public void setApiCall(String category, String input) {
        if (!iQuestFactFragmentView.getCodeSnippet().hasNetworkConnection()) {
            iQuestFactFragmentView.getCodeSnippet().showNetworkMessage(iQuestFactFragmentView.getActivity().findViewById(R.id.quest_container));
        } else {
            request = new BaseRequest();
            request.setCategory(category.toLowerCase());
            request.setInput(input);
            doSpecificFactApiCall(1, request);
        }
    }
}
