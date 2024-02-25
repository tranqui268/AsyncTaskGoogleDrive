package com.example.baitap3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.tv.TvContract;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class VideoDownLoader extends AsyncTask<String,String,Boolean> {
    private Activity activity;
    private ProgressDialog progressDialog;
    public static String downloadFile = String.valueOf(Environment.getExternalStorageDirectory()) + File.separator + "Download/Demo.mp4";
    private MethodCallback methodCallback;

    public VideoDownLoader(Activity activity){
         this.activity = activity;
         methodCallback = (MethodCallback) activity;
    }

    public  interface MethodCallback {
        void onMethodCallback(String downloadFile);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Downloading....please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        int count;
        try{
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream is = new BufferedInputStream(url.openStream(),8192);
            Log.e("DownloadFile",downloadFile);
            OutputStream os = new FileOutputStream(downloadFile);
            byte data[] = new byte[1024];
            while ((count = is.read(data)) != -1){
                os.write(data,0,count);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if (aBoolean){
            methodCallback.onMethodCallback(downloadFile);
        }else {
            Toast.makeText(activity,"Download fail",Toast.LENGTH_SHORT).show();
        }
    }
}
