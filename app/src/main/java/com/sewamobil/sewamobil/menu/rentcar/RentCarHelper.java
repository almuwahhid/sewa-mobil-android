package com.sewamobil.sewamobil.menu.rentcar;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentCarModel;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentGeneralModel;
import com.sewamobil.sewamobil.utils.Functions;

import java.util.ArrayList;
import java.util.List;

public class RentCarHelper {
    public static List<RentGeneralModel> detail(RentCarModel model){
        List<RentGeneralModel> detail = new ArrayList<>();

        RentGeneralModel rentGeneralModel = new RentGeneralModel("Model", model.getNama_model(), R.drawable.ic_info_black_24dp);
        detail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Merk", model.getMerk(), R.drawable.ic_directions_car_black_24dp);
        detail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Plat Nomor", model.getPlat_nomor(), R.drawable.ic_format_list_numbered_black_24dp);
        detail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Tipe", model.getTipe(), R.drawable.ic_directions_car_black_24dp);
        detail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Tahun Pembuatan", model.getTahun_pembuatan(), R.drawable.ic_timeline_black_24dp);
        detail.add(rentGeneralModel);

        /*rentGeneralModel = new RentGeneralModel("Isi Silinder", model.getIsi_silinder(), R.drawable.ic_toys_black_24dp);
        detail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Nomor Rangka", model.getNomor_rangka(), R.drawable.ic_toys_black_24dp);
        detail.add(rentGeneralModel);

        rentGeneralModel = new RentGeneralModel("Nomor Mesin", model.getNomor_mesin(), R.drawable.ic_toys_black_24dp);
        detail.add(rentGeneralModel);*/

        rentGeneralModel = new RentGeneralModel("Tarif per hari", Functions.rupiahFormat(Float.valueOf(model.getTarif())), R.drawable.ic_monetization_on_black_24dp);
        detail.add(rentGeneralModel);

        return detail;
    }
}
