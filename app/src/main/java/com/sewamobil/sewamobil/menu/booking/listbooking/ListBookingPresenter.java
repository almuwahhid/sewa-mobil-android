package com.sewamobil.sewamobil.menu.booking.listbooking;

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

public class ListBookingPresenter implements ListBookingInterface.Presenter{
    Context context;
    ListBookingInterface.View view;
    Gson gson;


    public ListBookingPresenter(Context context, ListBookingInterface.View view) {
        this.context = context;
        this.view = view;
        gson = new Gson();
    }

    @Override
    public void requestList() {
        UiLibRequest.POST(Endpoints.stringListBooking(), context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("status")== LibConstant.CODE_SUCCESS){
                        JSONArray data = response.getJSONArray("data");
                        List<BookingModel> bookingModels = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            bookingModels.add(gson.fromJson(data.get(i).toString(), BookingModel.class));
                        }

                        view.onRequestList(bookingModels);
                    }else {
                        view.onEmptyData();
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
