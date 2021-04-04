package controller;

import model.Model;
import view.GUI;

public class Controller {
    private GUI gui;
    private Model model;

    public Controller() {
        model = new Model();
    }

    public void run(){
        gui = new GUI(this);
    }

    public Model getModel() {
        return model;
    }
}
