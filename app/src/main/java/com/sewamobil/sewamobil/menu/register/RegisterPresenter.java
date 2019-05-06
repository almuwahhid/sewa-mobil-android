package com.sewamobil.sewamobil.menu.register;

import android.content.Context;

import com.sewamobil.sewamobil.utils.Endpoints;
import com.sewamobil.sewamobil.utils.ProjectConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.UiLibRequest;

public class RegisterPresenter implements RegisterInterface.Presenter{

    Context context;
    RegisterInterface.View view;

    public RegisterPresenter(Context context, RegisterInterface.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void registerData(final HashMap<String, String> param) {
        UiLibRequest.POST(Endpoints.stringRegister(), context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("status")== LibConstant.CODE_SUCCESS){
                        view.onSuccessRegister();
                    }else {
                        view.onFailedRegister(response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.onFailedRegister("Bermasalah dengan server");
                }
            }

            @Override
            public void onFailure(String error) {
                view.onHideLoading();
                view.onFailed();
            }

            @Override
            public Map<String, String> requestParam() {
                return param;
            }

            @Override
            public Map<String, String> requestHeaders() {
                Map<String, String> header_param = new HashMap<String, String>();
                return header_param;
            }
        });
    }
}
