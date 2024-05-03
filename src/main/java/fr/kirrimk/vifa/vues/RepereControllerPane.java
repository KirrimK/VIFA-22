package fr.kirrimk.vifa.vues;

import fr.kirrimk.vifa.Configuration;
import fr.kirrimk.vifa.Modele;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

/**
 * Panneau permettant de faire tourner les repères les uns par rapport aux autres.
 */
public class RepereControllerPane extends ControllerPane{

    private final Label psil;
    private final Slider psis;
    private TextField psils;

    private TextField phils;
    private final Label phil;
    private final Slider phis;

    private TextField thetals;

    private TextField alphals;
    private final Label thetal;
    private final Slider thetas;

    private final Label alphal;
    private final Slider alphas;

    private TextField betals;

    private final Label betal;
    private final Slider betas;
    
    private final double betaMax=Configuration.getInstance().getBetaMax();
    private final double betaMin=Configuration.getInstance().getBetaMin();
    private final double alphaMax=Configuration.getInstance().getAlphaMax();
    private final double alphaMin=Configuration.getInstance().getAlphaMin();

    public RepereControllerPane(Vue3D vue){
        //Box sliders repères
        super(vue);
        Label rpl = new Label("Repères:");
        setColumnSpan(rpl, 2);

        setRowIndex(rpl, 0);
        psil = new Label("Psi(°)");
        setRowIndex(psil, 1);
        psils = new TextField("0.0");
        psils.setMaxSize(50, 30);
        psils.setMaxSize(50, 50);
        setColumnIndex(psils, 2);
        setRowIndex(psils, 1);
        psis = new Slider(-180, 180, 0);
        setRowIndex(psis, 1);
        setColumnIndex(psis, 1);
        psis.valueProperty().bindBidirectional(Modele.getInstance().getPsiProperty());
        psis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePsi(t1.doubleValue());
            //genericSliderListener("Psi (°): ", psil, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sPsi = new NumberStringConverter();
        Bindings.bindBidirectional(psils.textProperty(), psis.valueProperty(), sPsi);

        phil = new Label("Phi(°)");
        setRowIndex(phil, 4);
        phils = new TextField("0.0");
        phils.setMaxSize(50, 30);
        setRowIndex(phils,3);
        phis = new Slider(-180, 180, 0);
        phis.setStyle(" -fx-color: RED;");
        phis.setOrientation(Orientation.VERTICAL);
        setRowIndex(phis, 2);
        phis.valueProperty().bindBidirectional(Modele.getInstance().getPhiProperty());
        phis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePhi(t1.doubleValue());
            //genericSliderListener("Phi (°): ", phil, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sPhi = new NumberStringConverter();
        Bindings.bindBidirectional(phils.textProperty(), phis.valueProperty(), sPhi);

        Knob phiKnob = new Knob(-180, 180, 0, 55, Color.RED);
        phiKnob.valueProperty().bindBidirectional(Modele.getInstance().getPhiProperty());
        phiKnob.setMaxSize(100, 100);
        setRowIndex(phiKnob, 2);
        setColumnIndex(phiKnob, 1);
        phiKnob.setTranslateX(15);
        //phiKnob.setTranslateY(10);

        thetal = new Label("Theta(°)");
        setRowIndex(thetal, 4);
        setColumnIndex(thetal, 2);
        thetals = new TextField("0.0");
        thetals.setMaxSize(50, 30);
        setColumnIndex(thetals, 2);
        setRowIndex(thetals,3);
        thetas = new Slider(-180, 180, 0);
        thetas.setOrientation(Orientation.VERTICAL);
        setRowIndex(thetas, 2);
        setColumnIndex(thetas, 2);
        thetas.valueProperty().bindBidirectional(Modele.getInstance().getThetaProperty());
        thetas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateTheta(t1.doubleValue());
            //genericSliderListener("Theta (°): ", thetal, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sTheta = new NumberStringConverter();
        Bindings.bindBidirectional(thetals.textProperty(),thetas.valueProperty(), sTheta);

        Separator sep = new Separator();

        setColumnSpan(sep, 3);
        setRowIndex(sep, 5);

        alphal = new Label("Alpha(°)");
        setRowIndex(alphal, 6);
        alphals = new TextField("0.0");
        alphals.setMaxSize(50, 30);
        setColumnIndex(alphals, 2);
        setRowIndex(alphals,6);
        alphas = new Slider(alphaMin, alphaMax, 0);
        setRowIndex(alphas, 6);
        setColumnIndex(alphas, 1);
        alphas.valueProperty().bindBidirectional(Modele.getInstance().getAlphaProperty());
        alphas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateAlpha(t1.doubleValue());
            //genericSliderListener("Alpha (°): ", alphal, t1);
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sAlpha = new NumberStringConverter();
        Bindings.bindBidirectional(alphals.textProperty(), alphas.valueProperty(), sAlpha);

        betal = new Label("Beta(°)");
        setRowIndex(betal, 7);
        betals = new TextField("0.0");
        betals.setMaxSize(50, 30);
        setColumnIndex(betals, 2);
        setRowIndex(betals,7);
        betas = new Slider(betaMin, betaMax, 0);
        setRowIndex(betas, 7);
        setColumnIndex(betas, 1);
        betas.valueProperty().bindBidirectional(Modele.getInstance().getBetaProperty());
        betas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateBeta(-t1.doubleValue());
            //genericSliderListener("Beta (°): ", betal, t1);
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sBeta = new NumberStringConverter();
        Bindings.bindBidirectional(betals.textProperty(), betas.valueProperty(), sBeta);

        getChildren().addAll(rpl, psil, psis,psils, phil, phis,phils, thetal, thetas,thetals, alphal, alphas,alphals, betal, betas,betals, phiKnob, sep );
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");
        for (Node node: getChildren()){
            setHgrow(node, Priority.ALWAYS);
            setVgrow(node, Priority.ALWAYS);
        }

        setMargin(sep, new Insets(10, 2, 10, 2));
        //setPadding(new Insets(4, 4, 4, 4));
    }

    public void reset(){
        resetting = true;
        Modele.getInstance().setPhi(0);
        Modele.getInstance().setPsi(0);
        Modele.getInstance().setTheta(0);
        Modele.getInstance().setAlpha(0);
        Modele.getInstance().setBeta(0);
        resetting = false;
    }
}
