package com.hhu;

import com.hhu.timer.HealthReporterTimer;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2021-11-07 00:01:00");
        Timer timer = new Timer();
        //设置定时器每天打卡
        timer.schedule(new HealthReporterTimer(),date,1000*60*60*24);
    }
}
