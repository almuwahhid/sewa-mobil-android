package com.sewamobil.sewamobil.menu.booking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;
import com.sewamobil.sewamobil.menu.booking.detailbooking.DetailBookingActivity;
import com.sewamobil.sewamobil.menu.register.RegisterActivity;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentCarModel;
import com.sewamobil.sewamobil.utils.Functions;
import com.sewamobil.sewamobil.utils.ProjectConstant;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

import static lib.almuwahhid.utils.LibUi.monthName;

public class BookingActivity extends DialogBuilder implements BookingInterface.View, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    BookingPresenter presenter;
    RelativeLayout lay_booking;
    LinearLayout btn_booking;
    EditText edt_begindate, edt_begintime, edt_duedate, edt_jaminan;
    TextView tv_duetime, tv_totalbiaya, tv_hari;

    String time = "";

    int PICKER = 0;
    int PICKER_BEGINDATE = 1;
    int PICKER_BEGINTIME = 2;
    int PICKER_DUEDATE = 3;

    BookingModel getBookingModel;
    RentCarModel rentCarModel;

    public BookingActivity(final Context context, final RentCarModel rentCarModel) {
        super(context, R.layout.activity_booking);
        this.rentCarModel = rentCarModel;

        presenter = new BookingPresenter(getContext(), this);

        getBookingModel = new BookingModel();
        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_booking = dialog.findViewById(R.id.lay_booking);
                btn_booking = dialog.findViewById(R.id.btn_booking);
                edt_begindate = dialog.findViewById(R.id.edt_begindate);
                edt_duedate = dialog.findViewById(R.id.edt_duedate);
                edt_begintime = dialog.findViewById(R.id.edt_begintime);
                tv_duetime = dialog.findViewById(R.id.tv_duetime);
                tv_totalbiaya = dialog.findViewById(R.id.tv_totalbiaya);
                tv_hari = dialog.findViewById(R.id.tv_hari);
                edt_jaminan = dialog.findViewById(R.id.edt_jaminan);

                setFullWidth(lay_booking);
                setGravity(Gravity.BOTTOM);
                setAnimation(R.style.DialogBottomAnimation);

                edt_begintime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edt_begindate.equals("")){
                            edt_begindate.setError("Isi dulu tanggal berangkat");
                        } else {
                            PICKER = PICKER_BEGINTIME;
                            Calendar now = Calendar.getInstance();
                            TimePickerDialog tp = TimePickerDialog.newInstance(
                                    BookingActivity.this,
                                    now.get(Calendar.HOUR_OF_DAY),
                                    now.get(Calendar.MINUTE),
                                    false
                            );
                            tp.setAccentColor(getContext().getResources().getColor(R.color.colorPrimary));
                            tp.show(getActivity().getFragmentManager(), "Datepickerdialog");
                        }

                    }
                });

                edt_begindate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PICKER = PICKER_BEGINDATE;
                        Calendar now = Calendar.getInstance();
