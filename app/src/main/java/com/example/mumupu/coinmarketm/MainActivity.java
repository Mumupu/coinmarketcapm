package com.example.mumupu.coinmarketm;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.Application;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

     private RecyclerView recycler;
     private CoinsAdapter adapter;
     List<Coin> coins;
     RelativeLayout myLayout;
     AnimationDrawable animationDrawable;

     Boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new CoinsAdapter();

        Button Pch =  findViewById(R.id.button7);
        Button Price = findViewById(R.id.Price);
        Button Name = findViewById(R.id.Name);
        myLayout = (RelativeLayout) findViewById(R.id.myLayout);

            animationDrawable = (AnimationDrawable) myLayout.getBackground();
            animationDrawable.setEnterFadeDuration(4500);
            animationDrawable.setExitFadeDuration(4500);
            animationDrawable.start();

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        loadData();


        Pch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Collections.sort(coins, new Comparator<Coin>(){

                        @Override
                        public int compare(Coin o1, Coin o2) {
                            return Double.compare(o2.percentChange, o1.percentChange);
                        }
                    });
                    flag = false;
                } else {
                    Collections.sort(coins, new Comparator<Coin>(){

                        @Override
                        public int compare(Coin o1, Coin o2) {
                            return Double.compare(o1.percentChange, o2.percentChange);
                        }
                    });
                    flag = true;
                }

                adapter.setCoins(coins);
            }
        });

        Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Collections.sort(coins, new Comparator<Coin>(){

                        @Override
                        public int compare(Coin o1, Coin o2) {
                            return Double.compare(o2.priceUsd, o1.priceUsd);
                        }
                    });
                    flag = false;
                } else {
                    Collections.sort(coins, new Comparator<Coin>(){

                        @Override
                        public int compare(Coin o1, Coin o2) {
                            return Double.compare(o1.priceUsd, o2.priceUsd);
                        }
                    });
                    flag = true;
                }

                adapter.setCoins(coins);
            }
        });

        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Collections.sort(coins, new Comparator<Coin>(){

                        @Override
                        public int compare(Coin o1, Coin o2) {
                            return o2.name.toString().compareTo(o1.name.toString());
                        }
                    });
                    flag = false;
                } else {
                    Collections.sort(coins, new Comparator<Coin>(){

                        @Override
                        public int compare(Coin o1, Coin o2) {
                            return o1.name.toString().compareTo(o2.name.toString());
                        }
                    });
                    flag = true;
                }

                adapter.setCoins(coins);
            }
        });




    }

    private void loadData() {

        Application application = getApplication();
        App app = (App) application;
        Api api = app.getApi();

        api.ticker().enqueue(new Callback<List<Coin>>() {
            @Override
            public void onResponse(Call<List<Coin>> call, Response<List<Coin>> response) {

                coins = response.body();
                adapter.setCoins(coins);
            }

            @Override
            public void onFailure(Call<List<Coin>> call, Throwable t) {
            }
        });
    }
}

