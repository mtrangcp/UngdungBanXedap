package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;

public class SpinCategoryAdapter extends BaseAdapter {
    ArrayList<Category> list;
    public SpinCategoryAdapter ( ArrayList<Category> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        Category obj = list.get(i);
        return obj;
    }

    @Override
    public long getItemId(int i) {
        Category obj = list.get(i);
        return obj.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        if ( view == null){
            itemView = View.inflate(viewGroup.getContext(), R.layout.layout_item_spin_category, null);
        }else
            itemView = view;

        final Category obj = list.get(i);
        final int index = i;

        TextView tv_id = itemView.findViewById(R.id.tv_id);
        TextView tv_name = itemView.findViewById(R.id.tv_name);
        tv_id.setText(obj.getId()+"");
        tv_name.setText(obj.getName());

        return itemView;
    }
}
