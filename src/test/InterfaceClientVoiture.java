package test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class InterfaceClientVoiture {
    public void afficher(Stage stage, MainApp app) {
        TableView<Voiture> table = new TableView<>();
        
        TableColumn<Voiture, String> colImmat = new TableColumn<>("Immatriculation");
        TableColumn<Voiture, String> colMarque = new TableColumn<>("Marque");
        TableColumn<Voiture, String> colType = new TableColumn<>("Type");
        TableColumn<Voiture, String> colCategorie = new TableColumn<>("Catégorie");
        TableColumn<Voiture, Integer> colPlaces = new TableColumn<>("Places");
        
        colImmat.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colPlaces.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));
        
        table.getColumns().addAll(colImmat, colMarque, colType, colCategorie, colPlaces);
        
        try {
            List<Voiture> voitures = new GestionVoiture().obtenirToutesVoitures()
                    .stream()
                    .filter(Voiture::isDisponible)
                    .collect(Collectors.toList());
            ObservableList<Voiture> data = FXCollections.observableArrayList(voitures);
            table.setItems(data);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", e.getMessage());
        }
        
        Button btnReserver = new Button("Réserver");
        btnReserver.setOnAction(e -> reserverVoiture(table.getSelectionModel().getSelectedItem(), stage, app));
        
        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> app.showDashboard(stage));
        
        VBox root = new VBox(10, table, btnReserver, btnRetour);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Véhicules disponibles");

    }
    
    private void reserverVoiture(Voiture voiture, Stage stage, MainApp app) {
        if (voiture == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un véhicule");
            return;
        }
        new InterfaceReservation().afficher(stage, app, voiture);
    }
    
    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}