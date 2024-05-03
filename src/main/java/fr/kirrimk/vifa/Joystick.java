package fr.kirrimk.vifa;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import javafx.application.Platform;

/**
 * Permet au démarrage de l'application
 * la connexion d'un joystick pour
 * faire varier les paramètres dn, dx, dl, dm
 */

public class Joystick extends Thread{
	private ControllerEnvironment ce;
	private ArrayList<Controller> controllers;
	private Modele m;

	// valeurs départ joystick 
	private double dm;
	private double dn;
	private double dl;
	
	// association axes et variables
	private String axeDn;
	private String axeDl;
	private String axeDm;
	private String axeDx;
	
	// inversion axes
	private boolean boolDn;
	private boolean boolDl;
	private boolean boolDm;
	private boolean boolDx;

	
	public Joystick(Modele m){
		System.setProperty("net.java.games.input.librarypath", new File("lib/jinput-2.0.7/natives").getAbsolutePath());
		this.m = m;
		this.ce = ControllerEnvironment.getDefaultEnvironment();
		this.controllers = availableController();
		this.initAxes();
	}
	
	/**
	 * Récupération des données du joystick
	 * et modification sur l'interface
	 */
	public void run() {
		int i = 0;
		if (this.controllersEmpty()) return; // si aucun joystick -> fin
		Controller controller = this.controllers.get(0); // premier joystick
		EventQueue queue = controller.getEventQueue();
		Event event = new Event();
		while(controller.poll()) { // tant qu'il est branché
			
		    while(queue.getNextEvent(event)) {
	           Component comp = event.getComponent();
	           double value = comp.getPollData(); 
	           
	           switch(i) {
		        case 0:
		        	this.dn = value;
		        	break;
		        case 2:
		        	this.dm = value;
		        	break;
		        case 3:
		        	this.dl = value;
		        	break;
		        default:
		        	if (comp.toString().equals(this.axeDl)) {
		        	   if (value > dl+0.07 || value < dl-0.07)
			        	   Platform.runLater(new Runnable() {
			                   @Override public void run() {
			                	   if (boolDl) m.setDl(value*(-30));
			                	   else m.setDl(value*(30));
			                   }
			               });
		        	}
		        	else if (comp.toString().equals(this.axeDm)) {
		        	   if (value > dm+0.07 || value < dm-0.07)
		        		   Platform.runLater(new Runnable() {
			                   @Override public void run() {
			                	   if (boolDm) m.setDm(value*(-30));
			                	   else m.setDm(value*(30));                 
			                   }
			               });
		        		
		        	}
		        	else if (comp.toString().equals(this.axeDx)) {
		        		Platform.runLater(new Runnable() {
		                   @Override public void run() {
		                	   if (boolDx) m.setDx((-value+1)*0.5);	
		                	   else m.setDx((value+1)*(0.5));
		                   }
		               });
		        	}
		        	else if (comp.toString().equals(this.axeDn)) {
		        		if (value > dn+0.07 || value < dn-0.07)
		        		   Platform.runLater(new Runnable() {
			                   @Override public void run() {
			                	   if (boolDn) m.setDn(value*(-30));
			                	   else m.setDn(value*(30));                       
			                   }
			               });
		        	}
		        	break;
		        }
		        i++;
	        }
		
		}
	}

	/**
	 * Permet de changer les attributions de joystick 
	 * aux variables dn, dl, dm, gaz
	 */
	private void initAxes(){
		File confFile = new File ("vifa22.conf");
		
		// parametres de base
		this.axeDl = "Axe X";
		this.axeDm = "Axe Y";
		this.axeDn = "Rotation Y";
		this.axeDx = "Axe Z";
		
		this.boolDl = false;
		this.boolDm = false;
		this.boolDn = false;
		this.boolDx = false;
		
		if (!confFile.exists()) { // fichier non existant
			System.err.println("Fichier "+confFile.getName()+" introuvable"); // affichage erreur
		}
		else {
			try {
				BufferedReader lecteur = new BufferedReader(new FileReader(confFile)); // lecture
	            String ligne;
				try {
					while ((ligne = lecteur.readLine()) != null){ // jusqu'à fin du fichier
	                    String[] param = ligne.split(" , ", 2);
	                    if (ligne.charAt(0) != '#'){ // ligne pas en commentaire
	                    	String[] mots = param[0].split(" : ", 2);
	                        switch (mots[0]){
	                        case "dl":
	                        	this.axeDl = mots[1];
	                        	this.boolDl = Boolean.parseBoolean(param[1]);
	                        	break;
	                        case "dm":
	                        	this.axeDm = mots[1];
	                        	this.boolDm = Boolean.parseBoolean(param[1]);
	                        	break;
	                        case "dn":
	                        	this.axeDn = mots[1];
	                        	this.boolDn = Boolean.parseBoolean(param[1]);
	                        	break;
	                        case "dx":
	                        	this.axeDx = mots[1];
	                        	this.boolDx = Boolean.parseBoolean(param[1]);
	                        	break;
	                        default:
	                        	break;
	                        }
	                    }
					}
				}
				catch (IOException e) {
					System.err.println("Structure du fichier "+confFile.getName()+" modifié");
				}
			}
			catch(FileNotFoundException e) {
				System.err.println("Fichier "+confFile.getName()+" introuvable");
			}
		}
	}
	
	/*
	 * Regarde les joystick disponibles
	 * sur la machine
	 * @return  controllers
	 */
	public ArrayList<Controller> availableController() {
		ArrayList<Controller> controllers = new ArrayList<>();
		
		// enleve les erreurs de connexion des autres peripheriques
		PrintStream error=System.err;
	    System.setErr(new PrintStream(new OutputStream() {
	    	public void write(int a) {}
	    }));
	    
	    Controller[] cs = ce.getControllers();
	    
	    System.setErr(error); 
	    
		for (Controller i : cs) {
			if (i.getType() == Controller.Type.STICK) {
				controllers.add(i);
			}
		}
		return controllers;
	}
	
	/*
	 * Verifie la disponibilite d'un controller
	 */
	public boolean controllersEmpty() {
		return controllers.isEmpty();
	}
}
