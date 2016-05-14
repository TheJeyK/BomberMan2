package com.waving.managers;

import com.waving.generator.Block;
import com.waving.generator.World;
import com.waving.main.Main;
import com.waving.movableObjects.Player;
import my.project.gop.main.Vector2F;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class LightManager {

    private CopyOnWriteArrayList<LightSource> lightSources;

    public LightManager() {
        init();
    }

    public void init() {
        lightSources = new CopyOnWriteArrayList<>();

        lightSources.add(new LightSource(200, 600, 2));
        lightSources.add(new LightSource(1200, 400, 2));
        lightSources.add(new LightSource(1200, 1000, 2));
        lightSources.add(new LightSource(2300, 550, 2));
        lightSources.add(new LightSource(2300, 1000, 2));
        lightSources.add(new LightSource(1900, 1300, 2));
        lightSources.add(new LightSource(1250, 1600, 2));

    }

    public void tick() {

        /*
        for (Block block : blocks) {


            for (LightSource lightSource : lightSources) {
                if (block.intersects(lightSource.getLightDetection())) {
                    block.removeShadow(0.006F);
                } else {
                    block.addShadow(0.006F);
                }
            }

            if (block.intersects(lightSource.getLightDetection())) {
                block.removeShadow(0.006F);
            } else {
                block.addShadow(0.006F);
            }

        }
        */

        for (LightSource lightSource : lightSources) {
            lightSource.tick();
        }
    }




    public void render(Graphics2D g) {
        for (LightSource lightSource : lightSources) {
            lightSource.render(g);
        }
    }
}
