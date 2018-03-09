package alda.graph;


import java.text.Normalizer;
import java.util.Scanner;

public class Kevin6Degree {
    public static void main(String[] args) {
        boolean run = true;
        Scanner in = new Scanner(System.in);
        System.out.println("Kevin Bacon Program");
        System.out.println("-------------Loading-----------");
        MyUndirectedGraph<String> graph = new MyUndirectedGraph<>();
         GraphBuilder gb = new GraphBuilder();
         gb.buildGraph(graph);

        while (run) {
            System.out.println("Commands: ");
            System.out.println("N - Give an actors name and find its' Bacon-number");
            System.out.println("T - Give two actors names to find the Bacon-number between the two");
            System.out.println("X - Exit the program");
            System.out.println("Enter a command: ");

            switch (in.nextLine()) {
                case "T":
                case "t":
                    System.out.println("Please enter the first actor's last name: ");
                    String lastNameOne = normalizeName(in.nextLine());
                    System.out.println("Please enter first actor's first name: ");
                    String firstNameOne = normalizeName(in.nextLine());

                    String actorNameOne = lastNameOne + ", " + firstNameOne;

                    System.out.println("Please enter the second actor's last name: ");
                    String lastNameTwo = normalizeName(in.nextLine());
                    System.out.println("Please enter the second actor's first name: ");
                    String firstNameTwo = normalizeName(in.nextLine());

                    String actorNameTwo = lastNameTwo + ", " + firstNameTwo;

                    findKevinBaconNumberBetweenTwo(actorNameOne, actorNameTwo, graph);

                    break;
                case "N":
                case "n":
                    System.out.println("Please enter actor's last name: ");
                    String lastName = normalizeName(in.nextLine());
                    ;
                    System.out.println("Please enter actor's first name: ");
                    String firstName = normalizeName(in.nextLine());
                    ;

                    String actorName = lastName + ", " + firstName;
                    findKevinBaconNumber(actorName, graph);
                    break;
                case "X":
                case "x":
                    System.out.println("Good bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect command, please try again");
            }

        }
    }

    private static String normalizeName(String name) {
        if (!name.isEmpty()) {
            /*String tmp = name.substring(1, name.length());
            tmp = tmp.toLowerCase();
            String firstTmp = name.substring(0, 1);
            firstTmp = firstTmp.toUpperCase();
            name = firstTmp + tmp;*/

            name.trim();
        }
        return name;
    }

    private static void findKevinBaconNumber(String actorName, MyUndirectedGraph<String> graph) {

        if (graph.contains(actorName)) {
            System.out.println("Kevin Bacon-number for " + actorName + " is: " + graph.breadthFirstSearch(actorName, "Bacon, Kevin"));
        } else {
            System.out.println("Did not find " + actorName);
        }
    }

    private static void findKevinBaconNumberBetweenTwo(String firstActorName, String secondActorName, MyUndirectedGraph<String> graph) {
        if (!graph.contains(firstActorName)) {
            System.out.println("Did not find " + firstActorName + " in the actor list");
        } else if (!graph.contains(secondActorName)) {
            System.out.println("Did not find " + secondActorName + " in the actor list");
        } else {
            System.out.println("Kevin Bacon-number for " + firstActorName + " to " + secondActorName + " is: " + graph.breadthFirstSearch(firstActorName, secondActorName));

        }
    }
}
