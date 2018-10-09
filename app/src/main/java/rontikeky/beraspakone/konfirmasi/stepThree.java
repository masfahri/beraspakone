package rontikeky.beraspakone.konfirmasi;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import rontikeky.beraspakone.R;
import rontikeky.beraspakone.kurir.KurirAdapter;
import rontikeky.beraspakone.pembayaran.AtmAdapter;
import rontikeky.beraspakone.pembayaran.responseAtm;

/**
 * A simple {@link Fragment} subclass.
 */
public class stepThree extends Fragment {

    private RecyclerView rvAtm;
    private RecyclerView.LayoutManager mAtmLayoutManager;
    private RecyclerView.Adapter mAtmAdapter;

    protected Context mCtx;
    List<responseAtm> mAtm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_three, container, false);

        rvAtm = view.findViewById(R.id.rvAtm);
        rvAtm.setHasFixedSize(true);

        mAtmLayoutManager = new GridLayoutManager(mCtx, 2);
        rvAtm.setLayoutManager(mAtmLayoutManager);

        mAtmAdapter = new AtmAdapter();
        Log.d("Adapter", String.valueOf(mAtmAdapter));
        rvAtm.setAdapter(mAtmAdapter);
        rvAtm.setVisibility(View.VISIBLE);


        return view;
    }

    @Override
    public void onAttach(Context mCtx) {
        super.onAttach(mCtx);
        this.mCtx = mCtx;
    }




}
