package com.sewamobil.sewamobil.menu.wisata;

import android.content.Context;

import com.google.gson.Gson;
import com.sewamobil.sewamobil.menu.wisata.Model.WisataModel;
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

public class WisataPresenter implements WisataInterface.Presenter{

    Context context;
    WisataInterface.View view;
    Gson gson;

    public WisataPresenter(Context context, WisataInterface.View view) {
        this.context = context;
        this.view = view;
        gson = new Gson();
    }

    @Override
    public void requestWisata() {
        UiLibRequest.POST(Endpoints.stringWisata(), context, new UiLibRequest.OnPostRequest() {
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
                        List<WisataModel> bookingModels = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            bookingModels.add(gson.fromJson(data.get(i).toString(), WisataModel.class));
                        }

                        view.onRequestWisata(bookingModels);
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
                param.put("uid", LibUi.getSPString(context, ProjectConstant.SP_uid));
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
