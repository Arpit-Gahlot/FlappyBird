/**
 * This class handles the logic of all the obstacles
 */

package ObstacleDetails;

import Main.GamePanel;
import java.awt.*;
import java.util.Random;

public class Obstacles {

    //instance variables
    GamePanel gp;
    Random random;
    ObstacleQueue currentObstacles;
    int timeCounter;
    public int obstacleShiftSpeed;
    public ObstacleQueue.Node targetObstacle;
    public boolean collisionHappened;
    public int score;

    //constructor
    public Obstacles(GamePanel gp){
        this.gp = gp;
        random = new Random();
        currentObstacles = new ObstacleQueue();
        timeCounter = 0;
        obstacleShiftSpeed = 3;
        collisionHappened = false;
        score = 0;
    }

    ////This method is called 60 times each second and handles all the movements of the obstacles
    public void update() {

        if (!collisionHappened) {
            //every second, add a new obstacle to the far right of the screen
            if (timeCounter % 60 == 0) {
                currentObstacles.addObstacle(new Obstacle(gp.maxScreenColumn * gp.tileSize));
            }

            //target obstacle is the one for which we have to check collision for, initially set to the first obstacle
            if (currentObstacles.count == 1) {
                targetObstacle = currentObstacles.head;
            }

            //check for collisions
            gp.cChecker.checkObstacle(gp.bird, this);

            //only 5 obstacles are shown on the screen at a time, so we can remove the previous ones
            if (currentObstacles.count > 5) {

                currentObstacles.removeObstacle();
            }

            //keep shifting the obstacles to the left as long as collision hasn't happened
            ObstacleQueue.Node currentNode = currentObstacles.head;
            while (currentNode != null) {

                currentNode.data.currentXCoordinate = currentNode.data.currentXCoordinate - obstacleShiftSpeed;
                currentNode = currentNode.next;
            }
            timeCounter++;
        }
    }

    //draws the obstacles on the screen
    public void draw(Graphics2D g2) {

        ObstacleQueue.Node currentObstacle = currentObstacles.head;

        while (currentObstacle != null) {

            drawObstacle(g2, currentObstacle.data.topYCoordinate, currentObstacle.data.bottomYCoordinate, currentObstacle.data.currentXCoordinate);
            currentObstacle = currentObstacle.next;
        }

        if (collisionHappened) {
            gameOver(g2);
            timeCounter = 0;
        }

        if (timeCounter > 0) {
            gameInstructions(g2);
            showScore(g2);
        }
    }

    public void drawObstacle(Graphics2D g, int topYCoord, int bottomYCoord, int XCoord) {

        g.setColor(Color.black);
        g.fillRect(XCoord, 0, gp.tileSize, topYCoord * gp.tileSize);
        g.fillRect(XCoord, bottomYCoord * gp.tileSize, gp.tileSize, (gp.maxScreenRow - bottomYCoord) * gp.tileSize);
    }

    //This method pops up the "Game Over" text as soon as collision happens
    public void gameOver(Graphics g) {

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        g.drawString("Game Over", gp.screenWidth/2 - 4 * gp.tileSize, gp.screenHeight/2);

        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        g.drawString("Your score is: " + score, gp.screenWidth/2 - 4 * gp.tileSize + 20, gp.screenHeight/2 + 2 * gp.tileSize);
    }

    //This method displays a small instruction at the top during the game
    public void gameInstructions(Graphics g) {

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        g.drawString("Press W to Flap", gp.screenWidth/2 - 2 * gp.tileSize, gp.tileSize);
    }

    //This method is used for displaying the score during the game
    public void showScore(Graphics g) {

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        g.drawString("Score: " + score , gp.screenWidth - 3 * gp.tileSize + 20, gp.tileSize);
    }
}
