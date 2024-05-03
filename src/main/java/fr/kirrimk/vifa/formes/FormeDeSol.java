package fr.kirrimk.vifa.formes;

import fr.kirrimk.vifa.Configuration;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * Classe représentant le sol.
 * Sa hauteur et sa couleur sont réglables depuis le fichier de configuration.
 */
public class FormeDeSol extends Group{
    private int NB_BARRES = 15;
    private double hauteur;
    private Color couleur;

    public FormeDeSol() {
        Configuration c = Configuration.getInstance();
        this.hauteur = c.getHauteur();
        this.couleur = c.getCouleurSol();
        Material m = new PhongMaterial(couleur);
        Box plan = new Box();
        plan.setMaterial(m);
        this.setTranslateY(hauteur+0.2);
        plan.setHeight(0.1);
        plan.setWidth(5000);
        plan.setDepth(5000);
        this.getChildren().add(plan);

        Material m2 = new PhongMaterial(c.getCouleurGrilleSol());

        for (int i=0;i<NB_BARRES;i++){
            Box barreVert = new Box();
            barreVert.setWidth(5000);
            barreVert.setHeight(0.1);
            barreVert.setDepth(3);
            barreVert.setTranslateZ(-2500+i*5000/(NB_BARRES-1));
            barreVert.setMaterial(m2);

            Box barreHor = new Box();
            barreHor.setWidth(3);
            barreHor.setDepth(5000);
            barreHor.setHeight(0.1);
            barreHor.setTranslateX(-2500 + i*5000/(NB_BARRES-1));
            barreHor.setMaterial(m2);


            this.getChildren().addAll(barreHor, barreVert);
        }
    }
    
}
