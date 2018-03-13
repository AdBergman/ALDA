package alda.graph;

import java.util.*;

/**
 * @author Adrian Bergman adbe0777 bergman.adrian@gmail.com
 * @author Sebastian Backstrom Pino sebc5325 s.backstrompino@gmail.com
 * @author Martin Senden mase4691 martin.senden@gmail.com
 * @since 2018-01-19
 */

public class MyUndirectedGraph<E> implements UndirectedGraph<E> {
    private int totalEdges = 0;
    private Map<E, UndirectedGraphNode<E>> mapOfNodes = new HashMap<>();


    @Override
    public int getNumberOfNodes() {
        return mapOfNodes.size();
    }

    @Override
    public int getNumberOfEdges() {
        return totalEdges;
    }

    @Override
    public boolean add(E newNode) {
        if (mapOfNodes.containsKey(newNode)) {                          //Does not allow duplicates
            return false;
        }
        mapOfNodes.put(newNode, new UndirectedGraphNode<>(newNode));
        return true;
    }

    @Override
    public boolean connect(E node1, E node2) {
        UndirectedGraphNode<E> nodeObj1 = mapOfNodes.get(node1);
        UndirectedGraphNode<E> nodeObj2 = mapOfNodes.get(node2);
        if (nodeObj1.isConnected(nodeObj2) || nodeObj1.getData().equals(nodeObj2.getData())) {
            return false;
        }
        totalEdges++;
        return nodeObj1.connectNode(nodeObj2);    //Will iterate through all nodes and connect so bi-directional is not necessary.
    }

    @Override
    public boolean connectNodes(UndirectedGraphNode<E> nodeObj1, UndirectedGraphNode<E> nodeObj2) {
        if (nodeObj1.isConnected(nodeObj2) || nodeObj1.getData().equals(nodeObj2.getData())) {
            return false;
        }
        totalEdges++;
        return nodeObj1.connectNode(nodeObj2); //Will iterate through all nodes and connect so bi-directional is not necessary.
    }

    @Override
    public boolean isConnected(E node1, E node2) {
        UndirectedGraphNode<E> nodeObj1 = mapOfNodes.get(node1);
        UndirectedGraphNode<E> nodeObj2 = mapOfNodes.get(node2);
        return nodeObj1.isConnected(nodeObj2);
    }


    @Override
    public int breadthFirstSearch(E start, E end) {
        long startTime = System.currentTimeMillis();
        boolean found = false;
        int baconNumber = 0;
        UndirectedGraphNode<E> startNode = getNode(start);
        UndirectedGraphNode<E> endNode = getNode(end);      //this will be Kevin by standard. Optimized as start could be actor with very few roles.

         if (startNode == endNode) {                        //Check that we are looking for two different actors.
            long endTime = System.currentTimeMillis();
            System.out.println("BFS:  "+(endTime - startTime) + " ms");
            return baconNumber;
        } else {
            Set<UndirectedGraphNode<E>> toSearch = new HashSet<>(startNode.getConnectedNodes());   //Set nodes to be searched to the startNodes connected Nodes.
            Set<UndirectedGraphNode<E>> tempSet = new HashSet<>();
            Set<UndirectedGraphNode<E>> allCheckedNodes = new HashSet<>();
            allCheckedNodes.add(startNode);

            while (!found) {
                if (toSearch.contains(endNode)) {
                    found = true;
                } else {
                    for (UndirectedGraphNode<E> tempNode : toSearch) {                      //Adds all nodes that are connected to the nodes in toSearch
                        tempSet.addAll(tempNode.getConnectedNodes());                       // to the tempSet  HashSet
                    }

                    allCheckedNodes.addAll(toSearch);                                       //Add the checked nodes from toSearch to allCheckedNodes.
                    tempSet.removeAll(allCheckedNodes);                                     //Delete all previously nodes fro tempSet

                    toSearch = tempSet;
                    tempSet = new HashSet<>();

                }
                baconNumber++;
                if (System.currentTimeMillis() - startTime > 30000) return -1;              //This prevents an endless loop if there is no link.
            }                                                                               // Test this with  Macaroni, Anna
        }

        long endTime = System.currentTimeMillis();
        System.out.println("BFS:  "+(endTime - startTime) + " ms");
        return baconNumber;
    }

    public boolean addCredit(E node1, String production) {
        return mapOfNodes.get(node1).addCredit(production);
    }

    public Collection<UndirectedGraphNode<E>> getAllNodes() {
        return mapOfNodes.values();
    }

    public UndirectedGraphNode<E> getNode(E node1) {
        try{
            return mapOfNodes.get(node1);
        } catch (NullPointerException e){
            System.out.println("No such node found: " + node1 + "\nError: " + e);
        }
        return null;
    }

    public boolean contains(String actorName){
        return mapOfNodes.containsKey(actorName);
    }

    @Override
    public String toString() {
        return "Amount of nodes: " + getNumberOfNodes() + " Amount of edges: " + getNumberOfEdges();
    }
}
