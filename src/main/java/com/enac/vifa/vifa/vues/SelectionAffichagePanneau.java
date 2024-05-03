package com.enac.vifa.vifa.vues;


import javafx.scene.control.CheckBox;

/**
 * Panneau de contrôle permettant de choisir ce que l'on souhaite afficher.
 * Permet de selectionner les forces, le moment total, les repères et les angles.
 */
public class SelectionAffichagePanneau extends ControllerPane{

    private CheckBox caseAngles;
    private CheckBox caseForces;
    private CheckBox caseMoment;
    private CheckBox caseReperes;
    private CheckBox caseSol;

    public SelectionAffichagePanneau(Vue3D vue) {
        super(vue);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");

        this.caseAngles = new CheckBox("Angles");
        caseAngles.setOnAction(event -> vue.setVisibleGroupeAngle(caseAngles.isSelected()));
        caseAngles.setSelected(true);
        this.add(caseAngles,0,0);

        this.caseForces = new CheckBox("Forces");
        caseForces.setOnAction(event -> vue.setVisibleGroupeForce(caseForces.isSelected()));
        caseForces.setSelected(true);
        this.add(caseForces,0,1);

        this.caseMoment = new CheckBox("Moments");
        caseMoment.setOnAction(event -> vue.setVisibleGroupeMoment(caseMoment.isSelected()));
        caseMoment.setSelected(true);
        this.add(caseMoment,0,2);

        this.caseReperes = new CheckBox("Repères");
        caseReperes.setOnAction(event -> vue.setVisibleReperes(caseReperes.isSelected()));
        caseReperes.setSelected(true);
        this.add(caseReperes,0,3);

        this.caseSol = new CheckBox("Sol");
        caseSol.setOnAction(event -> vue.setVisibleSol(caseSol.isSelected()));
        caseSol.setSelected(true);
        this.add(caseSol,0,4);
    }
    
}
