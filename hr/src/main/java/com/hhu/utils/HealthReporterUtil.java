package com.hhu.utils;

import com.hhu.entity.StudentInfo;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HealthReporterUtil {

    public static final int TRY_MAX=30;

    public static boolean healthReport(StudentInfo studentInfo) throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://form.hhu.edu.cn/pdc/formDesignApi/dataFormSave?wid=A335B048C8456F75E0538101600A6A04&userId="+studentInfo.getXGH_336526());
        List<NameValuePair> params = getParams(studentInfo);
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params,"utf-8");
        RequestConfig requestConfig = RequestConfig.custom()
                                        .setSocketTimeout(5000)
                                        .setConnectTimeout(5000)
                                        .setConnectionRequestTimeout(5000).build();
        httpPost.setConfig(requestConfig);

        httpPost.setEntity(formEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 200){
                EmailUtil.sendMessage(studentInfo.getEmail());
                return true;
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    private static List<NameValuePair> getParams(StudentInfo studentInfo){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String today = sdf.format(date);
        List<NameValuePair> params =  new ArrayList<>();
        params.add(new BasicNameValuePair("DATETIME_CYCLE",today));
        params.add(new BasicNameValuePair("XGH_336526",studentInfo.getXGH_336526()));
        params.add(new BasicNameValuePair("XM_1474",studentInfo.getXM_1474()));
        params.add(new BasicNameValuePair("SFZJH_859173",studentInfo.getSFZJH_859173()));
        params.add(new BasicNameValuePair("SELECT_941320",studentInfo.getSELECT_941320()));
        params.add(new BasicNameValuePair("SELECT_459666",studentInfo.getSELECT_459666()));
        params.add(new BasicNameValuePair("SELECT_814855",studentInfo.getSELECT_814855()));
        params.add(new BasicNameValuePair("SELECT_525884",studentInfo.getSELECT_525884()));
        params.add(new BasicNameValuePair("SELECT_125597",studentInfo.getSELECT_125597()));
        params.add(new BasicNameValuePair("TEXT_950231",studentInfo.getTEXT_950231()));
        params.add(new BasicNameValuePair("TEXT_937296",studentInfo.getTEXT_937296()));
        params.add(new BasicNameValuePair("RADIO_6555",studentInfo.getRADIO_6555()));
        params.add(new BasicNameValuePair("RADIO_535015",studentInfo.getRADIO_535015()));
        params.add(new BasicNameValuePair("RADIO_891359",studentInfo.getRADIO_891359()));
        params.add(new BasicNameValuePair("RADIO_372002",studentInfo.getRADIO_372002()));
        params.add(new BasicNameValuePair("RADIO_618691",studentInfo.getRADIO_618691()));
        return params;
    }

    public static StudentInfo[] initInfo(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String today = sdf.format(date);
        StudentInfo[] students = {
                //填写打卡学生信息，具体对应关系见readme
                new StudentInfo(today,"","","","","","","","","","","","","","","",""),
                new StudentInfo(today,"","","","","","","","","","","","","","","","")
        };
        return students;
    }

    public static void doReport() throws Exception {
        StudentInfo[] students = HealthReporterUtil.initInfo();
        for (int i = 0; i < students.length; i++) {
            boolean flag = false;
            int count = 0;
            do{
                flag = HealthReporterUtil.healthReport(students[i]);
                count++;
                Thread.sleep(3000);
            }while(!flag && count<TRY_MAX);
        }
    }

}
