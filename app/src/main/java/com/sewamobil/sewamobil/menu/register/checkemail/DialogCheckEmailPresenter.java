package com.sewamobil.sewamobil.menu.register.checkemail;

import android.content.Context;

import com.sewamobil.sewamobil.utils.Endpoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.LibUi;
import lib.almuwahhid.utils.UiLibRequest;

public class DialogCheckEmailPresenter implements DialogCheckEmailInterface.Presenter{
    Context context;
    DialogCheckEmailInterface.View view;

    public DialogCheckEmailPresenter(Context context, DialogCheckEmailInterface.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void checkEmail(final String email) {
        UiLibRequest.POST(Endpoints.stringCekEmailAvailable(), context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("status") == LibConstant.CODE_SUCCESS){
                        view.onCheckEmail(true, response.getString("message"));
                    }else {
                        view.onCheckEmail(false, response.getString("message"));
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
                param.put("username", email);
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
