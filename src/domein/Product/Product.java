package domein.Product;

import domein.ovChip.OvChipkaart;

import java.security.PublicKey;
import java.util.ArrayList;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private float prijs;
    private OvChipkaart ovChipkaart;
    private ArrayList<OvChipkaart> ovChipkaartenList;


    public Product(int productNummer, String naam, String beschrijving, float prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovChipkaartenList = new ArrayList<OvChipkaart>();
    }


    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void deleteOvkaart(OvChipkaart ov) {
        if (ovChipkaartenList.contains(ov)) {
            ovChipkaartenList.remove(ov);
        }

        if (ov.getProductenLijst().contains(this)) {
            ov.deleteProductFromList(this);
        }
    }

    public void addOvkaart(OvChipkaart ov) {
        if (!ovChipkaartenList.contains(ov)) {
            ovChipkaartenList.add(ov);
        }

        if (!ov.getProductenLijst().contains(this)) {
            ov.addProductToList(this);
        }


    }

    public ArrayList<OvChipkaart> getOvChipkaarten() {
        return ovChipkaartenList;
    }


    public float getPrijs() {
        return prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public OvChipkaart getOvChipkaart() {
        return ovChipkaart;
    }

    public ArrayList<OvChipkaart> getOvChipkaartenList() {
        return ovChipkaartenList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNummer=" + productNummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", ovChipkaartenList=" + ovChipkaartenList +
                '}';
    }
}
