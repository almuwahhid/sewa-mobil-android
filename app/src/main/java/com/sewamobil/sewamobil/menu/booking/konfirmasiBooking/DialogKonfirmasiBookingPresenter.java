package com.sewamobil.sewamobil.menu.booking.konfirmasiBooking;

import android.content.Context;

import com.sewamobil.sewamobil.utils.ProjectConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.UiLibRequest;

public class DialogKonfirmasiBookingPresenter implements DialogKonfirmasiInterface.Presenter {

    Context context;
    DialogKonfirmasiInterface.View view;

    public DialogKonfirmasiBookingPresenter(Context context, DialogKonfirmasiInterface.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void konfirmasiBooking(final String id, final String img) {
        UiLibRequest.POST(ProjectConstant.API_KONFIRMASI_BOOKING, context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("code")== LibConstant.CODE_SUCCESS){
                        view.onKonfirmasiBooking();
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
                param.put("id", id);
                param.put("img", img);
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
