package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionContratLocation {

    public void ajouterContrat(ContratLocation contrat) throws SQLException {
        String query = "INSERT INTO Contrats (idClient, idVoiture, dateDebut, dateFin, montant) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, contrat.getClient().getIdClient());
            stmt.setInt(2, contrat.getVoiture().getIdVoiture());
            stmt.setDate(3, new java.sql.Date(contrat.getDateDebut().getTime()));
            stmt.setDate(4, new java.sql.Date(contrat.getDateFin().getTime()));
            stmt.setDouble(5, contrat.getMontant());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                contrat.setIdContrat(rs.getInt(1));
            }

            contrat.getVoiture().setDisponible(false);
            new GestionVoiture().mettreAJourDisponibilite(contrat.getVoiture().getIdVoiture(), false);
        }
    }

    public void modifierContrat(ContratLocation contrat) throws SQLException {
        String query = "UPDATE Contrats SET dateDebut=?, dateFin=?, montant=? WHERE idContrat=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, new java.sql.Date(contrat.getDateDebut().getTime()));
            stmt.setDate(2, new java.sql.Date(contrat.getDateFin().getTime()));
            stmt.setDouble(3, contrat.getMontant());
            stmt.setInt(4, contrat.getIdContrat());

            stmt.executeUpdate();
        }
    }

    public List<ContratLocation> getHistoriqueContrats() throws SQLException {
        List<ContratLocation> contrats = new ArrayList<>();

        String query = "SELECT c.*, cl.nom, cl.prenom, cl.adresse, cl.telephone, v.immatriculation, v.marque, v.type, v.categorie, v.nbPlaces, v.disponible " +
                       "FROM Contrats c " +
                       "JOIN Clients cl ON c.idClient = cl.idClient " +
                       "JOIN Voitures v ON c.idVoiture = v.idVoiture";

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
                    rs.getInt("nbPlaces"),
                    rs.getBoolean("disponible")
                );

                ContratLocation contrat = new ContratLocation(
                    rs.getInt("idContrat"),
                    client,
                    voiture,
                    rs.getDate("dateDebut"),
                    rs.getDate("dateFin"),
                    rs.getDouble("montant")
                );

                contrats.add(contrat);
            }
        }

        return contrats;
    }
    
    public void supprimerContrat(int idContrat) throws SQLException {
        String query = "DELETE FROM Contrats WHERE idContrat = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idContrat);
            stmt.executeUpdate();
        }
    }
}