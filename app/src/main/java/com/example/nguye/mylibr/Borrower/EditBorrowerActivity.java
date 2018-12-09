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
import com.example.nguye.mylibr.Book.ListBookActivity;
import com.example.nguye.mylibr.R;

import java.util.Calendar;

public class EditBorrowerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText edtBoNameEdit, edtPhoneBoEdit, edtAddressBoEdit, edtEmailBoEdit;
    TextView tvBoIdEdit, tvBirthdayBoEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_borrower);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        linkNetBo();
        setTextBo();
    }
    //Thêm menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save,menu);
        return true;
    }
    //Set chức năng cho item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                String borrowerIdEd = tvBoIdEdit.getText().toString();
                String nameBorrowerEd = edtBoNameEdit.getText().toString();
                String phoneBoEd = edtPhoneBoEdit.getText().toString();
                String addressBoEd = edtAddressBoEdit.getText().toString();
                String birthdayBoEd = tvBirthdayBoEdit.getText().toString();
                String emailBoEd = edtEmailBoEdit.getText().toString();
                //Chèn link
                UpdateBo(link(borrowerIdEd, nameBorrowerEd, phoneBoEd, addressBoEd, birthdayBoEd, emailBoEd));
                Intent intentBo = new Intent(EditBorrowerActivity.this, ListBorrowerActivity.class);
                startActivity(intentBo);
        }
        return super.onOptionsItemSelected(item);
    }
    //Link Edit Borrower
    public String link(String borrowerIdEd, String nameBorrowerEd, String phoneBoEd, String addressBoEd, String birthdayBoEd, String emailBoEd){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkEditBo = "http://10.0.136.36:5000/updateBorrower?picBorrower=&nameBorrower="+nameBorrowerEd+"&birthdayBo="+birthdayBoEd+"&phoneBo="+phoneBoEd+"&addressBo="+addressBoEd+"&emailBo="+emailBoEd+"&borrowerId="+borrowerIdEd;
        linkEditBo = linkEditBo.replace(" ", "%20");
        return linkEditBo;
    }
    //Chạy link update
    private void UpdateBo(String link){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String check = "\"affectedRows\":1";
                if (response.toString().indexOf(check)!=-1) {
                    Toast.makeText(getApplicationContext(),"Đã sửa xong!", Toast.LENGTH_LONG).show();
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

    public void linkNetBo(){
        tvBoIdEdit = (TextView)findViewById(R.id.tvBoIdEdit);
        edtBoNameEdit = (EditText)findViewById(R.id.edtBoNameEdit);
        edtPhoneBoEdit = (EditText)findViewById(R.id.edtPhoneBoEdit);
        edtAddressBoEdit = (EditText)findViewById(R.id.edtAddressBoEdit);
        tvBirthdayBoEdit = (TextView) findViewById(R.id.tvBirhtdayBoEdit);
        edtEmailBoEdit = (EditText)findViewById(R.id.edtEmailBoEdit);
    }

    public void setTextBo(){
        final int posib = ListBorrowerActivity.posib;
        tvBoIdEdit.setText(ListBorrowerActivity.borrowerArrayList.get(posib).getBorrowerId());
        edtBoNameEdit.setText(ListBorrowerActivity.borrowerArrayList.get(posib).getNameBorrower());
        edtPhoneBoEdit.setText(ListBorrowerActivity.borrowerArrayList.get(posib).getPhoneBo());
        edtAddressBoEdit.setText(ListBorrowerActivity.borrowerArrayList.get(posib).getAddressBo());
        tvBirthdayBoEdit.setText(ListBorrowerActivity.borrowerArrayList.get(posib).getBirthdayBo());
        edtEmailBoEdit.setText(ListBorrowerActivity.borrowerArrayList.get(posib).getEmailBo());
    }
    public void datePicker(View v){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(),"data picker Ed");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tvBirthdayBoEdit.setText(year+"-"+month+"-"+dayOfMonth);
    }
}
