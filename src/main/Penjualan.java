package main;

/**
 *
 * @author Aloysius
 */
public class Penjualan {
    public Pelanggan pelanggan;
    public Mobil[] arrMobil;
    public int jumlahPembelian;

    public Penjualan(){
        this.pelanggan = new Pelanggan();
        this.arrMobil = new Mobil[10];
    }
}
