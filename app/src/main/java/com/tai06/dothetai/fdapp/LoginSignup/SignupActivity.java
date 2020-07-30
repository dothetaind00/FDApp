package com.tai06.dothetai.fdapp.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tai06.dothetai.fdapp.R;
import com.tai06.dothetai.fdapp.URL.Link;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout txt_inputlayout1,txt_inputlayout2,txt_inputlayout5;
    private TextInputEditText email_signup,password_signup,confirm_signup,name_signup,sdt_signup;
    private Button btn_signup;
    private TextView login_signup;
    private String email,password,confirm,name,sdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        Click_event();
    }

    private void init(){
        email_signup = findViewById(R.id.email_signup);
        password_signup = findViewById(R.id.password_signup);
        confirm_signup = findViewById(R.id.confirm_signup);
        name_signup = findViewById(R.id.name_signup);
        sdt_signup = findViewById(R.id.sdt_signup);
        btn_signup = findViewById(R.id.btn_signup);
        login_signup = findViewById(R.id.login_signup);
        txt_inputlayout1 = findViewById(R.id.txt_inputlayout1);
        txt_inputlayout2 = findViewById(R.id.txt_inputlayout2);
        txt_inputlayout5 = findViewById(R.id.txt_inputlayout5);
    }

    private void getText(){
        email    = email_signup.getText().toString().trim();
        password = password_signup.getText().toString().trim();
        confirm  = confirm_signup.getText().toString().trim();
        name     = name_signup.getText().toString().trim();
        sdt    = sdt_signup.getText().toString().trim();
    }

    private boolean check_email(){
        String email = email_signup.getText().toString().trim();
        Matcher matcher = Link.PATTERN_EMAIL.matcher(email); // PATTERN_EMAIL là biểu thức chính quy
        if (!matcher.matches()){ // nếu không đúng biểu thức chính quy
            txt_inputlayout1.setErrorEnabled(true);
            txt_inputlayout1.setError("Kiểm tra lại email");
            return false;
        }else{
            txt_inputlayout1.setErrorEnabled(false);
            return true;
        }
    }

    private boolean check_psw(){
        String psw = password_signup.getText().toString().trim();
        Matcher matcher = Link.PATTERN_PASSWORD.matcher(psw);// PATTERN_PASSWORD là biểu thức chính quy
        if (!matcher.matches()){
            txt_inputlayout2.setErrorEnabled(true);
            txt_inputlayout2.setError("Mật khẩu dài hơn 8 kí tự,gồm a-zA-Z0-9,không kí tự đặc biệt");
            return false;
        }else{
            txt_inputlayout2.setErrorEnabled(false);
            return true;
        }
    }

    private boolean check_phone(){
        String phone = sdt_signup.getText().toString().trim();
        Matcher matcher = Link.PATTERN_PHONE.matcher(phone);
        if (!matcher.matches()){
            txt_inputlayout5.setErrorEnabled(true);
            txt_inputlayout5.setError("Kiểm tra lại số điện thoại");
            return false;
        }else{
            txt_inputlayout5.setErrorEnabled(false);
            return true;
        }
    }

    private void Click_event(){
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                getText();
                int count = password.length();
                if (email.equals("") || password.equals("") || confirm.equals("") || name.equals("") || sdt.equals("")){
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if(check_email() && check_psw() && check_phone()){
                        if (password.equals(confirm)) {
                            check_Email();
                        } else {
                            Toast.makeText(SignupActivity.this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity.this, "Kiểm tra lại thông tin!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view!=null){
            InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void check_Email(){
        getText();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Link.URL_check_email, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(SignupActivity.this, "Email đã được sử dụng", Toast.LENGTH_SHORT).show();
                        }else{
                            post_SignUp();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Error"+error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("email",email);
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        thread.start();
    }

    private void post_SignUp(){
        getText();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Link.URL_postInsertKH, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(SignupActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignupActivity.this, "Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi "+error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("email",email);
                        param.put("password",password);
                        param.put("ten_kh",name);
                        param.put("sdt",sdt);
                        param.put("image","");
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        thread.start();
    }
}