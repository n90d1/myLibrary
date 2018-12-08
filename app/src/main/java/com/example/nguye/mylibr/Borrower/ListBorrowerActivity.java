package com.example.nguye.mylibr.Borrower;

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
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class ListBorrowerActivity extends AppCompatActivity {
    ListView lvBorrower;
    //10.18.101.162|| wifi FPT Polytechnic
    //10.0.136.36|| wifi Mang Day KTX
    String linkGetBorrower= "http://10.0.136.36:5000/listBorrower";
    public static ArrayList<borrower> borrowerArrayList;
    borrowerAdapter borrowersAdapter;
    public static int borrowerId;
    public static int posib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_borrower);
        lvBorrower = (ListView)findViewById(R.id.lvBorrower);
        borrowerArrayList = new ArrayList<>();
        borrowersAdapter = new borrowerAdapter(borrowerArrayList,getApplicationContext());
        lvBorrower.setAdapter(borrowersAdapter);
        //Đổ dữ liệu vào list
        getDataBo(linkGetBorrower);
        //Chạm để show thông tin người dùng
        lvBorrower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posib = position;
                showDetailDialogBo();
            }
        });
        lvBorrower.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    posib = position;
                    showAlertDialogBo();
                    borrowerId = parseInt(borrowerArrayList.get(position).getBorrowerId());
                } catch (Exception e) {}
                return false;
            }
        });
    }
    //Tạo dialog chi tiết người dùng
    private void showDetailDialogBo(){
        final Dialog dialOg = new Dialog(this);
        dialOg.setContentView(R.layout.dialog_detail_borrower);
        //Chuyển nền
        dialOg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final int posibo = ListBorrowerActivity.posib;
        //Ánh Xạ dialog
        TextView tvBoNameDetail = (TextView) dialOg.findViewById(R.id.tvBoNameDetail);
        TextView tvPhoneBoDetail = (TextView) dialOg.findViewById(R.id.tvPhoneBoDetail);
        TextView tvBoIdDetail = (TextView) dialOg.findViewById(R.id.tvBoIdDetail);
        TextView tvBirthdayBoDetail = (TextView) dialOg.findViewById(R.id.tvBirthdayBoDetail);
        TextView tvAddressBoDetail = (TextView) dialOg.findViewById(R.id.tvAddressBoDetail);
        TextView tvEmailBoDetail = (TextView) dialOg.findViewById(R.id.tvEmailBoDetail);
        Button btnEditBO = (Button) dialOg.findViewById(R.id.btnEditBo);
        Button btnCancelEditBO = (Button) dialOg.findViewById(R.id.btnCancelEditBo);
        //Đẩy thông tin vào dialog
        tvBoNameDetail.setText(""+ListBorrowerActivity.borrowerArrayList.get(posibo).getNameBorrower());
        tvPhoneBoDetail.setText("Liên lạc: "+ListBorrowerActivity.borrowerArrayList.get(posibo).getPhoneBo());
        tvBoIdDetail.setText("Mã số: "+ListBorrowerActivity.borrowerArrayList.get(posibo).getBorrowerId());
        tvBirthdayBoDetail.setText("Ngày sinh: "+ListBorrowerActivity.borrowerArrayList.get(posibo).getBirthdayBo());
        tvAddressBoDetail.setText("Địa chỉ: "+ListBorrowerActivity.borrowerArrayList.get(posibo).getAddressBo());
        tvEmailBoDetail.setText("Email: "+ListBorrowerActivity.borrowerArrayList.get(posibo).getEmailBo());
        //Set nút bấm cho dialog
        btnEditBO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBo = new Intent(ListBorrowerActivity.this, EditBorrowerActivity.class);
                startActivity(intentBo);
                dialOg.cancel();
            }
        });
        btnCancelEditBO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialOg.cancel();
            }
        });
        dialOg.show();
    }
    //Tạo dialog tìm kiếm
    private void showFindDialogBo(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_find_book);
        //Chuyển nền thành trong suốt
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final EditText edtFindBo = (EditText) dialog.findViewById(R.id.edtFind);
        Button btnFindBo = (Button) dialog.findViewById(R.id.btnFind);
        Button btnCancelFindBo = (Button) dialog.findViewById(R.id.btnCancelFind);

        btnFindBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kt = edtFindBo.getText().toString();
                if (kt.matches("\\d+")){
                    getDataBo(linkFindBoById(parseInt(kt)));
                } else if(kt.equals("")){
                    getDataBo(linkGetBorrower);
                } else {
                    getDataBo(linkFindBoByName(kt));
                }
                dialog.cancel();
            }
        });
        btnCancelFindBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    //Tạo dialog thêm sách
    public void showAddBoDialog(){
        final Dialog dialoG = new Dialog(this);
        dialoG.setContentView(R.layout.dialog_alert);
        //Chuyển nền thành trong suốt
        dialoG.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final Button btnYesBo = (Button) dialoG.findViewById(R.id.btnYes);
        final Button btnNoBo = (Button) dialoG.findViewById(R.id.btnNo);
        TextView tvAlert = (TextView) dialoG.findViewById(R.id.tvAlert);
        tvAlert.setText("Người dùng bạn tìm có vẻ như không có trong kho, bạn có muốn thêm họ vào danh sách không?");
        btnYesBo.setText("Có");
        btnYesBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListBorrowerActivity.this, AddBorrowerActivity.class);
                startActivity(intent);
                dialoG.cancel();
            }
        });
        btnNoBo.setText("Không, cảm ơn");
        btnNoBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoG.cancel();
            }
        });
        dialoG.show();
    }
    //Tạo dialog cảnh báo xoá
    public void showAlertDialogBo(){
        final Dialog diaLog = new Dialog(this);
        diaLog.setContentView(R.layout.dialog_alert);
        //Trong suốt nền
        diaLog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final Button btnDeleteBo = diaLog.findViewById(R.id.btnYes);
        final Button btnCancelDelBo = diaLog.findViewById(R.id.btnNo);
        TextView tvDeleteBo = diaLog.findViewById(R.id.tvAlert);
        tvDeleteBo.setText("Bạn có muốn xoá người dùng này ra khỏi danh sách?");
        btnDeleteBo.setText("Xoá");
        btnDeleteBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Xoá sách vừa chọn", Toast.LENGTH_SHORT).show();
                Run(linkDelBo(borrowerId));
                getDataBo(linkGetBorrower);
                diaLog.cancel();
            }
        });
        btnCancelDelBo.setText("Huỷ");
        btnCancelDelBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog.cancel();
            }
        });
        diaLog.show();
    }
    //Gắn menu vào
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_book, menu);
        return true;
    }
    //Set chức năng cho item menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_search:
                showFindDialogBo();
                return true;
            case R.id.item_add:
                Intent intent = new Intent(this, AddBorrowerActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_reset:
                getDataBo(linkGetBorrower);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Link xoá người dùng
    public String linkDelBo(int borrowerId){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkDelBo = "http://10.0.136.36:5000/deleteBorrower?borrowerId="+borrowerId;
        return linkDelBo;
    }
    //Link tìm người dùng theo id
    public String linkFindBoById(int borrowerId){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkFindIdBook = "http://10.0.136.36:5000/findBorrowerById?borrowerId="+borrowerId;
        return linkFindIdBook;
    }
    //Link tìm người dùng theo tên
    public String linkFindBoByName(String nameBorrower){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkFindNBook = "http://10.0.136.36:5000/findBorrowerByName?nameBorrower="+nameBorrower;
        linkFindNBook = linkFindNBook.replace(" ", "%20");
        return linkFindNBook;
    }
    //Lấy data xuống
    public  void getDataBo(String link){
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    String borrowerId, picBorrower, nameBorrower,birthdayBo, phoneBo, addressBo, emailBo;
                    borrowerArrayList.clear();

                    for (int i = 0; i< response.length(); i++){
                        try{
                            JSONObject jsonObject = response.getJSONObject(i);
                            borrowerId = jsonObject.getString("borrowerId");
                            picBorrower = jsonObject.getString("picBorrower");
                            nameBorrower = jsonObject.getString("nameBorrower");
                            birthdayBo = jsonObject.getString("birthdayBo");
                            phoneBo = jsonObject.getString("phoneBo");
                            addressBo = jsonObject.getString("addressBo");
                            emailBo = jsonObject.getString("emailBo");
                            borrowerArrayList.add(new borrower(borrowerId, picBorrower, nameBorrower, birthdayBo, phoneBo, addressBo,emailBo));
                            //Reset lại thay đổi
                            borrowersAdapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showAddBoDialog();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //Thực thi lệnh
    private void Run(String Link){
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
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
