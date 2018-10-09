package rontikeky.beraspakone.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import rontikeky.beraspakone.R;

/**
 * Created by Acer on 3/18/2018.
 */

public class BannerAdapter extends PagerAdapter {

    private Context mCtx;
    private LayoutInflater layoutInflater;
    private Integer[] banner = {R.drawable.vacum, R.drawable.nonvacum};
    private ImageView imgBanner;
    private ViewPager vpBanner;

    public BannerAdapter(Context context) {
        this.mCtx = context;
    }

    @Override
    public int getCount() {
        return banner.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mCtx.getSystemService(mCtx.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_banner, null);
        imgBanner = view.findViewById(R.id.img_Banner);
        imgBanner.setImageResource(banner[position]);

        vpBanner = (ViewPager) container;
        vpBanner.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vpBanner = (ViewPager) container;
        View view = (View) object;
        vpBanner.removeView(view);
        super.destroyItem(container, position, object);
    }
}
