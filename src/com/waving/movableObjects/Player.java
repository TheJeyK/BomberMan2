package com.waving.movableObjects;

import com.waving.gamestate.WavingLevelLoader;
import com.waving.gamestates.GameState;
import com.waving.gamestates.GameStateButton;
import com.waving.generator.Block;
import com.waving.generator.TileManager;
import com.waving.generator.World;
import com.waving.main.Animator;
import com.waving.main.Assets;
import com.waving.main.Check;
import com.waving.main.Main;
import com.waving.managers.GUImanager;
import com.waving.managers.HUDmanager;
import com.waving.managers.MouseManager;
import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Clase del personaje manejado por el jugador
 */
public class Player implements KeyListener {

    Vector2F pos;
    private World world;
    private int width = 50;
    private int height = 75;
    private static boolean up, down, left, right;
    private static boolean debug = false;
    private static boolean spawned;
    private final static float runSpeed = 6F;
    private final static float walkSpeed = 3F;
    private static float maxSpeed = 3F;
    private static long animationSpeed = 300L;
    private float speedUp = 0;
    private float speedDown = 0;
    private float speedLeft = 0;
    private float speedRight = 0;
    private float slowDown = 0.15F;
    private float fixDt = 52F/60F;
    private double laserScale = 3;
    private static boolean worldUnchanged = true;
    private static int changeCounter = 0;

    GameStateButton button1 = new GameStateButton(200, 200);
    private BufferedImage button1DefaultImage;

    private boolean shiftPressed = false;

    MouseManager playerMouseManager = new MouseManager();

    private int renderDistanceWidth = Main.width/34;
    private int renderDistanceHeight = Main.height/38;
    public static Rectangle render;
    public static Rectangle playerBox;

    private int animationState = 0;

    private HUDmanager hudManager;
    private GUImanager guiManager;
    private PlayerActions playerActions;

    /*
     * 0 = Up
     * 1 = Down
     * 2 = Right
     * 3 = Left
     * 4 = Idle
     */

    private ArrayList<BufferedImage> listUp;
    Animator ani_Up;
    private ArrayList<BufferedImage> listDown;
    Animator ani_Down;
    private ArrayList<BufferedImage> listRight;
    Animator ani_Right;
    private ArrayList<BufferedImage> listLeft;
    Animator ani_Left;
    private ArrayList<BufferedImage> listIdle;
    Animator ani_Idle;


    private boolean running;


    /**
     * Inicializacion del personaje jugador en el centro de la pantalla
     */
    public Player() {
        pos = new Vector2F(Main.width/2 - width/2, Main.height/2 - height/2);

    }

    public Vector2F getPos() {
        return pos;
    }

    public void init(World world) {

        playerActions = new PlayerActions(world);
        hudManager = new HUDmanager(world);
        guiManager = new GUImanager();
        this.world = world;

        render = new Rectangle((int)(pos.xPos - pos.getWorldLocation().xPos + pos.xPos - renderDistanceWidth *32/2 + width/2),
                (int)(pos.yPos - pos.getWorldLocation().yPos + pos.yPos - renderDistanceHeight*32/2 + height/2),
                renderDistanceWidth *32,
                renderDistanceHeight*32);

        playerBox = new Rectangle((int)(pos.xPos - pos.getWorldLocation().xPos + pos.xPos - renderDistanceWidth *32/2 + width/2),
                (int)(pos.yPos - pos.getWorldLocation().yPos + pos.yPos - renderDistanceHeight*32/2 + height/2),
                renderDistanceWidth *8,
                renderDistanceHeight*8);

        listUp = new ArrayList<BufferedImage>();
        listDown = new ArrayList<BufferedImage>();
        listRight = new ArrayList<BufferedImage>();
        listLeft = new ArrayList<BufferedImage>();
        listIdle = new ArrayList<BufferedImage>();

        listUp.add(Assets.player.getTile(0, 0, 16, 24));
        listUp.add(Assets.player.getTile(16, 0, 16, 24));
        listUp.add(Assets.player.getTile(0, 0, 16, 24));
        listUp.add(Assets.player.getTile(32, 0, 16, 24));

        listDown.add(Assets.player.getTile(16, 24, 16, 24));
        listDown.add(Assets.player.getTile(0, 24, 16, 24));
        listDown.add(Assets.player.getTile(32, 24, 16, 24));
        listDown.add(Assets.player.getTile(0, 24, 16, 24));

        listRight.add(Assets.player.getTile(0, 72, 16, 24));
        listRight.add(Assets.player.getTile(16, 72, 16, 24));
        listRight.add(Assets.player.getTile(0, 72, 16, 24));
        listRight.add(Assets.player.getTile(32, 72, 16, 24));

        listLeft.add(Assets.player.getTile(0, 48, 16, 24));
        listLeft.add(Assets.player.getTile(16, 48, 16, 24));
        listLeft.add(Assets.player.getTile(0, 48, 16, 24));
        listLeft.add(Assets.player.getTile(32, 48, 16, 24));

        listIdle.add(Assets.player.getTile(0, 96, 16, 24));
        listIdle.add(Assets.player.getTile(16, 96, 16, 24));


        //UP
        ani_Up = new Animator(listUp);
        ani_Up.setSpeed(animationSpeed);
        ani_Up.play();

        //DOWN
        ani_Down = new Animator(listDown);
        ani_Down.setSpeed(animationSpeed);
        ani_Down.play();

        //RIGHT
        ani_Right = new Animator(listRight);
        ani_Right.setSpeed(animationSpeed);
        ani_Right.play();

        //LEFT
        ani_Left = new Animator(listLeft);
        ani_Left.setSpeed(animationSpeed);
        ani_Left.play();

        //IDLE
        ani_Idle = new Animator(listIdle);
        ani_Idle.setSpeed(800);
        ani_Idle.play();

        spawned = true;
    }

