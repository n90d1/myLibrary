package com.example.nguye.mylibr;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nguye.mylibr.Book.AddBookActivity;
import com.example.nguye.mylibr.Book.ListBookActivity;
import com.example.nguye.mylibr.Borrower.ListBorrowerActivity;

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
        Intent intentBorrower = new Intent(MainActivity.this, ListBorrowerActivity.class);
        startActivity(intentBorrower);
    }
    public void nextBorrow(View v){
        Intent intentScan = new Intent(MainActivity.this, ScannerActivity.class);
        startActivity(intentScan);
    }
}
