package com.sewamobil.sewamobil.menu.biodata.DialogChangePassword;

import android.content.Context;

import com.sewamobil.sewamobil.utils.Endpoints;
import com.sewamobil.sewamobil.utils.ProjectConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.LibUi;
import lib.almuwahhid.utils.UiLibRequest;

public class DialogChangePasswordPresenter implements ChangePasswordInterface.Presenter{
    Context context;
    ChangePasswordInterface.View view;

    public DialogChangePasswordPresenter(Context context, ChangePasswordInterface.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void changePassword(final String p1, final String p2) {
        UiLibRequest.POST(Endpoints.stringEditPassword(), context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("status")== LibConstant.CODE_SUCCESS){
                        view.onSuccessChange();
                    }else {
                        view.onFailedChange(response.getString("message"));
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
                param.put("username", LibUi.getSPString(context, ProjectConstant.SP_username));
                param.put("password_lama", p1);
                param.put("password_baru", p2);
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
