package test;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InterfaceReservation {
    private static final double PRIX_JOURNALIER = 200.0; // Prix fixe de 200 DH par jour
    
    public void afficher(Stage stage, MainApp app, Voiture voiture) {
        // Création des composants
        DatePicker dpDebut = new DatePicker(LocalDate.now());
        DatePicker dpFin = new DatePicker(LocalDate.now().plusDays(1));
        TextField tfMontant = new TextField();
        tfMontant.setEditable(false);
        Label titre = new Label("Explorez sans limites – Réservez votre véhicule");
        titre.getStyleClass().add("titre-reservation");

        
        // Calcul initial du montant
        calculerMontant(dpDebut.getValue(), dpFin.getValue(), tfMontant);
        
        // Écouteurs pour recalculer le montant
        dpDebut.valueProperty().addListener((obs, oldVal, newVal) -> 
            calculerMontant(newVal, dpFin.getValue(), tfMontant));
        
        dpFin.valueProperty().addListener((obs, oldVal, newVal) -> 
            calculerMontant(dpDebut.getValue(), newVal, tfMontant));
        
        Button btnReserver = new Button("Confirmer la réservation");
        btnReserver.setId("btnReserver");
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.setId("btnAnnuler");
        
        // Configuration du formulaire
        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.addRow(0, new Label("Véhicule:"), new Label(voiture.getMarque() + " " + voiture.getType()));
        form.addRow(1, new Label("Immatriculation:"), new Label(voiture.getImmatriculation()));
        form.addRow(2, new Label("Date début:"), dpDebut);
        form.addRow(3, new Label("Date fin:"), dpFin);
        form.addRow(4, new Label("Montant estimé (DH):"), tfMontant);
        
        // Gestion des actions des boutons
        btnReserver.setOnAction(e -> {
            try {
                if (dpDebut.getValue() == null || dpFin.getValue() == null) {
                    afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner les dates");
                    return;
                }
                
                if (dpDebut.getValue().isAfter(dpFin.getValue())) {
                    afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La date de fin doit être après la date de début");
                    return;
                }
                
                String montantText = tfMontant.getText().replace(',', '.');
                double montant = Double.parseDouble(montantText);
                
                Client client = new GestionClient().getClientParId(app.getUtilisateurConnecte().getIdClient());
                ContratLocation contrat = new ContratLocation(
                    0,
                    client,
                    voiture,
                    java.sql.Date.valueOf(dpDebut.getValue()),
                    java.sql.Date.valueOf(dpFin.getValue()),
                    montant
                );
                new GestionContratLocation().ajouterContrat(contrat);
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Réservation confirmée");
                app.showDashboard(stage);
            } catch (SQLException ex) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", ex.getMessage());
            }
        });
        
        btnAnnuler.setOnAction(e -> app.showDashboard(stage));
        
        // Création de la racine
        VBox root = new VBox(20, titre, form, btnReserver, btnAnnuler);

        root.setPadding(new Insets(15));
        root.getStyleClass().add("reservation-container");
        
        // Configuration de la scène
        Scene scene = new Scene(root, 500, 350);
        scene.getStylesheets().add(getClass().getResource("/test/styleReserver.css").toExternalForm());
        
        // Configuration de la fenêtre
        stage.setScene(scene);
        stage.setTitle("Nouvelle réservation");
        stage.sizeToScene();
    }
    
    private void calculerMontant(LocalDate dateDebut, LocalDate dateFin, TextField tfMontant) {
        if (dateDebut != null && dateFin != null) {
            long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
            if (jours < 0) {
                tfMontant.setText("Dates invalides");
            } else {
                double montant = (jours + 1) * PRIX_JOURNALIER;
                tfMontant.setText(String.format("%.2f", montant).replace(',', '.'));
            }
        }
    }
    
    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}