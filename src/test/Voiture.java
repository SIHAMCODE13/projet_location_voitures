package test;

public class Voiture {
    private int idVoiture;
    private String immatriculation;
    private String marque;
    private String type;
    private String categorie;
    private int nbPlaces;
    private boolean disponible; // ← manquant

    public Voiture(int idVoiture, String immatriculation, String marque, String type, String categorie, int nbPlaces, boolean disponible) {
        this.idVoiture = idVoiture;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.type = type;
        this.categorie = categorie;
        this.nbPlaces = nbPlaces;
        this.disponible = disponible; // ← initialise la disponibilité
    }

    // Getters
    public int getIdVoiture() { return idVoiture; }
    public String getImmatriculation() { return immatriculation; }
    public String getMarque() { return marque; }
    public String getType() { return type; }
    public String getCategorie() { return categorie; }
    public int getNbPlaces() { return nbPlaces; }
    public boolean isDisponible() { return disponible; } // ← manquant

    // Setters
    public void setIdVoiture(int idVoiture) { this.idVoiture = idVoiture; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }
    public void setMarque(String marque) { this.marque = marque; }
    public void setType(String type) { this.type = type; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setNbPlaces(int nbPlaces) { this.nbPlaces = nbPlaces; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; } // ← manquant
    
    public double getPrixLocation() {
        return 200.0; // Prix fixe de 200 DH
    }
    
}