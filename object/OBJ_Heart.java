package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

    public OBJ_Heart(GamePanel gp){

        super(gp);
        name = "Heart";
        image = setup("imgs/heart_full.png");
        image2 = setup("imgs/heart_half.png");
        image3 = setup("imgs/heart_blank.png");
       
        collision = true;

    }
    
}
