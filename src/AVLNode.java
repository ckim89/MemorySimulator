import java.util.ArrayList;

/**
 * The AVLNode generic class, used to create and manipulate an AVL tree.
 * @author Matt Gigliotti, Eddie Kong, Dan Kim
 *
 * @param <T>
 */
public class AVLNode<T extends Comparable<T>> {

    /**
     * The amount that the tree may be imbalanced.
     */
    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * The data in each node.
     */
    private T data;

    /**
     * A reference to the left child.
     */
    private AVLNode<T> left;

    /**
     * A reference to the right child.
     */
    private AVLNode<T> right;

    /**
     * The height of this node.
     */
    private int height;

    /**
     * The size of the tree.
     */
    private int size;


    /**
     * Construct an AVLNode with initialized data.
     * @param type the data inside the node
     */
    public AVLNode(T type) {

        this(type, null, null);

    }

    /**
     * Construct an AVLNode with initialized data, left child reference, and
     * right child reference.
     * @param type the data inside the node
     * @param lt a reference to the left child
     * @param rt a reference to the right child
     */
    public AVLNode(T type, AVLNode<T> lt, AVLNode<T> rt) {

        this.data = type;
        this.left = lt;
        this.right = rt;
        this.height = 0;
        this.size = 1;
    }

    /**
     * Return the height of the node.
     * @param t the node
     * @return the height if the node exists, -1 if it is null
     */
    public int height(AVLNode<T> t) {

        if (t == null || t.data == null) {
            return -1;
        } else {
            return t.height;
        }
    }

    /**
     * Insert a new node into the tree.
     * @param type the data inside the new node
     * @param t the recursive node parameter
     * @return the root node if insert was successful, null otherwise
     */
    public AVLNode<T> insert(T type, AVLNode<T> t) {

        if (t == null || t.data == null) {
            t = new AVLNode<>(type, null, null);
            this.size++;
            return t;
        }

        final int compareResult = type.compareTo(t.data);

        if (compareResult < 0) {
            t.left = this.insert(type, t.left);
        } else if (compareResult >= 0) {
            t.right = this.insert(type, t.right);
        } 

        return this.balance(t);
    }

    /**
     * Balance the AVL tree if necessary, by performing rotations.
     * @param t the recursive node parameter
     * @return the root node
     */
    public AVLNode<T> balance(AVLNode<T> t) {

        if (t == null || t.data == null) {
            return t;
        }

        if (this.height(t.left) - this.height(t.right) > ALLOWED_IMBALANCE) {
            if (this.height(t.left.left) >= this.height(t.left.right)) {
                t = this.rotateWithLeftChild(t);
            } else {
                t = this.doubleWithLeftChild(t);
            }
        } else if (this.height(t.right) - this.height(t.left) 
                > ALLOWED_IMBALANCE) {
            if (this.height(t.right.right) >= this.height(t.right.left)) {
                t = this.rotateWithRightChild(t);
            } else {
                t = this.doubleWithRightChild(t);
            }
        }

        t.height = Math.max(this.height(t.left), this.height(t.right)) + 1;
        return t;
    }

    /**
     * Rotate a set of nodes left.
     * @param k2 the top node to rotate
     * @return the new top node
     */
    public AVLNode<T> rotateWithLeftChild(AVLNode<T> k2) {

        final AVLNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(this.height(k2.left), this.height(k2.right)) + 1;
        k1.height = Math.max(this.height(k1.left), k2.height) + 1;
        return k1;
    }

    /**
     * Rotate a set of nodes right.
     * @param k2 the top node to rotate
     * @return the new top node
     */
    public AVLNode<T> rotateWithRightChild(AVLNode<T> k2) {

        final AVLNode<T> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max(this.height(k2.right), this.height(k2.left)) + 1;
        k1.height = Math.max(this.height(k1.right), k2.height) + 1;
        return k1;
    }

    /**
     * Perform a double left rotation on a set of nodes.
     * @param k3 the top node to rotate
     * @return the new top node
     */
    public AVLNode<T> doubleWithLeftChild(AVLNode<T> k3) {

        k3.left = this.rotateWithRightChild(k3.left);
        return this.rotateWithLeftChild(k3);
    }

