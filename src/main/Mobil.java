package main;

/**
 *
 * @author Aloysius
 */
public class Mobil {
    public String jenis;
    public String tipe;
    public int jumlah;
    public int harga;
        
    public Mobil(String jenis, String tipe, int jumlah, int harga){
        this.jenis = jenis;
        this.tipe = tipe;
        this.jumlah = jumlah;
        this.harga = harga;
    }
        
    public Mobil(String jenis, String tipe, int jumlah){
        this.jenis = jenis;
        this.tipe = tipe;
        this.jumlah = jumlah;
    }
        
    public Mobil(){
    }
}
