package apps.projectegy.sanay3ytrend.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.generalAds.DataItem;
import apps.projectegy.sanay3ytrend.ui.activities.adsWebView.AdsWebView;
import apps.projectegy.sanay3ytrend.utils.Constant;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder> {

    private final List<DataItem> list;
    private Context context;

    public AdsAdapter(Context context, List<DataItem> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public AdsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_item, parent, false);
        return new AdsAdapter.ViewHolder(view);
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


        holder.itemView.setOnClickListener(v ->
        {

            if (list.get(listPosition).getLink().contains("facebook.com")) {
                try {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(context, list.get(listPosition).getLink());
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    context.startActivity(facebookIntent);
//                    context.startActivity(newFacebookIntent(context.getPackageManager(),list.get(listPosition).getLink()));
                } catch (Exception e) {
                    Intent intent = new Intent(context, AdsWebView.class);
                    intent.putExtra("url", list.get(listPosition).getLink());
                    context.startActivity(intent);
                }
            } else {
                Intent intent = new Intent(context, AdsWebView.class);
                intent.putExtra("url", list.get(listPosition).getLink());
                context.startActivity(intent);
            }

        });
    }

    public String getFacebookPageURL(Context context, String FACEBOOK_URL) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_URL.replace("https://www.facebook.com/", "");
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}