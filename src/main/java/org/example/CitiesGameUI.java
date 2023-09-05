package org.example;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

        Label label = new Label("Введіть назву міста:");
        TextField cityInput = new TextField();
        Button enterButton = new Button("Ввести");
        Button rulesButton = new Button("Правила");
        Button restartButton = new Button("Заново");
        Label ruleLabel = new Label();

        // Устанавливаем жирный шрифт для текста и кнопок
        Font boldFont = Font.font("Arial", FontWeight.BOLD, 14);
        label.setFont(boldFont);
        enterButton.setFont(boldFont);
        rulesButton.setFont(boldFont);
        restartButton.setFont(boldFont);

        // Устанавливаем стили для кнопок
        enterButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        rulesButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white;");
        restartButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        // Обработка нажатия на кнопку "Ввести"
        enterButton.setOnAction(e -> {
            String cityName = cityInput.getText();
            // Тут треба додати логіку обробки міста
        });

        // Обработка нажатия на кнопку "Правила"
        rulesButton.setOnAction(e -> {
            ruleLabel.setText("Правила\n1. Щоб закінчити гру введіть 'здаюсь'.\n2. Ви переможете, якщо комп'ютер не матиме міста для відповіді. Доступні тільки всі міста України.\n3. Щоб почати спочатку введіть 'заново'.\nВперед!!!");
        });

        // Обработка нажатия на кнопку "Заново"
        restartButton.setOnAction(e -> {
            // Тут перезапуск
        });

        // Создаем горизонтальный контейнер для кнопок "Правила" и "Заново"
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(rulesButton, restartButton);

        // Создаем вертикальный контейнер для компонентов
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(label, cityInput, enterButton, buttonBox, ruleLabel);

        // Создаем корневой контейнер
        BorderPane root = new BorderPane();
        root.setCenter(layout);

        // Создаем сцену и устанавливаем белый фон
        Scene scene = new Scene(root, 700, 400);
        scene.getRoot().setStyle("-fx-background-color: #FFFFFF;"); // Белый фон

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void handleSubmit() {
        String userCity = userInput.getText().trim();
    }
}
