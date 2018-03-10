package alda.graph;

import java.util.HashSet;
import java.util.Set;

public class UndirectedGraphNode<E> {
        private E data;
        private HashSet<String> credits;
        private Set<UndirectedGraphNode<E>> connectedNodes;


        public UndirectedGraphNode(E data) {
            this.data = data;
            credits = new HashSet<>();
            connectedNodes = new HashSet<>();

        }

        public boolean isConnected(UndirectedGraphNode<E> otherNode){
            return connectedNodes.contains(otherNode);
        }

        public boolean connectNode(UndirectedGraphNode<E> otherNode){
            return connectedNodes.add(otherNode);
        }

        public boolean addCredit(String production){
            return credits.add(production);
        }

        public HashSet<String> getCredits(){
            return credits;
        }

        public E getData(){
            return data;
        }

        public Set<UndirectedGraphNode<E>> getConnectedNodes(){
            return connectedNodes;
        }


}
