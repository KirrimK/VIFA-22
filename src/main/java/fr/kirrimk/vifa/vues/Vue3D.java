package fr.kirrimk.vifa.vues;

import static fr.kirrimk.vifa.vues.Mode.ATTITUDE;
import static java.lang.Double.max;
import static java.lang.Double.min;

import fr.kirrimk.vifa.Configuration;
import fr.kirrimk.vifa.Modele;
import fr.kirrimk.vifa.formes.FlecheArrondie3D;
import fr.kirrimk.vifa.formes.FormeDeSol;
import fr.kirrimk.vifa.formes.TireBouchon3D;
import fr.kirrimk.vifa.formes.Vecteur3D;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Classe contenant les objets 3D (avion, repères, forces,...)
 */
public class Vue3D extends SubScene {
    private final double ROTATION_TO_DEGRES= Configuration.getInstance().getVitesseRotationToDegres();
    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private FormeDeSol ground;
    private Group repereTerrestre;
    private Group flechesTerrestres=new Group();
    private Group psiThetaPhi=new Group();

    private Group repereAvion;
    private Group flechesAvion=new Group();
    private Group alpha=new Group();
    private Group groupeAvion;
    private Group groupe2D;
    private Group groupeForces;

    private Group repereAeroPart;
    private Group beta=new Group();
    private Group repereAero;

    private FlecheArrondie3D apsix;
    private FlecheArrondie3D athetax;

    private FlecheArrondie3D aphiy;
    private FlecheArrondie3D apsiy;

    private FlecheArrondie3D aphiz;
    private FlecheArrondie3D athetaz;

    private TireBouchon3D tbp;
    private TireBouchon3D tbq;
    private TireBouchon3D tbr;

    private FlecheArrondie3D aalphax;
    private FlecheArrondie3D abetax;

    private FlecheArrondie3D aalphaz;
    private FlecheArrondie3D abetay;

    //camera properties
    private double xrotdef = -27;
    private SimpleDoubleProperty xrotprop = new SimpleDoubleProperty(xrotdef);
    private double yrotdef = -57;
    private SimpleDoubleProperty yrotprop = new SimpleDoubleProperty(yrotdef);
    private double zoomdef = Configuration.getInstance().getZoomDefault();
    private SimpleDoubleProperty zoomprop = new SimpleDoubleProperty(zoomdef);

    private double ZOOM_MIN_VALUE = Configuration.getInstance().getZoomMin();
    private double ZOOM_MAX_VALUE = Configuration.getInstance().getZoomMax();

    //mode properties
    private Mode mode=Configuration.getInstance().getMode();

    //mouse event vars
    private double startx;
    private double starty;
    private boolean gaucher = ! Configuration.getInstance().getIsDroitier();

    public boolean isGaucher() {
        return gaucher;
    }

    public void setGaucher(boolean gaucher) {
        this.gaucher = gaucher;
    }

    private double limit(double d){
        if (d < -90){
            return -90;
        } else if (d > 90){
            return 90;
        } else {
            return d;
        }
    }

