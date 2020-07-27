package com.tai06.dothetai.fdapp.URL;

import java.util.regex.Pattern;

public class Link {
    public static final String URL_Food = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/Select_home.php";
    public static final String URL_Drink = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/Select_home1.php";
    public static final String URL_Combo = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/Select_home2.php";
    public static final String URL_Search = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/PostSearch.php";
    public static final String Info = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/getThongtin.php";

    public static final String URL_GETpost_HD = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/postDonhang.php";
    public static final String URL_POST_HD = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/postAddHoaDon.php";
    public static final String URL_CANCEL_HD = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/post_Cancel_HD.php";
    public static final String URL_DELTE_HD = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/post_Delete_HD.php";

    public static final String URL_CHECK_LOGIN = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/check_login.php";
    public static final String URL_INFO_PERSON = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/getInfoPerson.php";
    public static final String URL_UPDATE_PSW = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/postUpdatePassword.php";

    public static final String URL_CHECK_SIGNUP = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/checkSignUp.php";
    public static final String URL_POST_INSERT_SIGNUP = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/postInsertPerson.php";

    public static final String URL_CHECK_SEARCHVIEW = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/check_searchview.php";
    public static final String URL_CHECK_DONHANG = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/check_donhang.php";

    public static final String URL_GET_VIEWPLUS = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/get_ViewPlus.php";


    //Slide image
    public static final String URL_Slide1 = "https://dothetaind00.000webhostapp.com/FoodDrink/Slide/banner4.jpg";
    public static final String URL_Slide2 = "https://dothetaind00.000webhostapp.com/FoodDrink/Slide/banner7.jpg";
    public static final String URL_Slide3 = "https://dothetaind00.000webhostapp.com/FoodDrink/Slide/banner6.jpg";


    //Pattern
    public static final Pattern PATTERN_EMAIL = Pattern.compile("^[a-zA-Z0-9_[^!@#%^&$*()\\s-+=]]+@[ge]mail.com$");
    public static final Pattern PATTERN_PASSWORD = Pattern.compile("^[a-zA-Z0-9[^_!@#%^&$*()\\s-+=]]{8,}$");
    public static final Pattern PATTERN_PHONE = Pattern.compile("^(03|05|07|08|09)+[0-9]{8,}$");
    public static final String  SUBJECT_SEND = "FoodD & Drink";

    //Address
    public static final String url_getNameQuan = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/getNameQuan.php";
    public static final String url_getTenPhuong = "https://dothetaind00.000webhostapp.com/FoodDrink/Query/getTenPhuong.php";

    //id_quận
    public static final int id_hoankiem = 1;
    public static final int id_dongda = 2;
    public static final int id_badinh = 3;
    public static final int id_haibatrung = 4;
    public static final int id_hoangmai = 5;
    public static final int id_thanhxuan = 6;
    public static final int id_longbien = 7;
    public static final int id_namtuliem = 8;
    public static final int id_bactuliem = 9;
    public static final int id_tayho = 10;
    public static final int id_caugiay = 11;
    public static final int id_hadong = 12;

}
