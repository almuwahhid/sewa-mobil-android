package com.sewamobil.sewamobil.menu.biodata;

import android.content.Context;

import com.google.gson.Gson;
import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;
import com.sewamobil.sewamobil.utils.Endpoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.UiLibRequest;

public class BiodataPresenter implements BiodataInterface.Presenter{

    Context context;
    Gson gson;
    BiodataInterface.View view;

    public BiodataPresenter(Context context, BiodataInterface.View view) {
        this.context = context;
        this.view = view;
        gson = new Gson();
    }

    @Override
    public void changeProfil(final HashMap<String, String> param) {
        UiLibRequest.POST(Endpoints.stringEditUser(), context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("status")== LibConstant.CODE_SUCCESS){
                        view.onChangeProfil(gson.fromJson(response.getJSONObject("data").toString(), UserModel.class));
                    }else {
                        view.onFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.onFailed();
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
