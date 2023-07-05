package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{
    
    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
    }

    public void getImage(){

        up1 = setup("npc/oldman/oldman_up1");
        up2 = setup("npc/oldman/oldman_up2");
        down1 = setup("npc/oldman/oldman_down1");
        down2 = setup("npc/oldman/oldman_down2");
        left1 = setup("npc/oldman/oldman_left1");
        left2 = setup("npc/oldman/oldman_left2");
        right1 = setup("npc/oldman/oldman_right1");
        right2 = setup("npc/oldman/oldman_right2");
        
    }

    public void setAction(){
        actionClockCounter++;

        if(actionClockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick a num from 1 to 100

            if(i <= 25) {
                direction = "up";
            }if(i>25 && i<=50){
                direction = "down";
            }if(i>50 && i<=75){
                direction = "left";
            }if(i>75 && i<=100){
                direction = "right";
            }
            actionClockCounter = 0;

        }
    
    }

}