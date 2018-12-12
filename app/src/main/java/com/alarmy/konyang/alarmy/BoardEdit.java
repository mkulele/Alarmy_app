package com.alarmy.konyang.alarmy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

import static com.alarmy.konyang.alarmy.Constant.BOARD_EDIT_URL;
import static com.alarmy.konyang.alarmy.Constant.BOARD_VIEW_URL;

public class BoardEdit extends AppCompatActivity {
    String eurl = BOARD_EDIT_URL;
    EditText eText;
    TextView eName;
    TextView eTile;
    String idx;
    String vurl = BOARD_VIEW_URL;
    String vText,vTitle,vName;
    String category,category1;
    String ownerId;
    String eNum;
    TextView eCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_edit);
        Intent i = getIntent();
        idx=i.getExtras().getString("idx");
        category=i.getExtras().getString("category");
        eNum = i.getExtras().getString("eNum");
        ownerId = i.getExtras().getString("ownerId");
        eName = (TextView)findViewById(R.id.ename);
        eText = (EditText)findViewById(R.id.etext);
        eTile = (TextView)findViewById(R.id.etitle);
        eCat = (TextView)findViewById(R.id.ecat);
        eCat.setText(category);
        sendRequest();
    }
    public  void eedit(View vietestw){
        JSONObject js = new JSONObject();
        try {
            js.accumulate("text", eText.getText().toString());
            js.accumulate("idx",idx);
            VolleyPost(js.toString());
        }
        catch (Exception e){}
    }
    public void sendRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, vurl+idx,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    vTitle = response.getString("title");
                    vName = response.getString("owner");
                    vText = response.getString("content");
                    category1 = response.getString("category");
                    eTile.setText(vTitle);
                    eName.setText(vName);
                    eText.setText(vText);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jsonObjectRequest);
    }
    public void VolleyPost(final String requestBody){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.POST, eurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                        Dialog("글수정 성공");
                        Intent i = new Intent(BoardEdit.this, BoardView.class);
                        i.putExtra("idx", idx);
                        i.putExtra("category",category);
                        i.putExtra("ownerId", ownerId);
                        i.putExtra("category", category);
                        i.putExtra("eNum", eNum);
                        startActivity(i);
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Dialog("글수정 실패");
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
            builder = new AlertDialog.Builder(BoardEdit.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(BoardEdit.this);
        }
        builder.setTitle(title).setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}
