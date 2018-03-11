package alda.graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GraphBuilder {
    private MyUndirectedGraph<String> graph;
    private HashMap<String, HashSet<String>> creditMap; //The string is the production. The HashSet contains unique actors names as strings.

    public void buildGraph(MyUndirectedGraph graph) {
        long startTime = System.currentTimeMillis();   //Timer start
        creditMap = new HashMap<>();                   //Key: credit/production   Value: HashSet actors
        this.graph = graph;
        parseFileToNodesAndMap();                      //Parses file to create: all actor nodes in the graph, populates the creditMap and adds the credit to each actor node.
        connectAllNodes();
        long endTime = System.currentTimeMillis();
        System.out.println("Graph build time: "+(endTime - startTime) + " ms");
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
        HashSet<String> credits;                                                    //Note: A Node can not Link itself.
        for (UndirectedGraphNode<String> actorNode : graph.getAllNodes()) {         //For every Actor Node in the Graph
            credits = new HashSet<>(actorNode.getCredits());                        //Get the  Actor Node credit Set
            for (String credit : credits) {                                         //For every Actor Node Credit in Set
                for (String actor : creditMap.get(credit)) {                         //Iterate through Central Credits Map that contains List of Actors for that credit
                    graph.connectNodes(actorNode, graph.getNode(actor));             //Link Actor Node to All Actor Nodes sharing same Credit
                }
            }
        }
    }


}
