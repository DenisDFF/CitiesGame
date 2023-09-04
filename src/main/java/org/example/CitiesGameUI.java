package org.example;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CitiesGameUI extends Application {

    private Main citiesGame;

    public void setCitiesGame(Main citiesGame) {
        this.citiesGame = citiesGame;
    }

    private TextField userInput;
    private Button submitButton;
    private Label messageLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Гра \"Міста\"");

        userInput = new TextField();
        userInput.setPromptText("Введіть назву міста");

        submitButton = new Button("Зробити хід");
        submitButton.setOnAction(e -> handleSubmit());

        messageLabel = new Label("Добро пожаловать в игру \"Міста\"!");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(userInput, submitButton, messageLabel);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void handleSubmit() {
        String userCity = userInput.getText().trim();
    }
}
