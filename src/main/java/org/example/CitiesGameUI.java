package org.example;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private Label rulesLabel;
    private Button rulesButton;
    private Button restartButton;
    private Button finishButton;
    private boolean finished = false;
    private Font boldFont;
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

        boldFont = Font.font("Arial", FontWeight.BOLD, 14);

        VBox mainLayout = createMainLayout();

        Scene scene = new Scene(mainLayout, 750, 500);
        scene.getRoot().setStyle("-fx-background-color: #FFFFFF;");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(40);
        mainLayout.setAlignment(Pos.CENTER);


        HBox layoutDescriptionAndValue = createDescriptionAndValueLayout();

        submitButton = new Button("Зробити хід");
        submitButton.setFont(boldFont);
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(event -> {
            userInput.requestFocus();
            userInput.selectAll();
            updateGame();
        });

        HBox buttonBox = createButtonLayout(boldFont);

        labelForMessage = new Label("");

        mainLayout.getChildren().addAll(labelForMessage, layoutDescriptionAndValue, submitButton, buttonBox, rulesLabel);
        return mainLayout;
    }

    private HBox createButtonLayout(Font boldFont) {
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        rulesLabel = new Label();
        rulesLabel.setText("Правила\n" +
                "1. Щоб закінчити гру введіть 'здаюсь'.\n" +
                "3. Ви переможете, якщо комп'ютер не матиме міста для відповіді. Доступні тільки всі міста України.\n" +
                "4. Щоб почати спочатку введіть 'заново'.\n" +
                "5. Щоб підтвердити введення — скористайтеся кнопкою 'Зробити хід' або натисніть Enter.\n" +
                "Вперед!!!");
        rulesLabel.setVisible(false);

        rulesButton = new Button("Правила");
        rulesButton.setFont(boldFont);
        rulesButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white;");
        rulesButton.setOnAction(e -> {
            rulesLabel.setVisible(!rulesLabel.isVisible());
        });

        restartButton = new Button("Заново");
        restartButton.setFont(boldFont);
        restartButton.setStyle("-fx-background-color: #f4bb36; -fx-text-fill: white;");
        restartButton.setOnAction(e -> {
            restart();
        });

        finishButton = new Button("Здаюсь");
        finishButton.setFont(boldFont);
        finishButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        finishButton.setOnAction(e -> {
            finished = true;
            showAlert("Поразка", "На жаль, ви програли!",
                    "src/main/resources/images/fail.png", new ButtonType("Закрити", ButtonBar.ButtonData.OK_DONE), new ButtonType("Заново", ButtonType.APPLY.getButtonData()));

        });

        buttonBox.getChildren().addAll(restartButton, rulesButton, finishButton);
        return buttonBox;
    }

    private HBox createDescriptionAndValueLayout() {
        HBox layoutDescriptionAndValue = new HBox(20);
        layoutDescriptionAndValue.setAlignment(Pos.CENTER);

        VBox layoutDescription = createDescriptionLayout();

        VBox layoutValue = createValueLayout();

        layoutDescriptionAndValue.getChildren().addAll(layoutDescription, layoutValue);
        return layoutDescriptionAndValue;
    }

    private VBox createValueLayout() {
        VBox layoutValue = new VBox(20);
        layoutValue.setAlignment(Pos.CENTER_LEFT);

        Insets elementsInsets = new Insets(7, 8, 7, 8);

        userInput = new TextField();
        userInput.setPromptText("Введіть назву міста");
        userInput.setPadding(elementsInsets);
        userInput.setStyle("-fx-font-size: 16px");
        userInput.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                updateGame();
                userInput.selectAll();
            }
        });
        labelForComputerAnswer = new Label();
        labelForComputerAnswer.setPadding(elementsInsets);
        labelForComputerAnswer.setStyle("-fx-font-size: 16px");
        layoutValue.getChildren().addAll(userInput, labelForComputerAnswer);
        return layoutValue;
    }

    private VBox createDescriptionLayout() {
        VBox layoutDescription = new VBox(44);
        layoutDescription.setAlignment(Pos.CENTER_RIGHT);
        labelYou = new Label("Ви:");
        labelYou.setFont(boldFont);
        labelComputer = new Label("Комп'ютер:");
        labelComputer.setFont(boldFont);

        layoutDescription.getChildren().addAll(labelYou, labelComputer);
        return layoutDescription;
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
                    "src/main/resources/images/fail.png", new ButtonType("Закрити", ButtonBar.ButtonData.OK_DONE), new ButtonType("Заново", ButtonType.APPLY.getButtonData()));
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
                        citiesGame.getLastLetter() + "' використані.", "src/main/resources/images/fail.png", new ButtonType("Закрити", ButtonBar.ButtonData.OK_DONE), new ButtonType("Заново", ButtonType.APPLY.getButtonData()));
            }
        } else {
            labelForComputerAnswer.setText("");

            finished = true;
            showAlert("Перемога", "Ви перемогли!!! Всі міста України на букву '" +
                    citiesGame.getLastLetter() + "' використані.", "src/main/resources/images/pass.png", new ButtonType("Закрити", ButtonBar.ButtonData.OK_DONE), new ButtonType("Ще раз", ButtonType.APPLY.getButtonData()));
        }
    }

    private Alert createAlert() {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
            restart();
            alert.hide();
        });

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

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(buttonTypes);

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            ButtonType type = buttonType.get();
            if (type.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
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
