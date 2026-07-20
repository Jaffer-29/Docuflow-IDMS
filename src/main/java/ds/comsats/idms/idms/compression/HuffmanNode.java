package ds.comsats.idms.idms.compression;

public class HuffmanNode {
    public int byteValue;
    public int frequency;
    public HuffmanNode left;
    public HuffmanNode right;
    public int order;

    public HuffmanNode(int byteValue, int frequency, int order) {
        this.byteValue = byteValue;
        this.frequency = frequency;
        this.order = order;
        this.left = null;
        this.right = null;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right, int order) {
        this.byteValue = -1;
        this.frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
        this.order = order;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}