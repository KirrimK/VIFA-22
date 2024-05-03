package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Configuration;
import com.enac.vifa.vifa.Modele;

import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;


/**
 * Panneau permettant d'actionner les gouvernes de l'avion.
 */
public class GouverneControllerPane extends ControllerPane {

    private final Label profInfo;
    private final Slider prof;
    private final TextField profs;
    private final Label dirInfo;
    private final Slider dir;
    private final TextField dirs;
    private final Label ailInfo;
    private final Slider ailerons;
    private final TextField ails;

    private final double govMax=Configuration.getInstance().getGovMax();
    private final double govMin=Configuration.getInstance().getGovMin();
    private final double trimMax=Configuration.getInstance().getTrimMax();
    private final double trimMin=Configuration.getInstance().getTrimMin();

    private final Label triml;

    private final Slider trims;

    private final TextField trimt;


    protected void genericSliderListener(String labelText, Label label, Number newValue){
        String text = labelText + newValue.doubleValue();
        label.setText(text.substring(0, Math.min(22, text.length())));
    }
    public GouverneControllerPane(Vue3D vue){
        super(vue);
        Label titre = new Label("Commandes de vol:");
        setColumnSpan(titre, 4);
        setRowIndex(titre, 0);

        profInfo = new Label("Prof (°)");
        setRowIndex(profInfo, 3);
        setColumnIndex(profInfo, 1);
        profs = new TextField(Double.toString(Modele.getInstance().getDm()));
        profs.setMaxSize(50, 30);
        setRowIndex(profs,2);
        setColumnIndex(profs,1);
        //setColumnSpan(profs, 2);
        NumberStringConverter scProf = new NumberStringConverter();


        prof = new Slider(govMin, govMax, 0);
        prof.setStyle(" -fx-color: RED;");
        prof.valueProperty().bindBidirectional(Modele.getInstance().getDmProperty());
        prof.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Profondeur (deg): ", profInfo, t1);
            if (!resetting){
                //profs.setText(Double.toString(prof.getValue()));
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(prof, 1);
        setColumnIndex(prof, 1);
        //Binding bidirectionnel Slider-Textfield
        Bindings.bindBidirectional(profs.textProperty(), prof.valueProperty(), scProf);

        dirInfo = new Label("Dir (°)");
        setRowIndex(dirInfo, 3);
        setColumnIndex(dirInfo, 4);
        dirs = new TextField(Double.toString(Modele.getInstance().getDn()));
        //setColumnSpan(dirs, 2);
        dirs.setMaxSize(50, 30);
        setRowIndex(dirs,3);
        setColumnIndex(dirs,3);
        NumberStringConverter scDir = new NumberStringConverter();

        dir = new Slider(govMin, govMax, 0);
        dir.valueProperty().bindBidirectional(Modele.getInstance().getDnProperty());
        dir.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Direction (deg): ", dirInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(dir, 3);
        setColumnIndex(dir, 2);
        //Binding bidirectionnel Textfield-Slider
        Bindings.bindBidirectional(dirs.textProperty(), dir.valueProperty(), scDir);

        //Contrôle des ailerons
        ailInfo = new Label("Ail (°)");
        ailInfo.setStyle("-fx-text-color: RED;");
        setRowIndex(ailInfo, 2);
        setColumnIndex(ailInfo, 4);
        ails = new TextField(Double.toString(Modele.getInstance().getDm()));
        //setColumnSpan(ails, 2);
        ails.setMaxSize(50, 30);
        setRowIndex(ails,2);
        setColumnIndex(ails,3);
        NumberStringConverter scAil = new NumberStringConverter();

        ailerons = new Slider(govMin, govMax, 0);
        ailerons.setStyle(" -fx-color: RED;");
        ailerons.valueProperty().bindBidirectional(Modele.getInstance().getDlProperty());
        ailerons.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Ailerons (deg): ", ailInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(ailerons, 2);
        setColumnIndex(ailerons, 2);
        //Binding bidirectionnel slider-Textfield
        Bindings.bindBidirectional(ails.textProperty(), ailerons.valueProperty(), scAil);

        prof.setOrientation(Orientation.VERTICAL);

        triml = new Label("Trim (°)");
        setRowIndex(triml, 3);
        setColumnIndex(triml, 0);

        trims = new Slider(trimMin, trimMax, 0);
        trims.setOrientation(Orientation.VERTICAL);
        setRowIndex(trims, 1);
        setColumnIndex(trims, 0);
        trims.valueProperty().bindBidirectional(Modele.getInstance().getTrimProperty());
        trims.valueProperty().addListener(((observableValue, number, t1) -> {
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        trimt = new TextField(Double.toString(Modele.getInstance().getTrim()));
        //setColumnSpan(trimt, 2);
        trimt.setMaxSize(50, 30);
        setRowIndex(trimt, 2);
        setColumnIndex(trimt, 0);
        NumberStringConverter scTri = new NumberStringConverter();
        Bindings.bindBidirectional(trimt.textProperty(), trims.valueProperty(), scTri);

        MiniManche manche = new MiniManche();
        manche.setMaxSize(140, 140);
        manche.profPropProperty().bindBidirectional(prof.valueProperty());
        manche.ailPropProperty().bindBidirectional(ailerons.valueProperty());
        setColumnIndex(manche, 2);
        setRowIndex(manche, 1);

        getChildren().addAll(titre, prof, dir, ailerons, profInfo, dirInfo, ailInfo,profs,dirs,ails, triml, trims, trimt, manche);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");

        setVgap(5);
        setAlignment(Pos.CENTER);

        for (Node node: getChildren()){
            setHgrow(node, Priority.ALWAYS);
            setVgrow(node, Priority.ALWAYS);
        }
    }

    public void reset(){
        resetting = true;
        Modele.getInstance().setDl(0);
        Modele.getInstance().setDm(0);
        Modele.getInstance().setDn(0);
        Modele.getInstance().setTrim(0);
        resetting = false;
    }
}
