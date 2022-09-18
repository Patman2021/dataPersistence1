package domein.reiziger;

import domein.adres.Adres;
import domein.ovChip.OvChipkaart;

import java.util.ArrayList;
import java.util.Date;

public class Reiziger {
    private  int id;
    private String voorletters;
    private  String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    private Adres adres;

    private ArrayList<OvChipkaart> ovChipkaart = new ArrayList<>();

    public  Reiziger( int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum){
        this.id= id;
        this.voorletters = voorletters;
        this.tussenvoegsel= tussenvoegsel;
        this.achternaam= achternaam;
        this.geboortedatum= geboortedatum;
    }

    public  int getId(){
        return  this.id;
    }


    public String getNaam(){
        return  this.voorletters + " " + this.tussenvoegsel + " " + this.achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void adresToevoegenAanReiziger(Adres adres){
        this.adres= adres;
    }

    public ArrayList<OvChipkaart> getOvChipkaart() {
        return ovChipkaart;
    }

    public void addOvChipKaart(OvChipkaart ovChipkaart) {
        this.ovChipkaart.add(ovChipkaart);
    }

    public void removeOvChipKaart(OvChipkaart ov){
            ovChipkaart.remove(ov);
    }

    public Adres getAdres() {
        return adres;
    }



    @Override
    public String toString() {
        return   "reiziger {"+this.id +": "+ getNaam() +". Geboren op:"+ geboortedatum +" "+ adres +"}  Ov_chipkaart" + ovChipkaart;
    }
}
