package com.waving.gamestate;

import com.waving.gamestates.GameState;
import com.waving.gamestates.GameStateManager;
import com.waving.generator.TileManager;
import com.waving.generator.World;
import com.waving.movableObjects.Player;

import java.awt.*;

public class WavingLevelLoader extends GameState {

    public static World world;
    private static String worldName;
    private String mapName;

    public WavingLevelLoader(GameStateManager gsm) {
        super(gsm);
    }

    public WavingLevelLoader(GameStateManager gsm, String worldname, String mapname) {
        super(gsm);
        worldName = worldname;
        this.mapName = mapname;
    }

    @Override
    public void init() {

        if (worldName == null) {
            worldName = "world1";
            mapName = "map";
        }

        world = new World(worldName, this, gsm);
        world.setSize(100, 100);
        world.setWorldSpawn(1, 4);
        world.addPlayer(new Player());
        world.init();
        world.generate(mapName);
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

    public static void changeWorldTo(String worldName, String map) {

        TileManager.getBlocks().clear();
        WavingLevelLoader.worldName = worldName;
        world.generate(map);
    }

    public static String getWorldName() {
        return worldName;
    }
}
