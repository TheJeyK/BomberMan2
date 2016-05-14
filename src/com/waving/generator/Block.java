package com.waving.generator;

import com.waving.main.Assets;
import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Clase para los tipos de tiles o bloques
 */
public class Block extends Rectangle{

    Vector2F pos = new Vector2F();
    public static int blockSize = 54;
    private BlockType blockType;
    private BufferedImage block;
    private boolean isSolid = true;
    private boolean isAlive;
    private Rectangle area;
    private boolean dropped = false;
    private float lightLevel = 1;

    /**Constructor del la clase Block
     * @param pos
     * @param blockType
     */
    public Block(Vector2F pos, BlockType blockType) {
        this.pos = pos;
        this.blockType = blockType;

        //MODIFICAR CUANDO SE AÑADA UN NUEVO BLOQUE SOLIDO
        this.isSolid = this.blockType == BlockType.WALL_1 || this.blockType == BlockType.ROOF_1 ||
                this.blockType == BlockType.INTER_WALL_1 || this.blockType == BlockType.ICE_WALL_1 ||
                this.blockType == BlockType.ICE_ROOF_1;
        init();
    }

    public Block(Vector2F pos) {
        this.pos = pos;
        area = new Rectangle((int)pos.xPos, (int)pos.yPos, blockSize, blockSize);
    }

    /**
     * Inicializador que lee la imagen correspondiente al tipo de
     * bloque a partir de la clase Assets
     */

    //MODIFICAR AL AÑADIR UN NUEVO BLOQUE
    public void init() {
        area = new Rectangle((int)pos.xPos, (int)pos.yPos, blockSize, blockSize);
        switch (blockType) {
            case STONE_1:
                block = Assets.getStone_1();
                break;
            case WALL_1:
                block = Assets.getWall_1();
                break;
            case ROOF_1:
                block = Assets.getRoof_1();
                break;
            case INTER_WALL_1:
                block = Assets.getInter_wall_1();
                break;
            case NORMAL_FELICIDADES_1:
                block = Assets.getNormal_felicidades_1();
                break;
            case NORMAL_FELICIDADES_2:
                block = Assets.getNormal_felicidades_2();
                break;
            case NORMAL_FELICIDADES_3:
                block = Assets.getNormal_felicidades_3();
                break;
            case NORMAL_FELICIDADES_4:
                block = Assets.getNormal_felicidades_4();
                break;
            case NORMAL_FELICIDADES_5:
                block = Assets.getNormal_felicidades_5();
                break;
            case ICE_WALL_1:
                block = Assets.getIce_wall_1();
                break;
            case ICE_ROOF_1:
                block = Assets.getIce_roof_1();
                break;
            case ICE_FLOOR_1:
                block = Assets.getIce_floor_1();
                break;
            case ICE_ROAD_HORIZONTAL:
                block = Assets.getIce_road_horizontal();
                break;
            case ICE_ROAD_VERTICAL:
                block = Assets.getIce_road_vertical();
                break;
            case ICE_ROAD_LEFT_DOWN:
                block = Assets.getIce_road_left_down();
                break;
            case ICE_ROAD_UP_RIGHT_DOWN:
                block = Assets.getIce_road_up_right_down();
                break;
            case EXIT_ICE:
                block = Assets.getExit_ice();
                break;
            case ICE_FELICIDADES_1:
                block = Assets.getIce_felicidades_1();
                break;
            case ICE_FELICIDADES_2:
                block = Assets.getIce_felicidades_2();
                break;
            case ICE_FELICIDADES_3:
                block = Assets.getIce_felicidades_3();
                break;
            case ICE_FELICIDADES_4:
                block = Assets.getIce_felicidades_4();
                break;
            case ICE_FELICIDADES_5:
                block = Assets.getIce_felicidades_5();
                break;
        }
    }

    /**
     * Genera los bordes para cada bloque de modo que el detector de colision los pueda leer
     * @param deltaTime
     */
    public void tick(double deltaTime) {
        if (isAlive) {
            setBounds((int)(pos.xPos), (int)(pos.yPos), blockSize, blockSize);
        }
    }

    public void renderBlockLightLevel(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lightLevel));
        g.setColor(Color.BLACK);
        g.fillRect((int)pos.getWorldLocation().xPos, (int)pos.getWorldLocation().yPos, blockSize, blockSize);
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    /**
     * Renderiza o dibuja en la pantalla el bloque
     * @param g
     */
    public void render(Graphics2D g) {

        if (isAlive) {
            if (block != null) {
                g.drawImage(block, (int)pos.getWorldLocation().xPos, (int)pos.getWorldLocation().yPos, blockSize, blockSize, null);
            }
        } else if (!dropped) {
            float xpos = pos.xPos + 24 - 12;
            float ypos = pos.yPos + 24 - 12;

            Vector2F newpos = new Vector2F(xpos, ypos);

            //World.dropBlockEntity(newpos, block);

            dropped = true;
        }

    }

    /**
     * Da la informacion sobre si ese bbloque particular es solido
     * o no
     * @return
     */
    public boolean isSolid() {
        return isSolid;
    }

    public void addShadow(float amount) {
        if (lightLevel != 1) {
            if (lightLevel < 5.006000) {
                lightLevel += amount;
            }
        }

    }

    public void removeShadow(float amount) {
        if (lightLevel > 0.006000) {
            lightLevel -= amount;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Asigna al bloque si tiene que ser solido o no
     * @param isSolid
     */

    public void setIsSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Rectangle getArea() {
        return area;
    }



    public Vector2F getBlockLocation() {
        return this.pos;
    }

    /**
     * Almacena los tipos de bloques que existen. En los comentarios al frente del
     * tipo de bloque se encuentra codigo de color asignado a ese tipo de bloque
     */
    public enum BlockType {
        STONE_1, //808080
        WALL_1,  //202020
        ROOF_1, //606060
        INTER_WALL_1, //101010
        ICE_WALL_1, //93d6bf
        ICE_ROOF_1, //26c30f
        ICE_FLOOR_1, //2697f0
        ICE_ROAD_HORIZONTAL, //bfb4f8
        ICE_ROAD_VERTICAL, //d93cf0
        ICE_ROAD_LEFT_DOWN, //403578
        ICE_ROAD_UP_RIGHT_DOWN, //6c2940
        EXIT_ICE, //ff0000
        NORMAL_FELICIDADES_1, //57d957
        NORMAL_FELICIDADES_2, //574057
        NORMAL_FELICIDADES_3, //9a4057
        NORMAL_FELICIDADES_4, //3540ba
        NORMAL_FELICIDADES_5, //35eeba
        ICE_FELICIDADES_1, //403578
        ICE_FELICIDADES_2, //d93cf0
        ICE_FELICIDADES_3, //135740
        ICE_FELICIDADES_4, //d9680f
        ICE_FELICIDADES_5 //bfca87
    }

    public static int getBlockSize() {
        return blockSize;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public float getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(float lightLevel) {
        this.lightLevel = lightLevel;
    }
}
