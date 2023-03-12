package main;

import javax.swing.JOptionPane;

/**
 *
 * @author Aloysius
 */
public class main {
    static Penjualan[] jual = new Penjualan[100];
    static int indexPenjualanMobil = 0; //untuk memudahkan pemberian index saat input data Penjualan baru
    
    static Mobil[] gudang = new Mobil[10];
    static int indexMobilGudang = 0;    //untuk memudahkan pemberian index saat input data Mobil baru pada gudang
    
    public static void main(String[] args) {
        boolean exit = false;
        int menu;
        
        //tahap persiapan karena panjang array hardcode
        for (int i = 0; i < 100; i++) {
            jual[i] = new Penjualan();
        }

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                jual[i].arrMobil[j] = new Mobil(); 
            }
        }

        for (int i = 0; i < 10; i++) {
            gudang[i] = new Mobil();
        }
        
        while (!exit) {
            menu = Integer.parseInt(JOptionPane.showInputDialog(null, "Menu\n1. Pencatatan Mobil Tersedia\n"
                                                                            + "2. Penjualan\n"
                                                                            + "3. Hitung Keuntungan dealer\n"
                                                                            + "4. Print cek\n"
                                                                            + "5. Exit"));

            switch (menu) {
                case 1:
                    PencatatanMobil();
                    break;
                case 2:
                    Penjualan();
                    break;
                case 3:
                    MenuHitungKeuntungan();
                    break;
                case 4:
                    printMobilPadaGudang();
                    break;
                default:
                    exit = true;
            }
        }
    }
    
    public static void PencatatanMobil(){
        JOptionPane.showMessageDialog(null, "Input data mobil");
        String jenis = JOptionPane.showInputDialog(null, "Input Jenis: ");
        String tipe = JOptionPane.showInputDialog(null, "Input Tipe Mobil: ");
        int jumlah = Integer.parseInt(JOptionPane.showInputDialog(null, "Input jumlah: "));
        int harga = Integer.parseInt(JOptionPane.showInputDialog(null, "Input harga: "));

        int indexMobil = cekMobil(jenis, tipe);

        if(indexMobil != -1){
            gudang[indexMobil].jumlah += jumlah; 
        }else{
            gudang[indexMobilGudang] = new Mobil(jenis, tipe, jumlah, harga);
            indexMobilGudang++;  
        }
    }

    public static int cekMobil(String jenis, String tipe){
        int indexMobil = -1;

        for (int i = 0; i < indexMobilGudang; i++) {
            if(jenis.equals(gudang[i].jenis) && tipe.equals(gudang[i].tipe)){
                indexMobil = i;
            }
        }
        return indexMobil;
    }
    
    public static void printMobilPadaGudang(){
        for (int i = 0; i < indexMobilGudang; i++) {
            JOptionPane.showMessageDialog(null, "Mobil " + (i+1) + "\nJenis: " + gudang[i].jenis + "\nTipe: " + gudang[i].tipe + "\nJumlah: " + gudang[i].jumlah + "\nHarga: " + gudang[i].harga);
        }
        
        if(indexMobilGudang == 0)
            JOptionPane.showMessageDialog(null,"Kosong");
    }
    
    public static void Penjualan(){
        printMobilPadaGudang();
        if(indexMobilGudang == 0)
            return;
        
        double total = 0;
        int indexMobil;
        int beliLagi;
        int indexArrMobil = 0;  //untuk memudahkan pemberian index saat input data Penjualan mobil baru
        
        String nama = JOptionPane.showInputDialog(null, "Input nama pembeli");
        int usia = Integer.parseInt(JOptionPane.showInputDialog(null, "Input usia pembeli"));
        String alamat = JOptionPane.showInputDialog(null, "Input alamat pembeli");
        
        String jenisPilihan;
        String tipePilihan;
        int jumlahPilhan;

        do{
            jenisPilihan = JOptionPane.showInputDialog(null, "Input jenis mobil yang ingin dibeli");
            tipePilihan = JOptionPane.showInputDialog(null, "Input tipe mobil yang ingin dibeli");
            jumlahPilhan = Integer.parseInt(JOptionPane.showInputDialog(null, "Input jumlah mobil yang ingin dibeli"));

            indexMobil = cekMobil(jenisPilihan, tipePilihan);
            if(indexMobil == -1){
                JOptionPane.showMessageDialog(null,"Mobil tidak tersedia");
            }else if(gudang[indexMobil].jumlah < jumlahPilhan){
                JOptionPane.showMessageDialog(null,"Jumlah mobil pada gudang kurang. (" + gudang[indexMobil].jumlah + ")");
            }else{
                jual[indexPenjualanMobil].pelanggan = new Pelanggan(nama, usia, alamat);
                jual[indexPenjualanMobil].arrMobil = new Mobil[100];
                jual[indexPenjualanMobil].arrMobil[indexArrMobil] = new Mobil(jenisPilihan, tipePilihan, jumlahPilhan);
                jual[indexPenjualanMobil].arrMobil[indexArrMobil].harga = gudang[indexArrMobil].harga;
                gudang[indexMobil].jumlah -= jumlahPilhan;

                if(gudang[indexMobil].jumlah == 0){
                    indexMobilGudang--;
                }
                
                total = HitungPenjualan(jual[indexPenjualanMobil].arrMobil[indexArrMobil]) + HitungPajak(jual[indexPenjualanMobil].arrMobil[indexArrMobil]);
                JOptionPane.showMessageDialog(null, "Total = " + total);
                indexArrMobil++;
            }

            beliLagi = Integer.parseInt(JOptionPane.showInputDialog(null, "Input angka 1 jika ingin membeli lagi"));

        }while(beliLagi == 1);

        if(indexArrMobil > 0){  //artinya sudah ada pembelian
            jual[indexPenjualanMobil].jumlahPembelian = indexArrMobil;
            indexPenjualanMobil++;
        }
    }
    
    public static void MenuHitungKeuntungan(){
        double pajak = HitungTotalPajakDealer();
        int pendapatanAkhir = HitungKeuntunganAkhirDealer();
        
        JOptionPane.showMessageDialog(null, "Total pendapatan awal : " + (pendapatanAkhir + pajak) + "\n"
                                            + "Total pajak : " + pajak + "\n"
                                            + "Pendapatan Bersih : " + pendapatanAkhir);

    }
    
    public static int HitungPenjualan(Mobil mobil){
        return mobil.jumlah * mobil.harga;
    }
    
    public static double HitungPajak(Mobil mobil){
        int hargaAwal = mobil.jumlah * mobil.harga;
        if(mobil.harga > 300)
            return hargaAwal * 0.14 + hargaAwal * 0.25 + hargaAwal * 0.15;
        else if(mobil.harga > 200)
            return hargaAwal * 0.12 + hargaAwal * 0.20 + hargaAwal * 0.10;
        else if(mobil.harga >= 100)
            return hargaAwal * 0.10 + hargaAwal * 0.15 + hargaAwal * 0.05;
        else
            return hargaAwal;
    }
    
    public static int HitungKeuntunganAkhirDealer(){
        int total = 0;
        for (int i = 0; i < indexPenjualanMobil; i++) {             //Panjang array sudah hardcode, pakai variable pembantu untuk menghindari null
            for (int j = 0; j < jual[i].jumlahPembelian; j++) {
                total += HitungPenjualan(jual[i].arrMobil[j]);
            }
        }
        return total;
    }
    
    
    public static double HitungTotalPajakDealer(){
        double total = 0;
        for (int i = 0; i < indexPenjualanMobil; i++) {             //Panjang array sudah hardcode, pakai variable pembantu untuk menghindari null
            for (int j = 0; j < jual[i].jumlahPembelian; j++) {
                total += HitungPajak(jual[i].arrMobil[j]);
            }
        }
        return total;
    }
}