    public Vue3D(Scene mainScene, Group repereTerrestreVide){
        super(repereTerrestreVide, 100.0, 100.0, true, SceneAntialiasing.BALANCED);
        this.mode = ATTITUDE;
        this.heightProperty().bind(mainScene.heightProperty());
        this.widthProperty().bind(mainScene.widthProperty());
        setFill(Configuration.getInstance().getCouleurFond());

        this.repereTerrestre = repereTerrestreVide;
        repereTerrestre.getChildren().add(new AmbientLight(Color.WHITESMOKE));
        Configuration conf = Configuration.getInstance();
        Color terrColor = conf.getCouleurRepèreTerrestre();
        Color avColor = conf.getCouleurRepèreAvion();
        Color aerColor = conf.getCouleurRepèreAero();

        double repereScaling = Configuration.getInstance().getRepereScaling();
        double sensibilite = Configuration.getInstance().getReducSensibilite();

        //repère terrestre
        Vecteur3D trx = new Vecteur3D("x terrestre", new Point3D(25, 0, 0), new Point3D(repereScaling, 0, 0), terrColor);
        trx.refreshView();
        Vecteur3D trz = new Vecteur3D("z terrestre", new Point3D(0, 25, 0), new Point3D(0, repereScaling, 0), terrColor);
        trz.refreshView();
        Vecteur3D try_ = new Vecteur3D("y terrestre", new Point3D(0, 0, -25), new Point3D(0, 0, -repereScaling), terrColor);
        try_.refreshView();

        ground = new FormeDeSol();

        flechesTerrestres.getChildren().add(trx);
        flechesTerrestres.getChildren().add(try_);
        flechesTerrestres.getChildren().add(trz);
        repereTerrestre.getChildren().addAll(flechesTerrestres, ground);


        //angles de rotation du repère terrestre
        apsix = new FlecheArrondie3D("psi", 25+repereScaling, 0, conf.getCouleurPsiThetaPhi());
        apsix.getTransforms().setAll(
                new Rotate(-90, Rotate.X_AXIS)
        );
        athetax = new FlecheArrondie3D("theta", 25+repereScaling, 0, conf.getCouleurPsiThetaPhi());
        athetax.getTransforms().setAll(
                new Rotate(0, Rotate.Y_AXIS)
        );

        apsiy = new FlecheArrondie3D("psi", 25+repereScaling, 0, conf.getCouleurPsiThetaPhi());
        apsiy.getTransforms().setAll(
                new Rotate(90, Rotate.Y_AXIS),
                new Rotate(-90, Rotate.X_AXIS)
        );
        aphiy = new FlecheArrondie3D("phi", 25+repereScaling, 0, conf.getCouleurPsiThetaPhi());
        aphiy.getTransforms().setAll(
                new Rotate(-90, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS),
                new Rotate(90, Rotate.Z_AXIS)
        );


        aphiz = new FlecheArrondie3D("phi", 25+repereScaling, 0, conf.getCouleurPsiThetaPhi());
        aphiz.getTransforms().setAll(
                new Rotate(90, Rotate.Y_AXIS),
                new Rotate(180, Rotate.X_AXIS)
        );
        athetaz = new FlecheArrondie3D("theta", 25+repereScaling, 0, conf.getCouleurPsiThetaPhi());
        athetaz.getTransforms().setAll(
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(90, Rotate.Z_AXIS)
        );

        psiThetaPhi.getChildren().add(apsix);
        psiThetaPhi.getChildren().add(athetax);

        psiThetaPhi.getChildren().add(apsiy);
        psiThetaPhi.getChildren().add(aphiy);

        psiThetaPhi.getChildren().add(aphiz);
        psiThetaPhi.getChildren().add(athetaz);

        repereTerrestre.getChildren().add(psiThetaPhi);

        repereAvion = new Group();

        Group groupeAvionTemp = new Group();
        groupeAvionTemp.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Rotate(180, Rotate.Z_AXIS));

        groupeAvion = new Group();
        groupeAvionTemp.getChildren().add(groupeAvion);
        groupeAvion.getTransforms().add(new Translate(0, 0, 0));

        groupe2D = new Group();
        groupeAvion.getChildren().add(groupe2D);

        groupeForces = new Group();
        groupeForces.getTransforms().add(new Translate(0, 0, 0));

        repereAvion.getChildren().add(groupeAvionTemp);
        repereAvion.getChildren().add(groupeForces);

        Vecteur3D avx = new Vecteur3D("x avion", new Point3D(25+repereScaling, 0, 0), new Point3D(repereScaling, 0, 0), avColor);
        avx.refreshView();
        Vecteur3D avz = new Vecteur3D("z avion", new Point3D(0, 25+repereScaling, 0), new Point3D(0, repereScaling, 0), avColor);
        avz.refreshView();
        Vecteur3D avy = new Vecteur3D("y avion", new Point3D(0, 0, -25-repereScaling), new Point3D(0, 0, -repereScaling), avColor);
        avy.refreshView();

