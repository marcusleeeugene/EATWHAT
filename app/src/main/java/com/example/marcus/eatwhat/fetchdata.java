package com.example.marcus.eatwhat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marcus on 26-Dec-17.
 */

public class fetchdata extends AsyncTask<Void,Void,Void> {

    String data = "";
    String eatingplace = "";
    String logoimagejson = "";
    Bitmap logoimage;
    public static String promovalidity = "";
    URL promoimageurl;
    String promoimagejson = "";
    Bitmap promoimage;
    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL("https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1zfDf5wE6EKniXZN7EHKw4nkfvT46F6OITDIk6vZkgic&sheet=Sheet1");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line!=null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            data = data.substring(10,data.length()-5); //Clean JSON Data from unneccessary symbols/characters
            //***NEXT TIME CAN CHECK LOCATION, IF SG THEN SUBSTRING NUMBER WILL CHANGE TO SG STRING LENGTH, AUTOMATION
            System.out.println(data);
            //Get EatingPlace Name from GoogleSheet
            JSONArray JA = new JSONArray(data);
            JSONObject JO = (JSONObject) JA.get(randomWithRange(0,JA.length()-1)); //get random group (int) Math.ceil(Math.random() * JA.length())
            eatingplace = JO.getString("EatingPlace");

            //Get EatingPlace Logo from GoogleSheet
            logoimagejson = JO.getString("ImageUrl");
            URL logourl = new URL(logoimagejson);
            logoimage = BitmapFactory.decodeStream(logourl.openConnection().getInputStream());

            //Check Validity, if promotion valid, show inside promoad layout
            promovalidity = JO.getString("Validity");
            if(promovalidity.equals("VALID")){
                promoimagejson = JO.getString("PromotionUrl");
                promoimageurl = new URL(promoimagejson);
                promoimage = BitmapFactory.decodeStream(promoimageurl.openConnection().getInputStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        HomeFragment.label.setText(eatingplace);
        System.out.println(eatingplace);
        HomeFragment.imagebox.setImageBitmap(logoimage);
        if(promovalidity.equals("VALID")){
            BitmapDrawable bitmapDrawable = new BitmapDrawable(promoimage);
            HomeFragment.promoad.setBackgroundDrawable(bitmapDrawable);
            HomeFragment.uri = Uri.parse(promoimageurl.toString()); // missing 'http://' will cause crashe
        }

    }

    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}




