package com.waving.gamestate;

import com.waving.gamestates.GameState;
import com.waving.gamestates.GameStateButton;
import com.waving.gamestates.GameStateManager;
import com.waving.main.Main;
import com.waving.managers.MouseManager;

import java.awt.*;

public class MenuState extends GameState{


    //Añadir aqui cualquier boton adicional para el menu
    GameStateButton startGame;
    GameStateButton multiPlayer;
    GameStateButton options;
    GameStateButton quitGame;

    MouseManager mouseManager;

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        mouseManager = new MouseManager();

        startGame = new GameStateButton(Main.width/3, 200, new WavingLevelLoader(gsm), gsm, "Start Game");
        multiPlayer = new GameStateButton(Main.width/3, 300, new WavingLevelLoader(gsm), gsm, "Multiplayer");
        options = new GameStateButton(Main.width/3, 400, new WavingLevelLoader(gsm), gsm, "Options");
        quitGame = new GameStateButton(Main.width/3, 500, new QuitState(gsm), gsm, "Quit");
    }

    @Override
    public void tick(double deltaTime) {
        startGame.tick();
        multiPlayer.tick();
        options.tick();
        quitGame.tick();

        mouseManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        startGame.render(g);
        multiPlayer.render(g);
        options.render(g);
        quitGame.render(g);

        mouseManager.render(g);

        g.clipRect(0, 0, Main.width, Main.height);
    }
}
