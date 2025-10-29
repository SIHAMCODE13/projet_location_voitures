package test;

public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String login;
    private String motDePasse;
    
    // Constructeur
    public Client(int idClient, String nom, String prenom, String adresse, String telephone) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
    }
    
   
    // Getters
    public int getIdClient() { return idClient; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getAdresse() { return adresse; }
    public String getTelephone() { return telephone; }
    public String getLogin() { return login; }
    public String getMotDePasse() { return motDePasse; }
    
    // Setters
    public void setIdClient(int idClient) { this.idClient = idClient; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setLogin(String login) { this.login = login; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    @Override
    public String toString() {
        return idClient + " - " + nom + " " + prenom;
    }
}