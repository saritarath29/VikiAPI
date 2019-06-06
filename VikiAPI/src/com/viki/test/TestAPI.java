package com.viki.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Reporter;

public class TestAPI {

    public static void main(String[] args) {

        int i = 0;
        while (true) {
            String uri = "http://api.viki.io/v4/videos.json?app=100250a&per_page=50&page=";
            uri += i;
            HttpGet get = new HttpGet(uri);

            StringBuffer result = new StringBuffer();

            CloseableHttpClient client = HttpClients.createDefault();

            try {

                HttpResponse response = client.execute(get);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String responseString = "";

                while ((responseString = reader.readLine()) != null) {

                    result.append(responseString);

                    Reporter.log(result.toString(), true);

                }

            } catch (IOException e) {

                e.printStackTrace();

            }


            JSONObject jsonObject = new JSONObject(result.toString());

            Boolean moreStatus = jsonObject.getBoolean("more");
            if (moreStatus == false){
                System.out.println("more_val is FALSE");
                break;}
            i++;
            JSONArray response_arr = jsonObject.getJSONArray("response");
            int hd_TRUE_count = 0;
            int hd_FALSE_count = 0;
                for (int j = 0; j < response_arr.length(); j++) {
                    JSONObject response_obj = response_arr.getJSONObject(j);
                    Boolean hd_val = response_obj.getJSONObject("flags").getBoolean("hd");
                    if (hd_val = true) {
                        hd_TRUE_count++;
                    }
                    else
                        hd_FALSE_count++;

                    //System.out.println(moreStatus);

                }
            //System.out.println("Page COUNT with 50 rows per page: "+i);
            System.out.println("hd_TRUE_COUNT on page number "+i+" with 50 rows per page is "+hd_TRUE_count);
            System.out.println("hd_FALSE_COUNT on page number "+i+" with 50 rows per page is "+hd_FALSE_count);

        }
    }
}