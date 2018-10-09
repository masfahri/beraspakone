package rontikeky.beraspakone.kurir;

/**
 * Created by Acer on 2/23/2018.
 */

public class responseKurir {
    private String kurirNama;
    private String biayaEstimsi;
    private int mThumbnail;

    public String getKurirNama() {
        return kurirNama;
    }

    public void setKurirNama(String namaKurir) {
        this.kurirNama = namaKurir;
    }

    public String getBiayaEstimsi() {
        return biayaEstimsi;
    }

    public void setBiayaEstimsi(String biayaEstimsi) {
        this.biayaEstimsi = biayaEstimsi;
    }

    public int getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(int mThumbnail) {
        this.mThumbnail = mThumbnail;
    }
}
