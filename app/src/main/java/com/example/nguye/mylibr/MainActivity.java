package com.example.nguye.mylibr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nguye.mylibr.Book.AddBookActivity;
import com.example.nguye.mylibr.Book.ListBookActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void nextBook(View v){
        Intent intentBook = new Intent(MainActivity.this, ListBookActivity.class);
        startActivity(intentBook);
    }
    public void nextBorrower(View v){
    }
    public void nextBorrow(View v){
    }
}