        tbp = new TireBouchon3D("p", 5, 0, Configuration.getInstance().getCouleurVitessesRotation());
        tbq = new TireBouchon3D("q", 5, 0, Configuration.getInstance().getCouleurVitessesRotation());
        tbr = new TireBouchon3D("r", 5, 0, Configuration.getInstance().getCouleurVitessesRotation());

        tbp.getTransforms().setAll(new Translate(25+repereScaling, 0, 0));
        tbq.getTransforms().setAll(
            new Rotate(90, Rotate.Y_AXIS),
            new Translate(25+repereScaling, 0, 0)
        );
        tbr.getTransforms().setAll(
            new Rotate(90, Rotate.Z_AXIS),
            new Translate(25+repereScaling, 0, 0)
        );

        flechesAvion.getChildren().addAll(avx, avy, avz);
        repereAvion.getChildren().addAll(flechesAvion, tbp, tbq, tbr);

        boolean inverse = Configuration.getInstance().getInversionAero();

        aalphax = new FlecheArrondie3D("alpha", 25+repereScaling*2, 0, conf.getCouleurAlphaBeta(), inverse);

        abetax = new FlecheArrondie3D("beta", 25+repereScaling*2, 0, conf.getCouleurAlphaBeta(), inverse);
        abetax.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS));

        aalphaz = new FlecheArrondie3D("alpha", 25+repereScaling*2, 0, conf.getCouleurAlphaBeta(), inverse);
        aalphaz.getTransforms().setAll(
                new Rotate(90, Rotate.Z_AXIS)
        );

        abetay = new FlecheArrondie3D("beta", 25+repereScaling*2, 0, conf.getCouleurAlphaBeta(), inverse);
        abetay.getTransforms().setAll(
                new Rotate(90, Rotate.X_AXIS),
                new Rotate(-90, Rotate.Z_AXIS)
        );

        repereTerrestre.getChildren().add(repereAvion);

        alpha.getChildren().addAll(aalphax, aalphaz);
        repereAvion.getChildren().add(alpha);

        repereAeroPart = new Group();
        repereAero = new Group();

        beta.getChildren().addAll(abetax, abetay);
        repereAeroPart.getChildren().add(beta);
        repereAeroPart.getChildren().add(repereAero);

        Vecteur3D aerx = new Vecteur3D("x aéro", new Point3D(25+repereScaling*2, 0, 0), new Point3D(repereScaling, 0, 0), aerColor);
        aerx.refreshView();
        Vecteur3D aerz = new Vecteur3D("z aéro", new Point3D(0, 25+repereScaling*2, 0), new Point3D(0, repereScaling, 0), aerColor);
        aerz.refreshView();
        Vecteur3D aery = new Vecteur3D("y aéro", new Point3D(0, 0, -25-repereScaling*2), new Point3D(0, 0, -repereScaling), aerColor);
        aery.refreshView();

        repereAero.getChildren().add(aerx);
        repereAero.getChildren().add(aery);
        repereAero.getChildren().add(aerz);

        repereAvion.getChildren().add(repereAeroPart);

        camera.setFarClip(5000.0f);

        setCamera(camera);

        camera.getTransforms().setAll (
                new Rotate(yrotprop.get(), Rotate.Y_AXIS),
                new Rotate(xrotprop.get(), Rotate.X_AXIS),
                new Translate(0, 0, -zoomprop.get()));

        xrotprop.addListener(((observableValue, number, t1) -> {
            camera.getTransforms().set(1, new Rotate(xrotprop.get(), Rotate.X_AXIS));
        }));

        yrotprop.addListener(((observableValue, number, t1) -> {
            camera.getTransforms().set(0, new Rotate(yrotprop.get(), Rotate.Y_AXIS));
        }));

        zoomprop.addListener(((observableValue, number, t1) -> {
            camera.getTransforms().set(2, new Translate(0, 0, -zoomprop.get()));
        }));

        setOnScroll((event) -> {
            double number = min(max(-event.getDeltaY()/2 + zoomprop.get(), ZOOM_MIN_VALUE),ZOOM_MAX_VALUE);
            zoomprop.set(number);
        });

        setOnMousePressed((mouseEvent -> {
                startx = mouseEvent.getX();
                starty = mouseEvent.getY();
        }));

        setOnMouseDragged((mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            double diffx = (x - startx)/sensibilite;
            double diffy = (y - starty)/sensibilite;
            if (mouseEvent.getButton() == ((gaucher)?MouseButton.SECONDARY:MouseButton.PRIMARY)){
                switch (mode){
                    case ATTITUDE -> {                        
                        SimpleDoubleProperty psiprop = Modele.getInstance().getPsiProperty();
                        SimpleDoubleProperty thetaprop = Modele.getInstance().getThetaProperty();
                        psiprop.set(psiprop.get()-diffx);
                        thetaprop.set(thetaprop.get()-diffy);
                    }
                    case AVION -> {                        
                        SimpleDoubleProperty psiprop = Modele.getInstance().getPsiProperty();
                        SimpleDoubleProperty thetaprop = Modele.getInstance().getThetaProperty();
                        SimpleDoubleProperty alphaprop = Modele.getInstance().getAlphaProperty();
                        SimpleDoubleProperty betaprop = Modele.getInstance().getBetaProperty();
                        psiprop.set(psiprop.get()-diffx);
                        thetaprop.set(thetaprop.get()-diffy);
                        alphaprop.set(alphaprop.get()-diffy);
                        betaprop.set(betaprop.get()+diffx);
                    }
                    case AERO -> {
                        SimpleDoubleProperty alphaprop = Modele.getInstance().getAlphaProperty();
                        SimpleDoubleProperty betaprop = Modele.getInstance().getBetaProperty();
                        alphaprop.set(alphaprop.get()+diffy);
                        betaprop.set(betaprop.get()-diffx);
                    }
                }
            }
            else if (mouseEvent.getButton() == ((!gaucher)?MouseButton.SECONDARY:MouseButton.PRIMARY)){
                yrotprop.set(yrotprop.get() + diffx);
                xrotprop.set(limit(xrotprop.get() - diffy));
            }
            startx = x;
            starty = y;
        }));

        rotateRepereAvion(0, 0, 0);
        rotateRepereAero(0, 0);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Group getGroupe2D() {
        return groupe2D;
    }

    public void setGroupe2D(Group groupe2D) {
        this.groupe2D = groupe2D;
    }

    public Group getRepereTerrestre() {
        return repereTerrestre;
    }

    public Group getRepereAvion() {
        return repereAvion;
    }

    public Group getRepereAero() {
        return repereAero;
    }

    public void rotateCamera(double xrot, double yrot, double zoom){
        xrotprop.set(limit(xrot));
        yrotprop.set(yrot);
        zoomprop.set(zoom);
    }

    public Group getGroupeAvion() {
        return groupeAvion;
    }

    public void setGroupeAvion(Group groupeAvion) {
        this.groupeAvion = groupeAvion;
    }

    public Group getGroupeForces() {
        return groupeForces;
    }

    public void setGroupeForces(Group groupeForces) {
        this.groupeForces = groupeForces;
    }

    public void cameraDefault(){
        rotateCamera(xrotdef, yrotdef, zoomdef);
    }

    public void rotateRepereAvion(double psi, double phi, double theta){
        repereAvion.getTransforms().setAll(
                new Rotate(psi, Rotate.Y_AXIS),
                new Rotate(-theta, Rotate.Z_AXIS),
                new Rotate(phi, Rotate.X_AXIS));
        rotatePhi(psi);
        rotatePhi(phi);
        rotateTheta(theta);
    }

    public void rotatePsi(double psi){
        repereAvion.getTransforms().set(0, new Rotate(psi, Rotate.Y_AXIS));
        apsix.setEndAngle(psi);
        apsiy.setEndAngle(psi);
        aphiy.getTransforms().set(0, new Rotate(-90+psi, Rotate.Y_AXIS));
        aphiz.getTransforms().set(0, new Rotate(90+psi, Rotate.Y_AXIS));
        athetax.getTransforms().set(0, new Rotate(psi, Rotate.Y_AXIS));
        athetaz.getTransforms().set(0, new Rotate(psi, Rotate.Y_AXIS));
    }

    public void rotatePhi(double phi){
        repereAvion.getTransforms().set(2, new Rotate(phi, Rotate.X_AXIS));
        aphiy.setEndAngle(-phi);
        aphiz.setEndAngle(-phi);
    }

    public void rotateTheta(double theta){
        repereAvion.getTransforms().set(1, new Rotate(-theta, Rotate.Z_AXIS));
        aphiy.getTransforms().set(1, new Rotate(-theta, Rotate.X_AXIS));
        aphiz.getTransforms().set(1, new Rotate(180+theta, Rotate.X_AXIS));

        athetax.setEndAngle(-theta);
        athetaz.setEndAngle(-theta);
    }

    public void rotateRepereAero(double alpha, double beta){
        //vérifier les signes
        repereAero.getTransforms().setAll(new Rotate(-beta, Rotate.Y_AXIS));
        repereAeroPart.getTransforms().setAll(new Rotate(alpha, Rotate.Z_AXIS));
    }

    public void rotateAlpha(double alpha){
        repereAeroPart.getTransforms().set(0, new Rotate(alpha, Rotate.Z_AXIS));
        aalphax.setEndAngle(alpha);
        aalphaz.setEndAngle(alpha);
    }

    public void rotateBeta(double beta){
        repereAero.getTransforms().set(0, new Rotate(-beta, Rotate.Y_AXIS));
        abetax.setEndAngle(beta);
        abetay.setEndAngle(beta);
    }

    public void updateP(double p){
        tbp.setEndAngle(ROTATION_TO_DEGRES*p);
    }

    public void updateQ(double q){
        tbq.setEndAngle(ROTATION_TO_DEGRES*q);
    }

    public void updateR(double r){
        tbr.setEndAngle(ROTATION_TO_DEGRES*r);
    }

    public double getXrotprop() {
        return xrotprop.get();
    }

    public SimpleDoubleProperty xrotpropProperty() {
        return xrotprop;
    }

    public double getYrotprop() {
        return yrotprop.get();
    }

    public SimpleDoubleProperty yrotpropProperty() {
        return yrotprop;
    }

    public double getZoomprop() {
        return zoomprop.get();
    }

    public SimpleDoubleProperty zoompropProperty() {
        return zoomprop;
    }

    /**
     * Affiche ou non les forces
     * @param b (Boolean) 
     */
    public void setVisibleGroupeForce(boolean b){
        groupeForces.setVisible(b);
        for (Vecteur3D f:Modele.getInstance().getListeDesForces()){
            if(f.getNom().equals("mg")){
                f.setVisible(b);
                break;
            }
        }
    }

    /**
     * Affiche ou non les angles entre les repères
     * @param b (Boolean) 
     */
    public void setVisibleGroupeAngle(boolean b){
        psiThetaPhi.setVisible(b);
        alpha.setVisible(b);
        beta.setVisible(b);
    }

    /**
     * Affiche ou non le moment total
     * @param b (Boolean) 
     */
    public void setVisibleGroupeMoment(boolean b){
        Modele.getInstance().getMomentTotal().setVisible(b);
    }


    /**
     * Affiche ou non les repères
     * @param b (Boolean) 
     */
    public void setVisibleReperes(boolean b){
        repereAero.setVisible(b);
        flechesTerrestres.setVisible(b);
        flechesAvion.setVisible(b);
    }

    public void setVisibleSol (boolean b){
        ground.setVisible(b);
    }
}
