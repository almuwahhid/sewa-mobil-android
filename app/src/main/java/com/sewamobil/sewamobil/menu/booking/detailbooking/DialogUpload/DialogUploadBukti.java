package com.sewamobil.sewamobil.menu.booking.detailbooking.DialogUpload;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.utils.Endpoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.LibUi;
import lib.almuwahhid.utils.UiLibRequest;

public class DialogUploadBukti extends DialogBuilder {

    Bitmap bitmap;
    String base64 = "";
    String id_booking = "";

    ImageView img, img_close;
    LinearLayout lay_upload;
    OnDialogUploadBukti onDialogUploadBukti;

    public DialogUploadBukti(Context context, final Bitmap bitmap, String base64, String id_booking, OnDialogUploadBukti onDialogUploadBukti) {
        super(context, R.layout.dialog_upload_bukti);
        this.base64 = base64;
        this.bitmap = bitmap;
        this.id_booking = id_booking;
        this.onDialogUploadBukti = onDialogUploadBukti;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_upload = dialog.findViewById(R.id.lay_upload);
                img_close = dialog.findViewById(R.id.img_close);
                img = dialog.findViewById(R.id.img);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                img.setImageBitmap(bitmap);

                lay_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendUploadFile();
                    }
                });
            }
        });

        show();
    }

    public interface OnDialogUploadBukti{
        void onAfterUpload();
    }

    private void sendUploadFile(){
        UiLibRequest.POST(Endpoints.stringConfirmPembayaran(), getContext(), new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                LibUi.showLoadingDialog(getContext(), R.drawable.logo_rent);
            }

            @Override
            public void onSuccess(JSONObject response) {
                LibUi.hideLoadingDialog(getContext());
                try {
                    if(response.getInt("status")== LibConstant.CODE_SUCCESS){
                        onDialogUploadBukti.onAfterUpload();
                    }else {
                        Toast.makeText(getContext(), "Bermasalah dengan server, coba lagi", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                LibUi.hideLoadingDialog(getContext());
            }

            @Override
            public Map<String, String> requestParam() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id_booking", id_booking);
                param.put("foto", base64);
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
