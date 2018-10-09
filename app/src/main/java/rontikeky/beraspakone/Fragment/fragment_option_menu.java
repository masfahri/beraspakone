package rontikeky.beraspakone.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import rontikeky.beraspakone.R;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.main_container_home;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_option_menu extends Fragment {

    ListView lvMenu;
    TextView tvRiwayatPembelian;
    RadioGroup rgRiwayatPembelian, rgUbahPassword, rgPengaturanAlamat, rgPengaturanProfile;
    Button btnLogout;
    Context mCtx;
    public fragment_option_menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_option_menu, container, false);
        tvRiwayatPembelian = view.findViewById(R.id.tvRiwayatPembelian);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefHandler.setLogout();
                Toast.makeText(getActivity().getApplicationContext(), "Sukses Logout", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(getActivity().getApplicationContext(), main_container_home.class);
                startActivity(home);
            }
        });
        rgRiwayatPembelian = view.findViewById(R.id.rgRiwayatPembelian);
        rgRiwayatPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity().getApplicationContext(), tvRiwayatPembelian.getText(), Toast.LENGTH_SHORT).show();
                fragment_riwayat_pemesanan mFragmentRiwayat = new fragment_riwayat_pemesanan();
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame_option, mFragmentRiwayat, "Fragment_Riwayat")
                        .addToBackStack(null)
                        .commit();
            }
        });
        rgUbahPassword = view.findViewById(R.id.rgUbahPassword);
        rgUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getActivity().getApplicationContext(), tvRiwayatPembelian.getText(), Toast.LENGTH_SHORT).show();
                fragment_ubah_password mFragmentPassword = new fragment_ubah_password();
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame_option, mFragmentPassword)
                        .addToBackStack(null)
                        .commit();
            }
        });
        rgPengaturanAlamat = view.findViewById(R.id.rgPengaturanAlamat);
        rgPengaturanAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_pengaturan_alamat mFragmentAlamat = new fragment_pengaturan_alamat();
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame_option, mFragmentAlamat)
                        .addToBackStack(null)
                        .commit();
            }
        });
        rgPengaturanProfile = view.findViewById(R.id.rgPengaturanProfile);
        rgPengaturanProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_edit_profile mFragmentProfile = new fragment_edit_profile();
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame_option, mFragmentProfile)
                        .addToBackStack(null)
                        .commit();
            }
        });


//        lvMenu = view.findViewById(R.id.listMenuOption);
        final String[] menu = new String[] {"Riwayat Pemesanan", "Pengaturan Alamat", "Pengaturan Profile", "Ubah Password"};
        final Integer[] iconMenu = new Integer[] {R.drawable.mandiri_logo, R.drawable.bri_logo, R.drawable.bni_logo, R.drawable.bca_logo, };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menu);
//        lvMenu.setAdapter(adapter);
//        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(), menu[position], Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }

}
