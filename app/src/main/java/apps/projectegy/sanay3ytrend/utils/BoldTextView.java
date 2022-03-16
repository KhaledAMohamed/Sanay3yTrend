package apps.projectegy.sanay3ytrend.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public final class BoldTextView extends AppCompatTextView {


    public BoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoldTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf;
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Tajawal-Medium.otf");
        setTypeface(tf);
    }

}
