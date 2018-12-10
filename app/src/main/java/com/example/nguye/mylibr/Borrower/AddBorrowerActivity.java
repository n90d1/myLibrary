package com.example.nguye.mylibr.Borrower;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nguye.mylibr.R;
import com.example.nguye.mylibr.Book.ScannerActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBorrowerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static EditText edtBorrowerId;
    EditText edtBorrowerName, edtPhoneBo, edtAddressBo, edtEmailBo;
    TextView tvBirthdayBo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_add_borrower);
        linkNetBo();
    }
    //Linh Add Borrower
    public String linkBo (String nameBorrower, String phoneBo, String addressBo, String borrowerId, String birthdayBo, String emailBo){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkAddBo = "http://10.0.136.36:5000/addBorrower?borrowerId="+borrowerId+"&picBorrower="+"&nameBorrower="+nameBorrower+"&birthdayBo="+birthdayBo+"&phoneBo="+phoneBo+"&addressBo="+addressBo+"&emailBo="+emailBo;
        linkAddBo = linkAddBo.replace(" ","%20");
        return linkAddBo;
    }
    //Add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);
        return true;
    }
    //Gắn chức năng cho item Dialog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                try{
                    boolean check = true;
                    String borrowerId = edtBorrowerId.getText().toString();
                    String nameBorrower = edtBorrowerName.getText().toString();
                    String addressBo = edtAddressBo.getText().toString();
                    String phoneBo = edtPhoneBo.getText().toString();
                    String birthdayBo = tvBirthdayBo.getText().toString();
                    String emailBo = edtEmailBo.getText().toString();
                    if (borrowerId.equals("")||nameBorrower.equals("")||addressBo.equals("")
                            ||phoneBo.equals("")||birthdayBo.equals("")||emailBo.equals("")){
                        Toast.makeText(this, "Vui lòng không bỏ trống bất kỳ trường nào", Toast.LENGTH_SHORT).show();
                        check = false;
                    }else if (!phoneBo.matches("\\d+")){
                        Toast.makeText(this, "Vui lòng kiểm tra lại số điện thoại", Toast.LENGTH_SHORT).show();
                        check=false;
                    }else if (phoneBo.length()>10){
                        Toast.makeText(this, "Số điện thoại không quá 10 số", Toast.LENGTH_SHORT).show();
                        check = false;
                    }
                    if (check==true){
                        //Chèn zô link Add
                        AddBo(linkBo(nameBorrower, phoneBo, addressBo, borrowerId, birthdayBo, emailBo));
                        Intent intentNext = new Intent(AddBorrowerActivity.this, ListBorrowerActivity.class);
                        startActivity(intentNext);
                    }
                }catch (Exception e){
                    Toast.makeText(this, "Lỗi ! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Ánh xạ
    public void linkNetBo(){
        edtBorrowerName = (EditText) findViewById(R.id.edtBorrowerName);
        edtPhoneBo = (EditText) findViewById(R.id.edtPhoneBo);
        edtAddressBo = (EditText) findViewById(R.id.edtAddressBo);
        edtBorrowerId = (EditText) findViewById(R.id.edtBorrowerId);
        tvBirthdayBo = (TextView) findViewById(R.id.tvBirthdayBo);
        edtEmailBo = (EditText) findViewById(R.id.edtEmailBo);
    }
    //Chạy link
    private void AddBo(String link){
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

    public void dialogDatePicker(View v){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"data picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c =Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        tvBirthdayBo.setText(currentDateString);

    }
    public void scannerNext(View v){
        Intent intentNext= new Intent(AddBorrowerActivity.this, Scanner2Activity.class);
        startActivity(intentNext);
    }
}
