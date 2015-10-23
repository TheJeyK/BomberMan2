package com.waving.gamestate;

import com.waving.gamestates.GameState;
import com.waving.gamestates.GameStateButton;
import com.waving.gamestates.GameStateManager;
import com.waving.main.Main;
import com.waving.managers.MouseManager;

import java.awt.*;

public class QuitState extends GameState{


    //Añadir aqui cualquier boton adicional para el menu
    GameStateButton yes;
    GameStateButton no;

    MouseManager mouseManager;

    public QuitState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        mouseManager = new MouseManager();

        yes = new GameStateButton(Main.width/3, 200, "Yes!");
        no = new GameStateButton(Main.width/3, 300, "No!");
    }

    @Override
    public void tick(double deltaTime) {
        yes.tick();
        no.tick();

        if (yes.isHeldOver()) {
            if (yes.isPressed()) {
                System.exit(1);
            }
        }

        if (no.isHeldOver()) {
            if (no.isPressed()) {
                gsm.states.push(new MenuState(gsm));
                gsm.states.peek().init();
            }
        }



        mouseManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        yes.render(g);
        no.render(g);

        mouseManager.render(g);

        g.clipRect(0, 0, Main.width, Main.height);
    }
}