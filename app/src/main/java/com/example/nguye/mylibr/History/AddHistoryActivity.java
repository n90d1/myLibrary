package com.example.nguye.mylibr.History;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nguye.mylibr.History.Scanner.Scanner3Activity;
import com.example.nguye.mylibr.History.Scanner.Scanner4Activity;
import com.example.nguye.mylibr.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddHistoryActivity extends AppCompatActivity {
    public static TextView tvBorrowerIdHis, tvBookIdHis;
    TextView tvDateBorrow;
    Button btnAddHis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);
        linkNet();
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        tvDateBorrow.setText(currentDate);
        btnAddHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                String borrowerIdHis = tvBorrowerIdHis.getText().toString();
                String bookIdHis= tvBookIdHis.getText().toString();
                String dateBorrow = tvDateBorrow.getText().toString();
                if (borrowerIdHis.equals("")||bookIdHis.equals("")||dateBorrow.equals("")){
                    Toast.makeText(AddHistoryActivity.this, "Vui lòng không bỏ trống bất kìa trường nào", Toast.LENGTH_SHORT).show();
                    check = false;
                }
                if (check==true){
                    AddHis(linkHis(borrowerIdHis, bookIdHis, dateBorrow));
                    Intent intentNexHis = new Intent(AddHistoryActivity.this, ListHistoryActivity.class);
                    startActivity(intentNexHis);
                }
            }
        });
    }
    //Link Add History
    public String linkHis(String borrowerIdHis, String bookIdHis, String dateBorrow){
        String linkAddHis = "http://10.0.136.36:4000/addHistory?idHistory=null&borrowerId="+borrowerIdHis+"&bookId="+bookIdHis+"&dateBorrow="+dateBorrow;
        linkAddHis = linkAddHis.replace(" ","%20");
        return linkAddHis;
    }
    //Ánh Xạ
    public void linkNet(){
        tvBorrowerIdHis = (TextView)findViewById(R.id.tvBorrowerIdHis);
        tvBookIdHis = (TextView)findViewById(R.id.tvBookIdHis);
        tvDateBorrow = (TextView)findViewById(R.id.tvDateBorrowHis);
        btnAddHis = (Button)findViewById(R.id.btnAddHis);
    }
    private void AddHis(String link){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String check = "\"affectedRows\":1";
                if (response.toString().indexOf(check)!=-1) {
                    Toast.makeText(getApplicationContext(),"Đã thêm!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Lỗi, vui lòng kiểm tra lại hoặc liên lạc với quản trị viên!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void nextBoScan(View view){
        Intent intentBo = new Intent(AddHistoryActivity.this, Scanner3Activity.class);
        startActivity(intentBo);
    }
    public void nextBokScan(View view){
        Intent intentBok = new Intent(AddHistoryActivity.this, Scanner4Activity.class);
        startActivity(intentBok);
    }
}
