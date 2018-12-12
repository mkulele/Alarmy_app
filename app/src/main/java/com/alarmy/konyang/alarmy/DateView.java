package com.alarmy.konyang.alarmy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.alarmy.konyang.alarmy.Constant.BOARD_LIST_URL;

public class DateView extends AppCompatActivity {

    private ListView dListView;
    private MyAdapter dadapter;
    private ArrayList<MyItem> dItems;
    String bTitle, bName, bTime, bIdx;
    String url=BOARD_LIST_URL;
    String eNum=" ";
    String date;
    String category;
    String ownerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_view);
        Intent i = getIntent();
        eNum = i.getExtras().getString("eNum");
        date = i.getExtras().getString("date");
        dListView = (ListView) findViewById(R.id.notice);
        dItems = new ArrayList<>();
        dadapter = new MyAdapter(DateView.this, dItems);
        dListView.setAdapter(dadapter);
        BoardList();
        dListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DateView.this, BoardView.class);
                i.putExtra("idx", dItems.get(position).getIdx());
                i.putExtra("ownerId",dItems.get(position).getOwnerid());
                i.putExtra("category",dItems.get(position).getCategory());
                i.putExtra("eNum",eNum);
                startActivity(i);
            }
        });
    }
    public void BoardList(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+date, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        bIdx = object.getString("num");
                        bTime = object.getString("time");
                        bTitle = object.getString("title");
                        bName = object.getString("owner");
                        category = object.getString("category");
                        ownerId = object.getString("ownerid");
                        dItems.add(new MyItem(bIdx,bTitle,bName,bTime,category,ownerId));
                        dadapter.notifyDataSetChanged();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
    }
}
