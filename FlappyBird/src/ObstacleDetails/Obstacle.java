/**
 * This class is used for initializing every obstacle
 */

package ObstacleDetails;

import java.util.Random;


public class Obstacle {

    //instance variables
    Random random;
    public int topYCoordinate;
    public int bottomYCoordinate;
    public int currentXCoordinate;

    //constructor
    public Obstacle(int currentXCoordinate){
        random = new Random();
        this.currentXCoordinate = currentXCoordinate;
        setDefaultValues();
        obstacleYPositions();
    }

    //sets the default values to the specified instance variables
    public void setDefaultValues() {

        topYCoordinate = 0;
        bottomYCoordinate = 0;
    }

    //randomly assigns a gap position near the middle of the obstacle
    public void obstacleYPositions() {

        int gapYPosition = random.nextInt(2,6);
        topYCoordinate = gapYPosition - 1; //The last part of the upper obstacle
        bottomYCoordinate = gapYPosition + 3; //The first part of the bottom obstacle
    }
}
