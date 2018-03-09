package alda.tree;


/**
 * @author Adrian Bergman adbe0777 bergman.adrian@gmail.com
 * @author Sebastian Backstrom Pino sebc5325 s.backstrompino@gmail.com
 * @author Martin Senden mase4691 martin.senden@gmail.com
 * @since 2018-02-01
 */


public class BinarySearchTreeNode<T extends Comparable<T>> {

    private T data;
    private BinarySearchTreeNode<T> left;
    private BinarySearchTreeNode<T> right;

    public BinarySearchTreeNode(T data) {
        this.data = data;
    }

    public boolean add(T data) { //ROOT is already checked as not NULL
        int compare = data.compareTo(this.data);
        boolean success = true;
        if (compare == 0) {
            return false;
        }

        if ((compare > 0 ) && right == null){
            this.right = new BinarySearchTreeNode<>(data);
            return true;
        }

        if (compare < 0 && left == null){
            this.left = new BinarySearchTreeNode<>(data);
            return true;
        }

        if (compare > 0){
            success = right.add(data);
        }

        if (compare < 0){
            success = left.add(data);
        }
        return success;
    }

    private T findMin() {
        T returnObject = this.data;
        if (this.left != null){
            returnObject = left.findMin();
        }
        return returnObject;
    }

    /**
     * <p>
     * First we compare the </p>
     * <ul>
     * <LI></LI>
     * <LI></LI>
     * <LI></LI>
     * <LI></LI>
     * </ul>
     * @param data The index at which the element is to be added to the list.
     */

    public BinarySearchTreeNode<T> remove(T data) {
        int compare = data.compareTo(this.data);

        if (compare < 0 && left != null) {          //If Data is less -> Traverse Left if Possible
            left = left.remove(data);

        } else if (compare > 0 && right != null) {  //Else If Data is greater -> Traverse Right if Possible
            right = right.remove(data);

        } else if(compare != 0){     //Else if Data does not Match any Node -> Return
            return this;

        } else if (left != null && right != null) { //Else if Data Matches and has Two Children
            this.data = right.findMin();            //This will overwrite data with the closest higher value.
            right = right.remove(this.data);        //This will call to remove the leaf Node with that value.

        } else {                                    //Else return Right or Left to parent to re-link as removal.
            if (left != null) {
                return left;
            } else {
                return right;                       //This will link to null upon deleting leaf.
            }
        }
        return this;
    }

    public boolean contains(T data) {
            if (this.data == data) {
                return true;
            }

            int compare = data.compareTo(this.data);
            boolean nodeExists = false;

            if (compare > 0 && right!=null){
                nodeExists = (right.contains(data));
            }
            if (compare < 0 && left != null){
                nodeExists = (left.contains(data));
            }

        return nodeExists;
    }

    public int size() {
        int totalSize = 1;
        if (left != null){
            totalSize += left.size();
        }
        if (right != null){
            totalSize += right.size();
        }
        return totalSize;
    }


    public int depth() {
        int leftDepth = 0;
        int rightDepth = 0;
        if (left != null) {
            leftDepth = left.depth() + 1;
        }
        if (right != null) {
            rightDepth = right.depth() + 1;
        }
        return (leftDepth >= rightDepth) ? leftDepth : rightDepth;
    }

    public String toString() {
        String theToString = "" + data;
        if (left != null){
            theToString = left.toString() + ", " + theToString;
        }
        if (right != null){
            theToString += ", " + right.toString();
        }
        return theToString;
    }
}