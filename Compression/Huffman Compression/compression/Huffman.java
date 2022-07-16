import java.util.PriorityQueue;

public class Huffman {

    // alphabet size of extended ASCII
    private static final int R = 256;

    // Do not instantiate.
    private Huffman() { }

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses them
     * using Huffman codes with an 8-bit alphabet; and writes the results
     * to standard output.
     */

    public static void compress() {

        String binaryInput = BinaryStdIn.readString();
        char[] input = binaryInput.toCharArray();

        int[] frequencyChar = buildFrequencyTable(input);

        Node root = encodeHuffman(frequencyChar);

        String[] string = new String[R];

        buildCode(string, root, "");

        printTree(root);

        BinaryStdOut.write(input.length);

        for (int i = 0; i < input.length; i++) {
            String code = string[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
                }
                else {
                    BinaryStdOut.write(true);
                }
            }
        }
        BinaryStdOut.close();
    }
    private static int[] buildFrequencyTable(char[] input){
        int[] frequencyChar = new int[R];
        for (int i = 0; i < input.length; i++) {
            frequencyChar[input[i]]++;
        }
        return frequencyChar;
    }


    private static Node encodeHuffman(int[] frequencyChar) {

       final  PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (char i = 0; i < R; i++) {
            if (frequencyChar[i] > 0)
            {
                priorityQueue.add(new Node(i, frequencyChar[i], null, null));
            }
        }
        while (priorityQueue.size() > 1) {
            Node left  = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }


    private static void printTree(Node node) {
        if (node.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(node.ch,8);
            return;
        }
        BinaryStdOut.write(false);
        printTree(node.left);
        printTree(node.right);
    }

    private static void buildCode(String[] stringArray, Node node, String string) {
        if (!node.isLeaf()) {
            buildCode(stringArray, node.left,  string + '0');
            buildCode(stringArray, node.right, string + '1');
        }
        else {
            stringArray[node.ch] = string;
        }
    }

    /**
     * Reads a sequence of bits that represents a Huffman-compressed message from
     * standard input; expands them; and writes the results to standard output.
     */
    public static void expand() {

        Node root = readTrie();

        int length = BinaryStdIn.readInt();

        for (int i = 0; i < length; i++) {
            Node temp = root;
            while (!temp.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit){
                    temp = temp.right;
                }
                else{
                    temp = temp.left;
                }
            }
            BinaryStdOut.write(temp.ch, 8);
        }
        BinaryStdOut.close();
    }

    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
            return new Node(BinaryStdIn.readChar(), -1, null, null);
        }
        else {
            return new Node('\0', -1, readTrie(), readTrie());
        }
    }

    /**
     * Sample client that calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
