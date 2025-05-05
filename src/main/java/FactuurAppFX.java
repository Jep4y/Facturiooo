import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FactuurAppFX extends Application {

    private FactuurAplicatie factuurApp = new FactuurAplicatie();
    private KlantDatabase klantDatabase = new KlantDatabase();  // Zorg ervoor dat de database wordt geladen

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Facturio - Facturatie");

        //nieuwen klant toevoege
        Label newClientLabel = new Label("Nieuwe Klant");
        TextField nameField = new TextField();
        nameField.setPromptText("Naam");
        TextField addressField = new TextField();
        addressField.setPromptText("Adres");
        TextField emailField = new TextField();
        emailField.setPromptText("E-mail");
        Button addClientButton = new Button("Opslaan");
        addClientButton.setPrefSize(200, 40);

        VBox newClientBox = new VBox(10, newClientLabel, nameField, addressField, emailField, addClientButton);
        newClientBox.setPadding(new Insets(20));
        newClientBox.setStyle("-fx-border-color: #333; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        // factuur maken 
        Label invoiceLabel = new Label("Factuur");
        ComboBox<Klant> clientComboBox = new ComboBox<>();
        clientComboBox.setPromptText("Selecteer Klant");
        clientComboBox.setPrefWidth(200);
        TextField amountField = new TextField();
        amountField.setPromptText("Bedrag (€)");
        Button sendButton = new Button("Verzend Factuur");
        sendButton.setPrefSize(200, 40);
        Label statusLabel = new Label();

        VBox invoiceBox = new VBox(10, invoiceLabel, clientComboBox, amountField, sendButton, statusLabel);
        invoiceBox.setPadding(new Insets(20));
        invoiceBox.setStyle("-fx-border-color: #333; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        //  hoofdlayout
        HBox root = new HBox(20, newClientBox, invoiceBox);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #ffffff;");

        // Laad klanten in de ComboBox (omzetten naar een list)
        List<Klant> klantenLijst = new ArrayList<>(klantDatabase.getKlanten().values());
        clientComboBox.getItems().addAll(klantenLijst);

        // klant Opslaan
        addClientButton.setOnAction(e -> {
            String naam = nameField.getText().trim();
            String adres = addressField.getText().trim();
            String email = emailField.getText().trim();
            Klant klant = new Klant(naam, adres, email);
            if (!klant.isGeldig()) {
                statusLabel.setText("Ongeldige klantgegevens.");
                return;
            }
            factuurApp.voegKlantToe(klant);
            klantDatabase.voegKlantToe(klant); // Voeg klant toe aan database
            clientComboBox.getItems().add(klant);  // Voeg klant toe aan combobox
            statusLabel.setText("Klant toegevoegd: " + naam);
            nameField.clear(); addressField.clear(); emailField.clear();
        });

        // factuur verzende
        sendButton.setOnAction(e -> {
            Klant geselecteerde = clientComboBox.getValue();
            if (geselecteerde == null) {
                statusLabel.setText("Selecteer eerst een klant.");
                return;
            }
            try {
                double bedrag = Double.parseDouble(amountField.getText().trim());
                Factuur factuur = factuurApp.maakNieuweFactuur(geselecteerde.getEmail(), bedrag);
                if (factuur == null) {
                    statusLabel.setText("Factuur maken mislukt.");
                    return;
                }
                factuurApp.verwerkFactuur(factuur);
                statusLabel.setText("Factuur " + factuur.getFactuurNummer() + " verzonden!");
                amountField.clear();
            } catch (NumberFormatException ex) {
                statusLabel.setText("Ongeldig bedrag invoeren.");
            }
        });

        Scene scene = new Scene(root, 600, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
