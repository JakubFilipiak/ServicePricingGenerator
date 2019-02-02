package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jakub Filipiak on 31.01.2019.
 */
public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "/MainView.fxml"));
        Parent layout = fxmlLoader.load();

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Generator wycen - PDF");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}