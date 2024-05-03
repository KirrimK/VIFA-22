package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Configuration;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static java.lang.Double.min;
import static java.lang.Double.max;

/**
 * Panneau reproduisant le comportement d'un manche d'avion.
 */
public class MiniManche extends Pane {

    SimpleDoubleProperty ailProp;
    SimpleDoubleProperty profProp;

    public double getAilProp() {
        return ailProp.get();
    }

    public SimpleDoubleProperty ailPropProperty() {
        return ailProp;
    }

    public void setAilProp(double ailProp) {
        this.ailProp.set(ailProp);
    }

    public double getProfProp() {
        return profProp.get();
    }

    public SimpleDoubleProperty profPropProperty() {
        return profProp;
    }

    public void setProfProp(double profProp) {
        this.profProp.set(profProp);
    }

    public MiniManche(){
        super();
        ailProp = new SimpleDoubleProperty(0);
        profProp = new SimpleDoubleProperty(0);
        Rectangle fond = new Rectangle();
        Rectangle curseur = new Rectangle(15, 15);
        curseur.setFill(Color.RED);
        fond.heightProperty().bind(this.heightProperty());
        fond.widthProperty().bind(fond.heightProperty());
        fond.setFill(Color.BLACK);
        getChildren().add(fond);
        getChildren().add(curseur);

        curseur.setTranslateX(-7.5);
        curseur.setTranslateY(-7.5);

        double govMax = Configuration.getInstance().getGovMax();

        setOnMouseDragged((mouseEvent -> {
            double ail = (mouseEvent.getX() - this.getHeight()/2)/(this.getHeight()/2)*govMax;
            double prof = (-mouseEvent.getY() + this.getHeight()/2)/(this.getHeight()/2)*govMax;
            ailProp.set(ail);
            profProp.set(prof);
        }));

        ailProp.addListener(((observableValue, number, t1) -> {
            curseur.setX(min(max(7.5, this.getHeight()/2+(t1.doubleValue()/govMax)*this.getHeight()/2), this.getHeight()-7.5));
        }));
        profProp.addListener(((observableValue, number, t1) -> {
            curseur.setY(min(max(7.5, this.getHeight()/2-(t1.doubleValue()/govMax)*this.getHeight()/2), this.getHeight()-7.5));
        }));

        curseur.setX(68);
        curseur.setY(68);
    }
}
