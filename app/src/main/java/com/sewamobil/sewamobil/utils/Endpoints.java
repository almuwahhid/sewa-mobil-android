package com.sewamobil.sewamobil.utils;

import com.sewamobil.sewamobil.BuildConfig;

public class Endpoints {
    public static String a = BuildConfig.base_url;
    private static String b = BuildConfig.api;
    private static String c = a+b;

    public static String stringLogin() {
        return c + BuildConfig.login;
    }

    public static String stringRegister() {
        return c + BuildConfig.register;
    }

    public static String stringEditUser() {
        return c + BuildConfig.editUser;
    }

    public static String stringEditPassword() {
        return c + BuildConfig.editPassword;
    }

    public static String stringListBooking() {
        return c + BuildConfig.listbooking;
    }

    public static String stringConfirmPembayaran() {
        return c + BuildConfig.confirmPembayaran;
    }

    public static String stringCekEmailAvailable() {
        return c + BuildConfig.cekEmaiAvailable;
    }

    public static String stringListKendaraan() {
        return c + BuildConfig.listKendaraan;
    }

    public static String stringWisata() {
        return c + BuildConfig.wisata;
    }

    public static String stringBooking() {
        return c + BuildConfig.booking;
    }

    public static String stringSearchBooking() {
        return c + BuildConfig.searchbooking;
    }
}
