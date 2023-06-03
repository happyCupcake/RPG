package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.*;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

// import com.apple.laf.ScreenMenuBar;

import java.awt.Color;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;


        this.gp = gp;
        this.keyH = keyH;

        solidArea = new Rectangle();
        //upper left corner of the blocked "solid" area on the character
        solidArea.x = (1/6) * gp.tileSize;
        solidArea.y = (1/3) * gp.tileSize;
        //size of the blocked "solid" area on the character
        solidArea.width = (2/3) * gp.tileSize;
        solidArea.height = (2/3) * gp.tileSize;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX=  gp.tileSize*23;
        worldY =gp.tileSize *21;
        speed =4;
        direction="up";
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("imgs/boy_back_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("imgs/boy_back_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("imgs/boy_forward_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("imgs/boy_forward_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("imgs/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("imgs/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("imgs/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("imgs/boy_right_2.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
                worldY -= speed;
            }else if(keyH.downPressed){
                direction = "down";
                worldY += speed;
            }else if(keyH.leftPressed){
                direction = "left";
                worldX -= speed;
            }else if(keyH.rightPressed){
                direction = "right";
                worldX += speed;
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);
    
            spriteCounter++;
            if(spriteCounter>12){
                if(spriteNum==1){
                    spriteNum=2;
                }else if(spriteNum==2){
                    spriteNum=1;
                }
                spriteCounter=0;
            }
        }
    
    }

    public void draw(Graphics2D g2){
        // g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;

        if(direction == "up"){
            if(spriteNum==1){
                image = up1;
            }
            if(spriteNum ==2){
                image = up2;
            }
        }if(direction == "down"){
            if(spriteNum==1){
                image = down1;
            }
            if(spriteNum==2){
                image = down2;
            }
        }if(direction == "left"){
            if(spriteNum==1){
                image = left1;
            }if(spriteNum==2){
                image = left2;
            }
        }if(direction == "right"){
            if(spriteNum==1){
                image = right1;
            }if(spriteNum==2){
                image = right2;
            }
            
        }

        //image = up1;

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
