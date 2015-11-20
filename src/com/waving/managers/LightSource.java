package com.waving.managers;

import com.waving.generator.Block;
import com.waving.generator.TileManager;
import my.project.gop.main.Vector2F;

import java.awt.*;

public class LightSource {

    private Vector2F lightLocation = new Vector2F();
    private int lightSize = 54;
    private double lightDistance;

    private Rectangle lightDetection;

    public LightSource(Vector2F pos) {
        this.lightLocation = pos;
        this.lightDistance = lightSize*5;

        lightDetection = new Rectangle(
                (int)(lightLocation.getWorldLocation().xPos - lightDistance/2 + lightSize/2),
                (int)(lightLocation.getWorldLocation().yPos - lightDistance/2 + lightSize/2),
                (int)lightDistance,
                (int)lightDistance);
    }

    public LightSource(int xpos, int ypos, double lightDistance) {
        this.lightLocation.xPos = xpos;
        this.lightLocation.yPos = ypos;
        this.lightDistance = lightDistance*lightSize;

        lightDetection = new Rectangle(
                (int)(lightLocation.getWorldLocation().xPos - lightDistance/2 + lightSize/2),
                (int)(lightLocation.getWorldLocation().yPos - lightDistance/2 + lightSize/2),
                (int)lightDistance,
                (int)lightDistance);
    }

    public LightSource(int xpos, int ypos) {
        this.lightLocation.xPos = xpos;
        this.lightLocation.yPos = ypos;
        this.lightDistance = lightSize*5;

        lightDetection = new Rectangle(
                (int)(lightLocation.getWorldLocation().xPos - lightDistance/2 + lightSize/2),
                (int)(lightLocation.getWorldLocation().yPos - lightDistance/2 + lightSize/2),
                (int)lightDistance,
                (int)lightDistance);
    }

    public void tick() {
        lightDetection = new Rectangle(
                (int)(lightLocation.xPos - lightSize*lightDistance/2 + lightSize/2),
                (int)(lightLocation.yPos - lightSize*lightDistance/2 + lightSize/2),
                (int)(lightSize*lightDistance),
                (int)(lightSize*lightDistance));
        lightLocation.xPos++;
    }

    public void render(Graphics2D g) {

        for (Block block : TileManager.getBlocks()) {
            if (block != null) {
                if (lightDetection != null) {
                    if (lightDetection.intersects(block.getBounds())) {
                        float distance = (float) Vector2F.getDistanceOnScreen(lightLocation, block.getBlockLocation());

                        for (int dist = 48; dist < lightDistance*54; dist++){
                            if (distance < dist) {
                                switch (dist) {
                                    case 54:
                                        if (block.getLightLevel() > 0.20) {
                                            block.removeShadow(0.045F);
                                        }
                                        break;

                                    case 54*2:
                                        if (block.getLightLevel() > 0.30) {
                                            block.removeShadow(0.035F);
                                        }
                                        break;

                                    case 54*3:
                                        if (block.getLightLevel() > 0.40) {
                                            block.removeShadow(0.025F);
                                        }
                                        break;

                                    case 54*4:
                                        if (block.getLightLevel() > 0.50) {
                                            block.removeShadow(0.015F);
                                        }
                                        break;

                                    case 54*5:
                                        if (block.getLightLevel() > 0.60) {
                                            block.removeShadow(0.009F);
                                        }
                                        break;

                                    case 54*6:
                                        if (block.getLightLevel() > 0.70) {
                                            block.removeShadow(0.008F);
                                        }
                                        break;

                                    case 54*7:
                                        if (block.getLightLevel() > 0.80) {
                                            block.removeShadow(0.007F);
                                        }
                                        break;

                                    case 54*8:
                                        if (block.getLightLevel() > 0.90) {
                                            block.removeShadow(0.006F);
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }

        //g.fillRect((int)lightLocation.getWorldLocation().xPos, (int)lightLocation.getWorldLocation().yPos, lightSize, lightSize);


        /*g.drawRect(
                (int)(lightLocation.getWorldLocation().xPos - (lightDistance/2) + (lightSize/2)),
                (int)(lightLocation.getWorldLocation().yPos - (lightDistance/2) + (lightSize/2)),
                (int)lightDistance,
                (int)lightDistance);
                */
    }

    public Vector2F getLightLocation() {
        return lightLocation;
    }

    public Rectangle getLightDetection() {
        return lightDetection;
    }
}
