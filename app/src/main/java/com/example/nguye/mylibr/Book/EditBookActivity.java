package com.example.nguye.mylibr.Book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class EditBookActivity extends AppCompatActivity {
    EditText  edtBookNameEdit, edtKindEdit, edtpHEdit, edtAuthorEdit, edtPriceEdit, edtNoteEdit;
    TextView tvBookIdEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        linkNet();
        setText();
    }
    //Thêm menu vào edit book
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save,menu);
        return true;
    }
    //Set chức năng cho item trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                String bookIdEd = tvBookIdEdit.getText().toString();
                String bookNameEd = edtBookNameEdit.getText().toString();
                String kindEd = edtKindEdit.getText().toString();
                String pHEd = edtpHEdit.getText().toString();
                String authorEd = edtAuthorEdit.getText().toString();
                int priceEd = Integer.parseInt(edtPriceEdit.getText().toString());
                String noteEd = edtNoteEdit.getText().toString();
                //Chèn các biến vào link
                Update(link(bookIdEd,bookNameEd,kindEd,pHEd,authorEd,priceEd,noteEd));
                Intent intentBa = new Intent(EditBookActivity.this, ListBookActivity.class);
                startActivity(intentBa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Link Edit Book
    public String link (String bookIdEd, String bookNameEd, String kindEd, String pHEd, String authorEd, int priceEd, String noteEd){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkEditBook = "http://192.168.43.168:3000/updateBook?bookName="+bookNameEd+"&kind="+kindEd+"&pH="+pHEd+"&author="+authorEd+"&price="+priceEd+"&picPre=&note="+noteEd+"&bookId="+bookIdEd;
        linkEditBook = linkEditBook.replace(" ", "%20");
        return linkEditBook;
    }
    private void Update(String link){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String check = "\"affectedRows\":1";
                if (response.toString().indexOf(check)!=-1) {
                    Toast.makeText(getApplicationContext(),"Đã sửa thông tin sách!", Toast.LENGTH_LONG).show();
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
    //Ánh Xạ
    public void linkNet(){
        tvBookIdEdit = (TextView)findViewById(R.id.tvBookIdEdit);
        edtBookNameEdit = (EditText)findViewById(R.id.edtBookNameEdit);
        edtKindEdit = (EditText)findViewById(R.id.edtKindEdit);
        edtpHEdit = (EditText)findViewById(R.id.edtpHEdit);
        edtAuthorEdit = (EditText)findViewById(R.id.edtAuthorEdit);
        edtPriceEdit = (EditText)findViewById(R.id.edtPriceEdit);
        edtNoteEdit = (EditText)findViewById(R.id.edtNoteEdit);
    }
    //Đẩy thông tin lên EditText
    public void setText(){
        final int posi = ListBookActivity.posi;
        tvBookIdEdit.setText(ListBookActivity.bookArrayList.get(posi).getBookId());
        edtBookNameEdit.setText(ListBookActivity.bookArrayList.get(posi).getBookName());
        edtKindEdit.setText(ListBookActivity.bookArrayList.get(posi).getKind());
        edtpHEdit.setText(ListBookActivity.bookArrayList.get(posi).getpH());
        edtAuthorEdit.setText(ListBookActivity.bookArrayList.get(posi).getAuthor());
        edtPriceEdit.setText(ListBookActivity.bookArrayList.get(posi).getPrice()+"");
        edtNoteEdit.setText(ListBookActivity.bookArrayList.get(posi).getNote());
    }
}
