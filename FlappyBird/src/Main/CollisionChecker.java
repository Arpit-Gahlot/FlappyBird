/**
 * This class checks for collisions of the bird with obstacles
 */

package Main;

import Entity.Bird;
import ObstacleDetails.Obstacles;

public class CollisionChecker {

    //instance variables
    GamePanel gp;

    //constructor
    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
    }

    //method to check for collision
    public void checkObstacle(Bird bird, Obstacles obstacles) {

        //for checking for collision, we first need the coordinates of the bird's hitbox
        int leftBirdX = gp.bird.birdX + gp.bird.solidArea.x;
        int rightBirdX = gp.bird.birdX + gp.bird.solidArea.x + gp.bird.solidArea.width;
        int topBirdY = gp.bird.birdY + gp.bird.solidArea.y;
        int bottomBirdY = gp.bird.birdY + gp.bird.solidArea.y + gp.bird.solidArea.height;

        //check for Horizontal collision
        if (topBirdY < obstacles.targetObstacle.data.topYCoordinate * gp.tileSize
                || bottomBirdY > obstacles.targetObstacle.data.bottomYCoordinate * gp.tileSize) {
            if (obstacles.targetObstacle.data.currentXCoordinate == rightBirdX) {

                bird.collisionHappened = true;
                obstacles.collisionHappened = true;
            }
        }

        //check for Vertical collisions
        if ((rightBirdX >= obstacles.targetObstacle.data.currentXCoordinate
                && rightBirdX <= obstacles.targetObstacle.data.currentXCoordinate + gp.tileSize)
                || (leftBirdX >= obstacles.targetObstacle.data.currentXCoordinate
                && leftBirdX <= obstacles.targetObstacle.data.currentXCoordinate + gp.tileSize)) {

            //Soaring Down collision
            if (gp.bird.direction.equals("soaringDown")) {
                if (bottomBirdY >= obstacles.targetObstacle.data.bottomYCoordinate * gp.tileSize ) {

                    bird.collisionHappened = true;
                    obstacles.collisionHappened = true;
                }
            }
            //Soaring Up collision
            if (gp.bird.direction.equals("soaringUp")) {
                if (topBirdY <= obstacles.targetObstacle.data.topYCoordinate * gp.tileSize ) {

                    bird.collisionHappened = true;
                    obstacles.collisionHappened = true;
                }
            }
        }

        //if the target obstacle has passed the bird, we no longer need to check it for collision
        //Assign the next obstacle to targetObstacle
        if (obstacles.targetObstacle.data.currentXCoordinate + gp.tileSize < leftBirdX) {

            obstacles.targetObstacle = obstacles.targetObstacle.next;
            obstacles.score = obstacles.score + 1;
        }
    }
}

