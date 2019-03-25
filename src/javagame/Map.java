package javagame;

public class Map {
    public int[][] blockPosition, blockValues; // index used here reffer to the enum BlockType

    public Map(int[][] blockPosition, int[][] blockValues) {
        this.blockPosition = blockPosition;
        this.blockValues = blockValues;
    }
}
