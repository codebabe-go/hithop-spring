package me.codebabe.common;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * author: code.babe
 * date: 2018-03-13 16:08
 */
public class HttpTest {

    @Test
    public void testGetMRI2() throws IOException, InterruptedException {
        String urlFuzzy = "http://10.171.160.56:58006/boss/trigger?jobName=fullDoseDeviationSessionResponseLengthHourly&timestamp=%d&attachment=19130&attachmentType=long";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for (long time = 1519952400000L; time <= 1520931600000L; time += 1000 * 60 * 60) {
            String url = String.format(urlFuzzy, time);
            System.out.println(String.format("request url: %s, time: %s", url, new Timestamp(time)));
            HttpGet request = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(request);
            System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
            Thread.sleep(1000 * 10L);
        }
    }

    @Test
    public void testOnce() throws IOException, InterruptedException {
        String urlFuzzy = "http://10.171.160.56:58006/boss/trigger?jobName=fullDoseDeviationSessionResponseLengthHourly&timestamp=%d&attachment=19130&attachmentType=long";
        Long a = 1519952400000L + 1000 * 60 * 60;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for (long time = 1519952400000L; time <= 1519952400000L; time += 1000 * 60 * 60) {
            String url = String.format(urlFuzzy, a);
            System.out.println(String.format("request url: %s, time: %s", url, new Timestamp(a).toString()));
            HttpGet request = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(request);
            System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
            Thread.sleep(1000 * 10L);
        }
    }

    @Test
    public void testGetTime() {
        for (long time = 1519952400000L; time <= 1520931600000L; time += 1000 * 60 * 60) {
            System.out.println(time);
        }
    }

    @Test
    public void testJSON() {
        Map<String, User> map = new HashMap<>();
        map.put("1", new User(1L, "fangzhe"));
        map.put("2", new User(2L, "lanlv"));
        String json = JSON.toJSONString(JSON.toJSON(map));
        System.out.println(json);
    }

}
