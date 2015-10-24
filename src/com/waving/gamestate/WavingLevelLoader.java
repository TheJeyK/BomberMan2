package com.waving.gamestate;

import com.waving.gamestates.GameState;
import com.waving.gamestates.GameStateManager;
import com.waving.generator.World;
import com.waving.movableObjects.Player;

import java.awt.*;

public class WavingLevelLoader extends GameState {

    public WavingLevelLoader(GameStateManager gsm) {
        super(gsm);
    }

    World world;

    @Override
    public void init() {
        world = new World("World1");
        world.setSize(100, 100);
        world.setWorldSpawn(1, 4);
        world.addPlayer(new Player());
        world.init();
        world.generate("map");
    }

    @Override
    public void tick(double deltaTime) {
        if (world.isGenerated()) {
            world.tick(deltaTime);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (world.isGenerated()) {
            world.render(g);
        }
    }

}
