package fr.kirrimk.vifa.vues;

import fr.kirrimk.vifa.Configuration;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Widget 2D représentant un bouton rotatif.
 */
public class Knob extends Pane{
    SimpleDoubleProperty valueProp;
    SimpleDoubleProperty lastX;
    SimpleDoubleProperty lastY;
    SimpleDoubleProperty valAtLastPress;
    public double getValueProp() {
        return valueProp.get();
    }

    public SimpleDoubleProperty valueProperty() {
        return valueProp;
    }

    public void setAilProp(double ailProp) {
        this.valueProp.set(ailProp);
    }

    /**
     *
     * @param min Valeur minimale pouvant être choisie avec ce widget
     * @param max Valeur maximale pouvant être choisie avec ce widget
     * @param value Valeur de départ du widget
     * @param radius Rayon du widget
     * @param col Couleur du curseur du widget
     */
    public Knob(double min, double max, double value, double radius, Color col){
        valueProp = new SimpleDoubleProperty(value);
        lastX = new SimpleDoubleProperty();
        lastY = new SimpleDoubleProperty();
        valAtLastPress = new SimpleDoubleProperty();
        this.setHeight(radius*2);
        this.setWidth(radius*2);
        Circle knob = new Circle(0,0,radius);
        knob.setTranslateY(this.getWidth()/2);
        knob.setTranslateX(this.getHeight()/2);
        knob.setFill(Color.BLACK);
        Circle curseur = new Circle(-0.9*radius*Math.cos(Math.PI*179/90*((value-min)/(max-min))-Math.PI/2),-0.9*radius*Math.sin(Math.PI*179/90*((value-min)/(max-min))-Math.PI/2),radius/10);
        curseur.setFill(col);
        curseur.setTranslateY(this.getWidth()/2);
        curseur.setTranslateX(this.getHeight()/2);
        getChildren().addAll(knob,curseur);
        setOnMousePressed((mouseEvent -> {
            lastX.set(mouseEvent.getX());
            lastY.set(mouseEvent.getY());
            valAtLastPress.set(valueProp.get());
        }));
        double sensibilite = Configuration.getInstance().getDivSensibKnob();
        setOnMouseDragged((mouseEvent -> {
            valueProp.set(Double.min(Double.max(valAtLastPress.get()+(mouseEvent.getX()-mouseEvent.getY()-lastX.get()+lastY.get())/radius*(max-min)/sensibilite,min),max));
        }));
        valueProp.addListener(((observableValue, number, t1) -> {
            if (valueProp.get()>=min && valueProp.get()<=max) {
                curseur.setCenterX(-0.9*radius*Math.cos(Math.PI*179/90*((t1.doubleValue()-min)/(max-min))-Math.PI/2));
                curseur.setCenterY(-0.9*radius*Math.sin(Math.PI*179/90*((t1.doubleValue()-min)/(max-min))-Math.PI/2));
        }}));
    }

}
