package apps.projectegy.sanay3ytrend.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public final class RegularEditText extends AppCompatEditText {


    public RegularEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegularEditText(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf;
        if (Constant.getLng(MyApp.getAppContext()).equals("ar"))
        {
            tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Tajawal-Light.otf");
        }
        else
        {
            tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Tajawal-Light.otf");

        }
        setTypeface(tf);
    }

}
