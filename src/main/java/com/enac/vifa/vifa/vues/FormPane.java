/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Panneau permettant de changer des propriétés de l'avion (masse, calage, centrage, ...)
 * @author hugocourtadon
 */
public class FormPane extends ControllerPane {
    
    protected boolean resetting = false;
    
    private Label masse;
   
    private TextField masses;
    
    private Label cg;
    private TextField cgs;
    
    private Label vp;
    private TextField vps;
    
    private Label calage;
    private TextField calages;
    
    public FormPane(Vue3D vue){
        //Box sliders repères
        super(vue);
        
        Modele model = Modele.getInstance();
        //Masse
        masse = new Label("Masse (kg) : ");
        setRowIndex(masse, 0);
        setColumnIndex(masse, 0);
        masses = new TextField(Double.toString(model.getMass()));
        masses.setMaxWidth(100);
        setRowIndex(masses, 0);
        setColumnIndex(masses, 1);
        
        //Position du centre de gravité de l'avion
        cg = new Label("Position du centre de gravité (%CAM):");
        setRowIndex(cg, 1);
        setColumnIndex(cg, 0);
        cgs = new TextField(Double.toString(model.getxCentrage()));
        cgs.setMaxWidth(100);
        setRowIndex(cgs, 1);
        setColumnIndex(cgs, 1);
        
        //Vitesse propre
        vp = new Label("Vitesse propre (kts):");
        setRowIndex(vp, 2);
        setColumnIndex(vp, 0);
        vps = new TextField(Double.toString(model.getvAir()));
        vps.setMaxWidth(100);
        setRowIndex(vps, 2);
        setColumnIndex(vps, 1);
        
        //Calage de l'aile
        calage = new Label("Calage de l'aile (°):");
        setRowIndex(calage, 3);
        setColumnIndex(calage, 0);
        calages = new TextField(Double.toString(model.getA0()));
        calages.setMaxWidth(100);
        setRowIndex(calages, 3);
        setColumnIndex(calages, 1);
        
       
        // action event
        EventHandler<ActionEvent> eventMasse = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                model.setMass(Double.parseDouble(masses.getText()));
                if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
                }
            }
        };
        
        EventHandler<ActionEvent> eventCg = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                model.setxCentrage(Double.parseDouble(cgs.getText()));
                if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
                }
                
            }
        };
        
        EventHandler<ActionEvent> eventVp = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                model.setvAir(Double.parseDouble(vps.getText()));
                if (!resetting){
                Modele.getInstance().getForcesMomentService.restart();
                }
                
            }
        };
        
        EventHandler<ActionEvent> eventCalage = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                model.setA0(Double.parseDouble(calages.getText()));
                if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
                }
                
            }
        };
        //Actions
        masses.setOnAction(eventMasse);
        cgs.setOnAction(eventCg);
        vps.setOnAction(eventVp);
        calages.setOnAction(eventCalage);
        
        //Ajout du form à la scène
        getChildren().addAll(masse,masses,cg,cgs,vp,vps,calage,calages);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");
    }

    @Override
    public void reset() {
        resetting = true;
        Modele.getInstance().setMass(70000.0);
        masses.setText(Double.toString(70000.0));
        
        Modele.getInstance().setxCentrage(0.2555);
        cgs.setText(Double.toString(0.2555));
        
        Modele.getInstance().setvAir(150);
        vps.setText(Double.toString(150));
        
        Modele.getInstance().setA0(3.031);
        calages.setText(Double.toString(3.031));
        
        resetting = false;
    }
}
