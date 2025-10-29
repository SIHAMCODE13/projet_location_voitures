package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionFacture {

    public void ajouterFacture(Facture facture) throws SQLException {
        String query = "INSERT INTO Factures (idClient, idVoiture, dateFacture, dateDepart, " +
                      "dateArrivee, kmDepart, kmArrivee, montantTotal, forfaitJournalier, prixKm) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, facture.getClient().getIdClient());
            stmt.setInt(2, facture.getVoiture().getIdVoiture());
            stmt.setDate(3, new java.sql.Date(facture.getDateFacture().getTime()));
            stmt.setDate(4, new java.sql.Date(facture.getDateDepart().getTime()));
            stmt.setDate(5, new java.sql.Date(facture.getDateArrivee().getTime()));
            stmt.setInt(6, facture.getKmDepart());
            stmt.setInt(7, facture.getKmArrivee());
            stmt.setDouble(8, facture.getMontantTotal());
            stmt.setDouble(9, facture.getForfaitJournalier());
            stmt.setDouble(10, facture.getPrixKm());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    facture.setIdFacture(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Facture> obtenirToutesFactures() throws SQLException {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT f.*, c.nom, c.prenom, c.adresse, c.telephone, " +
                      "v.immatriculation, v.marque, v.type, v.categorie, v.nbPlaces " +
                      "FROM Factures f " +
                      "JOIN Clients c ON f.idClient = c.idClient " +
                      "JOIN Voitures v ON f.idVoiture = v.idVoiture";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Client client = new Client(
                    rs.getInt("idClient"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getString("telephone")
                );
                
                Voiture voiture = new Voiture(
                    rs.getInt("idVoiture"),
                    rs.getString("immatriculation"),
                    rs.getString("marque"),
                    rs.getString("type"),
                    rs.getString("categorie"),
                    rs.getInt("nbPlaces"), false
                );
                
                Facture facture = new Facture(
                    rs.getInt("idFacture"),
                    client,
                    voiture,
                    rs.getDate("dateFacture"),
                    rs.getDate("dateDepart"),
                    rs.getDate("dateArrivee"),
                    rs.getInt("kmDepart"),
                    rs.getInt("kmArrivee"),
                    rs.getDouble("montantTotal"),
                    rs.getDouble("forfaitJournalier"),
                    rs.getDouble("prixKm")
                );
                
                factures.add(facture);
            }
        }
        return factures;
    }
}