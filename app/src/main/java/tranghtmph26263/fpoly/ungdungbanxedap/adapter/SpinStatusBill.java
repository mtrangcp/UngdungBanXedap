package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;

public class SpinStatusBill extends BaseAdapter {
    ArrayList<String> list = new ArrayList();

    public SpinStatusBill(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View itemView;
        if ( view == null){
            itemView = View.inflate(viewGroup.getContext(), R.layout.layout_item_spin_category, null);
        }else
            itemView = view;

        TextView tv_name = itemView.findViewById(R.id.tv_name);
        tv_name.setText(list.get(i));


        return itemView;
    }
}
