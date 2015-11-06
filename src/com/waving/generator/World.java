package com.waving.generator;


import com.waving.gamestate.WavingLevelLoader;
import com.waving.gamestates.GameStateManager;
import com.waving.main.Main;
import com.waving.movableObjects.Player;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World {

    public static Vector2F map_pos = new Vector2F();

    private String worldName;
    private BufferedImage map;
    private int worldWidth;
    private int worldHeight;
    private int blockSize = 54;
    private  static Player player;

    //LISTS
    public TileManager tiles;

    //WORLD SPAWN
    private Block spawn;


    //BOOLEANS
    private boolean hasSize = false;
    private boolean generated;

    public World(String worldName, WavingLevelLoader wavingLevelLoader, GameStateManager gsm) {
        this.worldName = worldName;
        Vector2F.setWorldVariables(map_pos.xPos, map_pos.yPos);
    }

    public void init() {
        tiles = new TileManager();

        map_pos.xPos = spawn.getBlockLocation().xPos - player.getPos().xPos;
        map_pos.yPos = spawn.getBlockLocation().yPos - player.getPos().yPos;

        if (player != null) {
            player.init(this);
        }
    }

    public void tick(double deltaTime) {

        Vector2F.setWorldVariables(map_pos.xPos, map_pos.yPos);

        if (!player.hasSpawned()) {
            spawn.tick(deltaTime);
        }

        tiles.tick(deltaTime);

        if (player != null) {
            player.tick(deltaTime);
        }
    }

    public void render(Graphics2D g) {
        tiles.render(g);

        if (!player.hasSpawned()) {
            spawn.render(g);
        }

        if (player != null) {
            player.render(g);
        }
    }

    public void generate(String mapFile) {

        map = null;

        if (hasSize) {
            try {
                map = loadImageFrom.LoadImageFrom(Main.class, mapFile+".png");
            } catch (Exception ignored) {

            }

            for (int x = 0; x < worldWidth; x++) {
                for (int y = 0; y < worldHeight; y++) {

                    int col = 0;
                    if (map != null) {
                        col = map.getRGB(x, y);
                    }

                    switch (col & 0xFFFFFF) {
                        case 0x808080:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.STONE_1));
                            break;
                        case 0x202020:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.WALL_1));
                            break;
                        case 0x606060:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.ROOF_1));
                            break;
                        case 0x101010:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.INTER_WALL_1));
                            break;
                        case 0x93d6bf:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.ICE_WALL_1));
                            break;
                        case 0x26c30f:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.ICE_ROOF_1));
                            break;
                        case 0x2697f0:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.ICE_FLOOR_1));
                            break;
                        case 0xbfb4f8:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.ICE_ROAD_HORIZONTAL));
                            break;
                        case 0xd93cf0:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.ICE_ROAD_VERTICAL));
                            break;
                        case 0x403578:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.ICE_ROAD_LEFT_DOWN));
                            break;
                        case 0x6c2940:
                            TileManager.blocks.add(
                                    new Block(new Vector2F(x * Block.getBlockSize(),
                                            y * Block.getBlockSize()), Block.BlockType.ICE_ROAD_UP_RIGHT_DOWN));
                            break;
                    }
                }
            }
        }

        generated = true;
    }

    public void setSize(int world_width, int world_height) {
        this.worldWidth = world_width;
        this.worldHeight = world_height;
        hasSize = true;
    }

    public void addPlayer(Player player) {
        World.player = player;
    }

    public static Player getPlayer() {
        return player;
    }

    public Vector2F getWorldPos() {
        return map_pos;
    }

    public float getWorldXpos() {
        return map_pos.xPos;
    }

    public float getWorldYpos() {
        return map_pos.yPos;
    }

    public void setWorldSpawn(float xPos, float yPos) {
        if (xPos < worldWidth) {
            if (yPos < worldHeight) {
                this.spawn = new Block(new Vector2F(xPos*blockSize, yPos*blockSize));
            }
        }
    }

    public Vector2F getWorldSpawn() {
        return spawn.pos;
    }

    public TileManager getWorldBlocks() {
        return tiles;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void resetWorld() {
        tiles.getBlocks().clear();
        //blockEnts.clear();
        spawn = null;
    }
}
