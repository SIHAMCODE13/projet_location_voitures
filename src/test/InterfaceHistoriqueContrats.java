package test;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class InterfaceHistoriqueContrats {

    public void afficher(Stage stage, MainApp app) {
        Label lblTitre = new Label("Historique des Contrats de Location");
        TableView<ContratLocation> table = new TableView<>();

        TableColumn<ContratLocation, String> clientCol = new TableColumn<>("Client");
        clientCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getClient().getNom() + " " + data.getValue().getClient().getPrenom()));

        TableColumn<ContratLocation, String> vehiculeCol = new TableColumn<>("Véhicule");
        vehiculeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getVoiture().getImmatriculation()));

        TableColumn<ContratLocation, String> datesCol = new TableColumn<>("Dates");
        datesCol.setCellValueFactory(data -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return new javafx.beans.property.SimpleStringProperty(
                    sdf.format(data.getValue().getDateDebut()) + " à " + sdf.format(data.getValue().getDateFin()));
        });

        TableColumn<ContratLocation, String> montantCol = new TableColumn<>("Montant (DH)");
        montantCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.format("%.2f", data.getValue().getMontant())));

        table.getColumns().addAll(clientCol, vehiculeCol, datesCol, montantCol);

        try {
            table.getItems().addAll(new GestionContratLocation().getHistoriqueContrats());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Erreur chargement historique : " + e.getMessage()).show();
        }

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> app.start(stage));

        VBox vbox = new VBox(10, lblTitre, table, btnRetour);
        vbox.setPadding(new javafx.geometry.Insets(15));
        Scene scene = new Scene(vbox, 700, 400);
        stage.setScene(scene);
    }
}