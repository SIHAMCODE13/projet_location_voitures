package test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class InterfaceClientFacture {
    public void afficher(Stage stage, MainApp app) {
        TableView<Facture> table = new TableView<>();
        
        TableColumn<Facture, String> colVoiture = new TableColumn<>("Véhicule");
        TableColumn<Facture, String> colDates = new TableColumn<>("Période");
        TableColumn<Facture, String> colMontant = new TableColumn<>("Montant");
        
        colVoiture.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getVoiture().getMarque() + " " + 
                cellData.getValue().getVoiture().getType()));
        
        colDates.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new javafx.beans.property.SimpleStringProperty(
                sdf.format(cellData.getValue().getDateDepart()) + " - " + 
                sdf.format(cellData.getValue().getDateArrivee()));
        });
        
        colMontant.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                String.format("%.2f DH", cellData.getValue().getMontantTotal())));
        
        table.getColumns().addAll(colVoiture, colDates, colMontant);
        
        try {
            List<Facture> factures = new GestionFacture().obtenirToutesFactures()
                    .stream()
                    .filter(f -> f.getClient().getIdClient() == app.getUtilisateurConnecte().getIdClient())
                    .collect(Collectors.toList());
            ObservableList<Facture> data = FXCollections.observableArrayList(factures);
            table.setItems(data);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", e.getMessage());
        }
        
        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> app.showDashboard(stage));
        
        VBox root = new VBox(10, table, btnRetour);
       
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Mes factures");
    }
    
    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}