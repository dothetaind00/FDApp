package com.tai06.dothetai.fdapp.URL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomCode {
    public int randomCode(int min, int max) {
        Random random = new Random();
        int result = 0;
        do {
            result = random.nextInt((max - min + 1) + min);
        } while (result < min);
        return result;
    }

    public String getStringForDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm");
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d = new Date();
        try {/*from   w  w  w  . j  a  v  a  2s . c  om*/
            d = formatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dateString = f.format(d);
        return dateString;

    }
}
