package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Configuration;
import com.enac.vifa.vifa.Modele;
import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

/**
 * Panneau permettant de régler les vitesses de rotation de l'avion sur ses axes.
 */
public class PQRPane extends ControllerPane {

    private final Label pl;
    private final Slider ps;
    private final TextField pls;

    private final Label ql;
    private final Slider qs;
    private final TextField qls;

    private final Label rl;
    private final Slider rs;
    private final TextField rls;


    public PQRPane(Vue3D vue) {
        super(vue);
        double vMax = Configuration.getInstance().getVitesseRotMax();
        Label rpl = new Label("Vitesses de rotation:");
        setColumnSpan(rpl, 2);

        pl = new Label("P (°/s)");
        setRowIndex(pl, 1);
        pls = new TextField("0.0");
        pls.setMaxSize(50, 30);
        setRowIndex(pls, 1);
        setColumnIndex(pls, 2);
        ps = new Slider(-vMax, vMax, 0);
        ps.setStyle(" -fx-color: RED;");
        setRowIndex(ps, 1);
        setColumnIndex(ps, 1);
        ps.valueProperty().bindBidirectional(Modele.getInstance().getPProperty());
        ps.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("P (rad/s): ", pl, t1);
            vue.updateP(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sPl = new NumberStringConverter();
        Bindings.bindBidirectional(pls.textProperty(), ps.valueProperty(), sPl);

        Knob pKnob = new Knob(-vMax, vMax, 0, 55, Color.RED);
        pKnob.valueProperty().bindBidirectional(Modele.getInstance().getPProperty());
        pKnob.setMaxSize(100, 100);
        setRowIndex(pKnob, 2);
        setColumnIndex(pKnob, 1);
        pKnob.setTranslateX(16);

        ql = new Label("Q (°/s)");
        setRowIndex(ql, 4);
        qls = new TextField("0.0");
        qls.setMaxSize(50, 30);
        setRowIndex(qls, 3);
        qs = new Slider(-vMax, vMax, 0);
        qs.setOrientation(Orientation.VERTICAL);
        setRowIndex(qs, 2);
        qs.valueProperty().bindBidirectional(Modele.getInstance().getQProperty());
        qs.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Q (rad/s): ", ql, t1);
            vue.updateQ(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sQl = new NumberStringConverter();
        Bindings.bindBidirectional(qls.textProperty(), qs.valueProperty(), sQl);

        rl = new Label("R (°/s)");
        setRowIndex(rl, 4);
        setColumnIndex(rl, 2);
        rls = new TextField("0.0");
        rls.setMaxSize(50, 30);
        setRowIndex(rls, 3);
        setColumnIndex(rls, 2);
        rs = new Slider(-vMax, vMax, 0);
        rs.setOrientation(Orientation.VERTICAL);
        setRowIndex(rs, 2);
        setColumnIndex(rs, 2);
        rs.valueProperty().bindBidirectional(Modele.getInstance().getRProperty());
        rs.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("R (rad/s): ", rl, t1);
            vue.updateR(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sRl = new NumberStringConverter();
        Bindings.bindBidirectional(rls.textProperty(), rs.valueProperty(), sRl);

        getChildren().addAll(rpl,pl,pls, ps, ql,qls, qs, rl,rls, rs, pKnob);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");

        for (Node node: getChildren()){
            setHgrow(node, Priority.ALWAYS);
            setVgrow(node, Priority.ALWAYS);
        }
    }

    public void reset(){
        resetting = true;
        Modele.getInstance().setP(0);
        Modele.getInstance().setQ(0);
        Modele.getInstance().setR(0);
        resetting = false;
    }
}
