package fr.kirrimk.vifa;

import java.lang.reflect.Method;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service permettant la gestion non-bloquante de la communication avec Ivy et de l'affichage des informations reçues dans l'application.
 */
public class CommunicationService extends Service<Void> {
    private Method m;

    /**
     * Constructeur d'un service de communication Ivy. 
     * @param m (Method) Soit Modele.getDescription, soit Modele.getForceAndMoment
     */
    public CommunicationService(Method m) {
        this.m = m;
    }

    /**
     * Méthode appelée par Service.start() ou service.restart()
     * Crée la tâche à effectuer dans le Thread du Service.
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                m.invoke(Modele.getInstance());
                return null;
            }
           
        };
    }
}
