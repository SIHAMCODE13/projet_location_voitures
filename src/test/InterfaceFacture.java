package test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class InterfaceFacture {

    private TableView<Facture> tableFactures;
    private DatePicker datePicker;
    private ComboBox<Client> cbClient;
    private ComboBox<Voiture> cbVoiture;
    private TextField tfImmat;
    private TextField tfMarque;
    private TextField tfType;
    private TextField tfCategorie;
    private TextField tfNbPlaces;
    private TextField tfNom;
    private TextField tfPrenom;
    private TextField tfAdresse;
    private TextField tfTel;
    private DatePicker dpDepart;
    private ComboBox<String> cbAgenceDepart;
    private DatePicker dpArrivee;
    private ComboBox<String> cbAgenceArrivee;
    private TextField tfKmDepart;
    private TextField tfKmArrivee;
    private TextField tfMontant;
    private TextField tfForfaitAgence;
    private TextField tfForfaitJournalier;
    private TextField tfJours;
    private TextField tfTotalJournalier;
    private TextField tfPrixKm;
    private TextField tfKmParcourus;
    private TextField tfTotalKm;
    private TextField tfTotale;

    public void afficher(Stage stage, MainApp app) {
        // Main layout with a modern blue gradient background
        BorderPane mainPane = new BorderPane();

        // Dégradé de fond moderne
        Stop[] stops = new Stop[] {  new Stop(0, Color.web("#388e3c")), new Stop(1, Color.web("#b0bec5")) };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        Background background = new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY));
        mainPane.setBackground(background);
        
        
        



        // Création du titre
        Label lblTitre = new Label("GESTION DES FACTURES");
        lblTitre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Tableau pour l'historique des factures (placé à droite)
        tableFactures = new TableView<>();
        tableFactures.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d1d1d1; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        tableFactures.setPrefHeight(400);

        TableColumn<Facture, Integer> colId = new TableColumn<>("ID Facture");
        TableColumn<Facture, String> colClient = new TableColumn<>("Client");
        TableColumn<Facture, String> colVoiture = new TableColumn<>("Voiture");
        TableColumn<Facture, Date> colDateFacture = new TableColumn<>("Date Facture");
        TableColumn<Facture, Double> colMontant = new TableColumn<>("Montant Total");

        colId.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
        colClient.setCellValueFactory(cellData -> {
            Facture facture = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(facture.getClient().getNom() + " " + facture.getClient().getPrenom());
        });
        colVoiture.setCellValueFactory(cellData -> {
            Facture facture = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(facture.getVoiture().getImmatriculation());
        });
        colDateFacture.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));

        tableFactures.getColumns().addAll(colId, colClient, colVoiture, colDateFacture, colMontant);

        // Ajout d'un ScrollPane pour le tableau
        ScrollPane scrollPane = new ScrollPane(tableFactures);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setStyle("-fx-background: #ffffff; -fx-border-color: #d1d1d1; -fx-background-radius: 5px;");

        // Section Date
        Label lblDate = new Label("LA DATE");
        lblDate.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        // Section Client
        Label lblClient = new Label("N° CLIENT");
        lblClient.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        cbClient = new ComboBox<>();
        try {
            cbClient.getItems().addAll(new GestionClient().obtenirTousClients());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbClient.setPromptText("Sélectionner un client");
        cbClient.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        cbClient.setCellFactory(param -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(client.getIdClient()));
                }
            }
        });

        cbClient.setButtonCell(new ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(client.getIdClient()));
                }
            }
        });
        cbClient.getSelectionModel().selectFirst();

        // Détails client
        Label lblNom = new Label("NOM");
        lblNom.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfNom = new TextField();
        tfNom.setEditable(false);
        tfNom.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblPrenom = new Label("PRENOM");
        lblPrenom.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfPrenom = new TextField();
        tfPrenom.setEditable(false);
        tfPrenom.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblAdresse = new Label("ADRESSE");
        lblAdresse.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfAdresse = new TextField();
        tfAdresse.setEditable(false);
        tfAdresse.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblTel = new Label("TEL");
        lblTel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfTel = new TextField();
        tfTel.setEditable(false);
        tfTel.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        // Section Voiture
        Label lblVoiture = new Label("VOITURE");
        lblVoiture.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        cbVoiture = new ComboBox<>();
        try {
            cbVoiture.getItems().addAll(new GestionVoiture().obtenirToutesVoitures());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbVoiture.setPromptText("Sélectionner une voiture");
        cbVoiture.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        cbVoiture.setCellFactory(param -> new ListCell<Voiture>() {
            @Override
            protected void updateItem(Voiture voiture, boolean empty) {
                super.updateItem(voiture, empty);
                if (empty || voiture == null) {
                    setText(null);
                } else {
                    setText(voiture.getImmatriculation());
                }
            }
        });

        cbVoiture.setButtonCell(new ListCell<Voiture>() {
            @Override
            protected void updateItem(Voiture voiture, boolean empty) {
                super.updateItem(voiture, empty);
                if (empty || voiture == null) {
                    setText(null);
                } else {
                    setText(voiture.getImmatriculation());
                }
            }
        });
        cbVoiture.getSelectionModel().selectFirst();

        // Détails voiture
        Label lblImmat = new Label("IMMATRICULATION");
        lblImmat.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfImmat = new TextField();
        tfImmat.setEditable(false);
        tfImmat.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblMarque = new Label("MARQUE");
        lblMarque.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfMarque = new TextField();
        tfMarque.setEditable(false);
        tfMarque.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblType = new Label("TYPE");
        lblType.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfType = new TextField();
        tfType.setEditable(false);
        tfType.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblCategorie = new Label("CATEGORIE");
        lblCategorie.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfCategorie = new TextField();
        tfCategorie.setEditable(false);
        tfCategorie.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblNbPlaces = new Label("NB PLACES");
        lblNbPlaces.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfNbPlaces = new TextField();
        tfNbPlaces.setEditable(false);
        tfNbPlaces.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        // Section Location
        Label lblDepart = new Label("DEPART");
        lblDepart.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        dpDepart = new DatePicker();
        dpDepart.setValue(LocalDate.now());
        dpDepart.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        cbAgenceDepart = new ComboBox<>();
        cbAgenceDepart.getItems().addAll("Agence 1", "Agence 2", "Agence 3");
        cbAgenceDepart.setValue("Agence 1");
        cbAgenceDepart.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        tfKmDepart = new TextField();
        tfKmDepart.setPromptText("KM DEPART");
        tfKmDepart.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblArrivee = new Label("ARRIVÉE");
        lblArrivee.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        dpArrivee = new DatePicker();
        dpArrivee.setValue(LocalDate.now().plusDays(1));
        dpArrivee.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        cbAgenceArrivee = new ComboBox<>();
        cbAgenceArrivee.getItems().addAll("Agence 1", "Agence 2", "Agence 3");
        cbAgenceArrivee.setValue("Agence 1");
        cbAgenceArrivee.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        tfKmArrivee = new TextField();
        tfKmArrivee.setPromptText("KM ARRIVÉE");
        tfKmArrivee.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        // Section Calcul
        Label lblMontant = new Label("MONTANT LOCATION");
        lblMontant.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfMontant = new TextField();
        tfMontant.setEditable(false);
        tfMontant.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblForfaitAgence = new Label("FORFAIT AGENCE");
        lblForfaitAgence.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfForfaitAgence = new TextField("0");
        tfForfaitAgence.setEditable(false);
        tfForfaitAgence.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblForfaitJournalier = new Label("FORFAIT JOURNALIER");
        lblForfaitJournalier.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfForfaitJournalier = new TextField("120");
        tfForfaitJournalier.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblJours = new Label("JOURS");
        lblJours.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfJours = new TextField();
        tfJours.setEditable(false);
        tfJours.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblTotalJournalier = new Label("DH");
        lblTotalJournalier.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfTotalJournalier = new TextField();
        tfTotalJournalier.setEditable(false);
        tfTotalJournalier.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblPrixKm = new Label("Km");
        lblPrixKm.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfPrixKm = new TextField("5");
        tfPrixKm.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblKmParcourus = new Label("Km");
        lblKmParcourus.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfKmParcourus = new TextField();
        tfKmParcourus.setEditable(false);
        tfKmParcourus.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblTotalKm = new Label("DH");
        lblTotalKm.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfTotalKm = new TextField();
        tfTotalKm.setEditable(false);
        tfTotalKm.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Label lblTotale = new Label("TOTALE");
        lblTotale.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfTotale = new TextField();
        tfTotale.setEditable(false);
        tfTotale.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        // Boutons
        Button btnCalculer = new Button("Calculer");
        btnCalculer.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnCalculer.setOnMouseEntered(e -> btnCalculer.setStyle("-fx-background-color: #357abd; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnCalculer.setOnMouseExited(e -> btnCalculer.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnCalculer.setOnAction(e -> calculerMontant());

        Button btnEnregistrer = new Button("Enregistrer");
        btnEnregistrer.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnEnregistrer.setOnMouseEntered(e -> btnEnregistrer.setStyle("-fx-background-color: #218838; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnEnregistrer.setOnMouseExited(e -> btnEnregistrer.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnEnregistrer.setOnAction(e -> enregistrerFacture());

        Button btnImprimer = new Button("Imprimer");
        btnImprimer.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnImprimer.setOnMouseEntered(e -> btnImprimer.setStyle("-fx-background-color: #138496; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnImprimer.setOnMouseExited(e -> btnImprimer.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnImprimer.setOnAction(e -> imprimerFacture());

        Button btnRetour = new Button("Retour");
        btnRetour.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnRetour.setOnMouseEntered(e -> btnRetour.setStyle("-fx-background-color: #5a6268; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRetour.setOnMouseExited(e -> btnRetour.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRetour.setOnAction(e -> app.showDashboard(stage));

        // Mise à jour des champs lorsque la voiture est sélectionnée
        cbVoiture.setOnAction(e -> {
            Voiture selected = cbVoiture.getSelectionModel().getSelectedItem();
            if (selected != null) {
                tfImmat.setText(selected.getImmatriculation());
                tfMarque.setText(selected.getMarque());
                tfType.setText(selected.getType());
                tfCategorie.setText(selected.getCategorie());
                tfNbPlaces.setText(String.valueOf(selected.getNbPlaces()));
            }
        });

        // Mise à jour des champs lorsque le client est sélectionné
        cbClient.setOnAction(e -> {
            Client selected = cbClient.getSelectionModel().getSelectedItem();
            if (selected != null) {
                tfNom.setText(selected.getNom());
                tfPrenom.setText(selected.getPrenom());
                tfAdresse.setText(selected.getAdresse());
                tfTel.setText(selected.getTelephone());
            }
        });

        // Gestion de la sélection dans le tableau
        tableFactures.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                datePicker.setValue(newSelection.getDateFacture().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                cbClient.getSelectionModel().select(newSelection.getClient());
                cbVoiture.getSelectionModel().select(newSelection.getVoiture());
                dpDepart.setValue(newSelection.getDateDepart().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                dpArrivee.setValue(newSelection.getDateArrivee().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                tfKmDepart.setText(String.valueOf(newSelection.getKmDepart()));
                tfKmArrivee.setText(String.valueOf(newSelection.getKmArrivee()));
                tfForfaitJournalier.setText(String.valueOf(newSelection.getForfaitJournalier()));
                tfPrixKm.setText(String.valueOf(newSelection.getPrixKm()));
                tfTotale.setText(String.valueOf(newSelection.getMontantTotal()));
                calculerMontant();
            }
        });

        // GridPane pour le formulaire à gauche
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 10px;");

        // Titre
        grid.add(lblTitre, 0, 0, 4, 1);

        // Date
        grid.add(lblDate, 0, 2);
        grid.add(datePicker, 1, 2);

        // Client
        grid.add(lblClient, 0, 3);
        grid.add(cbClient, 1, 3);

        // Détails client
        grid.add(lblNom, 0, 4);
        grid.add(tfNom, 1, 4);
        grid.add(lblPrenom, 2, 4);
        grid.add(tfPrenom, 3, 4);

        grid.add(lblAdresse, 0, 5);
        grid.add(tfAdresse, 1, 5);
        grid.add(lblTel, 2, 5);
        grid.add(tfTel, 3, 5);

        // Voiture
        grid.add(lblVoiture, 0, 6);
        grid.add(cbVoiture, 1, 6);

        // Détails voiture
        grid.add(lblImmat, 0, 7);
        grid.add(tfImmat, 1, 7);
        grid.add(lblMarque, 2, 7);
        grid.add(tfMarque, 3, 7);

        grid.add(lblType, 0, 8);
        grid.add(tfType, 1, 8);
        grid.add(lblCategorie, 2, 8);
        grid.add(tfCategorie, 3, 8);

        grid.add(lblNbPlaces, 0, 9);
        grid.add(tfNbPlaces, 1, 9);

        // Section Location
        grid.add(lblDepart, 0, 10);
        grid.add(dpDepart, 1, 10);
        grid.add(cbAgenceDepart, 2, 10);
        grid.add(tfKmDepart, 3, 10);

        grid.add(lblArrivee, 0, 11);
        grid.add(dpArrivee, 1, 11);
        grid.add(cbAgenceArrivee, 2, 11);
        grid.add(tfKmArrivee, 3, 11);

        // Section Calcul
        grid.add(lblMontant, 0, 12);
        grid.add(tfMontant, 1, 12);

        grid.add(lblForfaitAgence, 0, 13);
        grid.add(tfForfaitAgence, 1, 13);

        grid.add(lblForfaitJournalier, 0, 14);
        grid.add(tfForfaitJournalier, 1, 14);
        grid.add(lblJours, 2, 14);
        grid.add(tfJours, 3, 14);
        grid.add(lblTotalJournalier, 4, 14);
        grid.add(tfTotalJournalier, 5, 14);

        grid.add(lblPrixKm, 0, 15);
        grid.add(tfPrixKm, 1, 15);
        grid.add(lblKmParcourus, 2, 15);
        grid.add(tfKmParcourus, 3, 15);
        grid.add(lblTotalKm, 4, 15);
        grid.add(tfTotalKm, 5, 15);

        grid.add(lblTotale, 0, 16);
        grid.add(tfTotale, 1, 16);

        // Boutons
        HBox hbButtons = new HBox(10, btnCalculer, btnEnregistrer, btnImprimer, btnRetour);
        grid.add(hbButtons, 0, 17, 4, 1);

        // Configuration du BorderPane
        mainPane.setLeft(grid);
        mainPane.setRight(scrollPane);
        BorderPane.setMargin(scrollPane, new Insets(20, 20, 20, 0));
        
        

        // Charger les factures dans le tableau
        chargerFactures();

        Scene scene = new Scene(mainPane, 1200, 700);
        stage.setScene(scene);
        stage.setTitle("Gestion des Factures");
    }

    private void chargerFactures() {
        try {
            List<Facture> factures = new GestionFacture().obtenirToutesFactures();
            ObservableList<Facture> data = FXCollections.observableArrayList(factures);
            tableFactures.setItems(data);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur de chargement: " + e.getMessage());
        }
    }

    private void calculerMontant() {
        try {
            double forfaitJournalier = Double.parseDouble(tfForfaitJournalier.getText());
            double prixKm = Double.parseDouble(tfPrixKm.getText());

            long diff = dpArrivee.getValue().toEpochDay() - dpDepart.getValue().toEpochDay();
            if (diff < 0) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La date d'arrivée doit être après la date de départ.");
                return;
            }
            int jours = (int) diff;
            tfJours.setText(String.valueOf(jours));

            int kmDepart = Integer.parseInt(tfKmDepart.getText());
            int kmArrivee = Integer.parseInt(tfKmArrivee.getText());
            if (kmArrivee < kmDepart) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Le kilométrage d'arrivée doit être supérieur au kilométrage de départ.");
                return;
            }
            int kmParcourus = kmArrivee - kmDepart;
            tfKmParcourus.setText(String.valueOf(kmParcourus));

            double totalJournalier = jours * forfaitJournalier;
            double totalKm = kmParcourus * prixKm;
            double totale = totalJournalier + totalKm;

            tfTotalJournalier.setText(String.valueOf(totalJournalier));
            tfTotalKm.setText(String.valueOf(totalKm));
            tfTotale.setText(String.valueOf(totale));
            tfMontant.setText(String.valueOf(totale));

        } catch (NumberFormatException ex) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer des valeurs numériques valides.");
        }
    }

    private void enregistrerFacture() {
        try {
            Client client = cbClient.getSelectionModel().getSelectedItem();
            Voiture voiture = cbVoiture.getSelectionModel().getSelectedItem();

            if (client == null || voiture == null) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un client et une voiture.");
                return;
            }

            java.sql.Date dateFacture = java.sql.Date.valueOf(datePicker.getValue());
            java.sql.Date dateDepart = java.sql.Date.valueOf(dpDepart.getValue());
            java.sql.Date dateArrivee = java.sql.Date.valueOf(dpArrivee.getValue());
            int kmDepart = Integer.parseInt(tfKmDepart.getText());
            int kmArrivee = Integer.parseInt(tfKmArrivee.getText());
            double montantTotal = Double.parseDouble(tfTotale.getText());
            double forfaitJournalier = Double.parseDouble(tfForfaitJournalier.getText());
            double prixKm = Double.parseDouble(tfPrixKm.getText());

            Facture facture = new Facture(0, client, voiture, dateFacture,
                    dateDepart, dateArrivee, kmDepart, kmArrivee,
                    montantTotal, forfaitJournalier, prixKm);

            new GestionFacture().ajouterFacture(facture);
            chargerFactures();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Facture enregistrée avec succès.");
            clearFormulaire();

        } catch (NumberFormatException ex) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez calculer le montant avant d'enregistrer.");
        } catch (SQLException ex) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'enregistrement: " + ex.getMessage());
        }
    }

    private void imprimerFacture() {
        Facture selectedFacture = tableFactures.getSelectionModel().getSelectedItem();
        if (selectedFacture == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une facture à imprimer.");
            return;
        }

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");
        textArea.setText("FACTURE #" + selectedFacture.getIdFacture() + "\n" +
                         "Date de la facture: " + new SimpleDateFormat("dd/MM/yyyy").format(selectedFacture.getDateFacture()) + "\n" +
                         "Client: " + selectedFacture.getClient().getNom() + " " + selectedFacture.getClient().getPrenom() + "\n" +
                         "Adresse: " + selectedFacture.getClient().getAdresse() + "\n" +
                         "Téléphone: " + selectedFacture.getClient().getTelephone() + "\n" +
                         "Voiture: " + selectedFacture.getVoiture().getImmatriculation() + " (" + selectedFacture.getVoiture().getMarque() + " " + selectedFacture.getVoiture().getType() + ")\n" +
                         "Date de départ: " + new SimpleDateFormat("dd/MM/yyyy").format(selectedFacture.getDateDepart()) + "\n" +
                         "Date d'arrivée: " + new SimpleDateFormat("dd/MM/yyyy").format(selectedFacture.getDateArrivee()) + "\n" +
                         "KM Départ: " + selectedFacture.getKmDepart() + "\n" +
                         "KM Arrivée: " + selectedFacture.getKmArrivee() + "\n" +
                         "KM Parcourus: " + (selectedFacture.getKmArrivee() - selectedFacture.getKmDepart()) + "\n" +
                         "Forfait Journalier: " + selectedFacture.getForfaitJournalier() + " dh/jour\n" +
                         "Prix par KM: " + selectedFacture.getPrixKm() + " dh/km\n" +
                         "Montant Total: " + selectedFacture.getMontantTotal() + " dh");

        Button printButton = new Button("Imprimer");
        printButton.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        printButton.setOnMouseEntered(e -> printButton.setStyle("-fx-background-color: #138496; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        printButton.setOnMouseExited(e -> printButton.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        printButton.setOnAction(e -> {
            javafx.print.PrinterJob job = javafx.print.PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(null)) {
                boolean success = job.printPage(textArea);
                if (success) {
                    job.endJob();
                    afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Facture imprimée avec succès.");
                } else {
                    afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Échec de l'impression.");
                }
            }
        });

        VBox printLayout = new VBox(10, textArea, printButton);
        printLayout.setStyle("-fx-background-color: #ffffff; -fx-padding: 20;");
        Scene printScene = new Scene(printLayout, 600, 400);
        Stage printStage = new Stage();
        printStage.setTitle("Imprimer Facture");
        printStage.setScene(printScene);
        printStage.show();
    }

    private void clearFormulaire() {
        datePicker.setValue(LocalDate.now());
        cbClient.getSelectionModel().selectFirst();
        cbVoiture.getSelectionModel().selectFirst();
        dpDepart.setValue(LocalDate.now());
        dpArrivee.setValue(LocalDate.now().plusDays(1));
        tfKmDepart.clear();
        tfKmArrivee.clear();
        tfMontant.clear();
        tfForfaitAgence.setText("0");
        tfForfaitJournalier.setText("120");
        tfJours.clear();
        tfTotalJournalier.clear();
        tfPrixKm.setText("5");
        tfKmParcourus.clear();
        tfTotalKm.clear();
        tfTotale.clear();
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}