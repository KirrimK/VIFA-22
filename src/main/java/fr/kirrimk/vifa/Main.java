package fr.kirrimk.vifa;

import fr.kirrimk.vifa.vues.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

/**
 * Main de l'application VIFA
 */
public class Main extends Application {
    public Parent createContent(Scene mainScene){
        Vue3D vue = new Vue3D(mainScene, new Group());
        UI ui = new UI(vue);

        Modele modele = Modele.getInstance();
        modele.setVue(vue);

        modele.descriptionService.start();
        modele.getForcesMomentService.start();
        return ui;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group(), 900, 700);
        primaryStage.setTitle("VIFA 2022");
        scene.setRoot(createContent(scene));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((windowEvent -> System.exit(0)));
    }

    public static void main(String[] args) {
        Configuration conf = Configuration.getInstance();
        System.out.println(conf.getLaunchMessage());
        launch();
    }
}
