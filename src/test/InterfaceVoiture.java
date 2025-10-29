package test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.util.List;
import java.util.Optional;

public class InterfaceVoiture {

    private TableView<Voiture> tableVoitures;
    private TextField tfRecherche;
    private TextField tfCode;
    private TextField tfImmat;
    private TextField tfMarque;
    private TextField tfType;
    private TextField tfCategorie;
    private TextField tfNbPlaces;
    private CheckBox cbDisponible;
    private MainApp app;

    public void afficher(Stage stage, MainApp app) {
        this.app = app;

        // Titre
        Label lblTitre = new Label("Gestion des Véhicules");
        lblTitre.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
      



        // Création du tableau
        tableVoitures = new TableView<>();
        tableVoitures.setStyle("-fx-background-color: #ff7fff; -fx-border-color: #d1d1d1; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        tableVoitures.setStyle("-fx-text: black;");

        TableColumn<Voiture, String> colImmat = new TableColumn<>("Immatriculation");
        TableColumn<Voiture, String> colMarque = new TableColumn<>("Marque");
        TableColumn<Voiture, String> colType = new TableColumn<>("Type");
        TableColumn<Voiture, String> colCategorie = new TableColumn<>("Catégorie");
        TableColumn<Voiture, Integer> colNbPlaces = new TableColumn<>("Nb Places");
        TableColumn<Voiture, Boolean> colDisponible = new TableColumn<>("Disponible");
        

        colImmat.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colNbPlaces.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        tableVoitures.getColumns().addAll(colImmat, colMarque, colType, colCategorie, colNbPlaces, colDisponible);
        tableVoitures.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Zone de recherche
        Label lblRecherche = new Label("Rechercher par Immatriculation");
        lblRecherche.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfRecherche = new TextField();
        tfRecherche.setPromptText("Saisir l'immatriculation...");
        tfRecherche.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");
        
        Button btnRechercher = new Button("Rechercher");
        btnRechercher.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnRechercher.setOnMouseEntered(e -> btnRechercher.setStyle("-fx-background-color: #357abd; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRechercher.setOnMouseExited(e -> btnRechercher.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRechercher.setOnAction(e -> rechercherVoitures());

        HBox hbRecherche = new HBox(10, lblRecherche, tfRecherche, btnRechercher);
        hbRecherche.setAlignment(Pos.CENTER_LEFT);

        // Formulaire à gauche
        Label lblCode = new Label("Code Véhicule");
        lblCode.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfCode = new TextField();
        tfCode.setEditable(false);
        tfCode.setText("1");
        tfCode.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblImmat = new Label("Immatriculation");
        lblImmat.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfImmat = new TextField();
        tfImmat.setText("mmgf");
        tfImmat.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblMarque = new Label("Marque");
        lblMarque.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfMarque = new TextField();
        tfMarque.setText("Renault");
        tfMarque.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblType = new Label("Type");
        lblType.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfType = new TextField();
        tfType.setText("Megane");
        tfType.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblCategorie = new Label("Catégorie");
        lblCategorie.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfCategorie = new TextField();
        tfCategorie.setText("1");
        tfCategorie.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblNbPlaces = new Label("Nombre de Places");
        lblNbPlaces.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfNbPlaces = new TextField();
        tfNbPlaces.setText("4");
        tfNbPlaces.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblDisponible = new Label("Disponible");
        lblDisponible.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        cbDisponible = new CheckBox();
        cbDisponible.setSelected(true);
        cbDisponible.setStyle("-fx-text-fill: #ffffff;");

        GridPane gridFormulaire = new GridPane();
        gridFormulaire.setVgap(10);
        gridFormulaire.setHgap(10);
        gridFormulaire.addRow(0, lblCode, tfCode);
        gridFormulaire.addRow(1, lblImmat, tfImmat);
        gridFormulaire.addRow(2, lblMarque, tfMarque);
        gridFormulaire.addRow(3, lblType, tfType);
        gridFormulaire.addRow(4, lblCategorie, tfCategorie);
        gridFormulaire.addRow(5, lblNbPlaces, tfNbPlaces);
        gridFormulaire.addRow(6, lblDisponible, cbDisponible);

        VBox vbFormulaire = new VBox(20, gridFormulaire);
        vbFormulaire.setPadding(new Insets(20));
        vbFormulaire.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 10px;");

        // Gestion de la sélection
        tableVoitures.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tfCode.setText(String.valueOf(newSelection.getIdVoiture()));
                tfImmat.setText(newSelection.getImmatriculation());
                tfMarque.setText(newSelection.getMarque());
                tfType.setText(newSelection.getType());
                tfCategorie.setText(newSelection.getCategorie());
                tfNbPlaces.setText(String.valueOf(newSelection.getNbPlaces()));
                cbDisponible.setSelected(newSelection.isDisponible());
            }
        });

        // Boutons d'action
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnAjouter.setOnMouseEntered(e -> btnAjouter.setStyle("-fx-background-color: #218838; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnAjouter.setOnMouseExited(e -> btnAjouter.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnAjouter.setOnAction(e -> ajouterVoiture());

        Button btnModifier = new Button("Modifier");
        btnModifier.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnModifier.setOnMouseEntered(e -> btnModifier.setStyle("-fx-background-color: #357abd; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnModifier.setOnMouseExited(e -> btnModifier.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnModifier.setOnAction(e -> modifierVoiture());

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnSupprimer.setOnMouseEntered(e -> btnSupprimer.setStyle("-fx-background-color: #c82333; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnSupprimer.setOnMouseExited(e -> btnSupprimer.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnSupprimer.setOnAction(e -> supprimerVoiture());
        btnSupprimer.setDisable(!"ADMIN".equals(app.getUtilisateurConnecte().getRole()));

        Button btnRetour = new Button("Retour");
        btnRetour.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnRetour.setOnMouseEntered(e -> btnRetour.setStyle("-fx-background-color: #5a6268; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRetour.setOnMouseExited(e -> btnRetour.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRetour.setOnAction(e -> app.showDashboard(stage));

        HBox hbButtons = new HBox(10, btnAjouter, btnModifier, btnSupprimer, btnRetour);
        hbButtons.setAlignment(Pos.CENTER);

        // Disposition : formulaire à gauche, tableau à droite
        VBox vbTableau = new VBox(10, hbRecherche, tableVoitures);
        vbTableau.setPadding(new Insets(20));
        vbTableau.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 10px;");

        HBox hbMain = new HBox(20, vbFormulaire, vbTableau);
        hbMain.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20, lblTitre, hbMain, hbButtons);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);

  //******************
        BackgroundImage bgImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("resources/voiturre.jpg").toExternalForm(), 1000, 700, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
            );
            vbox.setBackground(new Background(bgImage));
            //***********************

        chargerVoitures();

        Scene scene = new Scene(vbox, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Gestion des Véhicules");
    }

    private void chargerVoitures() {
        try {
            List<Voiture> voitures = new GestionVoiture().obtenirToutesVoitures();
            ObservableList<Voiture> data = FXCollections.observableArrayList(voitures);
            tableVoitures.setItems(data);
            
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur de chargement: " + e.getMessage());
        }
    }

    private void rechercherVoitures() {
        String immat = tfRecherche.getText().trim();
        try {
            List<Voiture> voitures = new GestionVoiture().obtenirToutesVoitures()
                    .stream()
                    .filter(v -> v.getImmatriculation().toLowerCase().contains(immat.toLowerCase()))
                    .toList();
            ObservableList<Voiture> data = FXCollections.observableArrayList(voitures);
            tableVoitures.setItems(data);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur de recherche: " + e.getMessage());
        }
    }

    private void ajouterVoiture() {
        try {
            String immatriculation = tfImmat.getText().trim();
            String marque = tfMarque.getText().trim();
            String type = tfType.getText().trim();
            String categorie = tfCategorie.getText().trim();
            int nbPlaces = Integer.parseInt(tfNbPlaces.getText().trim());
            boolean disponible = cbDisponible.isSelected();

            if (immatriculation.isEmpty() || marque.isEmpty() || type.isEmpty() || categorie.isEmpty()) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            // Vérifier si l'immatriculation existe déjà
            List<Voiture> voituresExistantes = new GestionVoiture().obtenirToutesVoitures();
            boolean immatExiste = voituresExistantes.stream()
                    .anyMatch(v -> v.getImmatriculation().equalsIgnoreCase(immatriculation));
            if (immatExiste) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une voiture avec cette immatriculation existe déjà.");
                return;
            }

            Voiture voiture = new Voiture(0, immatriculation, marque, type, categorie, nbPlaces, disponible);
            new GestionVoiture().ajouterVoiture(voiture);

            chargerVoitures();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Voiture ajoutée avec succès.");
            clearFormulaire();

        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Le nombre de places doit être un nombre valide.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout: " + e.getMessage());
        }
    }

    private void modifierVoiture() {
        Voiture selectedVoiture = tableVoitures.getSelectionModel().getSelectedItem();
        if (selectedVoiture == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une voiture à modifier.");
            return;
        }

        try {
            String immatriculation = tfImmat.getText().trim();
            String marque = tfMarque.getText().trim();
            String type = tfType.getText().trim();
            String categorie = tfCategorie.getText().trim();
            int nbPlaces = Integer.parseInt(tfNbPlaces.getText().trim());
            boolean disponible = cbDisponible.isSelected();

            if (immatriculation.isEmpty() || marque.isEmpty() || type.isEmpty() || categorie.isEmpty()) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            // Vérifier si l'immatriculation existe déjà (sauf pour la voiture actuellement modifiée)
            List<Voiture> voituresExistantes = new GestionVoiture().obtenirToutesVoitures();
            boolean immatExiste = voituresExistantes.stream()
                    .anyMatch(v -> v.getImmatriculation().equalsIgnoreCase(immatriculation) && v.getIdVoiture() != selectedVoiture.getIdVoiture());
            if (immatExiste) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une autre voiture avec cette immatriculation existe déjà.");
                return;
            }

            selectedVoiture.setImmatriculation(immatriculation);
            selectedVoiture.setMarque(marque);
            selectedVoiture.setType(type);
            selectedVoiture.setCategorie(categorie);
            selectedVoiture.setNbPlaces(nbPlaces);
            selectedVoiture.setDisponible(disponible);

            new GestionVoiture().modifierVoiture(selectedVoiture);

            chargerVoitures();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Voiture modifiée avec succès.");

        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Le nombre de places doit être un nombre valide.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification: " + e.getMessage());
        }
    }

    private void supprimerVoiture() {
        Voiture selectedVoiture = tableVoitures.getSelectionModel().getSelectedItem();
        if (selectedVoiture == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une voiture à supprimer.");
            return;
        }

        if (!"ADMIN".equals(app.getUtilisateurConnecte().getRole())) {
            afficherAlerte(Alert.AlertType.ERROR, "Accès refusé", "Seul un administrateur peut supprimer des voitures.");
            return;
        }

        try {
            // Vérifier si la voiture est utilisée dans un contrat
            GestionVoiture gestionVoiture = new GestionVoiture();
            if (gestionVoiture.voitureEstUtiliseeDansContrat(selectedVoiture.getIdVoiture())) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer cette voiture car elle est utilisée dans un ou plusieurs contrats.");
                return;
            }

            // Si aucune dépendance, demander confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Supprimer la voiture");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer la voiture avec l'immatriculation " 
                    + selectedVoiture.getImmatriculation() + " ? Cette action est irréversible.");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                gestionVoiture.supprimerVoiture(selectedVoiture.getIdVoiture());
                chargerVoitures();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Voiture supprimée avec succès.");
                clearFormulaire();
            } else {
                afficherAlerte(Alert.AlertType.INFORMATION, "Annulé", "La suppression a été annulée.");
            }
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression: " + e.getMessage());
        }
    }

    private void clearFormulaire() {
        tfCode.clear();
        tfImmat.clear();
        tfMarque.clear();
        tfType.clear();
        tfCategorie.clear();
        tfNbPlaces.clear();
        cbDisponible.setSelected(true);
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}