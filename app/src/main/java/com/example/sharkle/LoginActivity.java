package com.example.sharkle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//로그인하는 액티비티
/*
* 할일
* 1. 로그인 레이아웃에서 각 뷰들 만들기
* 2. 버튼 눌렸을 때 액티비티 옮기는 것 구현
* 3. 아이디 비밀번호 찾기
* 4. 어느 사이클에 들어가야하는지 고민해야함..
* 5. accesstoken 만료시 refreshtoken 보내는 것도 구현.
*/
public class LoginActivity extends AppCompatActivity
{

    SharedPreferences sp;
    EditText email;
    EditText password;
    ImageView loginButton;
    ImageView signUpButton;
    private RetrofitClient retrofitClient;
    private RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up_button);

        //retrofit 이용 위해 초기화
        retrofitClient = RetrofitClient.getInstance();
        retrofitAPI = RetrofitClient.getRetrofitInterface();

        //로그인 버튼 눌렸을 때 이메일 비번 있으면 로그인 요청 보내기
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.length()!=0 && password.length()!=0)
                {
                    //이메일, 비번 모두 있을 때

                    User input = new User(email.getText().toString().trim(),password.getText().toString().trim());
                    Log.d("test","response");
                    Call<LoginToken> call = retrofitAPI.loginData(input);
                    Log.d("test","response");

                    call.enqueue(new Callback<LoginToken>(){
                        @Override
                        public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {
                            if(response.isSuccessful())
                            {
                                Log.d("test","response");
                                //로그인 성공시
                                LoginToken loginToken = response.body();

                                //로그인 확인용
                                Log.d("loginTestA", loginToken.getAccessToken());
                                Log.d("loginTestR",loginToken.getRefreshToken());

                                //sp에 토큰 저장
                                sp = getSharedPreferences("sp",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("accessToken",loginToken.getAccessToken());
                                editor.putString("refreshToken",loginToken.getRefreshToken());
                                editor.commit();
                                Log.d("test",sp.getString("accessToken","?"));
                                //이후 로그인 이후 액티비티 정해야함.
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginToken> call, Throwable t)
                        {
                            Log.d("login","fail");
                            //로그인 오류 시 어떻게 처리할 지
                          t.printStackTrace();

                        }
                    });
                }
                else
                {
                    //이메일 비번 둘 중 하나 없을 때
                }
            }
        });

        //만약 회원가입 버튼 눌리면 SignUpActivity로 이동
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
