package com.bytedance.android.lesson.restapi.solution;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bytedance.android.lesson.restapi.solution.bean.Cat;
import com.bytedance.android.lesson.restapi.solution.newtork.ICatService;
import com.bytedance.android.lesson.restapi.solution.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.ViewHolder;

public class Solution2C1Activity extends AppCompatActivity {

    private static final String TAG = "DebugR";
    public Button mBtn;
    public RecyclerView mRv;
    private List<Cat> mCats = new ArrayList<>();
    //private String url;
    private Cat catTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution2_c1);
        mBtn = findViewById(R.id.btn);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new Adapter() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ImageView imageView = new ImageView(viewGroup.getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setAdjustViewBounds(true);
                return new MyViewHolder(imageView);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
                ImageView iv = (ImageView) viewHolder.itemView;

                //  Uncomment these 2 lines, assign image url of Cat to this url variable
                String url = mCats.get(i).getUrl();
                Glide.with(iv.getContext()).load(url).into(iv);
            }

            @Override
            public int getItemCount() {
                return mCats.size();
            }
        });
    }

    public static class MyViewHolder extends ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void requestData(View view) {
        mBtn.setText(R.string.requesting);
        mBtn.setEnabled(false);

        //  Send request for 5 random cats here, don't forget to use {@link retrofit2.Call#enqueue}
        // Call restoreBtn() and loadPics(response.body()) if success
        // Call restoreBtn() if failure
        //CatInfoGetThread infoGetThread = new CatInfoGetThread(handler);
        //infoGetThread.start();
//        try {
//            //Cat cat = NetworkUtils.getResponseWithRetrofit();
//            Log.d(TAG, "getCat() get cat = [" + catTransfer + "]");
//            ArrayList<Cat> cats = new ArrayList<>();
//            cats.add(catTransfer);
//            loadPics(cats);
//            restoreBtn();
//        } catch (Exception e) {
//            restoreBtn();
//        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<List<Cat>> call = retrofit.create(ICatService.class).randomFiveCats();
        call.enqueue(new Callback<List<Cat>>() {

            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {
                ;
                //Log.d(TAG, "getCat() get cat = [" + catTransfer + "]");
                //ArrayList<Cat> cats = new ArrayList<>();
                //cats.add(catTransfer);
                loadPics(response.body());
                restoreBtn();
            }

            @Override
            public void onFailure(Call<List<Cat>> arg0, Throwable arg1) {
                Log.d(TAG, "onFailure() called with: arg0 = [" + arg0 + "], arg1 = [" + arg1 + "]");
                restoreBtn();
            }
        });
    }


    private void loadPics(List<Cat> cats) {
        mCats = cats;
        mRv.getAdapter().notifyDataSetChanged();
    }

    private void restoreBtn() {
        mBtn.setText(R.string.request_data);
        mBtn.setEnabled(true);
    }
}
