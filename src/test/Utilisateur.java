package test;

public class Utilisateur {
    private int idUtilisateur;
    private String login;
    private String motDePasse;
    private String role;
    private int idClient;

    public Utilisateur(int idUtilisateur, String login, String motDePasse, String role, int idClient) {
        this.idUtilisateur = idUtilisateur;
        this.login = login;
        this.motDePasse = motDePasse;
        this.role = role;
        this.idClient = idClient;
    }

    // Getters
    public int getIdUtilisateur() { return idUtilisateur; }
    public String getLogin() { return login; }
    public String getMotDePasse() { return motDePasse; }
    public String getRole() { return role; }
    public int getIdClient() { return idClient; }

    // Setters
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    public void setLogin(String login) { this.login = login; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public void setRole(String role) { this.role = role; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
}