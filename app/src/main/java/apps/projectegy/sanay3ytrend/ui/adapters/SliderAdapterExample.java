
package apps.projectegy.sanay3ytrend.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.ui.activities.workerDetails.WorkerDetails;
import apps.projectegy.sanay3ytrend.utils.Constant;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private final Context context;
    private final List<String> list = new ArrayList<>();
    private List<SliderItem> mSliderItems = new ArrayList<>();
    private List<String> ids = new ArrayList<>();

    public SliderAdapterExample(Context context, List<String> images, List<String> ids) {
        this.context = context;
        this.ids = ids;
        for (int i = 0; i < images.size(); i++) {
            mSliderItems.add(new SliderItem("", Constant.BASE_URL_HTTP + images.get(i)));
            this.list.add(Constant.BASE_URL_HTTP + images.get(i));
//            this.imagesLink.add(imagesLink.get(i));
        }

    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImageUrl())
                .into(viewHolder.imageViewBackground);

        /*viewHolder.itemView.setOnClickListener(view -> {

            new StfalconImageViewer.Builder<>(context, list, (imageView, imageUrl) -> Glide.with(context).load(imageUrl).into(imageView)).show();
        });*/

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WorkerDetails.class);
            intent.putExtra(Constant.WorkerId, String.valueOf(ids.get(position)));
            context.startActivity(intent);
        });


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        AppCompatImageView imageViewBackground;
        AppCompatImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}