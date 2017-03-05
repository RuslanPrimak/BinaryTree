/*
 * Created by Ruslan Primak on 3/5/17 7:32 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 3/5/17 7:32 PM
 */

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BinaryTreeTest {
    private Random rnd;

    @Before
    public void setUp() throws Exception {
        rnd = new Random();
    }

    @Test
    public void randomInteger() throws Exception {
        BinaryTree<Integer> tree = new BinaryTree<>();

        final int total = rnd.nextInt(100); // up to max elements in tree
        ArrayList<Integer> items = new ArrayList<>();

        for (int i = 0; i < total; i++) {
            Integer value = rnd.nextInt();

            if (!items.contains(value)) {
                items.add(value);
                assertFalse(tree.exists(value));
                tree.add(value);
                assertTrue(tree.exists(value));
            }
        }

        while (items.size() > 0) {
            Integer value = items.remove(rnd.nextInt(items.size()));
            tree.delete(value);
            assertFalse(tree.exists(value));
            for (Integer item : items) {
                assertTrue(tree.exists(item));
            }
        }
    }
}