/*
 * Created by Ruslan Primak on 3/3/17 11:46 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 3/3/17 11:10 PM
 */

/**
 * Binary Tree Class
 */
public class BinaryTree<T extends Comparable<T>> {
    private TreeNode root;  // root of tree

    @Override
    public String toString() {
        return new NodePresenter(root).toString();
    }

    /**
     * Entry method for adding procedure
     *
     * @param node  - at this node adding starts
     * @param value - value to be added
     * @return - return added node or existing one
     */
    private TreeNode add(TreeNode node, T value) {
        if (node == null) return new TreeNode(value);

        final int cmp = value.compareTo(node.getValue());
        if (cmp > 0) {
            node.setRightNode(add(node.getRightNode(), value));
        } else if (cmp < 0) {
            node.setLeftNode(add(node.getLeftNode(), value));
        }
        return node;
    }

    /**
     * Adding value to the tree. Nothing happens if value already exists
     *
     * @param value to be added
     */
    public void add(T value) {
        root = add(root, value);
    }

    /**
     * Entry method for performing serach
     *
     * @param node  - at this node search starts
     * @param value - value to be looked
     * @return - true if value exists
     */
    private boolean exists(TreeNode node, T value) {
        if (node == null) return false;

        final int cmp = value.compareTo(node.getValue());
        if (cmp > 0) {
            return exists(node.getRightNode(), value);
        } else
            return cmp == 0 || exists(node.getLeftNode(), value);
    }

    /**
     * Checks if value exists
     *
     * @param value - value to be checked
     * @return - true if value exist
     */
    public boolean exists(T value) {
        return exists(root, value);
    }

    /**
     * Retrieve the first node with leftNode == null in the descending left children chain
     *
     * @param node - at this node search starts
     * @return - return first node witch has leftNode == null
     */
    private TreeNode getNodeWithLeftNull(TreeNode node) {
        return node.getLeftNode() == null ? node : getNodeWithLeftNull(node.getLeftNode());
    }

    /**
     * Entry method for deletion procedure value and reassign children
     *
     * @param node  - at this node deletion procedure starts
     * @param value - value to be deleted
     * @return changed node
     */
    private TreeNode delete(TreeNode node, T value) {
        if (node == null) return null; // in case value not found

        final int cmp = value.compareTo(node.getValue());
        if (cmp > 0) { // pass deletion to the right child
            node.setRightNode(delete(node.getRightNode(), value));
        } else if (cmp < 0) { // pass deletion to the left child
            node.setLeftNode(delete(node.getLeftNode(), value));
        } else {
            // this node has the value - this node will be replaced with right or left child
            // it depends on conditions
            // if rightNode does not exist then simply assign leftNode - even if it equals null - it means the deleted
            // node is last in the branch
            if (node.getRightNode() == null) {
                node = node.getLeftNode();
            } else {
                // if left node is null - simply assign rightNode
                if (node.getLeftNode() == null) {
                    node = node.getRightNode();
                } else {
                    // find the first leftNode of the rightNode witch equals null and assign it
                    getNodeWithLeftNull(node.getRightNode()).setLeftNode(node.getLeftNode());
                    node = node.getRightNode();
                }
            }
        }
        return node;
    }

    /**
     * Public method for deleting value from tree
     *
     * @param value - value to be deleted
     */
    public void delete(T value) {
        root = delete(root, value);
    }

    /**
     * Class of tree node
     */
    private class TreeNode {
        final private T value;
        private TreeNode leftNode;
        private TreeNode rightNode;

        TreeNode(T value) {
            this.value = value;
        }

        TreeNode getLeftNode() {
            return leftNode;
        }

        void setLeftNode(TreeNode node) {
            leftNode = node;
        }

        TreeNode getRightNode() {
            return rightNode;
        }

        void setRightNode(TreeNode node) {
            rightNode = node;
        }

        T getValue() {
            return value;
        }
    }

    /**
     * String presenter of node
     * NOTE: use Courier New as console font in IntelliJ IDEA for better visibility
     */
    private class NodePresenter {
        final private static char SPACER_CHAR = ' ';
        final private static char OFFSHOOT_CHAR = '┴';
        final private static char SIDE_SHOOT_CHAR = '─';
        final private static char LEFT_END_CHAR = '┌';
        final private static char RIGHT_END_CHAR = '┐';

        final private static int SELF_ROWS = 2;
        final private int rows;
        final private int columns;
        private String value;
        private NodePresenter leftNP;
        private NodePresenter rightNP;

        NodePresenter(TreeNode node) {
            if (node == null) {
                // for null node representation is two-space string
                value = "" + SPACER_CHAR + SPACER_CHAR;
                rows = 1;
                columns = value.length();
            } else {
                leftNP = new NodePresenter(node.getLeftNode());
                rightNP = new NodePresenter(node.getRightNode());

                // for existing node representation will consist of:
                value = node.getValue().toString();
                rows = Math.max(leftNP.getRows(), rightNP.getRows()) + SELF_ROWS;
                //             value          +1 row
                //    ┌───────┴───────┐  +1 row
                //   left                right
                // \left columns/ \right columns/
                //               +1 column as spacer
                // between left and right children
                columns = Math.max(value.length(), leftNP.getColumns() + rightNP.getColumns()) + 1;
            }

        }

        int getRows() {
            return rows;
        }

        int getColumns() {
            return columns;
        }

        /**
         * Gets char from virtual table of the tree representation
         * @param row - row for char
         * @param col - col for char
         * @return char of the virtual table
         */
        char charAtPos(int row, int col) {
            if ((leftNP == null) || (rightNP == null)) { //children could not exist in case this presenter is for null
                return SPACER_CHAR; // node and so it should return only spacers
            } else {
                final int middle = columns / 2;
                final int rightOffset = leftNP.getColumns() + 1;

                if (row < SELF_ROWS) {
                    // row 0 defines parent's value
                    // row 1 defines connections from parent to children
                    if (row == 0) {
                        final int offset = middle - value.length() / 2;
                        if ((col >= offset) && (col < offset + value.length())) {
                            return value.charAt(col - offset);
                        } else {
                            return ' ';
                        }
                    } else {
                        if (col == middle) {
                            return OFFSHOOT_CHAR;
                        } else if (col == (leftNP.getColumns() / 2)) {
                            return LEFT_END_CHAR;
                        } else if (col == (rightOffset + rightNP.getColumns() / 2)) {
                            return RIGHT_END_CHAR;
                        } else if ((col > (leftNP.getColumns() / 2)) &&
                                (col < (rightOffset + rightNP.getColumns() / 2))) {
                            return SIDE_SHOOT_CHAR;
                        } else {
                            return SPACER_CHAR;
                        }
                    }
                } else {
                    // get char from children
                    row -= SELF_ROWS;
                    if (col < leftNP.getColumns()) {
                        return leftNP.charAtPos(row, col);
                    } else if (col > leftNP.getColumns()) {
                        return rightNP.charAtPos(row, col - rightOffset);
                    } else {
                        return SPACER_CHAR;
                    }
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            char[] row = new char[columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    row[j] = charAtPos(i, j);
                }
                sb.append(String.copyValueOf(row)).append("\r\n");
            }

            return sb.toString();
        }
    }

}
