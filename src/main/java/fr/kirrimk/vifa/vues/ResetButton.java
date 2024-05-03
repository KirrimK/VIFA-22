package fr.kirrimk.vifa.vues;

import fr.kirrimk.vifa.Modele;
import javafx.scene.control.Button;

import java.util.ArrayList;

/**
 * Bouton de réinitialisation des valeurs.
 * Aligne tous les repères, annule les vitesses de rotation, et replace les gouvernes en configuration lisse. 
 */
public class ResetButton extends Button {


    public ResetButton(ArrayList<ControllerPane> resettables){
        super();
        setStyle("-fx-background-color: WHITE; -fx-opacity:0.85;");
        setText("RESET");
        setOnAction((actionEvent) -> {
            for (ControllerPane thing: resettables){
                thing.reset();
            }
            Modele.getInstance().descriptionService.restart();
            Modele.getInstance().getForcesMomentService.restart();
        });
    }
}
