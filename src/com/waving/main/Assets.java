package com.waving.main;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

import java.awt.image.BufferedImage;

public class Assets {

    SpriteSheet blocks = new SpriteSheet();
    SpriteSheet iceblocks = new SpriteSheet();

    //BACKGROUND
    private static BufferedImage background;

    //PLAYER
    public static SpriteSheet player = new SpriteSheet();
    public static BufferedImage laserAttack;
    public static BufferedImage laserVertical;

    //MOUSE
    public static SpriteSheet mouse = new SpriteSheet();
    public static BufferedImage mouse_pressed;
    public static BufferedImage mouse_released;

    //BUTTON
    public static SpriteSheet button = new SpriteSheet();
    public static BufferedImage button_pressed;
    public static BufferedImage button_released;
    public static BufferedImage button_heldOver;

    //BUTTON TEXT
    private static BufferedImage start_game_text;
    private static BufferedImage multiPlayer_text;
    private static BufferedImage options_text;
    private static BufferedImage quit_text;
    private static BufferedImage yes_text;
    private static BufferedImage no_text;

    //MODIFICAR AL AÑADIR UN NUEVO BLOQUE
    private static BufferedImage stone_1;
    private static BufferedImage wall_1;
    private static BufferedImage roof_1;
    private static BufferedImage inter_wall_1;
    private static BufferedImage ice_wall_1;
    private static BufferedImage ice_roof_1;
    private static BufferedImage ice_floor_1;
    private static BufferedImage ice_road_horizontal;
    private static BufferedImage ice_road_vertical;
    private static BufferedImage ice_road_left_down;
    private static BufferedImage ice_road_up_right_down;

    public void init() {
        mouse.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "mouse_sprites.png"));
        blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "sprites.png"));
        iceblocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "ice_world.png"));
        player.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "playersprite.png"));
        button.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "gamestate_button.png"));

        //LASER
        laserAttack = loadImageFrom.LoadImageFrom(Main.class, "laser.png");

        //BACKGROUND
        background = loadImageFrom.LoadImageFrom(Main.class, "background.png");

        //MOUSE
        mouse_pressed = mouse.getTile(7,0, 7, 7);
        mouse_released = mouse.getTile(0, 0,7, 7);

        //BUTTON
        button_released = button.getTile(0, 0, 21, 7);
        button_heldOver = button.getTile(0, 7, 21, 7);
        button_pressed = button.getTile(0, 14, 21, 7);

        //BUTTON TEXT
        start_game_text = loadImageFrom.LoadImageFrom(Main.class, "startgame_button.png");
        multiPlayer_text = loadImageFrom.LoadImageFrom(Main.class, "multi_button.png");
        options_text = loadImageFrom.LoadImageFrom(Main.class, "options_button.png");
        quit_text = loadImageFrom.LoadImageFrom(Main.class, "quit_button.png");
        yes_text = loadImageFrom.LoadImageFrom(Main.class, "yes_button.png");
        no_text = loadImageFrom.LoadImageFrom(Main.class, "no_button.png");

        //MODIFICAR AL AÑADIR UN NUEVO BLOQUE
        stone_1 = blocks.getTile(0, 0, 16, 16);
        wall_1 = blocks.getTile(16, 0, 16, 16);
        roof_1 = blocks.getTile(32, 0, 16, 16);
        inter_wall_1 = blocks.getTile(48, 0, 16, 16);
        ice_wall_1 = iceblocks.getTile(48, 16, 16, 16);
        ice_roof_1 = iceblocks.getTile(0, 0, 16, 16);
        ice_floor_1 = iceblocks.getTile(32, 16, 16, 16);
        ice_road_horizontal = iceblocks.getTile(0, 32, 16, 16);
        ice_road_vertical = iceblocks.getTile(16, 32, 16, 16);
        ice_road_left_down = iceblocks.getTile(48, 32, 16, 16);
        ice_road_up_right_down = iceblocks.getTile(16, 48, 16 ,16);
    }

    public static BufferedImage getButton_heldOver() {
        return button_heldOver;
    }

    public static BufferedImage getButton_pressed() {
        return button_pressed;
    }

    public static BufferedImage getButton_released() {
        return button_released;
    }

    public static BufferedImage getMouse_pressed() {
        return mouse_pressed;
    }

    public static BufferedImage getMouse_released() {
        return mouse_released;
    }

    public static BufferedImage getStart_game_text() {
        return start_game_text;
    }

    public static BufferedImage getMultiPlayer_text() {
        return multiPlayer_text;
    }

    public static BufferedImage getOptions_text() {
        return options_text;
    }

    public static BufferedImage getQuit_text() {
        return quit_text;
    }

    public static BufferedImage getYes_text() {
        return yes_text;
    }

    public static BufferedImage getNo_text() {
        return no_text;
    }

    public static BufferedImage getStone_1() {
        return stone_1;
    }

    public static BufferedImage getWall_1() {
        return wall_1;
    }

    public static BufferedImage getRoof_1() {
        return roof_1;
    }

    public static BufferedImage getInter_wall_1() {
        return inter_wall_1;
    }

    public static BufferedImage getIce_wall_1() {
        return ice_wall_1;
    }

    public static BufferedImage getIce_roof_1() {
        return ice_roof_1;
    }

    public static BufferedImage getIce_floor_1() {
        return ice_floor_1;
    }

    public static BufferedImage getIce_road_horizontal() {
        return ice_road_horizontal;
    }

    public static BufferedImage getIce_road_vertical() {
        return ice_road_vertical;
    }

    public static BufferedImage getIce_road_left_down() {
        return ice_road_left_down;
    }

    public static BufferedImage getIce_road_up_right_down() {
        return ice_road_up_right_down;
    }

    public static BufferedImage getBackground() {
        return background;
    }

    public static BufferedImage getLaserAttack() {
        return laserAttack;
    }
}
