/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author Tom
 */
public class Main {

    public static int k, n, d, a, b, m;
    public static int first, last;

    public static void main(String[] args) throws IOException {
        loadInput();
        first = n;
        last = n + (int) (Math.pow(2, d)) - 2;

        Tree t = new Tree(first, last);
        //t.print(t.root);
        System.out.println(t.size);
        System.out.println("---");
        // t.iter(true);
        System.out.println("---");
        // t.iter(false);
        System.out.println("###################");
//        for (int i = 0; i < t.size; i++) {
//            System.out.println(t.findByIndex(i));
//        }
        

    }

    public static void loadInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        k = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
    }
}

class Tree {

    Node root;
    Node first, last;
    private Node lastLeft;
    int size = 0;
    int finalFirst;
    int finalLast;

    public Tree(int first, int last) {
        finalFirst = first;
        finalLast = last;
        size = 0;
        root = this.createTreeI(null, first, last, 0);
        Node node = this.last;
        while (node.leftLinear != null) {
            node.leftLinear.rightLinear = node;
            node = node.leftLinear;
        }
    }

    public void remove(int index) {
        removeNode(this.findByIndex(index));
    }

    private int removeNode(Node n) {
        int RV = n.value;
        if (n.left == null && n.right == null) {            //nema zadneho potomka
            //vymažu
        } else if (n.left != null && n.right == null) {     //ma pouze leveho
            //propojím následníka s rodičem
        } else if (n.left == null && n.right != null) {     //ma pouze praveho
            //propojím následníka s rodičem
        } else {                                            //ma oba dva
            //dosadím do uzlu hodnotu lin z prava
            RV=this.removeNode(n.rightLinear);
            n.value=RV;
        }

        return RV;
    }

    /**
     * Finds Node by specified index with complexity O(N/2) in worst case.
     *
     * @param index
     * @return Node on specified index
     */
    public Node findByIndex(int index) {
        int i;
        Node n;
        if (index < (size / 2)) { //prvek je v první polovině spojáku
            System.out.println("zepředu");
            i = 0;
            n = first;
            while (i != index) {
                n = n.rightLinear;
                i++;
            }
            return n;
        } else { //prvek je v druhé polovině spojáku
            i = size - 1;
            n = last;
            System.out.println("zezadu");
            while (i != index) {
                n = n.leftLinear;
                i--;
            }
            return n;
        }
    }

    /**
     * Creates binary search tree with given parameters
     *
     * @param parent parent Node
     * @param first start index of interval
     * @param last end index of interval
     * @param depth depth of node
     * @return Created node
     */
    public Node createTreeI(Node parent, int first, int last, int depth) {
        int middle = (first + last) / 2;
        Node node = new Node(parent, depth, middle);
        size++;
        if (first != last) {
            node.left = this.createTreeI(node, first, middle - 1, depth + 1);
        }
        if (middle == finalFirst) {
            this.first = node;
        }
        if (middle == finalLast) {
            this.last = node;
        }
        node.leftLinear = lastLeft;
        lastLeft = node;
        if (first != last) {
            node.right = this.createTreeI(node, middle + 1, last, depth + 1);
        }
        return node;
    }

    public void print(Node n) {
        if (n != null) {
            print(n.left);
            System.out.println(n);
            print(n.right);
        }
    }

    public void iter(boolean b) {
        if (b) {
            Node node = this.last;
            while (node != null) {
                System.out.println(node);
                node = node.leftLinear;
            }
        } else {
            Node node = this.first;
            while (node != null) {
                System.out.println(node);
                node = node.rightLinear;
            }
        }

    }

}

class Node {

    public Integer depth, value;
    public Node left;
    public Node right;
    public Node leftLinear;
    public Node rightLinear;
    public Node parent;

    public Node(Node parent, Integer depth, Integer value) {
        this.parent = parent;
        this.depth = depth;
        this.value = value;
        this.left = null;
        this.right = null;
        this.leftLinear = null;
        this.rightLinear = null;
    }

    @Override
    public String toString() {
        return "Node{" + "depth=" + depth + ", value=" + value + '}';
    }

}
