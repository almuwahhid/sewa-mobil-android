package com.sewamobil.sewamobil.menu.rentcar;

import android.content.Context;

import com.google.gson.Gson;
import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentCarModel;
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
import lib.almuwahhid.utils.UiLibRequest;

public class RentCarPresenter implements RentCarInterface.Presenter{
    Context context;
    RentCarInterface.View view;
    Gson gson;

    public RentCarPresenter(Context context, RentCarInterface.View view) {
        this.context = context;
        this.view = view;
        gson = new Gson();
    }

    @Override
    public void requestListRent() {
        UiLibRequest.POST(Endpoints.stringListKendaraan(), context, new UiLibRequest.OnPostRequest() {
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
                        List<RentCarModel> rentCarModels = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            rentCarModels.add(gson.fromJson(data.get(i).toString(), RentCarModel.class));
                        }
                        view.onRequestRent(rentCarModels);
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
