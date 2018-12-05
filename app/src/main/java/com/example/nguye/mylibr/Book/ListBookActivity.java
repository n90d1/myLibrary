package com.example.nguye.mylibr.Book;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nguye.mylibr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ListBookActivity extends AppCompatActivity {
    ListView lvBook;
    //10.18.101.162|| wifi FPT Polytechnic
    //10.0.136.36|| wifi Mang Day KTX
    String linkGetBook="http://10.0.136.36:3000/listBook";
    public static ArrayList<book> bookArrayList;
    bookAdapter booksAdapter;
    public static int bookId;
    public static int posi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);
        netLink();
        bookArrayList = new ArrayList<>();
        booksAdapter = new bookAdapter(bookArrayList, getApplicationContext());
        lvBook.setAdapter(booksAdapter);
        //Đổ dữ liệu từ data vào list
        getData(linkGetBook);
        //Chạm vào item để sửa thông tin sách
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            posi = position;
            Intent intent = new Intent(getApplicationContext(), EditBookActivity.class);
            startActivity(intent);
            }
        });
        //Nhấn giữ lâu để hiện dialog xoá
        lvBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    posi = position;
                    showAlertDialog();
                    bookId = parseInt(bookArrayList.get(position).getBookId());
                } catch (Exception e) {
                }
                return false;
            }
        });
}
//Gắn menu custom
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_book, menu);
        return true;
    }
//Tuỳ chỉnh item trong menu custom
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_search:
                showFindDialog();
                return true;
            case R.id.item_add:
                Intent intent = new Intent(this, AddBookActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_reset:
                getData(linkGetBook);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//Tạo dialog tìm kiếm
    private void showFindDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_find);
        dialog.setCanceledOnTouchOutside(false);
        //Chuyển nền thành trong suốt
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final EditText edtFindBok = (EditText) dialog.findViewById(R.id.edtFindBok);
        Button btnFindBok = (Button) dialog.findViewById(R.id.btnFindBok);
        Button btnCancelFindBok = (Button) dialog.findViewById(R.id.btnCancelFindBok);

        btnFindBok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kt = edtFindBok.getText().toString();
                if (kt.matches("\\d+")){
                    getData(linkFindById(parseInt(kt)));
                } else if(kt.equals("")){
                    getData(linkGetBook);
                } else {
                    getData(linkFindByName(kt));
                }
                dialog.cancel();
            }
        });
        btnCancelFindBok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
//Tạo dialog thêm sách
    public void showAddDialog(){
        final AlertDialog.Builder builderAdd = new AlertDialog.Builder(this);
        builderAdd.setTitle("Chú ý")
                .setMessage("Có vẻ như sách bạn tìm không có, bạn có muốn thêm sách mới không")
                .setCancelable(false)
                .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ListBookActivity.this, AddBookActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = builderAdd.create();
        alertDialog.show();
    }
//Tạo dialog cảnh báo xoá
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder .setTitle("Xóa")
                .setMessage("Bạn có muốn xóa không?")
                .setCancelable(false)
                .setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Xoá sách vừa chọn", Toast.LENGTH_SHORT).show();
                Run(linkDel(bookId));
                getData(linkGetBook);
            }
        });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
    }
//Hàm xoá sách
    public String linkDel(int bookId){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkDelBook = "http://10.0.136.36:3000/deleteBook?bookId="+bookId;
        return linkDelBook;
    }
//Hàm tìm sách theo id
    public String linkFindById(int bookId){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkFindIdBook = "http://10.0.136.36:3000/findBookById?bookId="+bookId;
        return linkFindIdBook;
    }
//Hàm tìm sách theo tên
    public String linkFindByName(String bookName){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkFindNBook = "http://10.0.136.36:3000/findBookByName?bookName="+bookName;
        linkFindNBook = linkFindNBook.replace(" ", "%20");
        return linkFindNBook;
    }
//Lấy data xuống sau đó đẩy lên list
//Dùn biến String để tuỳ chọn thông tin lấy
    public  void getData(String link){
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    String bookId, bookName, kind,pH, author, picPre, note;
                    int price;
                    bookArrayList.clear();

                    for (int i = 0; i< response.length(); i++){
                        try{
                            JSONObject jsonObject = response.getJSONObject(i);
                            bookId = jsonObject.getString("bookId");
                            bookName = jsonObject.getString("bookName");
                            kind = jsonObject.getString("kind");
                            pH = jsonObject.getString("pH");
                            author = jsonObject.getString("author");
                            price = jsonObject.getInt("price");
                            picPre = jsonObject.getString("picPre");
                            note = jsonObject.getString("note");
                            bookArrayList.add(new book(bookId, bookName, kind,pH,author,price,picPre,note));
                            //Reset lại thay đổi
                            booksAdapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"Lỗi ! Vui lòng kiểm tra lại hoặc liên hệ với quản trị viên",Toast.LENGTH_SHORT).show();
                showAddDialog();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
//Ánh xạ
    public void netLink(){
        lvBook = (ListView) findViewById(R.id.lvBook);
    }
//Hàm thực thi lệnh
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
