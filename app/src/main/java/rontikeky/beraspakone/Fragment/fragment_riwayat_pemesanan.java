package rontikeky.beraspakone.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import rontikeky.beraspakone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_riwayat_pemesanan extends Fragment {

    Button btnCari, btnDetail;
    Spinner cmbTahun, cmbBulan;
    TextView dateHistory;
    TableLayout tblHistory;
    public fragment_riwayat_pemesanan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_riwayat_pemesanan, container, false);

        dateHistory = view.findViewById(R.id.txtDate);

        btnCari = view.findViewById(R.id.sbmtHistory);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history(view);
            }
        });
        btnDetail = view.findViewById(R.id.btnDetailHistory);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailHistory(view);
            }
        });


        return view;
    }


    public void history(View view) {


    }

    public void DetailHistory (View view) {


    }

}
