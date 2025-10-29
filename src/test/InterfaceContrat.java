package test;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class InterfaceContrat {

    private TableView<ContratLocation> tableContrats;
    private ComboBox<Client> cbClient;
    private ComboBox<Voiture> cbVehicule;
    private DatePicker dpDateDebut;
    private DatePicker dpDateFin;
    private TextField tfMontant;
    private TextField tfContratId;
    private MainApp app;
    private static final double PRIX_JOUR = 200.0;

    public void afficher(Stage stage, MainApp app) {
        this.app = app;

        // Titre
        Label lblTitre = new Label("Gestion des Contrats de Location");
        lblTitre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        lblTitre.setTextFill(Color.WHITE);
        lblTitre.setEffect(new DropShadow(5, Color.BLACK));

        // Tableau
        tableContrats = new TableView<>();
        tableContrats.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10;");
        tableContrats.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ContratLocation, Integer> colId = new TableColumn<>("ID");
        TableColumn<ContratLocation, String> colClient = new TableColumn<>("Client");
        TableColumn<ContratLocation, String> colVoiture = new TableColumn<>("Véhicule");
        TableColumn<ContratLocation, Date> colDateDebut = new TableColumn<>("Début");
        TableColumn<ContratLocation, Date> colDateFin = new TableColumn<>("Fin");
        TableColumn<ContratLocation, Double> colMontant = new TableColumn<>("Montant (DH)");

        colId.setCellValueFactory(new PropertyValueFactory<>("idContrat"));
        colClient.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getClient().getNom() + " " + cellData.getValue().getClient().getPrenom()));
        colVoiture.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getVoiture().getMarque() + " " + cellData.getValue().getVoiture().getType()));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));

        tableContrats.getColumns().addAll(colId, colClient, colVoiture, colDateDebut, colDateFin, colMontant);

        // Style des lignes du tableau
        tableContrats.setRowFactory(tv -> {
            TableRow<ContratLocation> row = new TableRow<>();
            row.setStyle("-fx-background-color: " + (row.getIndex() % 2 == 0 ? "#F5F5F5" : "#FFFFFF") + ";");
            row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #E0F7FA;"));
            row.setOnMouseExited(e -> row.setStyle("-fx-background-color: " + (row.getIndex() % 2 == 0 ? "#F5F5F5" : "#FFFFFF") + ";"));
            return row;
        });

        // Formulaire
        GridPane formGrid = new GridPane();
        formGrid.setVgap(15);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-border-radius: 10; -fx-background-radius: 10;");

        Label lblContratId = new Label("ID Contrat");
        lblContratId.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        tfContratId = new TextField();
        tfContratId.setEditable(false);
        tfContratId.setStyle("-fx-background-radius: 5;");

        Label lblClient = new Label("Client");
        lblClient.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        cbClient = new ComboBox<>();
        cbClient.setPromptText("Sélectionnez un client");
        cbClient.setStyle("-fx-background-radius: 5;");
        loadClients();

        Label lblVehicule = new Label("Véhicule");
        lblVehicule.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        cbVehicule = new ComboBox<>();
        cbVehicule.setPromptText("Sélectionnez un véhicule");
        cbVehicule.setStyle("-fx-background-radius: 5;");
        loadAvailableVehicles();

        Label lblDateDebut = new Label("Date Début");
        lblDateDebut.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        dpDateDebut = new DatePicker();
        dpDateDebut.setStyle("-fx-background-radius: 5;");

        Label lblDateFin = new Label("Date Fin");
        lblDateFin.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        dpDateFin = new DatePicker();
        dpDateFin.setStyle("-fx-background-radius: 5;");

        Label lblMontant = new Label("Montant (DH)");
        lblMontant.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        tfMontant = new TextField();
        tfMontant.setEditable(false);
        tfMontant.setStyle("-fx-background-radius: 5;");

        // Ajout des éléments au GridPane
        formGrid.add(lblContratId, 0, 0);
        formGrid.add(tfContratId, 1, 0);
        formGrid.add(lblClient, 0, 1);
        formGrid.add(cbClient, 1, 1);
        formGrid.add(lblVehicule, 0, 2);
        formGrid.add(cbVehicule, 1, 2);
        formGrid.add(lblDateDebut, 0, 3);
        formGrid.add(dpDateDebut, 1, 3);
        formGrid.add(lblDateFin, 0, 4);
        formGrid.add(dpDateFin, 1, 4);
        formGrid.add(lblMontant, 0, 5);
        formGrid.add(tfMontant, 1, 5);

        // Écouteurs pour le calcul automatique du montant
        dpDateDebut.valueProperty().addListener((obs, oldVal, newVal) -> calculerMontant());
        dpDateFin.valueProperty().addListener((obs, oldVal, newVal) -> calculerMontant());

        // Gestion de la sélection dans le tableau
        tableContrats.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tfContratId.setText(String.valueOf(newSelection.getIdContrat()));
                cbClient.setValue(newSelection.getClient());
                cbVehicule.setValue(newSelection.getVoiture());
                dpDateDebut.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(newSelection.getDateDebut())));
                dpDateFin.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(newSelection.getDateFin())));
                tfMontant.setText(String.format(Locale.US, "%.2f DH", newSelection.getMontant()));
            }
        });

        // Boutons
        Button btnReserver = createStyledButton("Réserver", "#28a745", "✔");
        btnReserver.setOnAction(e -> reserverVoiture());

        Button btnModifier = createStyledButton("Modifier", "#007BFF", "✏");
        btnModifier.setOnAction(e -> modifierContrat());

        Button btnSupprimer = createStyledButton("Supprimer", "#dc3545", "🗑");
        btnSupprimer.setOnAction(e -> supprimerContrat());
        btnSupprimer.setDisable(app.getUtilisateurConnecte() == null || !"ADMIN".equals(app.getUtilisateurConnecte().getRole()));

        Button btnVider = createStyledButton("Vider", "#ffc107", "🗑");
        btnVider.setOnAction(e -> clearFormulaire());

        Button btnRetour = createStyledButton("Retour", "#6c757d", "←");
        btnRetour.setOnAction(e -> app.showDashboard(stage));

        HBox hbButtons = new HBox(15, btnReserver, btnModifier, btnSupprimer, btnVider, btnRetour);
        hbButtons.setAlignment(Pos.CENTER);

        // Disposition principale
        HBox hbMain = new HBox(20, formGrid, tableContrats);
        hbMain.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(20, lblTitre, hbMain, hbButtons);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        BackgroundImage bgImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("contrat.jpeg").toExternalForm(), 1000, 700, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(bgImage));

        // Animation d'apparition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), vbox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        chargerContrats();

        Scene scene = new Scene(vbox, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Gestion des Contrats");
        stage.show();
    }

    private void calculerMontant() {
        if (dpDateDebut.getValue() != null && dpDateFin.getValue() != null) {
            if (dpDateFin.getValue().isBefore(dpDateDebut.getValue())) {
                tfMontant.setText("Date fin invalide");
                return;
            }
            
            long jours = ChronoUnit.DAYS.between(dpDateDebut.getValue(), dpDateFin.getValue()) + 1;
            double montant = jours * PRIX_JOUR;
            tfMontant.setText(String.format(Locale.US, "%.2f DH", montant));
        }
    }

    private double parseMontant(String montantStr) throws NumberFormatException {
        try {
            String cleaned = montantStr.replace(" DH", "").replace(",", ".").trim();
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un montant valide (ex: 1600.00)");
            throw e;
        }
    }

    private Button createStyledButton(String text, String color, String icon) {
        Button button = new Button(icon + " " + text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 8;");
        button.setEffect(new DropShadow(3, Color.BLACK));

        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        button.setOnMouseEntered(e -> scaleUp.playFromStart());
        button.setOnMouseExited(e -> scaleDown.playFromStart());

        Tooltip tooltip = new Tooltip(text);
        button.setTooltip(tooltip);

        return button;
    }

    private void loadClients() {
        try {
            List<Client> clients = new GestionClient().obtenirTousClients();
            cbClient.setItems(FXCollections.observableArrayList(clients));
            cbClient.setCellFactory(lv -> new ListCell<Client>() {
                @Override
                protected void updateItem(Client client, boolean empty) {
                    super.updateItem(client, empty);
                    setText(empty || client == null ? null : client.getNom() + " " + client.getPrenom());
                }
            });
            cbClient.setButtonCell(new ListCell<Client>() {
                @Override
                protected void updateItem(Client client, boolean empty) {
                    super.updateItem(client, empty);
                    setText(empty || client == null ? null : client.getNom() + " " + client.getPrenom());
                }
            });
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des clients: " + e.getMessage());
        }
    }

    private void loadAvailableVehicles() {
        try {
            List<Voiture> voitures = new GestionVoiture().obtenirToutesVoitures()
                    .stream()
                    .filter(Voiture::isDisponible)
                    .toList();
            cbVehicule.setItems(FXCollections.observableArrayList(voitures));
            cbVehicule.setCellFactory(lv -> new ListCell<Voiture>() {
                @Override
                protected void updateItem(Voiture voiture, boolean empty) {
                    super.updateItem(voiture, empty);
                    setText(empty || voiture == null ? null : voiture.getMarque() + " " + voiture.getType() + " (" + voiture.getImmatriculation() + ")");
                }
            });
            cbVehicule.setButtonCell(new ListCell<Voiture>() {
                @Override
                protected void updateItem(Voiture voiture, boolean empty) {
                    super.updateItem(voiture, empty);
                    setText(empty || voiture == null ? null : voiture.getMarque() + " " + voiture.getType() + " (" + voiture.getImmatriculation() + ")");
                }
            });
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des véhicules: " + e.getMessage());
        }
    }

    private void chargerContrats() {
        try {
            List<ContratLocation> contrats = new GestionContratLocation().getHistoriqueContrats();
            ObservableList<ContratLocation> data = FXCollections.observableArrayList(contrats);
            tableContrats.setItems(data);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur de chargement: " + e.getMessage());
        }
    }

    private void reserverVoiture() {
        try {
            Client client = cbClient.getValue();
            Voiture voiture = cbVehicule.getValue();
            LocalDate dateDebut = dpDateDebut.getValue();
            LocalDate dateFin = dpDateFin.getValue();
            String montantStr = tfMontant.getText();

            if (client == null || voiture == null || dateDebut == null || dateFin == null || montantStr.isEmpty()) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            if (dateFin.isBefore(dateDebut)) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La date de fin doit être après la date de début.");
                return;
            }

            if (!voiture.isDisponible()) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Le véhicule n'est pas disponible.");
                return;
            }

            double montant = parseMontant(montantStr);
            Date dateDebutSql = java.sql.Date.valueOf(dateDebut);
            Date dateFinSql = java.sql.Date.valueOf(dateFin);

            ContratLocation contrat = new ContratLocation(0, client, voiture, dateDebutSql, dateFinSql, montant);
            GestionContratLocation gestionContrat = new GestionContratLocation();
            gestionContrat.ajouterContrat(contrat);

            voiture.setDisponible(false);
            new GestionVoiture().mettreAJourDisponibilite(voiture.getIdVoiture(), false);

            chargerContrats();
            loadAvailableVehicles();
            clearFormulaire();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Réservation effectuée avec succès.");

        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur BD", "Erreur de base de données: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Déjà géré dans parseMontant()
        } catch (Exception e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur inattendue: " + e.getMessage());
        }
    }

    private void modifierContrat() {
        ContratLocation selectedContrat = tableContrats.getSelectionModel().getSelectedItem();
        if (selectedContrat == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un contrat à modifier.");
            return;
        }

        try {
            Client client = cbClient.getValue();
            Voiture voiture = cbVehicule.getValue();
            LocalDate dateDebut = dpDateDebut.getValue();
            LocalDate dateFin = dpDateFin.getValue();
            String montantStr = tfMontant.getText();

            if (client == null || voiture == null || dateDebut == null || dateFin == null || montantStr.isEmpty()) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            if (dateFin.isBefore(dateDebut)) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La date de fin doit être après la date de début.");
                return;
            }

            double montant = parseMontant(montantStr);
            Date dateDebutSql = java.sql.Date.valueOf(dateDebut);
            Date dateFinSql = java.sql.Date.valueOf(dateFin);

            selectedContrat.setClient(client);
            selectedContrat.setVoiture(voiture);
            selectedContrat.setDateDebut(dateDebutSql);
            selectedContrat.setDateFin(dateFinSql);
            selectedContrat.setMontant(montant);

            new GestionContratLocation().modifierContrat(selectedContrat);

            chargerContrats();
            loadAvailableVehicles();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Contrat modifié avec succès.");

        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur BD", "Erreur de base de données: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Déjà géré dans parseMontant()
        } catch (Exception e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur inattendue: " + e.getMessage());
        }
    }

    private void supprimerContrat() {
        ContratLocation selectedContrat = tableContrats.getSelectionModel().getSelectedItem();
        if (selectedContrat == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un contrat à supprimer.");
            return;
        }

        if (app.getUtilisateurConnecte() == null || !"ADMIN".equals(app.getUtilisateurConnecte().getRole())) {
            afficherAlerte(Alert.AlertType.ERROR, "Accès refusé", "Seul un administrateur peut supprimer des contrats.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Supprimer le contrat");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer le contrat #" + selectedContrat.getIdContrat() + " ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                new GestionContratLocation().supprimerContrat(selectedContrat.getIdContrat());
                selectedContrat.getVoiture().setDisponible(true);
                new GestionVoiture().mettreAJourDisponibilite(selectedContrat.getVoiture().getIdVoiture(), true);
                chargerContrats();
                loadAvailableVehicles();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Contrat supprimé avec succès.");
                clearFormulaire();
            } catch (SQLException e) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression: " + e.getMessage());
            }
        }
    }

    private void clearFormulaire() {
        tfContratId.clear();
        cbClient.setValue(null);
        cbVehicule.setValue(null);
        dpDateDebut.setValue(null);
        dpDateFin.setValue(null);
        tfMontant.clear();
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10;");
        alert.showAndWait();
    }
}