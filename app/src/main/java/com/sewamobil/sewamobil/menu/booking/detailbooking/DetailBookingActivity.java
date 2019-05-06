package com.sewamobil.sewamobil.menu.booking.detailbooking;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;
import com.sewamobil.sewamobil.menu.booking.detailbooking.DialogUpload.DialogUploadBukti;
import com.sewamobil.sewamobil.menu.booking.listbooking.ListBookingActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.utils.LibUi;
import lib.almuwahhid.utils.PermissionChecker;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class DetailBookingActivity extends ActivityGeneral {
    BookingModel model;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lay_upload)
    LinearLayout lay_upload;

    DetailBookingAdapter adapter;

    Bitmap bitmapImage;
    String base64image = "";

    private static final String[] RequiredPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected PermissionChecker permissionChecker = new PermissionChecker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_booking);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Detail Booking");
        model = (BookingModel) getIntent().getSerializableExtra("data");


        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailBookingAdapter(getContext(), DetailBookingHelper.getDetail(model));
        rv.setAdapter(adapter);

        lay_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionChecker.verifyPermissions(DetailBookingActivity.this, RequiredPermissions, new PermissionChecker.VerifyPermissionsCallback() {

                    @Override
                    public void onPermissionAllGranted() {
                        EasyImage.openGallery(DetailBookingActivity.this, 0);
                    }

                    @Override
                    public void onPermissionDeny(String[] permissions) {
                        Toast.makeText(getContext(), "Please grant required permissions.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        if(model.getConfirmed().equals("N") && !model.getDelete().equals("")){
            lay_upload.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d("asdEdit", "onActivityResult: masuk sini");
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    //Some error handlingn
                    e.printStackTrace();
                }

                @Override
                public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                    Log.d("asdEdit", "onImagesPicked: "+imageFiles);

                    try {
                        Uri imageUri = Uri.fromFile(imageFiles.get(0));
                        bitmapImage = MediaStore.Images.Media.getBitmap(DetailBookingActivity.this.getContentResolver(), imageUri);
                        if (bitmapImage != null) {
                            base64image = LibUi.convertToBase64(bitmapImage);
                        }

                        new DialogUploadBukti(getContext(), bitmapImage, base64image, model.getId_booking(), new DialogUploadBukti.OnDialogUploadBukti() {
                            @Override
                            public void onAfterUpload() {
                                startActivity(new Intent(getContext(), ListBookingActivity.class));
                                finish();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCanceled(EasyImage.ImageSource source, int type) {
                    //Cancel handling, you might wanna remove taken photo if it was canceled
                    if (source == EasyImage.ImageSource.CAMERA) {
                        File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getContext());
                        if (photoFile != null) photoFile.delete();
                    }
                }
            });
        }
    }


}
