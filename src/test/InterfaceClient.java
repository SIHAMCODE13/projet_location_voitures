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

public class InterfaceClient {

    private TableView<Client> tableClients;
    private TextField tfRecherche;
    private TextField tfLogin;
    private PasswordField tfMotDePasse;
    private TextField tfNom;
    private TextField tfPrenom;
    private TextField tfAdresse;
    private TextField tfTel;
    private MainApp app;
    private String currentPassword = "";

    public void afficher(Stage stage, MainApp app) {
        this.app = app;

        // Titre
        Label lblTitre = new Label("Gestion des Clients");
        lblTitre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Création du tableau
        tableClients = new TableView<>();
        tableClients.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d1d1d1; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        TableColumn<Client, String> colNom = new TableColumn<>("Nom");
        TableColumn<Client, String> colPrenom = new TableColumn<>("Prénom");
        TableColumn<Client, String> colAdresse = new TableColumn<>("Adresse");
        TableColumn<Client, String> colTel = new TableColumn<>("Téléphone");
        TableColumn<Client, String> colLogin = new TableColumn<>("Login");

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

        tableClients.getColumns().addAll(colNom, colPrenom, colAdresse, colTel, colLogin);
        tableClients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Zone de recherche
        Label lblRecherche = new Label("Rechercher par Nom");
        lblRecherche.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfRecherche = new TextField();
        tfRecherche.setPromptText("Saisir le nom...");
        tfRecherche.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #d1d1d1;");

        Button btnRechercher = new Button("Rechercher");
        btnRechercher.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnRechercher.setOnMouseEntered(e -> btnRechercher.setStyle("-fx-background-color: #357abd; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRechercher.setOnMouseExited(e -> btnRechercher.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRechercher.setOnAction(e -> rechercherClients());

        HBox hbRecherche = new HBox(10, lblRecherche, tfRecherche, btnRechercher);
        hbRecherche.setAlignment(Pos.CENTER_LEFT);

        // Formulaire à gauche
        Label lblLogin = new Label("Login");
        lblLogin.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfLogin = new TextField();
        tfLogin.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblMotDePasse = new Label("Mot de Passe");
        lblMotDePasse.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfMotDePasse = new PasswordField();
        tfMotDePasse.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblNom = new Label("Nom");
        lblNom.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfNom = new TextField();
        tfNom.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblPrenom = new Label("Prénom");
        lblPrenom.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfPrenom = new TextField();
        tfPrenom.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblAdresse = new Label("Adresse");
        lblAdresse.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfAdresse = new TextField();
        tfAdresse.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        Label lblTel = new Label("Téléphone");
        lblTel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfTel = new TextField();
        tfTel.setStyle("-fx-background-radius: 5px; -fx-border-radius: 5px;");

        GridPane gridFormulaire = new GridPane();
        gridFormulaire.setVgap(10);
        gridFormulaire.setHgap(10);
        gridFormulaire.addRow(0, lblLogin, tfLogin);
        gridFormulaire.addRow(1, lblMotDePasse, tfMotDePasse);
        gridFormulaire.addRow(2, lblNom, tfNom);
        gridFormulaire.addRow(3, lblPrenom, tfPrenom);
        gridFormulaire.addRow(4, lblAdresse, tfAdresse);
        gridFormulaire.addRow(5, lblTel, tfTel);

        VBox vbFormulaire = new VBox(20, gridFormulaire);
        vbFormulaire.setPadding(new Insets(20));
        vbFormulaire.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 10px;");

        // Gestion de la sélection
        tableClients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tfLogin.setText(newSelection.getLogin());
                tfMotDePasse.setText(newSelection.getMotDePasse());
                tfNom.setText(newSelection.getNom());
                tfPrenom.setText(newSelection.getPrenom());
                tfAdresse.setText(newSelection.getAdresse());
                tfTel.setText(newSelection.getTelephone());
            }
        });

