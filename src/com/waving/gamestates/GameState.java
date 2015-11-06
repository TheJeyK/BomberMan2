package com.waving.gamestates;

import java.awt.*;

public abstract class GameState {

    protected static GameStateManager gsm;

    public GameState(GameStateManager gsm){
        this.gsm = gsm;
    }

    public abstract void tick(double deltaTime);

    public abstract void render(Graphics2D g);

    public abstract void init();
}
