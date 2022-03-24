package com.example.sharkle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity
{
    SharedPreferences sp;
    EditText email;
    EditText userId;
    EditText userName;
    EditText password;
    TextView signUpRequest;
    RetrofitClient retrofitClient;
    RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.sign_up_email);
        userId = findViewById(R.id.sign_up_user_id);
        userName = findViewById(R.id.sign_up_user_name);
        password = findViewById(R.id.sign_up_password);

        //retrofit 이용 위해 초기화
        retrofitClient = RetrofitClient.getInstance();
        retrofitAPI = RetrofitClient.getRetrofitInterface();

        // 만약 회원가입 확인 버튼 눌렀는데 정보 다 넣으면 회원가입 시도

        /* 회원가입 시에 중복확인이나 그런거..? 해야함
         *회원가입 글자수 어떻게 할 건지 정해야함.
         */

        signUpRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 요청 버튼 눌림

                if(email.length()!=0 && userId.length()!=0 && password.length()!=0 && userName.length() !=0)
                {
                    //빈 정보 입력창 없을 때 가입 보내기
                   SignUp input = new SignUp(email.getText().toString(), userId.getText().toString(), password.getText().toString(), userName.getText().toString());
                   Call<LoginToken> call = retrofitAPI.signUpData(input);

                    call.enqueue(new Callback<LoginToken>(){
                        @Override
                        public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {
                            if(response.isSuccessful())
                            {
                                //회원가입&로그인 성공시

                                LoginToken loginToken = response.body();
                                //회원가입 확인용
                                Log.d("signUpTestA", loginToken.getAccessToken());
                                Log.d("signUpTestR",loginToken.getRefreshToken());

                                //sp에 토큰 저장
                                sp = getSharedPreferences("sp",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("accessToken",loginToken.getAccessToken());
                                editor.putString("refreshToken",loginToken.getRefreshToken());
                                editor.commit();

                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t)
                        {
                            //회원가입 오류 시 어떻게 처리할 지
                            t.printStackTrace();

                        }
                    });
                }
                else {
                    //빈 정보 입력창 있으면 할 일

                }

            }
        });


    }
}