        // Boutons d'action
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnAjouter.setOnMouseEntered(e -> btnAjouter.setStyle("-fx-background-color: #218838; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnAjouter.setOnMouseExited(e -> btnAjouter.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnAjouter.setOnAction(e -> ajouterClient());

        Button btnModifier = new Button("Modifier");
        btnModifier.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnModifier.setOnMouseEntered(e -> btnModifier.setStyle("-fx-background-color: #357abd; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnModifier.setOnMouseExited(e -> btnModifier.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnModifier.setOnAction(e -> modifierClient());

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnSupprimer.setOnMouseEntered(e -> btnSupprimer.setStyle("-fx-background-color: #c82333; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnSupprimer.setOnMouseExited(e -> btnSupprimer.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnSupprimer.setOnAction(e -> supprimerClient());
        btnSupprimer.setDisable(!"ADMIN".equals(app.getUtilisateurConnecte().getRole()));

        Button btnRetour = new Button("Retour");
        btnRetour.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnRetour.setOnMouseEntered(e -> btnRetour.setStyle("-fx-background-color: #5a6268; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRetour.setOnMouseExited(e -> btnRetour.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnRetour.setOnAction(e -> app.showDashboard(stage));
        
        Button btnVider = new Button("Vider");
        btnVider.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnVider.setOnMouseEntered(e -> btnVider.setStyle("-fx-background-color: #e0a800; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnVider.setOnMouseExited(e -> btnVider.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 5px;"));
        btnVider.setOnAction(e -> clearFormulaire());


        HBox hbButtons = new HBox(10, btnAjouter, btnModifier, btnSupprimer, btnVider, btnRetour);


        hbButtons.setAlignment(Pos.CENTER);

        // Disposition : formulaire à gauche, tableau à droite
        VBox vbTableau = new VBox(10, hbRecherche, tableClients);
        vbTableau.setPadding(new Insets(20));
        vbTableau.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 10px;");

        HBox hbMain = new HBox(20, vbFormulaire, vbTableau);
        hbMain.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20, lblTitre, hbMain, hbButtons);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);

    //**********
        BackgroundImage bgImage = new BackgroundImage(
            new javafx.scene.image.Image(getClass().getResource("client.jpeg").toExternalForm(), 1000, 700, false, true),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(bgImage));
//****************

        chargerClients();

        Scene scene = new Scene(vbox, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Gestion des Clients");
    }

    private void chargerClients() {
        try {
            List<Client> clients = new GestionClient().obtenirTousClients();
            ObservableList<Client> data = FXCollections.observableArrayList(clients);
            tableClients.setItems(data);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur de chargement: " + e.getMessage());
        }
    }

    private void ajouterClient() {
        try {
            String nom = tfNom.getText().trim();
            String prenom = tfPrenom.getText().trim();
            String adresse = tfAdresse.getText().trim();
            String telephone = tfTel.getText().trim();
            String login = tfLogin.getText().trim();
            String motDePasse = tfMotDePasse.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || 
                login.isEmpty() || motDePasse.isEmpty()) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            // Vérification du numéro de téléphone existant
            if (new GestionClient().telephoneExiste(telephone)) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Un client avec ce numéro de téléphone existe déjà.");
                return;
            }



            Client client = new Client(0, nom, prenom, adresse, telephone);
            new GestionClient().ajouterClient(client, login, motDePasse);
            chargerClients();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Client ajouté avec succès.");
            clearFormulaire();

        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout: " + e.getMessage());
        }
    }

  
    	// Écouteur de sélection pour afficher les données du client sélectionné
    	private void setupTableSelectionListener() {
    	    tableClients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    	        if (newSelection != null) {
    	            // Afficher les données normales
    	            tfLogin.setText(newSelection.getLogin() != null ? newSelection.getLogin() : "");
    	            tfNom.setText(newSelection.getNom() != null ? newSelection.getNom() : "");
    	            tfPrenom.setText(newSelection.getPrenom() != null ? newSelection.getPrenom() : "");
    	            tfAdresse.setText(newSelection.getAdresse() != null ? newSelection.getAdresse() : "");
    	            tfTel.setText(newSelection.getTelephone() != null ? newSelection.getTelephone() : "");
    	            
    	            // Gestion spéciale du mot de passe
    	            String motDePasse = newSelection.getMotDePasse();
    	            if (motDePasse != null && !motDePasse.isEmpty()) {
    	                // Afficher seulement les 2 premiers caractères + masque
    	                String masque = motDePasse.length() > 2 
    	                    ? motDePasse.substring(0, 2) + "*****" 
    	                    : "*****";
    	                tfMotDePasse.setText(masque);
    	                
    	                // Stocker le vrai mot de passe pour les modifications
    	                currentPassword = motDePasse;
    	            } else {
    	                tfMotDePasse.setText("");
    	                currentPassword = "";
    	            }
    	        }
    	    });
    	}

    	// Méthode pour modifier un client
    	private void modifierClient() {
    	    Client selectedClient = tableClients.getSelectionModel().getSelectedItem();
    	    if (selectedClient == null) {
    	        afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un client à modifier.");
    	        return;
    	    }

    	    try {
    	        // Récupérer les valeurs des champs
    	        String login = tfLogin.getText() != null ? tfLogin.getText().trim() : "";
    	        String motDePasse = tfMotDePasse.getText() != null ? tfMotDePasse.getText().trim() : "";
    	        String nom = tfNom.getText() != null ? tfNom.getText().trim() : "";
    	        String prenom = tfPrenom.getText() != null ? tfPrenom.getText().trim() : "";
    	        String adresse = tfAdresse.getText() != null ? tfAdresse.getText().trim() : "";
    	        String telephone = tfTel.getText() != null ? tfTel.getText().trim() : "";

    	        // Validation des champs obligatoires
    	        if (login.isEmpty() || nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || telephone.isEmpty()) {
    	            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis (sauf mot de passe).");
    	            return;
    	        }

    	        // Vérifier si le mot de passe a été modifié
    	        boolean motDePasseModifie = !motDePasse.equals(currentPassword) && 
    	                                  !motDePasse.contains("*****");

    	        // Vérifier l'unicité du téléphone
    	        if (new GestionClient().telephoneExistePourAutreClient(telephone, selectedClient.getIdClient())) {
    	            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Ce numéro de téléphone est déjà utilisé par un autre client.");
    	            return;
    	        }

    	        // Mettre à jour le client
    	        selectedClient.setLogin(login);
    	        selectedClient.setNom(nom);
    	        selectedClient.setPrenom(prenom);
    	        selectedClient.setAdresse(adresse);
    	        selectedClient.setTelephone(telephone);
    	        
    	        // Ne mettre à jour le mot de passe que s'il a été modifié
    	        if (motDePasseModifie) {
    	            selectedClient.setMotDePasse(motDePasse);
    	        }

    	        // Appeler la méthode de modification
    	        new GestionClient().modifierClient(selectedClient, motDePasseModifie);
    	        chargerClients();
    	        afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Client modifié avec succès.");

    	    } catch (SQLException e) {
    	        afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification: " + e.getMessage());
    	    }
    	}

    private void rechercherClients() {
        String nom = tfRecherche.getText().trim();
        try {
            List<Client> clients = new GestionClient().obtenirTousClients()
                    .stream()
                    .filter(c -> c.getNom().toLowerCase().contains(nom.toLowerCase()))
                    .toList();
            ObservableList<Client> data = FXCollections.observableArrayList(clients);
            tableClients.setItems(data);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur de recherche: " + e.getMessage());
        }
    }

  
    private void supprimerClient() {
        Client selectedClient = tableClients.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un client à supprimer.");
            return;
        }

        if (!"ADMIN".equals(app.getUtilisateurConnecte().getRole())) {
            afficherAlerte(Alert.AlertType.ERROR, "Accès refusé", "Seul un administrateur peut supprimer des clients.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Supprimer le client");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer le client " 
                + selectedClient.getNom() + " " + selectedClient.getPrenom() + " ? Cette action est irréversible.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                new GestionClient().supprimerClient(selectedClient.getIdClient());
                chargerClients();
                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Client supprimé avec succès.");
                clearFormulaire();
            } catch (SQLException e) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression: " + e.getMessage());
            }
        } else {
            afficherAlerte(Alert.AlertType.INFORMATION, "Annulé", "La suppression a été annulée.");
        }
    }

    private void clearFormulaire() {
        tfLogin.clear();
        tfMotDePasse.clear();
        tfNom.clear();
        tfPrenom.clear();
        tfAdresse.clear();
        tfTel.clear();
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

}