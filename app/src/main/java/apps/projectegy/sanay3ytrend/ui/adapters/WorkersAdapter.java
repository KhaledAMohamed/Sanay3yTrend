package apps.projectegy.sanay3ytrend.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.workers.DataItem;
import apps.projectegy.sanay3ytrend.ui.activities.workerDetails.WorkerDetails;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.Constant;

public class WorkersAdapter extends RecyclerView.Adapter<WorkersAdapter.ViewHolder> {

    private final List<DataItem> list;
    private Context context;

    public WorkersAdapter(Context context, List<DataItem> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public WorkersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_item, parent, false);
        return new WorkersAdapter.ViewHolder(view);
    }

    private void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int listPosition) {

        if (list.get(listPosition).getImageUrl() != null && !list.get(listPosition).getImageUrl().equals(""))
            Picasso.get().load(Constant.BASE_URL_HTTP + list.get(listPosition).getImageUrl()).into(holder.img);

        holder.name.setText(list.get(listPosition).getUserName());
        holder.job.setText(list.get(listPosition).getDepartmentName());
        holder.address.setText(list.get(listPosition).getAddress());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, WorkerDetails.class);
            intent.putExtra(Constant.WorkerId, String.valueOf(list.get(listPosition).getWorkerId()));
            context.startActivity(intent);
        });
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayoutCompat linCardInfo;
        private final AppCompatImageView img;
        private final BoldTextView name;
        private final BoldTextView job;
        private final BoldTextView address;


        ViewHolder(View itemView) {
            super(itemView);
            linCardInfo = itemView.findViewById(R.id.lin_card_info);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            job = itemView.findViewById(R.id.job);

            address = (BoldTextView) itemView.findViewById(R.id.address);


        }


    }

}







