/*
 * Created by Ruslan Primak on 3/5/17 7:08 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 3/5/17 7:08 PM
 */

import java.util.Random;

public class PrintTree {
    /**
     * Visualizing method
     *
     * @param args - input params
     */
    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>();

        System.out.println("Initial tree");
        tree.add(5);
        tree.add(2);
        tree.add(1);
        tree.add(3);
        tree.add(4);
        tree.add(7);
        tree.add(6);
        tree.add(8);
        tree.add(9);
        System.out.println(tree);

        System.out.println("After deleted item");
        tree.delete(7);
        System.out.println(tree);

        System.out.println("Random tree");
        tree = new BinaryTree<>();
        Random rnd = new Random();

        for (int i = 0; i < 20; i++) {
            tree.add(rnd.nextInt(100));
        }
        System.out.println(tree);
    }
}
