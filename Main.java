import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static int towns = 0;
    static Graph graph = null;
    static String[] routes = null;

    /**
     * Main method that initializes graph and prints outputs.
     * Input takes in text file with comma separated list
     * with source, destination, and distance concatenated
     * example: "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7"
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        routes = readTextFile();

        if (routes != null) {
            towns = routes.length;
            graph = new Graph(towns);
            for (int i = 0; i < routes.length; i++) {
                String route = routes[i].replaceAll("\\s+","");
                char src = route.charAt(0);
                char dest = route.charAt(1);
                int dist = route.charAt(2) - '0';
                graph.addRoute(src, dest, dist);
            }

            graph.printDistance("ABC");
            graph.printDistance("AD");
            graph.printDistance("ADC");
            graph.printDistance("AEBCD");
            graph.printDistance("AED");
            System.out.println(graph.numTrips('C', 'C', 3, 0));
            System.out.println(graph.numTrips2Init('A', 'C', 4));
            System.out.println(graph.shortestRoute('A', 'C', 0));
            System.out.println(graph.shortestRoute('C', 'C', 0));
            System.out.println(graph.diffRoutesInit('C', 'C'));
            System.out.println("Thank you!");
        }


    }

    /**
     * This method reads a file from user input.
     * @return
     * @throws FileNotFoundException
     */
    public static String[] readTextFile() throws FileNotFoundException {
        try {
            System.out.print("Please enter the file name: ");
            Scanner in = new Scanner(System.in);
            File file = new File(in.nextLine());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            if ((line = reader.readLine()) != null) {
                routes = line.split(",");
            }
            in.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return routes;
    }
}
