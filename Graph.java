import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class Graph {

    private int numTowns;
    private HashMap<Character, LinkedList<Node>> adjacencyMap;


    Graph(int numTowns) {
        this.numTowns = numTowns;
        adjacencyMap = new HashMap<>();
        // Create adjacency map made of characters and linked lists
        for (char i = 0; i < numTowns; i++) {
            adjacencyMap.put((char) (i + 65), new LinkedList<>());
        }
    }

    /**
     * Adds a route from source to
     * destination with given distance.
     * @param src
     * @param dest
     * @param dist
     */
    public void addRoute(char src, char dest, int dist) {
        LinkedList<Node> list = adjacencyMap.get(src);
        Node town = new Node(dest, dist);
        list.add(town);
    }

    /**
     * Finds the total distance given a route.
     * @param route
     * @param totalDist
     * @return
     */
    public int findDistance(String route, int totalDist) {
        if (route.length() == 1) {
            return totalDist;
        }
        char src = route.charAt(0);
        char dest = route.charAt(1);
        LinkedList<Node> list = adjacencyMap.get(src);

        Boolean found = false;
        Node n = null;
        for (Iterator<Node> i = list.iterator(); i.hasNext(); ) {
            n = i.next();
            if (n.letter == dest) {
                found = true;
                break;
            }
        }
        if (found == false) {
            return -1;
        } else {
            return findDistance(route.substring(1), totalDist + n.weight);
        }
    }

    /**
     * Returns total distance or
     * prints no such route if route does not exist
     * @param route
     */
    public void printDistance(String route) {
        int totalDist = findDistance(route, 0);
        if (totalDist == -1) {
            System.out.println("NO SUCH ROUTE");
        } else {
            System.out.println(totalDist);
        }
    }

    /**
     * Finds number of trips from source to destination
     * with a certain number that represents maximum stops
     * @param src
     * @param dest
     * @param maxStops
     * @param stopCount
     * @return
     */
    public int numTrips(char src, char dest, int maxStops, int stopCount) {

        if (maxStops <= 0) {
            return 0;
        }

        LinkedList<Node> list = adjacencyMap.get(src);
        Node n = null;
        int num = 0;
        for (Iterator<Node> i = list.iterator(); i.hasNext(); ) {
            n = i.next();
            char c = n.letter;
            if (c == dest) {
                num += 1;
            } else {
                stopCount += numTrips(c, dest, maxStops -= 1, stopCount);
            }
        }

        return num + stopCount;
    }

    public int numTrips2Init(char src, char dest, int stops) {
        boolean[] visited = new boolean[26];
        return numTrips2(src, dest, stops, 0, visited);
    }

    /**
     * Finds number of trips from source to destination
     * with a certain exact number of stops.
     * @param src
     * @param dest
     * @param stops
     * @param count
     * @param visited
     * @return
     */
    public int numTrips2(char src, char dest, int stops, int count, boolean[] visited) {
        visited[src - 'A'] = true;
        if (stops <= 0) {
            return 0;
        }

        LinkedList<Node> list = adjacencyMap.get(src);
        Node n = null;
        int num = 0;
        for (Iterator<Node> i = list.iterator(); i.hasNext(); ) {

            n = i.next();
            char c = n.letter;
            if (c == dest && stops <= 1) {
                num += 1;
            } else if (!visited[c - 'A']) {
                int nextStop = stops - 1;
                count += numTrips2(c, dest, nextStop, count, visited);
            }
        }
        visited[src - 'A'] = false;

        return num + count;
    }

    /**
     * Finds the length of the shortest
     * route from source to destination.
     * @param src
     * @param dest
     * @param count
     * @return
     */
    public int shortestRoute(char src, char dest, int count) {
        if (src == dest && count != 0) {
            return 0;
        }
        LinkedList<Node> list = adjacencyMap.get(src);
        Node n = null;
        int min = Integer.MAX_VALUE;
        for (Iterator<Node> i = list.iterator(); i.hasNext(); ) {
            n = i.next();
            min = Math.min(n.weight + shortestRoute(n.letter, dest, count += 1), min);
        }
        return min;
    }

    public int diffRoutesInit(char src, char dest) {
        boolean[] visited = new boolean[26];
        return diffRoutes(src, dest,30,0, visited);
    }

    /**
     * Finds number of different routes from source to
     * destination with destination less than number
     * @param src
     * @param dest
     * @param count
     * @param distSoFar
     * @param visited
     * @return
     */
    public int diffRoutes(char src, char dest, int count, int distSoFar, boolean[] visited) {

        int num = 0;
        if(distSoFar >= count) return 0;
        if (src == dest && distSoFar != 0) {
            num+=1;
        }
        int total = 0;

            LinkedList<Node> list = adjacencyMap.get(src);
            Node n = null;

            for (Iterator<Node> i = list.iterator(); i.hasNext(); ) {
                n = i.next();
                total += diffRoutes(n.letter, dest, count, distSoFar + n.weight, visited);
            }
        return total + num;
    }

}
