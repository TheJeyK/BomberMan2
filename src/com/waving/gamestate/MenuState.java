package com.waving.gamestate;

import com.waving.gamestates.GameState;
import com.waving.gamestates.GameStateButton;
import com.waving.gamestates.GameStateManager;
import com.waving.main.Assets;
import com.waving.main.Main;
import com.waving.managers.MouseManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.waving.gamestates.GameStateButton.ButtonType.*;

public class MenuState extends GameState{


    //Añadir aqui cualquier boton adicional para el menu
    GameStateButton startGame;
    GameStateButton multiPlayer;
    GameStateButton options;
    GameStateButton quitGame;

    MouseManager mouseManager;

    BufferedImage image = Assets.getBackground();

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        mouseManager = new MouseManager();

        startGame = new GameStateButton(Main.width/2 -100, 200, new WavingLevelLoader(gsm), gsm, START_GAME);
        multiPlayer = new GameStateButton(Main.width/2 -100, 300, new WavingLevelLoader(gsm), gsm, MULTI_PLAYER);
        options = new GameStateButton(Main.width/2 -100, 400, new WavingLevelLoader(gsm), gsm, OPTIONS);
        quitGame = new GameStateButton(Main.width/2 -100, 500, new QuitState(gsm), gsm, QUIT);
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

        g.drawImage(image, 0, 0, Main.width, Main.height, null);

        startGame.render(g);
        multiPlayer.render(g);
        options.render(g);
        quitGame.render(g);

        mouseManager.render(g);

        //g.clipRect(0, 0, Main.width, Main.height);
    }
}
