package com.waving.managers;

import com.waving.gamestate.WavingLevelLoader;
import com.waving.generator.Block;
import com.waving.generator.TileManager;
import com.waving.generator.World;
import com.waving.main.Main;
import com.waving.movableObjects.Player;
import my.project.gop.main.Light;
import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HUDmanager {

    private BufferedImage lightMap = new BufferedImage(100 * Block.blockSize, 100 * Block.blockSize,
            BufferedImage.TYPE_INT_ARGB);

    private ArrayList<Light> lights = new ArrayList<>();

    private Vector2F lightMapPos = new Vector2F();

    private World world;

    public HUDmanager(World world) {
        this.world = world;

        //addLights();

        //light = loadImageFrom.LoadImageFrom(Main.class, "light.png");
    }
    /*
    public static Polygon up;
    public static Polygon down;
    public static Polygon right;
    public static Polygon left;
    */

    private void addLights() {

        lights.add(new Light(world.getPlayer().getPos().xPos, world.getPlayer().getPos().yPos, 100, 120));
    }

    public void updateLights() {

        Graphics2D g = null;
        if (g == null) {

            g = (Graphics2D) lightMap.getGraphics();
        }

        g.setColor(new Color(0, 0, 0, 255));
        g.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());
        g.setComposite(AlphaComposite.DstOut);

        for (Light light : lights) {
            light.render(g);
        }
        g.dispose();
    }

    public void render(Graphics2D g) {
        /*updateLights();

        g.drawImage(lightMap, (int)lightMapPos.getWorldLocation().xPos, (int)lightMapPos.getWorldLocation().yPos, null);
        */

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.width, Main.height/10);
        g.fillRect(0,630, Main.width, Main.height/10);
        g.fillRect(0, 0, Main.width/10, Main.height);
        g.fillRect(1250, 0, Main.width/10, Main.height);
        g.setColor(Color.WHITE);

        //g.drawImage(light, 0, 0, Main.width, Main.height, null);

        if (Player.isDebugging()) {
            g.drawString("[DEBUG]", 20, 20);
            g.drawString("[MapXpos]" + world.getWorldXpos(), 20, 50);
            g.drawString("[MapYpos]" + world.getWorldYpos(), 20, 80);
            g.drawString("[LoadedBlocks]" + TileManager.getBlocks().size(), 20, 110);
            g.drawString("[MAIN.WIDTH]" + Main.width, 20, 140);
            g.drawString("[MAIN.HEIGHT]" + Main.height, 20, 170);
            g.drawString("[WORLD.NAME]" + WavingLevelLoader.getWorldName(), 20, 200);
        }

        /*
        int[] ux = new int[]{Main.width-1, Main.width/2, Main.width/2, 0};
        int[] uy = new int[]{0, Main.height/2, Main.height/2, 0};
        up = new Polygon(ux, uy, ux.length);


        int[] dx = new int[]{Main.width - 1, Main.width / 2, Main.width / 2 , 0};
        int[] dy = new int[]{Main.height - 1, Main.height / 2, Main.height / 2, Main.height - 1};
        down = new Polygon(dx, dy, dx.length);

        int[] lx = new int[]{0, Main.width / 2, Main.width / 2, 0};
        int[] ly = new int[]{Main.height,Main.height / 2,Main.height / 2,0};
        left = new Polygon(lx, ly, lx.length);

        int[] rx = new int[]{Main.width - 1,Main.width / 2,Main.width / 2,Main.width - 1};
        int[] ry = new int[]{Main.height ,Main.height / 2,Main.height / 2,0};
        right = new Polygon(rx, ry, rx.length);


        g.drawPolygon(up);
        g.drawPolygon(down);
        g.drawPolygon(left);
        g.drawPolygon(right);
        */
    }
}
