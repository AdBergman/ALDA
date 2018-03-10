package alda.graph;

import java.util.*;

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
        if (mapOfNodes.containsKey(newNode)) {
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
        UndirectedGraphNode<E> endNode = getNode(end);

         if (startNode == endNode) {
            long endTime = System.currentTimeMillis();
            System.out.println("BFS:  "+(endTime - startTime) + " ms");
            return baconNumber;
        } else {
            Set<UndirectedGraphNode<E>> toSearch = new HashSet<>(startNode.getConnectedNodes());
            Set<UndirectedGraphNode<E>> tempSet = new HashSet<>();
            Set<UndirectedGraphNode<E>> allCheckedNodes = new HashSet<>();

            while (!found) {
                if (toSearch.contains(endNode)) {
                    found = true;
                } else {
                    for (UndirectedGraphNode<E> tempNode : toSearch) {
                        tempSet.addAll(tempNode.getConnectedNodes());
                    }
                    //Optimering  //TODO: could skip tempset.removeALL first 2 or 3 runs
                    tempSet.removeAll(allCheckedNodes);
                    allCheckedNodes.addAll(tempSet);

                    toSearch = tempSet;
                    tempSet = new HashSet<>();
                }
                baconNumber++;
            }

        }
        long endTime = System.currentTimeMillis();
        System.out.println("BFS:  "+(endTime - startTime) + " ms");
        return baconNumber;       //TODO: this list only contains Kevin Bacon number.
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
