package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp){
        super(gp);
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        type = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;

        solidAreaDefaultX = solidArea.x;

        getImage();

        collision = true;
    }

    public void getImage(){
        up1 = setup("/greenslime_down1");
        up2 = setup("/greenslime_down2");
        down1 = setup("/greenslime_down1");
        down2 = setup("/greenslime_down2");
        left1 = setup("/greenslime_down1");
        left2 = setup("/greenslime_down2");
        right1 = setup("/greenslime_down1");
        right2 = setup("/greenslime_down2");

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
