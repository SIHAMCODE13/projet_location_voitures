package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionClient {

  
	public List<Client> obtenirTousClients() throws SQLException {
	    List<Client> clients = new ArrayList<>();
	    String query = "SELECT c.*, u.login, u.motDePasse FROM Clients c " +
	                   "JOIN Utilisateurs u ON c.idClient = u.idClient";
	    
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
	            client.setLogin(rs.getString("login"));
	            client.setMotDePasse(rs.getString("motDePasse"));
	            clients.add(client);
	        }
	    }
	    return clients;
	}

    public Client getClientParId(int idClient) throws SQLException {
        String query = "SELECT * FROM Clients WHERE idClient = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                    rs.getInt("idClient"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getString("telephone")
                );
            }
            return null;
        }
    }

 // Méthode pour modifier un client
    public void modifierClient(Client client, boolean changerMotDePasse) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // 1. Mettre à jour les informations du client
            String updateClient = "UPDATE Clients SET nom=?, prenom=?, adresse=?, telephone=? WHERE idClient=?";
            try (PreparedStatement stmt = conn.prepareStatement(updateClient)) {
                stmt.setString(1, client.getNom());
                stmt.setString(2, client.getPrenom());
                stmt.setString(3, client.getAdresse());
                stmt.setString(4, client.getTelephone());
                stmt.setInt(5, client.getIdClient());
                stmt.executeUpdate();
            }
            
            // 2. Mettre à jour les informations de l'utilisateur
            String updateUser;
            if (changerMotDePasse) {
                updateUser = "UPDATE Utilisateurs SET login=?, motDePasse=? WHERE idClient=?";
            } else {
                updateUser = "UPDATE Utilisateurs SET login=? WHERE idClient=?";
            }
            
            try (PreparedStatement stmt = conn.prepareStatement(updateUser)) {
                stmt.setString(1, client.getLogin());
                if (changerMotDePasse) {
                    stmt.setString(2, client.getMotDePasse());
                    stmt.setInt(3, client.getIdClient());
                } else {
                    stmt.setInt(2, client.getIdClient());
                }
                stmt.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public void supprimerClient(int idClient) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Supprimer d'abord l'utilisateur associé
            String queryUser = "DELETE FROM Utilisateurs WHERE idClient = ?";
            try (PreparedStatement stmtUser = conn.prepareStatement(queryUser)) {
                stmtUser.setInt(1, idClient);
                stmtUser.executeUpdate();
            }
            
            // Puis supprimer le client
            String queryClient = "DELETE FROM Clients WHERE idClient = ?";
            try (PreparedStatement stmtClient = conn.prepareStatement(queryClient)) {
                stmtClient.setInt(1, idClient);
                stmtClient.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }
    
    public boolean telephoneExiste(String telephone) throws SQLException {
        String query = "SELECT COUNT(*) FROM Clients WHERE telephone = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, telephone);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public boolean telephoneExistePourAutreClient(String telephone, int idClient) throws SQLException {
        String query = "SELECT COUNT(*) FROM Clients WHERE telephone = ? AND idClient != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, telephone);
            stmt.setInt(2, idClient);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public boolean ajouterClient(Client client, String login, String password) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Ajout du client
            String queryClient = "INSERT INTO Clients (nom, prenom, adresse, telephone) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmtClient = conn.prepareStatement(queryClient, Statement.RETURN_GENERATED_KEYS)) {
                stmtClient.setString(1, client.getNom());
                stmtClient.setString(2, client.getPrenom());
                stmtClient.setString(3, client.getAdresse());
                stmtClient.setString(4, client.getTelephone());
                
                int affectedRows = stmtClient.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("L'ajout du client a échoué");
                }
                
                try (ResultSet generatedKeys = stmtClient.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        client.setIdClient(generatedKeys.getInt(1));
                        
                        // Ajout de l'utilisateur associé
                        String queryUser = "INSERT INTO Utilisateurs (login, motDePasse, role, idClient) VALUES (?, ?, 'CLIENT', ?)";
                        try (PreparedStatement stmtUser = conn.prepareStatement(queryUser)) {
                            stmtUser.setString(1, login);
                            stmtUser.setString(2, password);
                            stmtUser.setInt(3, client.getIdClient());
                            stmtUser.executeUpdate();
                        }
                        
                        conn.commit();
                        return true;
                    }
                    throw new SQLException("Aucun ID obtenu");
                }
            }
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }
    
    
}