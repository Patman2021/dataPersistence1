package domein.ovChip;

import domein.Product.Product;

import java.util.ArrayList;
import java.util.Date;

public class OvChipkaart {
    private int kaartNummer;
    private Date geligTot;
    private int klasse;
    private float saldo;
    private  int reizigerId;

    private  Product product;

    private ArrayList<Product> productenLijst;


    public OvChipkaart( int kaartNummer, Date geligTot, int klasse, float saldo, int reizigerId){
        this.kaartNummer= kaartNummer;
        this.geligTot= geligTot;
        this.klasse = klasse;
        this.saldo= saldo;
        this.reizigerId = reizigerId;
        this.productenLijst = new ArrayList<>();
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

    public ArrayList<Product> getProductenLijst() {
        return productenLijst;
    }

    public void addProductToList(Product p){
        if(!productenLijst.contains(p)){
            productenLijst.add(p);
        }
        if(!p.getOvChipkaarten().contains(this)) {
            p.addOvkaart(this);
        }

    }

    public void addProductList(ArrayList<Product> p){
    this.productenLijst = p;

    }

    public void deleteProductFromList(Product p){
        if(productenLijst.contains(p)){
            productenLijst.remove(p);
        }
        if(p.getOvChipkaarten().contains(this)) {
            p.deleteOvkaart(this);
        }

    }

    @Override
    public String toString() {
        ArrayList product= new ArrayList<>();
        for (Product p: productenLijst){
            product.add(p.getProductNummer());
        }

        return "OvChipkaart{" +
                "kaartNummer=" + kaartNummer +
                ", geligTot=" + geligTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reizigerId=" + reizigerId +
                ", productenLijst=" + product +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  OvChipkaart){
            if (((OvChipkaart) obj).getKaartNummer() == this.getKaartNummer()) {
                return true;
        }
        }
        return false;}
}
