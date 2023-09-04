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
        primaryStage.setTitle("Города");

        Label label = new Label("Введите название города:");
        Label ruleLabel = new Label();
        TextField cityInput = new TextField();
        Button enterButton = new Button("Ввести");
        Button rulesButton = new Button("Правила");
        Button restartButton = new Button("Заново");

        enterButton.setOnAction(e -> {
            String cityName = cityInput.getText();
            //тут треба додати логіку обробки міста
        });

        rulesButton.setOnAction(e -> {
            //тут правила
            ruleLabel.setText("Правила\n1. Щоб закінчити гру введіть 'здаюсь'.\n2. Ви переможете, якщо комп'ютер не матиме міста для відповіді. Доступні тільки всі міста України.\n3. Щоб почати спочатку введіть 'заново'.\nВперед!!!");
        });

        restartButton.setOnAction(e -> {
            // тут перезапуск
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(label, cityInput, enterButton, rulesButton, restartButton, ruleLabel);

        Scene scene = new Scene(layout, 700, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void handleSubmit() {
        String userCity = userInput.getText().trim();
    }
}
