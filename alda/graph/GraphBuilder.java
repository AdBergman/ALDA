package alda.graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GraphBuilder {
    MyUndirectedGraph<String> graph;
    HashMap<String, HashSet<String>> creditMap;

    public void buildGraph(MyUndirectedGraph graph) {
        creditMap = new HashMap<>();
        this.graph = graph;
        parseFileToNodesAndMap();
        connectAllNodes();
        //graphTests();
    }

    private void parseFileToNodesAndMap() {
        try {

            //BaconReader br = new BaconReader("/Users/martinsenden/Desktop/Programmering/ALDA/KevinBacon/src/alda/graph/actresses.list");
            BaconReader br = new BaconReader("/Users/peradrianbergman/Documents/ALDA/Kod/src/alda/graph/actresses.list");
            //BaconReader br = new BaconReader("/Users/peradrianbergman/Documents/ALDA/Kod/src/alda/graph/actressesTest.list");
            BaconReader.Part pr = br.getNextPart();
            String tempAct = "";
            Boolean title = false;
            String key = "";

            while (pr != null) {


                if (pr.type.toString().equals("NAME")) {
                    tempAct = pr.text.toString();
                    graph.add(tempAct);
                    pr = br.getNextPart();

                }
                if (pr.type.toString().equals("TITLE")) {
                    key = key + pr.text.toString();
                    title = true;
                    pr = br.getNextPart();
                }

                if (pr.type.toString().equals("YEAR") || pr.text.equals("????")) {
                    key = key + pr.text.toString();
                    pr = br.getNextPart();
                }
                if (pr.type.toString().equals("ID")) {
                    key = key + pr.text.toString();
                    pr = br.getNextPart();
                }
                if (pr.type.toString().equals("INFO")) {
                    pr = br.getNextPart();
                }

                if (creditMap.containsKey(key) && title) {
                    creditMap.get(key).add(tempAct);
                    graph.addCredit(tempAct, key);
                    key = "";
                    title = false;

                } else if (!creditMap.containsKey(key) && title) {
                    HashSet creditSet = new HashSet();
                    creditSet.add(tempAct);
                    creditMap.put(key, creditSet);
                    graph.addCredit(tempAct, key);
                    title = false;
                    key = "";
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void connectAllNodes() {
        HashSet<String> credits;                                                   //Note: A Node can not Link itself.
        for (UndirectedGraphNode<String> actorNode : graph.getAllNodes()) {                 //For every Actor Node
            credits = new HashSet<>(actorNode.getCredits());                                       //Get the  Actor Node credit Set
            for (String credit : credits) {                                        //For every Actor Note Credit in Set
                for (String actor : creditMap.get(credit)) {                       //Iterate through Central Credits Map that contains List of Actors
                    graph.connectNodes(actorNode, graph.getNode(actor));                 //Link Actor Node to All Actor Nodes sharing same Credit
                }
            }
        }
    }

    @Override
    public String toString() {
        return creditMap.toString();
    }

   /* private void graphTests() throws NullPointerException{
        System.out.println("\nGraph stats");
        System.out.println("Nodes: " + graph.getNumberOfNodes());
        System.out.println("Edges: " + graph.getNumberOfEdges());

        System.out.println("Bacon Number Bacon: " + graph.breadthFirstSearch("Bacon, Kevin", "Bacon, Kevin").get(0));
        System.out.println("Bacon Number Irene: " + graph.breadthFirstSearch("Bacon, Kevin", "Abadi, Maria").get(0));
        System.out.println("Bacon Number Azucena, Veronica: " + graph.breadthFirstSearch("Bacon, Kevin", "Azucena, Veronica").get(0));
        System.out.println("Bacon Number Azucena, Ayeena: " + graph.breadthFirstSearch("Bacon, Kevin", "Azucena, Ayeena").get(0));

       System.out.println("\nName: " + graph.getNode("De Paula, Tilde").getData());
        System.out.println("Node: " + graph.getNode("De Paula, Tilde"));
        System.out.println("Connected: " + graph.getNode("De Paula, Tilde").getConnectedNodes());
        for (UndirectedGraphNode<String> costar : graph.getNode("De Paula, Tilde").getConnectedNodes()){
            System.out.println(costar.getData());
            if (costar.getData().equals("Dilba")){
                System.out.println("Found!");
            }
        }
    }*/
}
