package fr.kirrimk.vifa.formes;

import fr.kirrimk.vifa.Configuration;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Objet 3D permettant de représenter les moments autour des 3 axes de l'avion sur la vue 3D.
 */
public class Moment3D extends Group {
    private FlecheArrondie3D mx_gauche;
    private FlecheArrondie3D mx_droit;
    private FlecheArrondie3D my_avant;
    private FlecheArrondie3D my_arriere;
    private FlecheArrondie3D mz_gauche;
    private FlecheArrondie3D mz_droit;

    private double mx;
    private double my;
    private double mz;

    private static final double MOMENT_TO_ANGLE = Configuration.getInstance().getMomentToDegre();

    /**
     *
     * @param center (Point3D) Centre autour duquel afficher les moments
     * @param rayon_x Distance au centre sur l'axe x à laquelle afficher les moments sur cet axe
     * @param rayon_y Distance au centre sur l'axe y à laquelle afficher les moments sur cet axe
     * @param rayon_z Distance au centre sur l'axe z à laquelle afficher les moments sur cet axe
     * @param mx Valeur du moment autour de x
     * @param my Valeur du moment autour de y
     * @param mz Valeur du moment autour de x
     * @param nom_mx Nom du moment autour de x
     * @param nom_my Nom du moment autour de y
     * @param nom_mz Nom du moment autour de z
     * @param couleur Couleur des moments à afficher
     */
    public Moment3D(Point3D center, double rayon_x, double rayon_y, double rayon_z, double mx, double my, double mz, String nom_mx, String nom_my, String nom_mz, Color couleur){
        super();
        this.mx = mx;
        this.my = my;
        this.mz = mz;
        // flèches autour de x_{repère}
        mx_gauche = new FlecheArrondie3D(nom_mx, rayon_x, -mx*MOMENT_TO_ANGLE, couleur);
        mx_gauche.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
        mx_droit = new FlecheArrondie3D(nom_mx, rayon_x, mx*MOMENT_TO_ANGLE, couleur);
        mx_droit.getTransforms().add(new Rotate(-90, Rotate.Y_AXIS));

        //flèches autour de y_{repère}
        my_avant = new FlecheArrondie3D(nom_my, rayon_y, -my*MOMENT_TO_ANGLE, couleur);
        my_arriere = new FlecheArrondie3D(nom_my, rayon_y, my*MOMENT_TO_ANGLE, couleur);
        my_arriere.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));

        //flèches autour de z_{repère}
        mz_gauche = new FlecheArrondie3D(nom_mz, rayon_z, mz*MOMENT_TO_ANGLE, couleur);
        mz_gauche.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        mz_droit = new FlecheArrondie3D(nom_mz, rayon_z, -mz*MOMENT_TO_ANGLE, couleur);
        mz_droit.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Rotate(180, Rotate.Y_AXIS));

        getTransforms().add(new Translate(center.getX(), center.getY(), center.getZ()));
        getChildren().addAll(mx_gauche, mx_droit, my_avant, my_arriere, mz_gauche, mz_droit);
    }

    public double getMx() {
        return mx;
    }

    public double getMy() {
        return my;
    }

    public double getMz() {
        return mz;
    }

    /**
     * Change les valeurs des moments selon x, y et z.
     */
    public void update(double mx, double my, double mz){
        this.mx = mx;
        this.my = my;
        this.mz = mz;
    }

    /**
     * Change l'affichage du moment sur la vue 3D.
     */
    public void refreshView(){
        mx_gauche.setEndAngle(-mx*MOMENT_TO_ANGLE);
        mx_droit.setEndAngle(mx*MOMENT_TO_ANGLE);
        my_avant.setEndAngle(-my*MOMENT_TO_ANGLE);
        my_arriere.setEndAngle(my*MOMENT_TO_ANGLE);
        mz_gauche.setEndAngle(mz*MOMENT_TO_ANGLE);
        mz_droit.setEndAngle(-mz*MOMENT_TO_ANGLE);
    }

    /**
     * Change le centre du moment sur la vue 3D
     * @param center (Point3D) Le nouveau centre de gravité
     */
    public void changeCenter(Point3D center){
        getTransforms().set(0, new Translate(center.getX(), center.getY(), center.getZ()));
    }
}
