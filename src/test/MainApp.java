package test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.effect.DropShadow;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import java.sql.SQLException;
import java.util.Map;

public class MainApp extends Application {
private static ObservableList<Map<String, Object>> reservations = FXCollections.observableArrayList();
    
    public static ObservableList<Map<String, Object>> getReservations() {
        return reservations;
    }

    private Utilisateur utilisateurConnecte;
    private static final String FONT_STYLE = "-fx-font-family: 'Segoe UI';";
    private static final String BUTTON_STYLE = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #388E3C); " +
                                              "-fx-text-fill: #E0E0E0; -fx-font-size: 14px; -fx-padding: 10 20; " +
                                              "-fx-background-radius: 25; -fx-border-radius: 25; " + FONT_STYLE;
    private static final String BUTTON_HOVER_STYLE = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #2E7D32); " +
                                                    "-fx-scale-x: 1.05; -fx-scale-y: 1.05;";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Application de location des voitures");
        showLogin(primaryStage);
    }

    public void showLogin(Stage stage) {
        BorderPane root = new BorderPane();

        // Background image
        BackgroundImage myBI = new BackgroundImage(
                new Image("file:voiturre.jpg", 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        root.setBackground(new Background(myBI));

        // Titre animé "Portail de Gestion – Location Automobile"
        Label mainTitle = new Label("Portail de Gestion – Location Automobile");
        mainTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #E0E0E0; " + 
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 5, 0, 0, 1); " + FONT_STYLE);
        
        // Animation du titre
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), mainTitle);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(1000), mainTitle);
        scaleUp.setFromX(0.9);
        scaleUp.setFromY(0.9);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(500), mainTitle);
        scaleDown.setFromX(1.05);
        scaleDown.setFromY(1.05);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        
        SequentialTransition titleAnimation = new SequentialTransition(fadeIn, scaleUp, scaleDown);
        titleAnimation.setCycleCount(1);
        titleAnimation.play();
        
        // Conteneur pour le titre
        StackPane titleContainer = new StackPane(mainTitle);
        titleContainer.setPadding(new Insets(50, 0, 0, 0));
        root.setTop(titleContainer);

        // Semi-transparent login card
        VBox loginCard = new VBox(20);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(30));
        loginCard.setMaxWidth(350);
        loginCard.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.2); " +
                "-fx-background-radius: 20; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.6), 15, 0, 0, 5);"
        );

        Label lblTitle = new Label("Connexion");
        lblTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #E0E0E0; " + FONT_STYLE);

        Label lblNom = new Label("Nom d'utilisateur");
        lblNom.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 14px; " + FONT_STYLE);
        TextField tfNom = new TextField();
        tfNom.setPromptText("Entrez votre login");
        tfNom.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                       "-fx-background-radius: 5; -fx-padding: 10; " + FONT_STYLE);

        Label lblPass = new Label("Mot de passe");
        lblPass.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 14px; " + FONT_STYLE);
        PasswordField tfPass = new PasswordField();
        tfPass.setPromptText("Entrez votre mot de passe");
        tfPass.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                        "-fx-background-radius: 5; -fx-padding: 10; " + FONT_STYLE);

        Button btnEnter = new Button("Connexion");
        btnEnter.setStyle(BUTTON_STYLE);
        btnEnter.setOnMouseEntered(e -> btnEnter.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnEnter.setOnMouseExited(e -> btnEnter.setStyle(BUTTON_STYLE));

        loginCard.getChildren().addAll(lblTitle, lblNom, tfNom, lblPass, tfPass, btnEnter);

        // Animation
        FadeTransition fade = new FadeTransition(Duration.millis(1000), loginCard);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        root.setCenter(loginCard);

        // Scene setup
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);

        // Login action
        btnEnter.setOnAction(e -> {
            String login = tfNom.getText().trim();
            String password = tfPass.getText().trim();
            if (login.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs");
                return;
            }
            GestionUtilisateur gu = new GestionUtilisateur();
            try {
                Utilisateur u = gu.authentifier(login, password);
                if (u != null) {
                    utilisateurConnecte = u;
                    showDashboard(stage);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Identifiants incorrects");
                }
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur de base de données : " + ex.getMessage());
            }
        });

        stage.setOnCloseRequest(e -> DatabaseConnection.closeConnection());
        stage.show();
    }

    public void showDashboard(Stage stage) {
        BorderPane root = new BorderPane();

        // Dégradé vert clair → vert forêt
        Background background = new Background(new BackgroundFill(
            new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#e8f5e9")),   // Vert très clair
                new Stop(1, Color.web("#2e7d32"))),    // Vert forêt profond
            CornerRadii.EMPTY,
            Insets.EMPTY
        ));
        root.setBackground(background);

        // Sidebar à gauche
        VBox sidebar = new VBox(20);
        sidebar.setStyle("-fx-background-color: rgba(34, 85, 50, 0.95); -fx-padding: 20; -fx-min-width: 230px; " +
                         "-fx-background-radius: 0 20 20 0;");
        sidebar.setEffect(new DropShadow(12, Color.rgb(0, 0, 0, 0.3)));

        Label lblBienvenue = new Label("Bienvenue, " + utilisateurConnecte.getLogin());
        lblBienvenue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #E0E0E0; " + FONT_STYLE);

        Button btnVehicules = createSidebarButton("Gérer les véhicules");
        Button btnClients = createSidebarButton("Gérer les clients");
        Button btnFactures = createSidebarButton("Facturation");
        Button btnContrats = createSidebarButton("Gérer les contrats");
        Button btnUtilisateurs = createSidebarButton("Gérer les utilisateurs");

        sidebar.getChildren().clear();
        sidebar.getChildren().add(lblBienvenue);

        if ("CLIENT".equals(utilisateurConnecte.getRole())) {
            btnVehicules.setText("Véhicules disponibles");
            btnFactures.setText("historique de mes factures");
            btnContrats.setText("historique de mes réservations");

            sidebar.getChildren().addAll(
                btnVehicules,
                btnContrats,
                btnFactures
            );
        } else {
            sidebar.getChildren().addAll(
                btnVehicules,
                btnClients,
                btnContrats,
                btnFactures
               
            );

            if (!"USER".equals(utilisateurConnecte.getRole())) {
                sidebar.getChildren().add(btnUtilisateurs);
            }
        }

        // Zone centrale avec un design moderne
        VBox contentArea = new VBox(20);
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setPadding(new Insets(30));
        contentArea.setStyle("-fx-background-color: rgba(255,255,255,0.25); -fx-background-radius: 20;");

        Label lblContent = new Label("Sélectionnez une option dans la barre latérale");
        lblContent.setStyle("-fx-font-size: 18px; -fx-text-fill: #1B5E20; -fx-font-weight: bold; " + FONT_STYLE);

        Button btnBackToLogin = new Button("Déconnexion");
        btnBackToLogin.setStyle(BUTTON_STYLE);
        btnBackToLogin.setOnMouseEntered(e -> btnBackToLogin.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnBackToLogin.setOnMouseExited(e -> btnBackToLogin.setStyle(BUTTON_STYLE));
        btnBackToLogin.setOnAction(e -> showLogin(stage));

        contentArea.getChildren().addAll(lblContent, btnBackToLogin);

        // Animations dynamiques
        TranslateTransition slide = new TranslateTransition(Duration.millis(600), sidebar);
        slide.setFromX(-250);
        slide.setToX(0);
        slide.play();

        FadeTransition fade = new FadeTransition(Duration.millis(800), contentArea);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        root.setLeft(sidebar);
        root.setCenter(contentArea);

        // Actions des boutons
        btnVehicules.setOnAction(e -> {
            if ("CLIENT".equals(utilisateurConnecte.getRole())) {
                new InterfaceClientVoiture().afficher(stage, this);
            } else {
                new InterfaceVoiture().afficher(stage, this);
            }
        });

        btnClients.setOnAction(e -> new InterfaceClient().afficher(stage, this));
        btnFactures.setOnAction(e -> {
            if ("CLIENT".equals(utilisateurConnecte.getRole())) {
                new InterfaceClientFacture().afficher(stage, this);
            } else {
                new InterfaceFacture().afficher(stage, this);
            }
        });

        btnContrats.setOnAction(e -> {
            if ("CLIENT".equals(utilisateurConnecte.getRole())) {
                new InterfaceClientContrat().afficher(stage, this);
            } else {
                new InterfaceContrat().afficher(stage, this);
            }
        });

        btnUtilisateurs.setOnAction(e -> new InterfaceUtilisateur().afficher(stage, this));

        // Scène finale
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Tableau de bord - " + utilisateurConnecte.getRole());
    }

    private Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.setStyle(BUTTON_STYLE + "-fx-pref-width: 200px; -fx-pref-height: 45px;");
        button.setOnMouseEntered(e -> button.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        button.setOnMouseExited(e -> button.setStyle(BUTTON_STYLE));
        return button;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.getDialogPane().setStyle("-fx-background-color: #225532; -fx-text-fill: #E0E0E0; " + FONT_STYLE);
        alert.showAndWait();
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
}