package fr.kirrimk.vifa.vues;

import fr.kirrimk.vifa.Modele;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;


/**
 * Panneau permettant de régler la poussée moteur.
 * @author hugocourtadon
 */
public class ThrottlePane extends ControllerPane {
    private final Label poussInfo;
    private final Slider pouss;

    protected void genericSliderListener(String labelText, Label label, Number newValue){
        String text = labelText + throtValue();
        label.setText(text.substring(0, Math.min(5, text.length())));
    }

    public ThrottlePane(Vue3D vue){
        super(vue);
        Label poussIntitule = new Label("GAZ (%)");
        setRowIndex(poussIntitule, 0);
        poussInfo = new Label(throtValue());
        setRowIndex(poussInfo,1);
        pouss = new Slider(0, 1,0);
        pouss.valueProperty().bindBidirectional(Modele.getInstance().getDxProperty());
        pouss.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("", poussInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(pouss,2);
        pouss.setOrientation(Orientation.VERTICAL);
        getChildren().addAll(poussIntitule, pouss,poussInfo);

        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");
        setHalignment(pouss, HPos.CENTER);
        setHalignment(poussInfo, HPos.CENTER);
        setVgrow(pouss, Priority.ALWAYS);
    }
    @Override
    public void reset(){
        resetting = true;
        Modele.getInstance().setDx(0);
       
        resetting = false;
    }
    public String throtValue(){
        if (Modele.getInstance().getDx()==0d){
            return "IDLE";
        }
        if (Modele.getInstance().getDx()==1d){
            return "TOGA";
        }
        else{
          return Double.toString(Modele.getInstance().getDx());
        }
    }
}
