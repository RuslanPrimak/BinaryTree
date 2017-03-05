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

    /**
     * Class of tree node
     */
    private class TreeNode {
        private TreeNode leftNode;
        private TreeNode rightNode;
        final private T value;

        TreeNode(T value) {
            this.value = value;
        }

        TreeNode getLeftNode() {
            return leftNode;
        }

        TreeNode getRightNode() {
            return rightNode;
        }

        T getValue() {
            return value;
        }

        void setLeftNode(TreeNode node) {
            leftNode = node;
        }

        void setRightNode(TreeNode node) {
            rightNode = node;
        }
    }

    private class NodePresenter {
        final private static char SPACER_CHAR = ' ';
        final private static char OFFSHOOT_CHAR = ' ';
        final private static char SIDESHOOT_CHAR = ' ';
        final private static char SIDEEND_CHAR = ' ';
        final private static int SELF_ROWS = 3;
        private String value;
        final private int rows;
        final private int columns;
        private NodePresenter leftNP;
        private NodePresenter rightNP;

        NodePresenter(TreeNode node) {
            if (node == null) {
                // for null node representation is two-space string
                value = "  ";
                rows = 1;
                columns = value.length();
            } else {
                leftNP = new NodePresenter(node.getLeftNode());
                rightNP = new NodePresenter(node.getRightNode());

                // for existing node representation will consist of:
                value = node.getValue().toString();                                 //             value             +1 row
                rows = Math.max(leftNP.getRows(), rightNP.getRows()) + SELF_ROWS;   //     __________┴_________      +1 row
                                                                                    //    |                    |     +1 row
                                                                                    //   left                right
                                                                                    // \left columns/ \right columns/
                                                                                    //               +1 column as spacer between
                                                                                    // left and right columns
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
            if ((row < 0) || (col < 0))
                throw new AssertionError("Overbound");

            if ((leftNP == null) || (rightNP == null)) { //children could not exist in case this presenter is for null
                return ' '; // node and so it should return only spacers
            } else {
                final int middle = columns / 2;

                if (row < SELF_ROWS) {
                    // row 0 defines parent's value
                    // row 1 defines parent's offshoot and side shoots to children
                    // row 2 defines connectors to the children
                    if (row == 0) {
                        final int offset = middle - value.length() / 2;
                        if ((col >= offset) && (col < offset + value.length())) {
                            return value.charAt(col - offset);
                        } else {
                            return ' ';
                        }
                    } else {
                        if (col < middle) {
                            if (row == 1) {
                                return col >= (leftNP.columns / 2) ? '_' : ' ';
                            } else { // row == 2
                                return col == (leftNP.columns / 2) ? '|' : ' ';
                            }
                        } else if (col > middle) {
                            if (row == 1) {
                                return col <= (middle + rightNP.columns / 2) ? '_' : ' ';
                            } else {  // row == 2
                                return col == (middle + rightNP.columns / 2) ? '|' : ' ';
                            }
                        } else {
                            assert(col == middle);
                            return row == 1 ? '|' : ' ';
                        }
                    }
                } else {
                    // get char from children
                    row -= SELF_ROWS;
                    if (col < middle) {
                        return leftNP.charAtPos(row, col);
                    } else if (col > middle) {
                        return rightNP.charAtPos(row, col - middle);
                    } else {
                        return ' ';
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

    @Override
    public String toString() {
        return new NodePresenter(root).toString();
    }

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

    public void add(T value) {
        root = add(root, value);
    }

    private boolean exists(TreeNode node, T value) {
        if (node == null) return false;

        final int cmp = value.compareTo(node.getValue());
        if (cmp > 0) {
            return exists(node.getRightNode(), value);
        } else
            return cmp == 0 || exists(node.getLeftNode(), value);
    }

    public boolean exists(T value) {
        return exists(root, value);
    }

    private TreeNode delete(TreeNode node, T value) {
        if (node == null) return null;

        final int cmp = value.compareTo(node.getValue());
        if (cmp > 0) {
            node.setRightNode(delete(node.getRightNode(), value));
        } else if (cmp < 0) {
            node.setLeftNode(delete(node.getLeftNode(), value));
        } else {
            node = (node.getRightNode() == null ? node.getLeftNode() : node.getRightNode());
        }
        return node;
    }

    public void delete(T value) {
        root = delete(root, value);
    }


    public static void main(String[] args) {
//        BinaryTree<Integer> tree = new BinaryTree<>();
//
//        tree.add(5);
//        System.out.println(tree);
//
//        tree.add(2);
//        System.out.println(tree);
//
//        tree.add(1);
//        System.out.println(tree);
//
//        tree.add(3);
//        tree.add(4);
//        tree.add(7);
//        tree.add(6);
//        tree.add(8);
//        tree.add(9);

        System.out.println("\u250c\u2500\u2534\u2500\u2510");
        System.out.println("Complete!");
    }
}