    /**
     Movimiento del personaje con deteccion de colision* @param deltaTime
     */
    public void tick(double deltaTime) {

        playerMouseManager.tick();
        button1.tick();

        playerBox = new Rectangle((int)(pos.xPos - pos.getWorldLocation().xPos + pos.xPos - renderDistanceWidth *32/2 + width/2),
                (int)(pos.yPos - pos.getWorldLocation().yPos + pos.yPos - renderDistanceHeight*32/2 + height/2),
                renderDistanceWidth *8,
                renderDistanceHeight*8);

        render = new Rectangle((int)(pos.xPos - pos.getWorldLocation().xPos + pos.xPos - renderDistanceWidth *32/2 + width/2),
                (int)(pos.yPos - pos.getWorldLocation().yPos + pos.yPos - renderDistanceHeight*32/2 + height/2),
                renderDistanceWidth *32,
                renderDistanceHeight*32);


        /*
        playerBox = new Rectangle((int)(pos.xPos),
                (int)(pos.yPos),
                width,
                height);
        */

        render.setBounds(render);
        float moveAmountUp = speedUp * fixDt;
        float moveAmountDown = speedDown * fixDt;
        float moveAmountRight = speedRight * fixDt;
        float moveAmountLeft = speedLeft * fixDt;


        if (up) {

            moveMapUp(moveAmountUp);
            animationState = 0;

        } else {

            moveMapUpGlide(moveAmountUp);

        }
        if (down) {

            moveMapDown(moveAmountDown);
            animationState = 1;

        } else {

            moveMapDownGlide(moveAmountDown);

        }
        if (right) {

            moveMapRight(moveAmountRight);
            animationState = 2;

        } else {

            moveMapRightGlide(moveAmountRight);
        }
        if (left) {

            moveMapLeft(moveAmountLeft);
            animationState = 3;

        } else {
            moveMapLeftGlide(moveAmountLeft);
        }

        if (!up && !down && !left && !right) {

            animationState = 4; //standing still
        }
    }

    public boolean overlaps(Rectangle r1, Rectangle r2) {
        return r1.x < r2.x + r2.width && r1.x + r1.width > r2.x && r1.y < r2.y + r2.width && r1.y + r1.height > r2.y;
    }


