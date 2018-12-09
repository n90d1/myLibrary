package com.example.nguye.mylibr.History;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nguye.mylibr.Book.book;
import com.example.nguye.mylibr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ListHistoryActivity extends AppCompatActivity {
    ListView lvHistory;
    String linkGetHistory = "http://10.0.136.36:3000/listHistory";
    public static ArrayList<history> historyArrayList;
    historyAdapter historysAdapter;
    public static int idHistory;
    public static int posih;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_history);
        lvHistory = (ListView)findViewById(R.id.lvHistory);
        historyArrayList = new ArrayList<>();
        historysAdapter =  new historyAdapter(historyArrayList, getApplicationContext());
        lvHistory.setAdapter(historysAdapter);
        getDataHis(linkGetHistory);

        lvHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    posih = position;
                    showAlertDialogHis();
                    idHistory = parseInt(historyArrayList.get(position).getIdHistory());
                }catch (Exception e){}
                return false;
            }
        });
    }
    public void showAlertDialogHis(){
        final Dialog diaLog = new Dialog(this);
        diaLog.setContentView(R.layout.dialog_alert);
        //Trong suốt nền
        diaLog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final Button btnDeleteHis = (Button) diaLog.findViewById(R.id.btnYes);
        final Button btnCancelDelHis = (Button) diaLog.findViewById(R.id.btnNo);
        TextView tvDeleteHis = (TextView) diaLog.findViewById(R.id.tvAlert);
        tvDeleteHis.setText("Bạn có muốn xoá lịch sử này không?");
        btnDeleteHis.setText("Xoá"); btnCancelDelHis.setText("Không xoá");
        btnDeleteHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunHis(linkDel(idHistory));
                diaLog.cancel();
                getDataHis(linkGetHistory);
            }
        });
        diaLog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_addHis:
                Intent intentAddHis = new Intent(ListHistoryActivity.this, AddHistoryActivity.class);
                startActivity(intentAddHis);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public String linkDel(int idHistory){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkDelHis = "http://10.0.136.36:4000/deleteHistory?idHistory="+idHistory;
        return linkDelHis;
    }

    public  void getDataHis(String link){
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    String bookId, idHistory, borrowerId, dateBorrow;
                    historyArrayList.clear();

                    for (int i = 0; i< response.length(); i++){
                        try{
                            JSONObject jsonObject = response.getJSONObject(i);
                            idHistory = jsonObject.getString("idHistory");
                            borrowerId = jsonObject.getString("borrowerId");
                            bookId = jsonObject.getString("bookId");
                            dateBorrow = jsonObject.getString("dateBorrow");
                            historyArrayList.add(new history(idHistory, borrowerId, bookId, dateBorrow));
                            //Reset lại thay đổi
                            historysAdapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListHistoryActivity.this, "Lỗi, Nếu gặp lại hiện tượng này vui lòng liên lạc với quản trị viên", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void RunHis(String Link){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String check = "\"affectedRows\":1";
                if (response.indexOf(check)!=-1) {
                    Toast.makeText(getApplicationContext(),"Đã xong", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Lỗi ! Vui lòng liên hệ với Quản trị viên", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
