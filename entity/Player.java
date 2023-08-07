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

import java.awt.AlphaComposite;

// import com.apple.laf.ScreenMenuBar;

import java.awt.Color;

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    //public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        this.keyH = keyH;

        //SETTING PLAYER BOUNDARIES, ADJUST BASED ON TILE SIZE
        solidArea = new Rectangle();
       //upper left corner of the blocked "solid" area on the character
        solidArea.x = gp.tileSize/6;
        solidArea.y = gp.tileSize/3;
        //size of the blocked "solid" area on the character
        solidArea.width = (gp.tileSize*5)/12;
        solidArea.height = (gp.tileSize*5)/12;

        attackArea.width = 48;
        attackArea.height = 48;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize*23;
        worldY = gp.tileSize *21;
        speed = 5;
        direction="up";

        //PLAYER STATUS
        maxLife = 8;
        life = maxLife;
    }

    public void getPlayerImage(){

        up1 = setup("player/boy_back_1", gp.tileSize, gp.tileSize);
        up2 = setup("player/boy_back_2", gp.tileSize, gp.tileSize);
        down1 = setup("player/boy_forward_1", gp.tileSize, gp.tileSize);
        down2 = setup("player/boy_forward_2", gp.tileSize, gp.tileSize);
        left1 = setup("player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("player/boy_right_2", gp.tileSize, gp.tileSize);
        
    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("player/boy_attack_up1", gp.tileSize, gp.tileSize);
        attackUp2 = setup("player/boy_attack_up2", gp.tileSize, gp.tileSize);
        attackDown1 = setup("player/boy_attack_down1", gp.tileSize, gp.tileSize);
        attackDown2 = setup("player/boy_attack_down2", gp.tileSize, gp.tileSize);
        attackLeft1 = setup("player/boy_attack_left1", gp.tileSize, gp.tileSize);
        attackLeft2 = setup("player/boy_attack_left2", gp.tileSize, gp.tileSize);
        attackRight1 = setup("player/boy_attack_right1", gp.tileSize, gp.tileSize);
        attackRight2 = setup("player/boy_attack_right2", gp.tileSize, gp.tileSize);
    }

    /*public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("imgs/" + imageName +".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;

    }*/

    public void update(){

        if(attacking == true){
            attacking();
        }
        else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed ){
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

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION   
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK EVENT 
            gp.eHandler.checkEvent();

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false && keyH.enterPressed == false){
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

            keyH.enterPressed = false;
    
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

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }


    
    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;

            //save current position and solidArea data 
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //adjust the data
            //no need for this, no offset in attack images in my code
            switch(direction){
                case "up": worldY -= 20;
                    break;
                case "down": worldY += 20;
                    break;
                case "left": worldX -= 20;
                    break;
                case "right": worldX += 20;
                    break;
            }

            //solidArea has same dimensions as attackArea
            solidArea.width = attackArea.width;
            solidAreaHeight = attackArea.height;

            //check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex); 

            //restore values
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i){
        if(i != 999){

        }
    }

    public void interactNPC(int i){
        if(gp.keyH.enterPressed){
            if(i!=999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            
            }else{
                gp.playSE(7);
                attacking = true;
            }
        }
    }

    public void contactMonster(int i){
        if(i != 999){
            if(!invincible){
                gp.playSE(6);
                life --;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i){
        if(i != 999){
            if(gp.monster[i].invincible == false){
                gp.playSE(5);
                gp.monster[i].life --;
                gp.monster[i].invincible = true;

                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true; 
                }
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if(attacking){
            if(direction == "up"){
                // tempScreenY = screenY - gp.tileSize;
                if(spriteNum==1){
                    image = attackUp1;
                }
                if(spriteNum ==2){
                    image = attackUp2;
                }
            }if(direction == "down"){
                if(spriteNum==1){
                    image = attackDown1;
                }
                if(spriteNum==2){
                    image = attackDown2;
                }
            }if(direction == "left"){
                //tempScreenX = screenX - gp.tileSize;
                if(spriteNum==1){
                    image = attackLeft1;
                }if(spriteNum==2){
                    image = attackLeft2;
                }
            }if(direction == "right"){
                if(spriteNum==1){
                    image = attackRight1;
                }if(spriteNum==2){
                    image = attackRight2;
                }
                
            }

        }if(!attacking){
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

        }

        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


    }
}