    /**
     * Perform a double right rotation on a set of nodes.
     * @param k3 the top node to rotate
     * @return the new top node
     */
    public AVLNode<T> doubleWithRightChild(AVLNode<T> k3) {

        k3.right = this.rotateWithLeftChild(k3.right);
        return this.rotateWithRightChild(k3);
    }

    /**
     * Remove a node from the tree.
     * @param block the data of the node to remove
     * @param t the recursive node parameter
     * @return the root node
     */
    public AVLNode<T> remove(T block, AVLNode<T> t) {

        if (t == null || t.data == null) {
            return t; // not found
        }

        final int compareResult = block.compareTo(t.data);

        if (compareResult < 0) {
            t.left = this.remove(block, t.left);
        } else if (compareResult > 0) {
            t.right = this.remove(block, t.right);
        } else if (t.left != null && t.right != null) {
            t.data = this.findMin(t.right).data;
            t.right = this.removeMin(t.data, t.right);
            //t.right = this.remove(t.data, t.right);
            
        } else if (t.left != null) {
            t = t.left;
            this.size--;
        } else if (t.right != null) {
            t = t.right;
            this.size--;
        } else {
            t.data = null;
            this.size--;
        }

        return this.balance(t);
    }
    
    /**
     * Remove the minimum node contained in a subtree.
     * @param block the block to compare to
     * @param t the recursive node element
     * @return the updated root node
     */
    public AVLNode<T> removeMin(T block, AVLNode<T> t) {

        if (t == null || t.data == null) {
            return t; // not found
        }

        final int compareResult = block.compareTo(t.data);

        if (compareResult < 0) {
            t.left = this.removeMin(block, t.left);
        } else if (compareResult > 0) {
            t.right = this.removeMin(block, t.right);  
        } else if (t.left != null) {
            t.left = this.removeMin(block, t.left);
        } else {
            t.data = null;
            this.size--;
        }

        return this.balance(t);
    }

    /**
     * Return the best fit data, and remove it from the tree.
     * @param block the data to be allocated
     * @param t the recursive node element
     * @return the best fit size for the block
     */
    public T removeBestFit(T block, AVLNode<T> t) {

        if (t == null || t.data == null) {
            return null;
        }

        final int compareResult = block.compareTo(t.data);

        if (compareResult < 0) {
            
            if (t.left == null || t.left.data == null) {
                return t.data;
            } else if (block.compareTo(t.left.data) > 0) {
                if (block.compareTo(t.left.findMax(t.left).data) <= 0) {
                    return this.removeBestFit(block, t.left);
                } 
                return t.data;
                
            } else {
                return this.removeBestFit(block, t.left);
            } 
            

        } else if (compareResult > 0) {
            return this.removeBestFit(block, t.right);
        } else {
            return t.data;
        }
    }

    /**
     * Return the maximum element in a subtree.
     * @param t the recursive node element
     * @return the maximum element
     */
    public AVLNode<T> findMax(AVLNode<T> t) {

        if (t.right != null && t.right.data != null) {
            return this.findMax(t.right);
        }

        return t;
    }
    /**
     * Find the descendant of a node with the lowest value.
     * @param t the recursive node parameter
     * @return the minimum ancestor node
     */
    public AVLNode<T> findMin(AVLNode<T> t) {

        if (t.left != null && t.left.data != null) {
            return this.findMin(t.left);
        }


        return t;
    }

    /**
     * Return the data from a node.
     * @param t the node
     * @return the data from the node
     */
    public T getData(AVLNode<T> t) {

        return t.data;
    }

    /**
     * Return an ArrayList representation of the tree.
     * @param root the root of the tree
     * @return an ArrayList representation
     */
    public ArrayList<T> toArray(AVLNode<T> root) {

        final ArrayList<T> array = new ArrayList<T>(this.size);

        this.toArrayHelp(root, array);
        return array;

    }

    /**
     * A helper method for the toArray method.
     * @param ref a reference to the current node
     * @param array the array to add to
     */
    private void toArrayHelp(AVLNode<T> ref, ArrayList<T> array) {

        if (ref == null || ref.data == null) {
            return;
        }
        this.toArrayHelp(ref.left, array);
        array.add(ref.data);
        this.toArrayHelp(ref.right, array);
    }
    
    /**
     * Return the number of elements in the tree.
     * @return the size
     */
    public int getSize() {
        return this.size;
    }
    

    

}
