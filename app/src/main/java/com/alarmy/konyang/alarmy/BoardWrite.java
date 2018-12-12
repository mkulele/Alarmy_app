package com.alarmy.konyang.alarmy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import static com.alarmy.konyang.alarmy.Constant.BOARD_WRITE_URL;
import static com.alarmy.konyang.alarmy.Constant.NAME_SEARCH_URL;

public class BoardWrite extends AppCompatActivity {

    String idx;
    EditText bTitle;
    TextView bName;
    EditText bText;
    TextView bCat;
    String wurl = BOARD_WRITE_URL;
    String url = NAME_SEARCH_URL;
    String getIdxurl;
    String eNum=" ";
    String eName;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        eNum = i.getExtras().getString("eNum");
        category = i.getExtras().getString("category");
        setContentView(R.layout.activity_board_write);
        bTitle = (EditText) findViewById(R.id.btitle);
        bName = (TextView) findViewById(R.id.bname);
        bText = (EditText) findViewById(R.id.btext);
        bCat = (TextView)findViewById(R.id.bcat);
        bCat.setText(category);
        NameSearch();
    }

    public void NameSearch(){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+eNum,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    eName= response.getString("name");
                    bName.setText(eName);

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
    public  void bcancel (View view)
    {
        Intent i = new Intent(BoardWrite.this, NoticeBoard.class);
        i.putExtra("eNum",eNum);
        i.putExtra("category",category);
        startActivity(i);
    }
    public void bwrite(View view){
        JSONObject js = new JSONObject();
        try {
            js.accumulate("title", bTitle.getText().toString());
            js.accumulate("name", bName.getText().toString());
            js.accumulate("text", bText.getText().toString());
            js.accumulate("category",bCat.getText().toString());
            js.accumulate("ownerid",eNum);
            VolleyPost(js.toString());
        } catch (Exception e){
        }
    }

    public void VolleyPost(final String requestBody){

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.POST, wurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                        idx = response.getString("idx");
                        Dialog("글작성 성공");
                        Intent i = new Intent(BoardWrite.this, BoardView.class);
                        i.putExtra("idx", idx);
                        i.putExtra("category",category);
                        i.putExtra("eNum",eNum);
                        i.putExtra("ownerId",eNum);
                        startActivity(i);

                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Dialog("글작성 실패");
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
            builder = new AlertDialog.Builder(BoardWrite.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(BoardWrite.this);
        }
        builder.setTitle(title).setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}
