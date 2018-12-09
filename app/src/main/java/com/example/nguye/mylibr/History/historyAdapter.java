package com.example.nguye.mylibr.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nguye.mylibr.R;

import java.util.ArrayList;

public class historyAdapter extends BaseAdapter {
    ArrayList<history> historyArrayList;
    Context context;

    public historyAdapter(ArrayList<history> historyArrayList, Context context){
        this.historyArrayList = historyArrayList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return historyArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView tvidHistory, tvBorrowerId, tvBookId, tvDateBorrow;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lvhistory, null);
            viewHolder.tvidHistory = (TextView) convertView.findViewById(R.id.tvIdHistory);
            viewHolder.tvBorrowerId = (TextView) convertView.findViewById(R.id.tvBorrowerIdHist);
            viewHolder.tvBookId = (TextView) convertView.findViewById(R.id.tvBookIdHist);
            viewHolder.tvDateBorrow = (TextView) convertView.findViewById(R.id.tvDateBorrow);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        history historys = (history) getItem(position);
        viewHolder.tvidHistory.setText(""+historys.idHistory);
        viewHolder.tvBorrowerId.setText("Mã người mượn: "+historys.borrowerId);
        viewHolder.tvBookId.setText("Mã sách mượn: "+historys.bookId);
        viewHolder.tvDateBorrow.setText("Ngày mượn: "+historys.dateBorrow);
        return convertView;
    }
}
