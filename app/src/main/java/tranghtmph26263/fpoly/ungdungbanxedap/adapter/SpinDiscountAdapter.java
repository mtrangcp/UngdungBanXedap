package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;

public class SpinDiscountAdapter extends BaseAdapter {
    ArrayList<Discount> list;
    public SpinDiscountAdapter ( ArrayList<Discount> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        Discount obj = list.get(i);
        return obj;
    }

    @Override
    public long getItemId(int i) {
        Discount obj = list.get(i);
        return obj.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        if ( view == null){
            itemView = View.inflate(viewGroup.getContext(), R.layout.layout_item_spinner_discount_user, null);
        }else
            itemView = view;

        final Discount obj = list.get(i);
        final int index = i;

        TextView tv_moTa = itemView.findViewById(R.id.id_detail_discount_spinner);
        tv_moTa.setText(""+obj.getDetail());

        return itemView;
    }
}
