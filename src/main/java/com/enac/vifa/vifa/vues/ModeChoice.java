package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Configuration;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 * Classe permettant le choix d'un mode de l'énumération Mode.
 */
public class ModeChoice extends ComboBox<Mode> {

    public ModeChoice(Vue3D vue){
        super(FXCollections.observableArrayList(Mode.AERO,Mode.AVION,Mode.ATTITUDE));
        setValue(Configuration.getInstance().getMode());
        setOnAction(e->vue.setMode((Mode)this.getValue()));
    }
}
