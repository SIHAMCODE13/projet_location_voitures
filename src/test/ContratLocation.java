package test;

import java.util.Date;

public class ContratLocation {
    private int idContrat;
    private Client client;
    private Voiture voiture; 
    private Date dateDebut;
    private Date dateFin;
    private double montant;

    public ContratLocation(int idContrat, Client client, Voiture voiture, Date dateDebut, Date dateFin, double montant) {
        this.idContrat = idContrat;
        this.client = client;
        this.voiture = voiture;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montant = montant;
    }


	// Getters
    public int getIdContrat() { return idContrat; }
    public Client getClient() { return client; }
    public Voiture getVoiture() { return voiture; } 
    public Date getDateDebut() { return dateDebut; }
    public Date getDateFin() { return dateFin; }
    public double getMontant() { return montant; }

    // Setters
    public void setIdContrat(int idContrat) { this.idContrat = idContrat; }
    public void setClient(Client client) { this.client = client; }
    public void setVoiture(Voiture voiture) { this.voiture = voiture; } 
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public void setMontant(double montant) { this.montant = montant; }
}