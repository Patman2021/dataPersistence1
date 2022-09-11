package domein.reiziger;

import domein.adres.Adres;

import java.util.Date;

public class Reiziger {
    private  int id;
    private String voorletters;
    private  String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    private Adres adres;

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

    @Override
    public String toString() {
        return  this.id +": "+ getNaam() +". Geboren op:"+ geboortedatum.toString();
    }
}
