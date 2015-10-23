package com.waving.gamestates;

import com.waving.main.Assets;
import com.waving.managers.MouseManager;
import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GameStateButton extends Rectangle{

    private GameState gameState;
    private Vector2F pos = new Vector2F();
    private GameStateManager gsm;
    private boolean isHeldOver;
    private int width = 40*3;
    private int height = 40;
    private BufferedImage defaultImage;
    private String buttonMessage;

    public GameStateButton(float xpos, float ypos, GameState gameState, GameStateManager gameStateManager, String buttonMessage) {
        this.pos.xPos = xpos;
        this.pos.yPos = ypos;
        this.gameState = gameState;
        this.gsm = gameStateManager;
        this.buttonMessage = buttonMessage;
        setBounds((int)pos.xPos, (int)pos.yPos, width, height);
        defaultImage = Assets.getButton_released();
    }

    public GameStateButton(float xpos, float ypos, String buttonMessage) {
        this.pos.xPos = xpos;
        this.pos.yPos = ypos;
        this.buttonMessage = buttonMessage;
        setBounds((int)pos.xPos, (int)pos.yPos, width, height);
        defaultImage = Assets.getButton_released();
    }

    public GameStateButton(float xpos, float ypos) {
        this.pos.xPos = xpos;
        this.pos.yPos = ypos;
        setBounds((int)pos.xPos, (int)pos.yPos, width, height);
        defaultImage = Assets.getButton_released();
    }

    public void tick() {

        setBounds((int)pos.xPos, (int)pos.yPos, width, height);

        if (getBounds().contains(MouseManager.mouse)) {

            isHeldOver = true;

        } else {

            isHeldOver = false;

        }

        if (isHeldOver) {

            if (isPressed() && defaultImage != Assets.getButton_pressed()) {
                defaultImage = Assets.getButton_pressed();
            } else if (defaultImage != Assets.getButton_heldOver()) {
                defaultImage = Assets.getButton_heldOver();
            }

        } else {

            if (defaultImage != Assets.getButton_released()) {
                defaultImage = Assets.getButton_released();
            }
        }

        if (gameState != null) {
            if (isHeldOver) {
                if (isPressed()) {
                    gsm.states.push(gameState);
                    gsm.states.peek().init();
                    isHeldOver = false;
                    MouseManager.isPressed(false);
                }
            }
        }
    }

    Font font = new Font("Serif", 10, 30);

    public void render(Graphics2D g) {

        g.drawImage(defaultImage, (int)pos.xPos, (int)pos.yPos, width, height, null);

        g.setFont(font);
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, true);
        int tw = (int)font.getStringBounds(buttonMessage, fontRenderContext).getWidth();

        g.setColor(Color.BLUE);
        g.drawString(buttonMessage, (int)pos.xPos+width/2 - tw/2, (int)pos.yPos+height/2 +8);
    }

    public boolean isHeldOver() {
        return isHeldOver;
    }

    public boolean isPressed() {
        return MouseManager.isPressed();
    }

}
