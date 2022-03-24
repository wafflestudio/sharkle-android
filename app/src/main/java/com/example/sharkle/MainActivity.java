package com.example.sharkle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.xml.transform.TransformerException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 앱 실행시 잠깐 아이콘 뜨면서 로그인 되어있는지 확인
//토큰 유효하면 마이페이지로, 토큰 유효하지 않으면 로그인 창으로

public class MainActivity extends AppCompatActivity
{
    SharedPreferences sp;
    RetrofitClient retrofitClient;
    RetrofitAPI retrofitAPI;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("sp",MODE_PRIVATE);

        retrofitClient = RetrofitClient.getInstance();
        retrofitAPI = RetrofitClient.getRetrofitInterface();

        if(sp.getString("accessToken","").equals("")){
            //sp에 저장된 토큰 없으면 바로 로그인 페이지로
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }else{
            //토큰 있으면 accessToken 만료 확인
            Call<String> accessToken = retrofitAPI.verifyToken(sp.getString("accessToken",""));
            accessToken.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        //홈 페이지로 바로 보내기
                        Intent homeIntent = new Intent(getBaseContext(), HomeActivity.class);
                        startActivity(homeIntent);
                    }
                    else{
                        onFailure(call, new Throwable());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Call<String> refreshToken = retrofitAPI.verifyToken(sp.getString("refreshToken",""));
                    refreshToken.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                Call<AccessToken> newAccessToken = retrofitAPI.getRefreshToken(sp.getString("refreshToken",""));
                                newAccessToken.enqueue(new Callback<AccessToken>() {
                                    @Override
                                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                                        if(response.isSuccessful()){
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.remove("accessToken");
                                            editor.putString("accessToken",response.body().getAccessToken());
                                            editor.commit();

                                            Intent homeIntent = new Intent(getBaseContext(), HomeActivity.class);
                                            startActivity(homeIntent);
                                        }
                                    }



                                    @Override
                                    public void onFailure(Call<AccessToken> call, Throwable t) {
                                        //accessToken 갱신 못 했을 때
                                        Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                                        startActivity(loginIntent);
                                    }
                                });
                            }else {
                                onFailure(call, new Throwable());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Call<AccessToken> refreshToken = retrofitAPI.getRefreshToken(sp.getString("refreshToken",""));
                            refreshToken.enqueue(new Callback<AccessToken>() {
                                @Override
                                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                                    if(response.isSuccessful()){
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.remove("refreshToken");
                                        editor.putString("refreshToken",response.body().getAccessToken());
                                        editor.commit();
                                    }
                                    else{
                                        onFailure(call, new Throwable());
                                    }
                                }

                                @Override
                                public void onFailure(Call<AccessToken> call, Throwable t) {
                                    Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                                    startActivity(loginIntent);
                                }
                            });
                        }
                    });
                }
            });
        }

    }





}
