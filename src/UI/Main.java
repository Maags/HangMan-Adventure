package UI;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Main extends Application {

    private Scene MenuScene;
    private Scene GameScene;
    public static int id;

    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/MenuScene.fxml"));
        primaryStage.setTitle("HANGMAN ADVENTURE");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public void changeSceneToGameScene(ActionEvent actionEvent) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/GameScene.fxml"));

        // Only creates a new scene if it doesn't already exist
        if (GameScene == null)
            GameScene = new Scene(root);
        // This is the line that gets the stage information
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(GameScene);
        primaryStage.show();
    }


}
