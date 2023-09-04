package org.example;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class CitiesGameUI extends Application {

    private Game citiesGame;

    private Window mainWindow;
    private Label labelForMessage;
    private Label labelYou;
    private TextField userInput;
    private Label labelComputer;
    private Label labelForComputerAnswer;
    private Button submitButton;
    private boolean finished = false;
    private Alert alert;

    public CitiesGameUI() throws IOException {
        citiesGame = new Game();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainWindow = primaryStage;
        primaryStage.setTitle("Гра \"Міста\"");
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

        VBox mainLayout = new VBox(30);
        mainLayout.setAlignment(Pos.CENTER);

        HBox layoutDescriptionAndValue = new HBox(20);
        layoutDescriptionAndValue.setAlignment(Pos.CENTER);

        VBox layoutDescription = new VBox(20);
        layoutDescription.setAlignment(Pos.CENTER_RIGHT);
        labelYou = new Label("Ви:");
        labelComputer = new Label("Комп'ютер:");

        layoutDescription.getChildren().addAll(labelYou, labelComputer);

        VBox layoutValue = new VBox(20);
        layoutValue.setAlignment(Pos.CENTER_LEFT);

        userInput = new TextField();
        userInput.setPromptText("Введіть назву міста");
        userInput.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                updateGame();
                userInput.selectAll();
            }
        });
        labelForComputerAnswer = new Label();
        layoutValue.getChildren().addAll(userInput, labelForComputerAnswer);

        // Обработка нажатия на кнопку "Правила"
        rulesButton.setOnAction(e -> {
            ruleLabel.setText("Правила\n1. Щоб закінчити гру введіть 'здаюсь'.\n2. Ви переможете, якщо комп'ютер не матиме міста для відповіді. Доступні тільки всі міста України.\n3. Щоб почати спочатку введіть 'заново'.\nВперед!!!");
        });

        submitButton = new Button("Зробити хід");
        submitButton.setOnAction(event -> {
            userInput.requestFocus();
            userInput.selectAll();
            updateGame();
        });

        layoutDescriptionAndValue.getChildren().addAll(layoutDescription, layoutValue);
        // Обработка нажатия на кнопку "Заново"
        restartButton.setOnAction(e -> {
            // Тут перезапуск
        });

        labelForMessage = new Label("");
        // Создаем горизонтальный контейнер для кнопок "Правила" и "Заново"
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(rulesButton, restartButton);

        mainLayout.getChildren().addAll(labelForMessage, layoutDescriptionAndValue, submitButton);
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


        Scene scene = new Scene(mainLayout, 700, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void updateGame() {
        if (finished) {
            return;
        }

        String selectedCity;
        Optional<String> selectedCityInOptional;
        selectedCity = userInput.getText();

        if (citiesGame.checkNotLetter(selectedCity)) {
            labelForMessage.setText("Ви нічого не ввели!");
            return;
        }

        if (selectedCity.equals("здаюсь")) {
            finished = true;
            showAlert("Поразка", "На жаль, ви програли!",
                    "src/main/resources/images/fail.png", new ButtonType("Закрити", ButtonType.CLOSE.getButtonData()), new ButtonType("Заново", ButtonType.APPLY.getButtonData()));
            return;
        } else if (selectedCity.equals("заново")) {
            restart();
            labelForMessage.setText("Починаємо спочатку!");
            citiesGame.restart();
            return;
        }

        if (!citiesGame.isCity(selectedCity)) {
            labelForMessage.setText("В Україні такого міста не існує!!!");
            return;
        }

        if (!citiesGame.checkCityFirstLetter(selectedCity)) {
            labelForMessage.setText("Це місто не починається з останньої букви попереднього міста: '" + citiesGame.getLastLetter() + "' !!!");
            return;
        }

        if (citiesGame.checkCityInBlackList(selectedCity)) {
            labelForMessage.setText("Це місто вже називали!!!");
            return;
        }

        citiesGame.putWordFromUser(selectedCity);
        selectedCityInOptional = citiesGame.getWordFromComputer();
        if (selectedCityInOptional.isPresent()) {
            labelForComputerAnswer.setText(selectedCityInOptional.get());

            if (!citiesGame.isCityOnLastLetterOfCity(selectedCityInOptional.get())) {
                finished = true;
                showAlert("Поразка", "На жаль, ви програли! Всі міста України на букву '" +
                        citiesGame.getLastLetter() + "' використані.", "src/main/resources/images/fail.png", new ButtonType("Закрити", ButtonType.CLOSE.getButtonData()), new ButtonType("Заново", ButtonType.APPLY.getButtonData()));
            }
        } else {
            labelForComputerAnswer.setText("");

            finished = true;
            showAlert("Перемога", "Ви перемогли!!! Всі міста України на букву '" +
                    citiesGame.getLastLetter() + "' використані.", "src/main/resources/images/pass.png", new ButtonType("Закрити", ButtonType.CLOSE.getButtonData()), new ButtonType("Ще раз", ButtonType.APPLY.getButtonData()));
        }
    }

    private Alert createAlert() {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainWindow);

        return alert;
    }

    private void showAlert(String headerText, String contentText, String pathToImg, ButtonType... buttonTypes) {
        if (alert == null) {
            alert = createAlert();
        }

        alert.setTitle("Результат гри");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        try {
            Image image = new Image(new FileInputStream(pathToImg));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(64);
            imageView.setFitHeight(64);

            alert.getDialogPane().setGraphic(imageView);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        alert.getButtonTypes().addAll(buttonTypes);

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            ButtonType type = buttonType.get();
            if (type.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                ((Stage) (mainWindow)).close();
            } else if (type.getButtonData() == ButtonBar.ButtonData.APPLY) {
                restart();
            }
        }
    }

    private void restart() {
        userInput.clear();
        labelForMessage.setText("");
        labelForComputerAnswer.setText("");
        citiesGame.restart();
        finished = false;
    }
}
