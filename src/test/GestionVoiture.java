package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionVoiture {

    public boolean ajouterVoiture(Voiture voiture) throws SQLException {
        String query = "INSERT INTO Voitures (immatriculation, marque, type, categorie, nbPlaces, disponible) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, voiture.getImmatriculation());
            stmt.setString(2, voiture.getMarque());
            stmt.setString(3, voiture.getType());
            stmt.setString(4, voiture.getCategorie());
            stmt.setInt(5, voiture.getNbPlaces());
            stmt.setBoolean(6, voiture.isDisponible()); // Ajout du champ disponible
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("L'ajout de la voiture a échoué, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    voiture.setIdVoiture(generatedKeys.getInt(1));
                    return true;
                }
            }
            return false;
        }
    }
    
    
    
    private Connection getConnection() throws SQLException {
        String dbPath = "jdbc:ucanaccess://C:\\Users\\hp\\Desktop\\Filière Ingénieur 3IACN\\S6\\API JAVA\\APPLICATION\\LocationVoiture.accdb";
        return DriverManager.getConnection(dbPath);
    }
    
 // Dans GestionVoiture.java

    public boolean voitureEstUtiliseeDansContrat(int idVoiture) throws SQLException {
        String query = "SELECT COUNT(*) FROM Contrats WHERE idVoiture = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idVoiture);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public List<Voiture> obtenirToutesVoitures() throws SQLException {
        List<Voiture> voitures = new ArrayList<>();
        String query = "SELECT * FROM Voitures";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Voiture voiture = new Voiture(
                    rs.getInt("idVoiture"),
                    rs.getString("immatriculation"),
                    rs.getString("marque"),
                    rs.getString("type"),
                    rs.getString("categorie"),
                    rs.getInt("nbPlaces"),
                    rs.getBoolean("disponible") // Récupération du champ disponible
                );
                voitures.add(voiture);
            }
        }
        return voitures;
    }

    public Voiture getVoitureParId(int idVoiture) throws SQLException {
        String query = "SELECT * FROM Voitures WHERE idVoiture = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idVoiture);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Voiture(
                    rs.getInt("idVoiture"),
                    rs.getString("immatriculation"),
                    rs.getString("marque"),
                    rs.getString("type"),
                    rs.getString("categorie"),
                    rs.getInt("nbPlaces"),
                    rs.getBoolean("disponible") // Récupération du champ disponible
                );
            }
            return null;
        }
    }

    public void modifierVoiture(Voiture voiture) throws SQLException {
        String query = "UPDATE Voitures SET immatriculation = ?, marque = ?, type = ?, categorie = ?, nbPlaces = ?, disponible = ? WHERE idVoiture = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, voiture.getImmatriculation());
            stmt.setString(2, voiture.getMarque());
            stmt.setString(3, voiture.getType());
            stmt.setString(4, voiture.getCategorie());
            stmt.setInt(5, voiture.getNbPlaces());
            stmt.setBoolean(6, voiture.isDisponible()); // Mise à jour du champ disponible
            stmt.setInt(7, voiture.getIdVoiture());
            stmt.executeUpdate();
        }
    }

    public void supprimerVoiture(int idVoiture) throws SQLException {
        String query = "DELETE FROM Voitures WHERE idVoiture = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idVoiture);
            stmt.executeUpdate();
        }
    }

    public void mettreAJourDisponibilite(int idVoiture, boolean disponible) throws SQLException {
        String query = "UPDATE Voitures SET disponible = ? WHERE idVoiture = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, disponible);
            stmt.setInt(2, idVoiture);
            stmt.executeUpdate();
        }
    }
}