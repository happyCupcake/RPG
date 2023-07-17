package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{
    
    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage(){

        up1 = setup("npc/oldman/oldman_up1", gp.tileSize, gp.tileSize);
        up2 = setup("npc/oldman/oldman_up2", gp.tileSize, gp.tileSize);
        down1 = setup("npc/oldman/oldman_down1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/oldman/oldman_down2", gp.tileSize, gp.tileSize);
        left1 = setup("npc/oldman/oldman_left1", gp.tileSize, gp.tileSize);
        left2 = setup("npc/oldman/oldman_left2", gp.tileSize, gp.tileSize);
        right1 = setup("npc/oldman/oldman_right1", gp.tileSize, gp.tileSize);
        right2 = setup("npc/oldman/oldman_right2", gp.tileSize, gp.tileSize);
        
    }

    public void setDialogue(){
        dialogues[0] = "Hello, lass.";
        dialogues[1] = "So you've come to this island to \nfind the treasure?";
        dialogues[2] = "I used to be a great wizard, but now . . . \nI'm a bit too old to go on an adventure";
        dialogues[3] = "Well, good luck to you.";

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

    public void speak(){
        super.speak();
    }

}
