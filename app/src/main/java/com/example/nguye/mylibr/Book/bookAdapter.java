package com.example.nguye.mylibr.Book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nguye.mylibr.R;

import java.util.ArrayList;

public class bookAdapter extends BaseAdapter {
    ArrayList<book> bookArrayList;
    Context context;

    public bookAdapter(ArrayList<book> bookArrayList, Context context){
        this.bookArrayList = bookArrayList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return bookArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvBookId, tvBookName, tvKind, tvAuthor;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lvbook, null);
            viewHolder.tvBookId = (TextView) convertView.findViewById(R.id.tvBookId);
            viewHolder.tvBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            viewHolder.tvKind = (TextView) convertView.findViewById(R.id.tvKind);
            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        book books = (book) getItem(position);
        viewHolder.tvBookId.setText(""+books.bookId);
        viewHolder.tvBookName.setText(""+books.bookName);
        viewHolder.tvKind.setText("Thể loại: "+books.kind);
        viewHolder.tvAuthor.setText("Tác giả: "+ books.author);
        return convertView;
    }
}
