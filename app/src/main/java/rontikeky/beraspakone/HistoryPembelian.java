package rontikeky.beraspakone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class HistoryPembelian extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


    }

    public void history(View view) {
        TextView dateHistory    = (TextView) findViewById(R.id.txtDate);
        TableLayout History     = (TableLayout) findViewById(R.id.tblHistory);
        Button btnDetil         = (Button) findViewById(R.id.btnDetailHistory);
        Spinner comboBulan      = (Spinner) findViewById(R.id.cmbBulan);
        Spinner comboTahun      = (Spinner) findViewById(R.id.cmbTahun);

        if (History.getVisibility() == View.VISIBLE) {
            comboBulan.setEnabled(false);
            comboTahun.setEnabled(false);
            dateHistory.setVisibility(view.INVISIBLE);
            History.setVisibility(view.INVISIBLE);
            btnDetil.setVisibility(view.INVISIBLE);
        }else{
            comboBulan.setEnabled(true);
            comboTahun.setEnabled(true);
            dateHistory.setVisibility(view.VISIBLE);
            History.setVisibility(view.VISIBLE);
            btnDetil.setVisibility(view.VISIBLE);
        }
    }

    public void DetailHistory (View view) {
        TableLayout DetHistory  = (TableLayout) findViewById(R.id.tblDetHistory);
        DetHistory.setVisibility(view.VISIBLE);

    }
}
