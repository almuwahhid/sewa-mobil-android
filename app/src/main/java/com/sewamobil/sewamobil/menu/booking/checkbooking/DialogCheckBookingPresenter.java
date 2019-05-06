package com.sewamobil.sewamobil.menu.booking.checkbooking;

import android.content.Context;

import com.google.gson.Gson;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;
import com.sewamobil.sewamobil.utils.Endpoints;
import com.sewamobil.sewamobil.utils.ProjectConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.LibUi;
import lib.almuwahhid.utils.UiLibRequest;

public class DialogCheckBookingPresenter implements DialogCheckInterface.Presenter{

    Context context;
    DialogCheckInterface.View view;
    Gson gson;

    public DialogCheckBookingPresenter(Context context, DialogCheckInterface.View view) {
        this.context = context;
        this.view = view;
        this.gson = new Gson();
    }

    @Override
    public void checkBookingModel(final String kodebooking) {
        UiLibRequest.POST(Endpoints.stringSearchBooking(), context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("status")== LibConstant.CODE_SUCCESS){
                        view.onSuccessCheck(gson.fromJson(response.getJSONObject("data").toString(), BookingModel.class));
                    }else {
                        view.onFailedCheck(response.getString("message"));
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
                param.put("kode_booking", kodebooking);
                param.put("id_member", LibUi.getSPString(context, ProjectConstant.SP_uid));
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
