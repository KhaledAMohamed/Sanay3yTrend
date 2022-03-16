package apps.projectegy.sanay3ytrend.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.departments.DataItem;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.Constant;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<DataItem> list;
    private final OnClickHandler onClickHandler;
    private Context context;

    public CategoryAdapter(Context context, List<DataItem> list, OnClickHandler onClickHandler) {
        this.context = context;
        this.list = list;
        this.onClickHandler = onClickHandler;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    private void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int listPosition) {

        if (list.get(listPosition).getImage() != null && !list.get(listPosition).getImage().equals(""))
            Picasso.get().load(Constant.BASE_URL_HTTP + list.get(listPosition).getImage()).into(holder.img);

        holder.name.setText(list.get(listPosition).getName());

        holder.itemView.setOnClickListener(view -> {
            onClickHandler.onItemClicked(String.valueOf(list.get(listPosition).getId()));
        });
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface OnClickHandler {
        void onItemClicked(String id);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView img;
        private final BoldTextView name;


        ViewHolder(View itemView) {
            super(itemView);
            img = (AppCompatImageView) itemView.findViewById(R.id.img);
            name = (BoldTextView) itemView.findViewById(R.id.name);


        }


    }

}







