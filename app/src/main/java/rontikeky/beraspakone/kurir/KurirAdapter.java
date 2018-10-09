package rontikeky.beraspakone.kurir;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rontikeky.beraspakone.R;

/**
 * Created by Acer on 2/24/2018.
 */

public class KurirAdapter extends RecyclerView.Adapter<KurirAdapter.KurirViewHolder> {

    List<responseKurir> mKurir;
    private Context mCtx;

    public KurirAdapter() {
        super();
        mKurir = new ArrayList<responseKurir>();
        responseKurir namaKurir = new responseKurir();

        namaKurir.setKurirNama("Kiosk Kurir");
        namaKurir.setmThumbnail(R.drawable.nonvacum);
        namaKurir.setBiayaEstimsi("10000");
        mKurir.add(namaKurir);

        namaKurir = new responseKurir();
        namaKurir.setKurirNama("Gojek Kurir");
        namaKurir.setmThumbnail(R.drawable.vacum);
        namaKurir.setBiayaEstimsi("10000");
        mKurir.add(namaKurir);
    }


    @Override
    public KurirViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_kurir, parent, false);
        KurirViewHolder viewHolder = new KurirViewHolder(v);
        return viewHolder;

    }

    @Override
    public int getItemCount() {
        return mKurir.size();
    }

    class KurirViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumb;
        public TextView nmKurir, estimasiHrg;

        public KurirViewHolder(View itemView) {
            super(itemView);

            imgThumb = (ImageView) itemView.findViewById(R.id.imgKurir);
            nmKurir = (TextView) itemView.findViewById(R.id.namaKurir);
            estimasiHrg = (TextView) itemView.findViewById(R.id.estimasiHarga);
        }
    }

    @Override
    public void onBindViewHolder(KurirViewHolder holder, int position) {
        responseKurir listKurir = mKurir.get(position);

        holder.imgThumb.setImageResource(listKurir.getmThumbnail());
        holder.nmKurir.setText(listKurir.getKurirNama());
        holder.estimasiHrg.setText(listKurir.getBiayaEstimsi());
    }


}