//                      now.add(Calendar.YEAR,-7);
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                BookingActivity.this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)
                        );
                        dpd.setMinDate(now);
                        dpd.setFirstDayOfWeek(Calendar.MONDAY);
                        dpd.setAccentColor(ContextCompat.getColor(getContext(), R.color.primary));
                        dpd.show(getActivity().getFragmentManager(), "Tanggal Kejadian");
                    }
                });


                edt_duedate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edt_begindate.getText().toString().equals("")){
                            edt_begindate.setError("Isi dulu tanggal berangkat");
                        } else if(edt_begintime.getText().toString().equals("")){
                            edt_begintime.setError("Isi dulu waktu pengambilan");
                        }
                        else {
                            PICKER = PICKER_DUEDATE;
                            Calendar now = Calendar.getInstance();
//                          now.add(Calendar.YEAR,-7);
                            DatePickerDialog dpd = DatePickerDialog.newInstance(
                                    BookingActivity.this,
                                    Integer.valueOf(getBookingModel.getBegin_date().split("-")[0]),
                                    Integer.valueOf(getBookingModel.getBegin_date().split("-")[1])-1,
                                    Integer.valueOf(getBookingModel.getBegin_date().split("-")[2])
                            );

                            Calendar calStarDate = Calendar.getInstance();
                            try {
                                calStarDate.set(Calendar.YEAR, Integer.valueOf(getBookingModel.getBegin_date().split("-")[0]));
                                calStarDate.set(Calendar.MONTH, Integer.valueOf(getBookingModel.getBegin_date().split("-")[1])-1);
                                calStarDate.set(Calendar.DATE, Integer.valueOf(getBookingModel.getBegin_date().split("-")[2]));
                            } catch (IndexOutOfBoundsException e){

                            }

                            dpd.setMinDate(calStarDate);
                            dpd.setFirstDayOfWeek(Calendar.MONDAY);
                            dpd.setAccentColor(ContextCompat.getColor(getContext(), R.color.primary));
                            dpd.show(getActivity().getFragmentManager(), "Tanggal Kejadian");
                        }

                    }
                });

                setFormsToValidate();

            }
        });

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LibUi.isFormValid(getContext(), getDialog().getWindow().getDecorView(), forms)){
                    Map<String, String> par = new HashMap<>();
                    par.put("id_member", LibUi.getSPString(context, ProjectConstant.SP_uid));
                    par.put("id_kendaraan", rentCarModel.getId_kendaraan());
                    par.put("begin_date", getBookingModel.getBegin_date()+" "+time);
                    par.put("due_date", getBookingModel.getDue_date()+" "+time);
                    par.put("jaminan", edt_jaminan.getText().toString());
                    presenter.requestBooking(par);
                }

            }
        });
    }

    @Override
    public void onRequestBooking(BookingModel model) {
        LibUi.ToastShort(getContext(), "Berhasil membooking");
        LibUi.ToastLong(getContext(), "Anda dapat melakukan pembayaran dan konfirmasi dalam waktu satu jam");
        getContext().startActivity(new Intent(getContext(), DetailBookingActivity.class).putExtra("data", model));
        dismiss();
    }

    @Override
    public void onLoading() {
        LibUi.showLoadingDialog(getContext(), R.drawable.logo_rent);
    }

    @Override
    public void onHideLoading() {
        LibUi.hideLoadingDialog(getContext());
    }

    @Override
    public void onFailed() {
        LibUi.ToastShort(getContext(), "Server bermasalah");
    }

    List<Integer> forms = new ArrayList<>();
    private void setFormsToValidate(){
        forms.add(R.id.edt_duedate);
        forms.add(R.id.edt_begindate);
        forms.add(R.id.edt_begintime);
        forms.add(R.id.edt_jaminan);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(PICKER == PICKER_BEGINTIME){

        } else if(PICKER == PICKER_BEGINDATE){
            edt_begindate.setError(null);
            edt_begindate.setText(dayOfMonth + " " + monthName(monthOfYear) + " " + year);
            getBookingModel.setBegin_date(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            edt_begintime.setText("");
            edt_duedate.setText("");
        } else if(PICKER == PICKER_DUEDATE){
            edt_duedate.setError(null);
            edt_duedate.setText(dayOfMonth + " " + monthName(monthOfYear) + " " + year);
            tv_duetime.setText(time);
            getBookingModel.setDue_date(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

            try {
                //Dates to compare
                String CurrentDate=  getBookingModel.getBegin_date().replaceAll("-","/");
                String FinalDate=  getBookingModel.getDue_date().replaceAll("-","/");;

                Date date1;
                Date date2;

                SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");

                //Setting dates
                date1 = dates.parse(CurrentDate);
                date2 = dates.parse(FinalDate);

                //Comparing dates
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);

                //Convert long to String
                String dayDifference = Long.toString(differenceDates);

                long total_biaya = differenceDates*Integer.valueOf(rentCarModel.getTarif());
                tv_hari.setText(dayDifference+" hari");
                tv_totalbiaya.setText(Functions.rupiahFormat(Float.valueOf(total_biaya)));

                Log.e("HERE","HERE: " + dayDifference);
            }
            catch (Exception exception) {
                Log.e("DIDN'T WORK", "exception " + exception);
            }
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        String h = String.valueOf(hourOfDay);
        if(hourOfDay < 10){
            h = "0"+h;
        }

        String m = String.valueOf(minute);
        if(minute < 10){
            m = "0"+m;
        }

        String s = String.valueOf(second);
        if(second < 10){
            s = "0"+s;
        }

        time = h+":"+m+":"+s;
        edt_begintime.setError(null);
        edt_begintime.setText(time);
        tv_duetime.setText(time);
        getBookingModel.setBegin_date(getBookingModel.getBegin_date());
    }
}
