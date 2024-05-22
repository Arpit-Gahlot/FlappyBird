/**
 * This class handles the logic and functionality for the bird.
 */

package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bird {

    //instance variables
    public int birdX;
    public int birdY;
    public int speed;
    public BufferedImage soaringUp, soaringDown;
    public BufferedImage backgroundImage;
    public String direction;
    public int timeCounterUpwards;
    public int timeCounterDownwards;
    public boolean isUpKeyReady;
    public int gravity;
    public Rectangle solidArea;
    public boolean collisionHappened;

    GamePanel gp;
    KeyHandler keyH;

    //constructor
    public Bird(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 12;
        solidArea.width = 40;
        solidArea.height = 24;

        setDefaultValues();
        getBackgroundImage();
        getBirdImage();

    }

    //sets the values of the specified instance variables to default
    public void setDefaultValues() {

        birdX = 100;
        birdY = 100;
        speed = 4;
        direction = "soaringDown";
        timeCounterUpwards = 0;
        timeCounterDownwards = 0;
        isUpKeyReady = true;
        gravity = 1;
        collisionHappened = false;
    }

    //Assigns the background image to the "backgroundImage" variable
    public void getBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/PlayerDesign/backgroundImage.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Assigns the bird images to the respective variables - soaringUp and soaringDown
    public void getBirdImage() {

        try {
            soaringUp = ImageIO.read(getClass().getResourceAsStream("/PlayerDesign/BirdSoaringUp.png"));
            soaringDown = ImageIO.read(getClass().getResourceAsStream("/PlayerDesign/BirdSoaringDown.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    //This method is called 60 times each second and handles all the movements of the bird
    public void update() {
        if (!collisionHappened) {
            //if the W key is pressed, start the upward timer and launch the bird
            if (keyH.upPressed && isUpKeyReady) {
                speed = 6;
                isUpKeyReady = false;
                timeCounterUpwards = 0;
                launchUp();
            }
            else if (!keyH.upPressed) {
                isUpKeyReady = true;
            }

            //if the bird is soaring up, gravity slows it down
            if (direction.equals("soaringUp")) {
                launchUp();
                timeCounterUpwards = timeCounterUpwards + 1;
                if (timeCounterUpwards % 5 == 0) {
                    speed = speed - gravity;
                }
                if (timeCounterUpwards > 30) {
                    descend();
                    speed = 1;
                    timeCounterDownwards = 0;
                }
            }

            //if the bird is soaring downwards, gravity keeps increasing its speed
            if (direction.equals("soaringDown")) {
                timeCounterDownwards = timeCounterDownwards + 1;

                if (timeCounterDownwards % 5 == 0) {
                    speed = speed + gravity;
                }
                descend();
            }

            checkOutOfBoundary();
        }
    }

    //Draws the images (the game is a sequence of images) on the screen
    public void draw(Graphics g) {
        BufferedImage image = null;

        switch (direction) {
            case "soaringUp":
                image = soaringUp;
                break;

            case "soaringDown":
                image = soaringDown;
                break;
        }

        g.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g.drawImage(image, birdX, birdY, gp.tileSize, gp.tileSize, null);
    }

    //launches the bird up (called when W is pressed)
    public void launchUp() {
        direction = "soaringUp";
        birdY = birdY - speed;
    }

    //descends the bird after reaching max height
    public void descend() {
        direction = "soaringDown";
        birdY = birdY + speed;
    }

    //check that the bird is within the screen
    public void checkOutOfBoundary() {
        if (gp.bird.birdY + gp.bird.solidArea.y <= 0) {
            collisionHappened = true;
            gp.obstacles.collisionHappened = true;
        }

        if (gp.bird.birdY + gp.bird.solidArea.y + gp.bird.solidArea.height >= gp.screenHeight) {
            collisionHappened = true;
            gp.obstacles.collisionHappened = true;
        }
    }
}
