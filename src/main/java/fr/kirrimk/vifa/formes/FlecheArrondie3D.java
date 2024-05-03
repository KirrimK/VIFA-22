package fr.kirrimk.vifa.formes;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import org.fxyz3d.shapes.primitives.ConeMesh;

import fr.kirrimk.vifa.Configuration;

/**
 * Objet 3D représentant un arc de cercle avec une flèche au bout.
 * Utilisé pour représenter les angles.
 */
public class FlecheArrondie3D extends Group {

    protected double endAngle;

    protected double rayon;

    protected String nom;
    protected Color couleur;

    protected MeshView cone;

    protected static final int APPROX_FACTOR = Configuration.getInstance().getFacteurApproximationArrondis();
    protected int DIV_NUMBER;

    protected boolean flecheInversee = false;

    /**
     *
     * @param nom Le nom de l'angle représenté par la flèche
     * @param rayon Le point d'où la flèche part
     * @param endAngle L'angle en degrés (>0 vers le "bas")
     * @param couleur Couleur de la flèche courbe
     */
    public FlecheArrondie3D (String nom, double rayon, double endAngle, Color couleur){
        super();
        this.rayon = rayon;
        this.couleur = couleur;
        this.nom = nom;
        this.cone = new MeshView();
        setEndAngle(endAngle);
        Tooltip tooltip = new Tooltip(nom);
        tooltip.setShowDelay(Duration.millis(0));
        Tooltip.install(this, tooltip);
    }

    public FlecheArrondie3D(String nom, double rayon, double endAngle, Color couleur, boolean inverse){
        super();
        this.rayon = rayon;
        this.couleur = couleur;
        this.nom = nom;
        this.cone = new MeshView();
        this.flecheInversee = inverse;
        setEndAngle(endAngle);
        Tooltip tooltip = new Tooltip(nom);
        tooltip.setShowDelay(Duration.millis(0));
        Tooltip.install(this, tooltip);
    }

    public double getEndAngle() {
        return endAngle;
    }

    /**
     * Ajuste l'angle et la longueur de la fleche arrondie.
     * @param endAngle la nouvelle valeur en degrés
     */
    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
        DIV_NUMBER = (Math.abs((int)endAngle)) / APPROX_FACTOR;
        Material materiau = new PhongMaterial(couleur);
        getChildren().clear();
        Configuration c=Configuration.getInstance();
        for (int i = (flecheInversee)?2:1; i < (flecheInversee?DIV_NUMBER :(DIV_NUMBER-1)); i++) {
            Cylinder oskours = new Cylinder(c.getRayonVecteur(), 2*Math.toRadians(Math.abs(endAngle))/DIV_NUMBER*rayon);
            oskours.setMaterial(materiau);
            getChildren().add(oskours);
            oskours.getTransforms().setAll(
                new Rotate(i*endAngle/DIV_NUMBER, Rotate.Z_AXIS),
                new Translate(rayon*Math.cos(i/DIV_NUMBER*Math.toRadians(endAngle)), rayon*Math.sin(i/DIV_NUMBER*Math.toRadians(endAngle)), 0));
        }
        ConeMesh jenaimarre = new ConeMesh(c.getTailleGrandCone(), c.getTailleGrandCone());
        cone.setMesh(jenaimarre.getMesh());
        cone.setMaterial(materiau);
        if (flecheInversee){
            cone.getTransforms().setAll(
                    new Translate(rayon, 0),
                    new Rotate(endAngle<0?(180):(0), Rotate.Z_AXIS));
        } else {
            cone.getTransforms().setAll(
                    new Translate(rayon*Math.cos(Math.toRadians(endAngle)), rayon*Math.sin(Math.toRadians(endAngle))),
                    new Rotate(endAngle<0?(endAngle):(-180+endAngle), Rotate.Z_AXIS));
        }
        if ((int) endAngle != 0){
            getChildren().add(cone);
        }
    }

    public double getRayon() {
        return rayon;
    }

    public String getNom() {
        return nom;
    }

    public Color getCouleur() {
        return couleur;
    }
}
