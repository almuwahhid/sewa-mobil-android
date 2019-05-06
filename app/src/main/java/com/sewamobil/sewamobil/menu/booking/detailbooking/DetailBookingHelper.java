package com.sewamobil.sewamobil.menu.booking.detailbooking;

import com.sewamobil.sewamobil.BuildConfig;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentGeneralModel;
import com.sewamobil.sewamobil.utils.Functions;

import java.util.ArrayList;
import java.util.List;

import lib.almuwahhid.utils.LibUi;

public class DetailBookingHelper {
    public static List<RentGeneralModel> getDetail(BookingModel model){
        List<RentGeneralModel> getDetail = new ArrayList<>();

        RentGeneralModel rentGeneralModel = new RentGeneralModel("Kode Booking", model.getKode_booking(), "text");
        getDetail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Merk", model.getMerk(), "text");
        getDetail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Model", model.getNama_model(), "text");
        getDetail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Jaminan", model.getJaminan(), "text");
        getDetail.add(rentGeneralModel);

        String begin = model.getBegin_date().split(" ")[0];
        String begintime = model.getBegin_date().split(" ")[1];
        String due = model.getDue_date().split(" ")[0];
        String duetime = model.getDue_date().split(" ")[1];
        String submit = model.getSubmit_date().split(" ")[0];
        String submittime = model.getSubmit_date().split(" ")[1];

        rentGeneralModel = new RentGeneralModel("Waktu Booking", submit.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(submit.split("-")[1])-1)+ " "+submit.split("-")[0]+" "+submittime, "text");
        getDetail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Tanggal Mulai", begin.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0]+" "+begintime, "text");
        getDetail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Tanggal Kembali", due.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(due.split("-")[1])-1)+ " "+due.split("-")[0]+" "+duetime, "text");
        getDetail.add(rentGeneralModel);


        String konfirm = "";
        if(model.getConfirmed().equals("Y")){
            if(!model.getDelete().equals("")){
                konfirm = "Dibatalkan";
            } else {
                konfirm = "Sudah dikonfirmasi";
            }
        } else if(model.getConfirmed().equals("N") && !model.getDelete().equals("")){
            konfirm = "Ditolak";
        } else {
            konfirm = "Belum dikonfirmasi oleh Admin";
        }
        rentGeneralModel = new RentGeneralModel("Status", konfirm, "text");
        getDetail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Biaya", Functions.rupiahFormat(Float.valueOf(model.getBiaya())), "text");
        getDetail.add(rentGeneralModel);

        String p_kon = "";
        if(!model.getConfirmation_photo().equals("")){
            p_kon = BuildConfig.base_url+"confirm/"+model.getConfirmation_photo();
        }

        rentGeneralModel = new RentGeneralModel("Foto Konfirmasi", p_kon, "photo");
        getDetail.add(rentGeneralModel);

        return getDetail;
    }
}
