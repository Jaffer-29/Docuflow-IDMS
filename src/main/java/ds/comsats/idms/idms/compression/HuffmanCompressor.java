package ds.comsats.idms.idms.compression;

import java.io.*;
import java.util.PriorityQueue;

public class HuffmanCompressor {

    public static void compress(String inputFile, String outputFile) throws IOException {
        byte[] data = readFile(inputFile);

        int[] frequency = new int[256];
        for (byte b : data) {
            frequency[b & 0xFF]++;
        }

        HuffmanNode root = buildTree(frequency);

        String[] codes = new String[256];
        if (root != null) {
            buildCodes(root, "", codes);
        }

        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(outputFile)));

        out.writeInt(data.length);

        int uniqueCount = 0;
        for (int i = 0; i < 256; i++) if (frequency[i] > 0) uniqueCount++;
        out.writeInt(uniqueCount);
        for (int i = 0; i < 256; i++) {
            if (frequency[i] > 0) {
                out.writeByte(i);
                out.writeInt(frequency[i]);
            }
        }


        int currentByte = 0;
        int bitCount = 0;
        for (byte b : data) {
            String code = codes[b & 0xFF];
            for (int j = 0; j < code.length(); j++) {
                int bit = code.charAt(j) - '0';
                currentByte = (currentByte << 1) | bit;
                bitCount++;
                if (bitCount == 8) {
                    out.writeByte(currentByte);
                    currentByte = 0;
                    bitCount = 0;
                }
            }
        }

        if (bitCount > 0) {
            currentByte = currentByte << (8 - bitCount);
            out.writeByte(currentByte);
        }
        out.close();

    }
    public static String getCompressionInfo(String inputFile,String outputFile){
        long originalSize=new File(inputFile).length();
        long compressedSize=new File(outputFile).length();

        double percent=0;
        if(originalSize>0){
            percent=100.0*compressedSize/originalSize;
        }

        return "Original Size : "+originalSize+" bytes\n"+
                "Compressed Size : "+compressedSize+" bytes\n"+
                "Compressed : "+String.format("%.1f",percent)+"% of original";
    }

    public static String getDecompressionInfo(String inputFile,String outputFile){
        long compressedSize=new File(inputFile).length();
        long decompressedSize=new File(outputFile).length();

        double increase=0;
        if(compressedSize>0){
            increase=100.0*decompressedSize/compressedSize;
        }

        return "Compressed Size : "+compressedSize+" bytes\n"+
                "Decompressed Size : "+decompressedSize+" bytes\n"+
                "Expanded Size : "+String.format("%.1f",increase)+"% of compressed";
    }


    public static void decompress(String inputFile, String outputFile) throws IOException {
        DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream(inputFile)));


        int totalBytes  = in.readInt();
        int uniqueCount = in.readInt();
        int[] frequency = new int[256];
        for (int i = 0; i < uniqueCount; i++) {
            int byteValue = in.readByte() & 0xFF;
            int freq      = in.readInt();
            frequency[byteValue] = freq;
        }

        HuffmanNode root = buildTree(frequency);

        byte[] result = new byte[totalBytes];
        int produced = 0;

        if (root != null && root.isLeaf()) {
            for (int i = 0; i < totalBytes; i++) {
                result[i] = (byte) root.byteValue;
            }
            produced = totalBytes;
        } else if (root != null) {
            HuffmanNode current = root;
            int b;
            while (produced < totalBytes && (b = in.read()) != -1) {
                for (int bitPos = 7; bitPos >= 0 && produced < totalBytes; bitPos--) {
                    int bit = (b >> bitPos) & 1;
                    current = (bit == 0) ? current.left : current.right;
                    if (current.isLeaf()) {
                        result[produced++] = (byte) current.byteValue;
                        current = root;
                    }
                }
            }
        }
        in.close();

        writeFile(outputFile, result);
    }

    public static HuffmanNode buildTree(int[] frequency) {

        PriorityQueue<HuffmanNode> heap = new PriorityQueue<>((a, b) -> {
            if (a.frequency != b.frequency) return a.frequency - b.frequency;
            return a.order - b.order;
        });

        int order = 0;
        for (int i = 0; i < 256; i++) {
            if (frequency[i] > 0) {
                heap.offer(new HuffmanNode(i, frequency[i], order++));
            }
        }

        if (heap.isEmpty()) return null;   // empty file -> no tree
        while (heap.size() > 1) {
            HuffmanNode left  = heap.poll();
            HuffmanNode right = heap.poll();
            heap.offer(new HuffmanNode(left, right, order++));
        }
        return heap.poll();
    }


    public static void buildCodes(HuffmanNode node, String path, String[] codes) {
        if (node.isLeaf()) {
            codes[node.byteValue] = path.isEmpty() ? "0" : path;
            return;
        }
        buildCodes(node.left,  path + "0", codes);
        buildCodes(node.right, path + "1", codes);
    }

    public static byte[] readFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) throw new IOException("File not found: " + path);
        byte[] data = new byte[(int) file.length()];
        DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream(file)));
        in.readFully(data);
        in.close();
        return data;
    }

    public static void writeFile(String path, byte[] data) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
        out.write(data);
        out.close();
    }
}
