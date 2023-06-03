package main;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        
        int solidEntityTopLeft = entity.worldX + entity.solidArea.x;
        int solidEntityTopRight = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int solidEntityBottomLeft = entity.worldY + entity.solidArea.y;
        int solidEntityBottomRight = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = solidEntityTopLeft/gp.tileSize;
        int entityRightCol = solidEntityTopRight/gp.tileSize;
        int entityTopRow = solidEnityBottomLeft
    }
}
