package com.example.nguye.mylibr.Book;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.nguye.mylibr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ListBookActivity extends AppCompatActivity {
    ListView lvBook;
    public static EditText edtFindBok;
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
        lvBook = (ListView) findViewById(R.id.lvBook);
        bookArrayList = new ArrayList<>();
        booksAdapter = new bookAdapter(bookArrayList, getApplicationContext());
        lvBook.setAdapter(booksAdapter);
        //Đổ dữ liệu từ data vào list
        getData(linkGetBook);
        //Chạm vào item để show thông tin sách
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            posi = position;
            showDetailDialog();
            }
        });
        //Nhấn giữ lâu để hiện dialog xoá
        lvBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    posi = position;
                    showAlertDialog();
                    bookId = Integer.parseInt(bookArrayList.get(position).bookId);
                } catch (Exception e) {}
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
        switch (item.getItemId()) {
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
    //Tạo dialog chi tiết sách
    private void showDetailDialog(){
        final Dialog dialOg = new Dialog(this);
        dialOg.setContentView(R.layout.dialog_detail_book);
        //Chuyển nền trong suốt
        dialOg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final int posit = ListBookActivity.posi;
        //Ánh xạ các thành phần trong dialog
        TextView tvBookNameDetail = (TextView) dialOg.findViewById(R.id.tvBookNameDetail);
        TextView tvAuthorDetail = (TextView) dialOg.findViewById(R.id.tvAuthorDetail);
        TextView tvBookIdDetail = (TextView) dialOg.findViewById(R.id.tvBookIdDetail);
        TextView tvpHDetail = (TextView) dialOg.findViewById(R.id.tvpHDetail);
        TextView tvKindDetail = (TextView) dialOg.findViewById(R.id.tvKindDetail);
        TextView tvPriceDetail = (TextView) dialOg.findViewById(R.id.tvPriceDetail);
        TextView tvNoteDetail = (TextView) dialOg.findViewById(R.id.tvNoteDetail);
        Button btnEditBok = (Button) dialOg.findViewById(R.id.btnEditBok);
        Button btnCancelEditBok = (Button) dialOg.findViewById(R.id.btnCancelEditBok);
        //Đẩy thông tin vào Dialog
        tvBookNameDetail.setText("Sách: "+ListBookActivity.bookArrayList.get(posit).getBookName());
        tvAuthorDetail.setText("Tác giả: "+ListBookActivity.bookArrayList.get(posit).getAuthor());
        tvBookIdDetail.setText("Mã số: "+ListBookActivity.bookArrayList.get(posit).getBookId());
        tvpHDetail.setText("Nhà xuất bản: "+ListBookActivity.bookArrayList.get(posit).getpH());
        tvKindDetail.setText("Thể loại: "+ListBookActivity.bookArrayList.get(posit).getKind());
        tvPriceDetail.setText("Giá bìa: "+ListBookActivity.bookArrayList.get(posit).getPrice()+"");
        tvNoteDetail.setText("Ghi chú: "+ListBookActivity.bookArrayList.get(posit).getNote());

        btnEditBok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(ListBookActivity.this, EditBookActivity.class);
                startActivity(intentEdit);
                dialOg.cancel();
            }
        });
        btnCancelEditBok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialOg.cancel();
            }
        });
        dialOg.show();
    }
    //Tạo dialog tìm kiếm
    private void showFindDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_find);
        //Chuyển nền thành trong suốt
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        edtFindBok = (EditText) dialog.findViewById(R.id.edtFind);
        Button btnFindBok = (Button) dialog.findViewById(R.id.btnFind);
        Button btnCancelFindBok = (Button) dialog.findViewById(R.id.btnCancelFind);
        ImageButton btnNextScanner = (ImageButton) dialog.findViewById(R.id.btnNextScaner);

        btnNextScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNextScaner = new Intent(ListBookActivity.this, ScannerActivity.class);
                startActivity(intentNextScaner);
            }
        });
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
        final Dialog dialoG = new Dialog(this);
        dialoG.setContentView(R.layout.dialog_alert);
        //Chuyển nền thành trong suốt
        dialoG.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final Button btnYes = (Button) dialoG.findViewById(R.id.btnYes);
        final Button btnNo = (Button) dialoG.findViewById(R.id.btnNo);
        TextView tvAlert = (TextView) dialoG.findViewById(R.id.tvAlert);
        tvAlert.setText("Sách bạn tìm có vẻ như không có trong kho, bạn có muốn thêm nó vào kho không?");
        btnYes.setText("Có");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListBookActivity.this, AddBookActivity.class);
                startActivity(intent);
                dialoG.cancel();
            }
        });
        btnNo.setText("Không, cảm ơn");
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoG.cancel();
            }
        });
        dialoG.show();
    }
    //Tạo dialog cảnh báo xoá
    public void showAlertDialog(){
        final Dialog diaLog = new Dialog(this);
        diaLog.setContentView(R.layout.dialog_alert);
        //Trong suốt nền
        diaLog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final Button btnDelete = (Button) diaLog.findViewById(R.id.btnYes);
        final Button btnCancelDel = (Button) diaLog.findViewById(R.id.btnNo);
        TextView tvDelete = (TextView) diaLog.findViewById(R.id.tvAlert);
        tvDelete.setText("Bạn có muốn xoá cuốn sách này ra khỏi kho sách không");
        btnDelete.setText("Xoá");
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunBok(linkDel(bookId));
                diaLog.cancel();
                getData(linkGetBook);
            }
        });
        btnCancelDel.setText("Không xoá");
        btnCancelDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog.cancel();
            }
        });
        diaLog.show();
    }
    //Link xoá sách
    public String linkDel(int bookId){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkDelBook = "http://10.0.136.36:3000/deleteBook?bookId="+bookId;
        return linkDelBook;
    }
    //Link tìm sách theo id
    public String linkFindById(int bookId){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkFindIdBook = "http://10.0.136.36:3000/findBookById?bookId="+bookId;
        return linkFindIdBook;
    }
    //Link tìm sách theo tên
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
                showAddDialog();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //Hàm thực thi lệnh xoá
    private void RunBok(String Link){
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
