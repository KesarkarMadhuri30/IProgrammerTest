package com.developer.testapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.developer.testapplication.Model.PhotoListModel;
import com.developer.testapplication.Adapter.PhotoAdapter;
import com.developer.testapplication.Retrofit.RetrofitApiClient;
import com.developer.testapplication.Retrofit.RetrofitClientInstance;
import com.developer.testapplication.R;
import com.developer.testapplication.Util.Utility;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.photo_recy)
    RecyclerView photo_recy;

    @BindView(R.id.empty_lyt)
    LinearLayout empty_lyt;

    PhotoAdapter photoAdapter;
    String TAG = "Main";
    @BindView(R.id.imgv_table)
    ImageView imgv_table;

    ArrayList<PhotoListModel> photoDataList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        photo_recy.setLayoutManager(layoutManager);
        photo_recy.hasFixedSize();

        if (Utility.isNetworkAvailable(this)) {
            getPhotoUrlData();
        }
        else
        {
            Toast.makeText(MainActivity.this, "No internet. Check Your Internet is connection", Toast.LENGTH_SHORT).show();

        }
    }

    @OnClick(R.id.imgv_table)
    public void showCompare()
    {

    }

    private void getPhotoUrlData() {
        photoDataList.clear();
        final ProgressDialog uploading;
        uploading = ProgressDialog.show(this, "Loading", "Please wait...", false, false);

        RetrofitApiClient service = RetrofitClientInstance.getInstance().getApi();
        Call<ArrayList<PhotoListModel>> photo_call = service.getPhotoList();

        photo_call.enqueue(new Callback<ArrayList<PhotoListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PhotoListModel>> call, Response<ArrayList<PhotoListModel>> response) {
                if (response.isSuccessful())
                {
                    uploading.dismiss();
                    empty_lyt.setVisibility(View.GONE);
                    photo_recy.setVisibility(View.VISIBLE);
                    Log.d(TAG,""+response.body());
                    photoDataList = response.body();
                    SetPhotoData(photoDataList);

                }
                else
                {
                    uploading.dismiss();
                    empty_lyt.setVisibility(View.VISIBLE);
                    photo_recy.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PhotoListModel>> call, Throwable t) {
                uploading.dismiss();
                photo_recy.setVisibility(View.GONE);
                empty_lyt.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SetPhotoData(ArrayList<PhotoListModel> photoDataList) {
        photoAdapter = new PhotoAdapter(this,photoDataList,true);
        photo_recy.setAdapter(photoAdapter);
    }
}