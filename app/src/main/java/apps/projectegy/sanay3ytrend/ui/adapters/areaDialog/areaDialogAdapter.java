package apps.projectegy.sanay3ytrend.ui.adapters.areaDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import apps.projectegy.sanay3ytrend.R;


public class areaDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener
        , View.OnLongClickListener {

    private final Context mContext;
    private ArrayList<areaDialogItem> dataSet;
    private View.OnClickListener itemListener;
    private RecyclerViewButtonClickListener btnListener;

    public areaDialogAdapter(ArrayList<areaDialogItem> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_dialog_row, parent, false);
        return new TextTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        if (itemListener != null) {
            holder.itemView.setOnClickListener(itemListener);
        }
        areaDialogItem object = dataSet.get(listPosition);

        if (object != null) {
            ((TextTypeViewHolder) holder).name.setText(String.valueOf(object.CityName));
            if (dataSet.get(listPosition).flag != null)
                setImg(object.flag, ((TextTypeViewHolder) holder).countryLogo);
//            String id = dataSet.get(listPosition).flag;
        }
    }

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.itemListener = clickListener;
    }

    public void setBtnLocationListener(RecyclerViewButtonClickListener listener) {
        this.btnListener = listener;
    }

    public void update(ArrayList<areaDialogItem> list) {
        this.dataSet = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setImg(String url, ImageView image) {

        try {
            Glide.with(mContext)
                    .load(url)
                    .into(image);
        } catch (Exception ignored) {

        }

    }

    static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView countryLogo;

        TextTypeViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.countryLogo = itemView.findViewById(R.id.countryLogo);


        }

    }


}
