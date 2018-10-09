package rontikeky.beraspakone.pembayaran;

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
import rontikeky.beraspakone.pembayaran.responseAtm;

/**
 * Created by Acer on 2/24/2018.
 */

public class AtmAdapter extends RecyclerView.Adapter<AtmAdapter.AtmViewHolder> {

    List<responseAtm> mAtm;
    private Context mCtx;

    public AtmAdapter() {
        super();
        mAtm = new ArrayList<responseAtm>();
        responseAtm namaAtm = new responseAtm();

        namaAtm.setNamaAtm("BNI");
        namaAtm.setImgAtm(R.drawable.bni_logo);
        namaAtm.setNoRekening("123456789");
        mAtm.add(namaAtm);

        namaAtm = new responseAtm();
        namaAtm.setNamaAtm("Mandiri");
        namaAtm.setImgAtm(R.drawable.mandiri_logo);
        namaAtm.setNoRekening("654321");
        mAtm.add(namaAtm);

        namaAtm = new responseAtm();
        namaAtm.setNamaAtm("BCA");
        namaAtm.setImgAtm(R.drawable.bca_logo);
        namaAtm.setNoRekening("12345");
        mAtm.add(namaAtm);

        namaAtm = new responseAtm();
        namaAtm.setNamaAtm("BRI");
        namaAtm.setImgAtm(R.drawable.bri_logo);
        namaAtm.setNoRekening("987654321");
        mAtm.add(namaAtm);

    }


    @Override
    public AtmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_atm, parent, false);
        AtmViewHolder viewHolder = new AtmViewHolder(v);
        return viewHolder;

    }

    @Override
    public int getItemCount() {
        return mAtm.size();
    }

    class AtmViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgAtm;
        public TextView nmAtm, noRek;

        public AtmViewHolder (View itemView) {
            super(itemView);

            imgAtm = (ImageView) itemView.findViewById(R.id.imgAtm);
            nmAtm  = (TextView) itemView.findViewById(R.id.namaAtm);
            noRek  = (TextView) itemView.findViewById(R.id.noRekening);
        }
    }

    @Override
    public void onBindViewHolder(AtmViewHolder holder, int position) {
        responseAtm listAtm = mAtm.get(position);

        holder.imgAtm.setImageResource(R.drawable.logo);
        holder.nmAtm.setText(listAtm.getNamaAtm());
        holder.noRek.setText(listAtm.getNoRekening());
    }


}
