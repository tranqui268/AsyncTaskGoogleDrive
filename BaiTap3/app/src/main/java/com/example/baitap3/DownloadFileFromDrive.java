package com.example.baitap3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.baitap3.api.ApiInterface;

import java.net.NetworkInterface;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadFileFromDrive extends AsyncTask<String,String,Boolean> {

    private Activity activity;
    private ProgressDialog progressDialog;
    String url = "https://drive.usercontent.google.com/u/0/uc?id=1w68dhZhGYf0_lMKMLiWB69BJelCZd0_z&export=download";

    public DownloadFileFromDrive(Activity mainActivity) {
        this.activity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean == false){
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            Toast.makeText(activity,"No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        if (isOnline(activity)){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.getHtmlResponse(url);
             call.enqueue(myRespone);

             return true;
        }else {
            return false;
        }
    }

    private Callback<ResponseBody> myRespone = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                URL link = response.raw().request().url().url();
                if (link.toString() != null){
                    Log.e("Link", link.toString());
                    new VideoDownLoader(activity).execute(link.toString());

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            Toast.makeText(activity,"Retry", Toast.LENGTH_SHORT).show();

        }
    };

    private boolean isOnline(Activity activity) {
        try{
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (cm != null){
                networkInfo = cm.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnectedOrConnecting();
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
