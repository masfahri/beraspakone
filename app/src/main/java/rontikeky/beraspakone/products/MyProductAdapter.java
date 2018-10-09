package rontikeky.beraspakone.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import rontikeky.beraspakone.R;

/**
 * Created by Acer on 1/22/2018.
 */

public class MyProductAdapter extends ArrayAdapter<Product> {

    List<Product> productList;
    Context context;
    private LayoutInflater mInflater;

    /*
    Constructor
     */

    public MyProductAdapter(Context context, List<Product> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        productList = objects;
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Product item = getItem(position);

        vh.txtProduct_Name.setText(item.getProductName());
        vh.txtProduct_description.setText(item.getProductDescription());
        vh.txtProduct_price.setText(item.getPrice());

        return  vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView txtProduct_Name;
        public final TextView txtProduct_description;
        public final TextView txtProduct_price;


        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView txtProduct_name, TextView txtProduct_description, TextView txtProduct_price) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.txtProduct_Name = txtProduct_name;
            this.txtProduct_description = txtProduct_description;
            this.txtProduct_price = txtProduct_price;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView txtProduct_Name = (TextView) rootView.findViewById(R.id.txtProduct_Name);
            TextView txtProduct_description = (TextView) rootView.findViewById(R.id.txtProduct_description);
            TextView txtProduct_price = (TextView) rootView.findViewById(R.id.txtProduct_price);

                return new ViewHolder(rootView, imageView, txtProduct_Name, txtProduct_description, txtProduct_price);
        }
    }
}
