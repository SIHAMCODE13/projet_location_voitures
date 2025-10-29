package test;
import java.util.Date;

public class Facture {
    private int idFacture;
    private Client client;
    private Voiture voiture;
    private Date dateFacture;
    private Date dateDepart;
    private Date dateArrivee;
    private int kmDepart;
    private int kmArrivee;
    private double montantTotal;
    private double forfaitJournalier;
    private double prixKm;

    public Facture(int idFacture, Client client, Voiture voiture, Date dateFacture, 
                  Date dateDepart, Date dateArrivee, int kmDepart, int kmArrivee, 
                  double montantTotal, double forfaitJournalier, double prixKm) {
        this.idFacture = idFacture;
        this.client = client;
        this.voiture = voiture;
        this.dateFacture = dateFacture;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.kmDepart = kmDepart;
        this.kmArrivee = kmArrivee;
        this.montantTotal = montantTotal;
        this.forfaitJournalier = forfaitJournalier;
        this.prixKm = prixKm;
    }

    // Getters et Setters
    public int getIdFacture() { return idFacture; }
    public Client getClient() { return client; }
    public Voiture getVoiture() { return voiture; }
    public Date getDateFacture() { return dateFacture; }
    public Date getDateDepart() { return dateDepart; }
    public Date getDateArrivee() { return dateArrivee; }
    public int getKmDepart() { return kmDepart; }
    public int getKmArrivee() { return kmArrivee; }
    public double getMontantTotal() { return montantTotal; }
    public double getForfaitJournalier() { return forfaitJournalier; }
    public double getPrixKm() { return prixKm; }

    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }
    public void setIdFacture (int idFacture) {
        this.idFacture = idFacture;
    }
}