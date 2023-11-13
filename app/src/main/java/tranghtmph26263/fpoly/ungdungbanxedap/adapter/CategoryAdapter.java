package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter  extends  RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private Context context;
    private ArrayList<Category> arrayList;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Category> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_category, parent, false );
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category obj = arrayList.get(position);
        int index = position;

        if (obj == null) {
            return;
        }
        holder.tvName.setText(obj.getName());
        holder.tvid.setText(obj.getId()+ "");

    }

    @Override
    public int getItemCount() {
        if ( arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvid;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.id_name_category);
            tvid = itemView.findViewById(R.id.id_category);

        }
    }
}
