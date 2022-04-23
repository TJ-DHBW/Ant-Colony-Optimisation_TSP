package data;

import java.util.List;

public class DataInstance {
    private final int[] coordinates;
    private final int numPositions;

    public DataInstance(List<int[]> positions) {
        this.coordinates = new int[positions.size()*2];
        this.numPositions = positions.size();

        for (int i = 0; i < positions.size(); i++) {
            int[] position = positions.get(i);
            if (position.length < 2) throw new RuntimeException("Every position has to consist of two integers.");

            this.coordinates[i*2] = position[0];
            this.coordinates[i*2+1] = position[1];
        }
    }

    public int[] getPosition(int id) {
        if (id < 1) throw new IllegalArgumentException("Ids start at 1. Received: %d.".formatted(id));
        if (id > numPositions) throw new IllegalArgumentException("Id (%d) can not exceed the number of stored positions (%d).".formatted(id, numPositions));

        int posIndex = (id-1)*2;
        return new int[]{
                this.coordinates[posIndex],
                this.coordinates[posIndex+1]
        };
    }

    public int getX(int id) {
        if (id < 1) throw new IllegalArgumentException("Ids start at 1. Received: %d.".formatted(id));
        if (id > numPositions) throw new IllegalArgumentException("Id (%d) can not exceed the number of stored positions (%d).".formatted(id, numPositions));

        int posIndex = (id-1)*2;
        return this.coordinates[posIndex];
    }

    public int getY(int id) {
        if (id < 1) throw new IllegalArgumentException("Ids start at 1. Received: %d.".formatted(id));
        if (id > numPositions) throw new IllegalArgumentException("Id (%d) can not exceed the number of stored positions (%d).".formatted(id, numPositions));

        int posIndex = (id-1)*2;
        return this.coordinates[posIndex+1];
    }
}
