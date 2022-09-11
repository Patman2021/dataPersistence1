package domein.adres;

import domein.reiziger.Reiziger;

import java.util.ArrayList;

public class Adres {
    private  int id;
    private  String postcode;
    private  String huisnummer;
    private String straat;
    private  String woonplaats;
    private  int reizigerId;


    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger){
        this.id= id;
        this.postcode= postcode;
        this.huisnummer= huisnummer;
        this.straat = straat;
        this.woonplaats= woonplaats;
        this.reizigerId =reiziger;

    }

    public int getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    @Override
    public String toString() {
        return "#"+ reizigerId +",met adres_id="+id +": Postcode="+ postcode +", Huisnummer= "+ huisnummer;
    }
}
