package com.hhu.timer;

import com.hhu.utils.HealthReporterUtil;

import java.util.TimerTask;

public class HealthReporterTimer extends TimerTask {
    @Override
    public void run() {
        try {
            HealthReporterUtil.doReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
