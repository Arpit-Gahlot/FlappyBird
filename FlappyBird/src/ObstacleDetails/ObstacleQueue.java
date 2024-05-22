/**
 * This class handles the addition and removal of the obstacles from the obstacle Queue
 */

package ObstacleDetails;

public class ObstacleQueue {

    //A Node subclass where every Node corresponds to a single obstacle
    public class Node {
        public Obstacle data;
        public Node next = null;

        public Node(){}
    }

    //instance variables
    Node head;
    int count;
    Node tail;

    //constructor
    public ObstacleQueue() {
        head = null;
        count = 0;
        tail = null;
    }

    //adds the obstacle to the back of the queue
    public void addObstacle(Obstacle obstacleToAdd) {

        Node n = new Node();
        n.data = obstacleToAdd;

        if (head == null) {

            head = n;
            tail = n;
        }
        else if (head == tail) {

            head.next = n;
            tail = n;
        }
        else {

            tail.next = n;
            tail = tail.next;
        }
        count = count + 1;

    }

    //removes the obstacle from the front of the queue
    public void removeObstacle() {

        head = head.next;
        count = count - 1;
    }
}
