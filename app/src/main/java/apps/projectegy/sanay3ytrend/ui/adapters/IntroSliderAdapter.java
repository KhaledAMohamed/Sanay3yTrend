package apps.projectegy.sanay3ytrend.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.List;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.RegularTextView;


public class IntroSliderAdapter extends PagerAdapter {

    private List<Drawable> imageList;
    private List<String > listTitles;
    private List<String > listDesc;
    private Context context;
    private AppCompatImageView img;
    private BoldTextView welcomeTitleTv;
    private RegularTextView welcomeDescTv;

    public IntroSliderAdapter(Context context, List<Drawable> imageList, List<String> listTitles, List<String> listDesc) {
        this.context = context;
        this.imageList = imageList;
        this.listTitles = listTitles;
        this.listDesc = listDesc;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider_welcome, null);

        initViews(view);

        Glide.with(context)
                .load(imageList.get(position))
                .into(img);

        welcomeTitleTv.setText(listTitles.get(position));
        welcomeDescTv.setText(listDesc.get(position));

            container.addView(view, 0);

        return view;
    }

    private void initViews(View view) {
        img = view.findViewById(R.id.img);
        welcomeTitleTv = (BoldTextView) view.findViewById(R.id.welcome_title_tv);
        welcomeDescTv = (RegularTextView) view.findViewById(R.id.welcome_desc_tv);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}

