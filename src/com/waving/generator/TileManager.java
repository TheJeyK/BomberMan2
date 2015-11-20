package com.waving.generator;

import com.waving.managers.LightManager;
import com.waving.movableObjects.Player;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TileManager {

    public static CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<>();

    private World world;

    LightManager lm;

    public TileManager(World world) {
        this.world = world;
        lm = new LightManager(blocks);
    }

    public void tick(double deltaTime) {
        for (Block block : blocks) {
            block.tick(deltaTime);

            if (Player.render.intersects(block.getArea())) {
                block.setIsAlive(true);
            } else {
                block.setIsAlive(false);
            }
        }

        lm.tick();
    }



    public void render(Graphics2D g) {
        for (Block block : blocks) {
            block.render(g);
        }

        lm.render(g);
    }

    public static CopyOnWriteArrayList<Block> getBlocks() {
        return blocks;
    }
}
