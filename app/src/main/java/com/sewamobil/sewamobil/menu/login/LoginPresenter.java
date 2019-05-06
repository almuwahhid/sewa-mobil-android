package com.sewamobil.sewamobil.menu.login;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;
import com.sewamobil.sewamobil.utils.Endpoints;
import com.sewamobil.sewamobil.utils.ProjectConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.UiLibRequest;

public class LoginPresenter implements LoginInterface.Presenter{
    Context context;
    LoginInterface.View view;
    Gson gson;

    public LoginPresenter(Context context, LoginInterface.View view) {
        this.context = context;
        this.view = view;
        gson = new Gson();
    }

    @Override
    public void requestLogin(final String username, final String password) {
        UiLibRequest.POST(Endpoints.stringLogin(), context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("status")== LibConstant.CODE_SUCCESS){
                        view.onSuccessLogin(gson.fromJson(response.getJSONObject("data").toString(), UserModel.class));
                    }else {
                        view.onFailedLogin();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                view.onHideLoading();
                view.onFailed();
            }

            @Override
            public Map<String, String> requestParam() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", username);
                param.put("password", password);
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
