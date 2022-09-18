package domein.ovChip;

import java.util.Date;

public class OvChipkaart {
    private int kaartNummer;
    private Date geligTot;
    private int klasse;
    private float saldo;
    private  int reizigerId;


    public OvChipkaart( int kaartNummer, Date geligTot, int klasse, float saldo, int reizigerId){
        this.kaartNummer= kaartNummer;
        this.geligTot= geligTot;
        this.klasse = klasse;
        this.saldo= saldo;
        this.reizigerId = reizigerId;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public Date getGeligTot() {
        return geligTot;
    }

    public float getSaldo() {
        return saldo;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setGeligTot(Date geligTot) {
        this.geligTot = geligTot;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return " reiziger_id: " + reizigerId + " OV-chipkaart: "+ kaartNummer +  "," + geligTot + "," + klasse + "," + saldo ;

    }
}
