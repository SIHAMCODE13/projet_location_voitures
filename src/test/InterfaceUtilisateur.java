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

public class InterfaceUtilisateur {

    private TextField tfLogin;
    private PasswordField tfMotDePasse;
    private ComboBox<String> cbRole;
    private TableView<Utilisateur> tableUtilisateurs;
    private MainApp app;
    private Utilisateur utilisateurSelectionne;

    public void afficher(Stage stage, MainApp app) {
        this.app = app;

        // Titre
        Label lblTitre = new Label("Gestion des Utilisateurs");
        lblTitre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Formulaire
        Label lblLogin = new Label("Login");
        lblLogin.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfLogin = new TextField();
        tfLogin.setStyle("-fx-background-radius: 5px;");

        Label lblMotDePasse = new Label("Mot de Passe");
        lblMotDePasse.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        tfMotDePasse = new PasswordField();
        tfMotDePasse.setStyle("-fx-background-radius: 5px;");

        Label lblRole = new Label("Rôle");
        lblRole.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        cbRole = new ComboBox<>();
        cbRole.getItems().addAll("ADMIN", "USER");
        cbRole.setStyle("-fx-background-radius: 5px;");

        GridPane gridFormulaire = new GridPane();
        gridFormulaire.setVgap(10);
        gridFormulaire.setHgap(10);
        gridFormulaire.addRow(0, lblLogin, tfLogin);
        gridFormulaire.addRow(1, lblMotDePasse, tfMotDePasse);
        gridFormulaire.addRow(2, lblRole, cbRole);

        // Boutons d'action
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnAjouter.setOnAction(e -> ajouterUtilisateur());

        Button btnModifier = new Button("Modifier");
        btnModifier.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnModifier.setOnAction(e -> modifierUtilisateur());

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnSupprimer.setOnAction(e -> confirmerSuppression());

        Button btnVider = new Button("Vider");
        btnVider.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnVider.setOnAction(e -> reinitialiserFormulaire());

        Button btnRetour = new Button("Retour");
        btnRetour.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        btnRetour.setOnAction(e -> app.showDashboard(stage));

        HBox hbBoutons = new HBox(10, btnAjouter, btnModifier, btnSupprimer, btnVider, btnRetour);
        hbBoutons.setAlignment(Pos.CENTER);

        // Tableau des utilisateurs
        tableUtilisateurs = new TableView<>();
        tableUtilisateurs.setStyle("-fx-background-radius: 5px;");

        TableColumn<Utilisateur, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idUtilisateur"));

        TableColumn<Utilisateur, String> colLogin = new TableColumn<>("Login");
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<Utilisateur, String> colRole = new TableColumn<>("Rôle");
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableUtilisateurs.getColumns().addAll(colId, colLogin, colRole);
        tableUtilisateurs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Remplir le tableau avec les utilisateurs existants
        rafraichirTableau();

        // Sélection d'un utilisateur dans le tableau
        tableUtilisateurs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                utilisateurSelectionne = newSelection;
                tfLogin.setText(newSelection.getLogin());
                cbRole.setValue(newSelection.getRole());
                tfMotDePasse.clear();
            }
        });

        // Layout principal
        VBox vbFormulaire = new VBox(20, gridFormulaire, hbBoutons);
        vbFormulaire.setPadding(new Insets(20));
        vbFormulaire.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 10px;");

        VBox vbox = new VBox(20, lblTitre, vbFormulaire, tableUtilisateurs);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);

        // Fond dégradé
        Stop[] stops = new Stop[] {
            new Stop(0, Color.web("#388e3c")),   // Vert foncé
            new Stop(1, Color.web("#b0bec5"))    // Gris clair
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        Background background = new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY));
        vbox.setBackground(background);

        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Gestion des Utilisateurs");
    }

    private void rafraichirTableau() {
        try {
            GestionUtilisateur gestionUtilisateur = new GestionUtilisateur();
            ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
            utilisateurs.addAll(gestionUtilisateur.obtenirUtilisateursParRole("ADMIN"));
            utilisateurs.addAll(gestionUtilisateur.obtenirUtilisateursParRole("USER"));
            tableUtilisateurs.setItems(utilisateurs);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des utilisateurs: " + e.getMessage());
        }
    }

    private void ajouterUtilisateur() {
        String login = tfLogin.getText().trim();
        String motDePasse = tfMotDePasse.getText().trim();
        String role = cbRole.getValue();

        if (login.isEmpty() || motDePasse.isEmpty() || role == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        try {
            if (new GestionUtilisateur().loginExiste(login)) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Ce login existe déjà.");
                return;
            }

            Utilisateur utilisateur = new Utilisateur(0, login, motDePasse, role, 0);
            new GestionUtilisateur().ajouterUtilisateur(utilisateur);
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Utilisateur ajouté avec succès.");
            rafraichirTableau();
            reinitialiserFormulaire();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout: " + e.getMessage());
        }
    }

    private void modifierUtilisateur() {
        if (utilisateurSelectionne == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un utilisateur à modifier.");
            return;
        }

        String login = tfLogin.getText().trim();
        String motDePasse = tfMotDePasse.getText().trim();
        String role = cbRole.getValue();

        if (login.isEmpty() || role == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Login et rôle doivent être remplis.");
            return;
        }

        try {
            // Si le mot de passe n'est pas modifié, on conserve l'ancien
            if (motDePasse.isEmpty()) {
                motDePasse = utilisateurSelectionne.getMotDePasse();
            }

            Utilisateur utilisateur = new Utilisateur(
                utilisateurSelectionne.getIdUtilisateur(),
                login,
                motDePasse,
                role,
                utilisateurSelectionne.getIdClient()
            );

            new GestionUtilisateur().modifierUtilisateur(utilisateur);
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Utilisateur modifié avec succès.");
            rafraichirTableau();
            reinitialiserFormulaire();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification: " + e.getMessage());
        }
    }

    private void confirmerSuppression() {
        if (utilisateurSelectionne == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un utilisateur à supprimer.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Supprimer l'utilisateur");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'utilisateur " + utilisateurSelectionne.getLogin() + "?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                supprimerUtilisateur();
            }
        });
    }

    private void supprimerUtilisateur() {
        try {
            new GestionUtilisateur().supprimerUtilisateur(utilisateurSelectionne.getIdUtilisateur());
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Utilisateur supprimé avec succès.");
            rafraichirTableau();
            reinitialiserFormulaire();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression: " + e.getMessage());
        }
    }

    private void reinitialiserFormulaire() {
        tfLogin.clear();
        tfMotDePasse.clear();
        cbRole.getSelectionModel().clearSelection();
        utilisateurSelectionne = null;
        tableUtilisateurs.getSelectionModel().clearSelection();
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}