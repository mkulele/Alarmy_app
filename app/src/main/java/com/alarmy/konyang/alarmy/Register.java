package com.alarmy.konyang.alarmy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.alarmy.konyang.alarmy.Constant.REGISTER_URL;

public class Register extends Activity {
    String url=REGISTER_URL;
    EditText Id;
    EditText Passwd;
    EditText Passwd2;
    EditText Name;
    EditText Phone;
    EditText Club;
    EditText Email;
    EditText Grade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Id = (EditText) findViewById(R.id.rid);
        Passwd = (EditText) findViewById(R.id.passwd);
        Passwd2 = (EditText) findViewById(R.id.passwd2);
        Name = (EditText) findViewById(R.id.name);
        Phone = (EditText) findViewById(R.id.phone);
        Club = (EditText) findViewById(R.id.club);
        Email = (EditText) findViewById(R.id.email);
        Grade = (EditText) findViewById(R.id.grade);
    }

    public void RegisterOk(View view){
             JSONObject js = new JSONObject();
        try {
            js.accumulate("id", Id.getText().toString());
            js.accumulate("passwd", Passwd.getText().toString());
            js.accumulate("passwd2", Passwd2.getText().toString());
            js.accumulate("name", Name.getText().toString());
            js.accumulate("phone", Phone.getText().toString());
            js.accumulate("club", Club.getText().toString());
            js.accumulate("email", Email.getText().toString());
            js.accumulate("grade", Grade.getText().toString());
            VolleyPost(js.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void VolleyPost(final String requestBody){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Dialog("회원가입 성공");
                    Intent i = new Intent(Register.this, MainActivity.class);
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Dialog("회원가입 실패");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(JOPR);
    }

    public void Dialog(String title){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Register.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(Register.this);
        }
        builder.setTitle(title).setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public void Cancel(View view){
        Intent cancel = new Intent(Register.this, MainActivity.class);
        startActivity(cancel);
    }
}
