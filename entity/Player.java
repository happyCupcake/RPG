package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.*;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.text.Utilities;

// import com.apple.laf.ScreenMenuBar;

import java.awt.Color;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH){

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;


        this.gp = gp;
        this.keyH = keyH;

        //SETTING PLAYER BOUNDARIES, ADJUST BASED ON TILE SIZE
        solidArea = new Rectangle();
       //upper left corner of the blocked "solid" area on the character
        solidArea.x = gp.tileSize/6;
        solidArea.y = gp.tileSize/3;
        //size of the blocked "solid" area on the character
        solidArea.width = (gp.tileSize*5)/12;
        solidArea.height = (gp.tileSize*5)/12;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize*23;
        worldY = gp.tileSize *21;
        speed = 4;
        direction="up";
    }

    public void getPlayerImage(){

        up1 = setup("boy_back_1");
        up2 = setup("boy_back_2");
        down1 = setup("boy_forward_1");
        down2 = setup("boy_forward_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
        
    }

    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("imgs/" + imageName +".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;

    }

    public void update(){
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
                //worldY -= speed;
            }else if(keyH.downPressed){
                direction = "down";
                //worldY += speed;
            }else if(keyH.leftPressed){
                direction = "left";
                //worldX -= speed;
            }else if(keyH.rightPressed){
                direction = "right";
                //worldX += speed;
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false){
                switch(direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;  
                }
            }
    
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

    public void pickUpObject(int i){
        if(i != 999){

            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a Key!");
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.playSE(3);
                        hasKey --;
                        gp.obj[i] = null;
                        gp.ui.showMessage("You opened the door!");
                    }else{
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed +=1;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed up!");
                    break;
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
                //System.out.println("up1");
            }
            if(spriteNum ==2){
                image = up2;
                //System.out.println("up2");
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

        g2.drawImage(image, screenX, screenY, null);

    }
}
