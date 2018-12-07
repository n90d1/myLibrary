package com.example.nguye.mylibr.Borrower;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nguye.mylibr.R;

import java.util.ArrayList;

public class borrowerAdapter extends BaseAdapter {
    ArrayList<borrower> borrowerArrayList;
    Context context;

    public borrowerAdapter(ArrayList<borrower> borrowerArrayList, Context context){
        this.borrowerArrayList = borrowerArrayList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return borrowerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return borrowerArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView tvBorrowerId, tvNameBorrower, tvPhoneBo, tvAddressBo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lvborrower, null);
            viewHolder.tvBorrowerId = (TextView) convertView.findViewById(R.id.tvBorrowerId);
            viewHolder.tvNameBorrower = (TextView) convertView.findViewById(R.id.tvNameBorrower);
            viewHolder.tvPhoneBo = (TextView) convertView.findViewById(R.id.tvPhoneBo);
            viewHolder.tvAddressBo = (TextView) convertView.findViewById(R.id.tvAdressBo);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        borrower borrowers = (borrower) getItem(position);
        viewHolder.tvBorrowerId.setText(""+borrowers.borrowerId);
        viewHolder.tvNameBorrower.setText(""+borrowers.nameBorrower);
        viewHolder.tvPhoneBo.setText("Số điện thoại: "+borrowers.phoneBo);
        viewHolder.tvAddressBo.setText("Địa chỉ: "+borrowers.addressBo);
        return convertView;
    }
}
