package cipher;

import java.util.*;

public class PlayfairMatrix {
    ArrayDeque<Byte> key = new ArrayDeque<>();
    private final byte[][] matrix = new byte[16][16];

    public PlayfairMatrix(String key) {
        for (int i = 0; i < key.length(); i++) {
            if (!this.key.contains((byte) key.charAt(i))) {
                this.key.offer((byte) key.charAt(i));
            }
        }
    }

    public byte[][] getMatrix() {
        return matrix;
    }

    public void createMatrix() {
        fill(key);
    }

    private void fill(Queue<Byte> key) {
        ArrayList<Byte> unique = uniqueBytes(key);
        int temp = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (!key.isEmpty()) {
                    matrix[i][j] = key.poll();
                } else {
                    matrix[i][j] = unique.get(temp);
                    temp++;
                }
            }
        }
    }

    //takes general queue as a param
    private ArrayList<Byte> uniqueBytes(Queue<Byte> key) {
        ArrayList<Byte> unique = new ArrayList<>();
        Byte[] keyArray = key.toArray(new Byte[0]);
        for (int i = -128; i <= 127; i++) {
            if (!Arrays.asList(keyArray).contains((byte) i)) {
                unique.add((byte) i);
            }
        }
        return unique;
    }
}