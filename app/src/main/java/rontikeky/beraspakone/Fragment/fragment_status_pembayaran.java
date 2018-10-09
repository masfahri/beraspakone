package rontikeky.beraspakone.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import rontikeky.beraspakone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_status_pembayaran extends Fragment {

    protected Context mCtx;
    protected Button btnDetail;

    public fragment_status_pembayaran() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_status_pembayaran, container, false);
        btnDetail = view.findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status(view);
            }
        });
        return view;
    }

    public void status(View view) {
        CardView tblStatus= (CardView) view.findViewById(R.id.cardViewHistoryAll);
        if (tblStatus.getVisibility() == View.VISIBLE) {
            tblStatus.setVisibility(view.INVISIBLE);
        }else{
            tblStatus.setVisibility(view.VISIBLE);
        }
    }

}