    public void moveMapUp(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos),
                        (int)(pos.yPos + World.map_pos.yPos - speed)),
                new Point((int)(pos.xPos + World.map_pos.xPos + width),
                        (int)(pos.yPos + World.map_pos.yPos - speed)))) {

            if (speedUp < maxSpeed) {
                speedUp += slowDown;
            } else {
                speedUp = maxSpeed;
            }

            World.map_pos.yPos -= speed;
        } else {
            speedUp = 0;
        }
    }

    public void moveMapUpGlide(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos),
                        (int)(pos.yPos + World.map_pos.yPos - speed)),
                new Point((int)(pos.xPos + World.map_pos.xPos + width),
                        (int)(pos.yPos + World.map_pos.yPos - speed)))) {

            if (speedUp != 0) {
                speedUp -= slowDown;
                if (speedUp < 0) {
                    speedUp = 0;
                }
            }
            World.map_pos.yPos -= speed;
        } else {
            speedUp = 0;
        }
    }

    public void moveMapDown(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos),
                        (int)(pos.yPos + World.map_pos.yPos + height + speed)),
                new Point((int)(pos.xPos + World.map_pos.xPos + width),
                        (int)(pos.yPos + World.map_pos.yPos + height + speed)))) {

            if (speedDown < maxSpeed) {
                speedDown += slowDown;
            } else {
                speedDown = maxSpeed;
            }

            World.map_pos.yPos += speed;
        } else {
            speedDown = 0;
        }
    }

    public void moveMapDownRun(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos),
                        (int)(pos.yPos + World.map_pos.yPos + height + speed)),
                new Point((int)(pos.xPos + World.map_pos.xPos + width),
                        (int)(pos.yPos + World.map_pos.yPos + height + speed)))) {

            if (speedDown < runSpeed) {
                speedDown += slowDown;
            } else {
                speedDown = runSpeed;
            }

            World.map_pos.yPos += speed;
        } else {
            speedDown = 0;
        }
    }

    public void moveMapDownGlide(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos),
                        (int)(pos.yPos + World.map_pos.yPos + height + speed)),
                new Point((int)(pos.xPos + World.map_pos.xPos + width),
                        (int)(pos.yPos + World.map_pos.yPos + height + speed)))) {

            if (speedDown != 0) {
                speedDown -= slowDown;
                if (speedDown < 0) {
                    speedDown = 0;
                }
            }
            World.map_pos.yPos += speed;
        } else {
            speedDown = 0;
        }
    }

    public void moveMapRight(float speed) {


        if (!Check.CollisionPlayerBlock(
                new Point((int) (pos.xPos + World.map_pos.xPos + width + speed),
                        (int) (pos.yPos + World.map_pos.yPos)),
                new Point((int) (pos.xPos + World.map_pos.xPos + width + speed),
                        (int) (pos.yPos + World.map_pos.yPos + height)))) {

            if (speedRight < maxSpeed) {
                speedRight += slowDown;
            } else {
                speedRight = maxSpeed;
            }

            World.map_pos.xPos += speed;
        } else {
            speedRight = 0;
        }
    }

    public void moveMapRightRun(float speed) {


        if (!Check.CollisionPlayerBlock(
                new Point((int) (pos.xPos + World.map_pos.xPos + width + speed),
                        (int) (pos.yPos + World.map_pos.yPos)),
                new Point((int) (pos.xPos + World.map_pos.xPos + width + speed),
                        (int) (pos.yPos + World.map_pos.yPos + height)))) {

            if (speedRight < runSpeed) {
                speedRight += slowDown;
            } else {
                speedRight = runSpeed;
            }

            World.map_pos.xPos += speed;
        } else {
            speedRight = 0;
        }
    }

    public void moveMapRightGlide(float speed) {

        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos + width + speed),
                        (int)(pos.yPos + World.map_pos.yPos)),
                new Point((int)(pos.xPos + World.map_pos.xPos + width + speed),
                        (int)(pos.yPos + World.map_pos.yPos + height)))) {

            if (speedRight != 0) {
                speedRight -= slowDown;
                if (speedRight < 0) {
                    speedRight = 0;
                }
            }
            World.map_pos.xPos += speed;
        } else {
            speedRight = 0;
        }

    }

    public void moveMapLeft(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos - speed),
                        (int)(pos.yPos + World.map_pos.yPos + height)),
                new Point((int)(pos.xPos + World.map_pos.xPos - speed),
                        (int)(pos.yPos + World.map_pos.yPos)))) {

            if (speedLeft < maxSpeed) {
                speedLeft += slowDown;
            } else {
                speedLeft = maxSpeed;
            }

            World.map_pos.xPos -= speed;
        } else {
            speedLeft = 0;
        }
    }

    public void moveMapLeftRun(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos - speed),
                        (int)(pos.yPos + World.map_pos.yPos + height)),
                new Point((int)(pos.xPos + World.map_pos.xPos - speed),
                        (int)(pos.yPos + World.map_pos.yPos)))) {

            if (speedLeft < runSpeed) {
                speedLeft += slowDown;
            } else {
                speedLeft = runSpeed;
            }

            World.map_pos.xPos -= speed;
        } else {
            speedLeft = 0;
        }
    }

    public void moveMapLeftGlide(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + World.map_pos.xPos - speed),
                        (int)(pos.yPos + World.map_pos.yPos + height)),
                new Point((int)(pos.xPos + World.map_pos.xPos - speed),
                        (int)(pos.yPos + World.map_pos.yPos)))) {

            if (speedLeft != 0) {
                speedLeft -= slowDown;
                if (speedLeft < 0) {
                    speedLeft = 0;
                }
            }

            World.map_pos.xPos -= speed;
        } else {
            speedLeft = 0;
        }
    }


    public void render(Graphics2D g) {

        //Vista cinematografica
        /*
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.width, Main.height/6);
        g.fillRect(0,600, Main.width, Main.height/6);
        g.fillRect(0, 0, Main.width/4, Main.height);
        g.fillRect(1050, 0, Main.width/4, Main.height);
        g.setColor(Color.WHITE);
        */

        //UP
        if (animationState == 0) {
            g.drawImage(ani_Up.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (up || speedUp != 0) {

                ani_Up.update(System.currentTimeMillis());
            }
        }

        //DOWN
        if (animationState == 1) {
            g.drawImage(ani_Down.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (down || speedDown != 0) {

                ani_Down.update(System.currentTimeMillis());
            }
        }

        //RIGHT
        if (animationState == 2) {
            g.drawImage(ani_Right.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (right || speedRight != 0) {

                ani_Right.update(System.currentTimeMillis());
            }
        }

        //LEFT
        if (animationState == 3) {
            g.drawImage(ani_Left.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (left || speedLeft != 0) {

                ani_Left.update(System.currentTimeMillis());
            }
        }

        //IDLE
        if (animationState == 4) {
            g.drawImage(ani_Idle.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (!left && !right && !down && !up && speedDown == 0 && speedUp == 0 && speedLeft == 0 && speedRight == 0) {
                ani_Idle.update(System.currentTimeMillis());
            }
        }


        guiManager.render(g);
        hudManager.render(g);

        playerMouseManager.render(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            up = true;
        }
        if (key == KeyEvent.VK_S) {
            down = true;
        }
        if (key == KeyEvent.VK_A) {
            left = true;
        }
        if (key == KeyEvent.VK_D) {
            right = true;
        }
        if (key == KeyEvent.VK_O) {
            WavingLevelLoader.changeWorldTo("world1", "map");
            changeCounter++;
        }
        if (key == KeyEvent.VK_P) {
            WavingLevelLoader.changeWorldTo("world2", "map2");
            changeCounter++;
        }
        if (key == KeyEvent.VK_I) {
            WavingLevelLoader.changeWorldTo("world3", "map3");
            changeCounter++;
        }
        if (key == KeyEvent.VK_SHIFT) {
            //running = true;
            //animationSpeed = animationRunningSpeed;
            maxSpeed = runSpeed;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
        if (key == KeyEvent.VK_F3) {
            debug = !debug;
        }
    }


    public static boolean isDebugging() {
        return debug;
    }

    public boolean hasSpawned() {
        return spawned;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            up = false;
        }
        if (key == KeyEvent.VK_S) {
            down = false;
        }
        if (key == KeyEvent.VK_A) {
            left = false;
        }
        if (key == KeyEvent.VK_D) {
            right = false;
        }
        if (key == KeyEvent.VK_SHIFT) {
            //running = false;
            //animationSpeed = animationWalkingSpeed;
            maxSpeed = walkSpeed;
        }
    }

    public PlayerActions getPlayerActions() {
        return playerActions;
    }

    public class PlayerActions {

        private BufferedImage attackImage;
        private World world;

        public PlayerActions(World world) {
            this.world = world;
        }

        public void attackUP() {
            attackImage = Assets.getLaserAttack();
        }

        public void attackDOWN() {
            attackImage = Assets.getLaserAttack();
        }

        public void attackLEFT() {
            attackImage = Assets.getLaserAttack();
        }

        public void attackRIGHT() {
            attackImage = Assets.getLaserAttack();
        }
    }
}
