package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DataReader {
    private DataReader() {
    }

    public static DataInstance readData(String dataName) {
        URL dataURL = Thread.currentThread().getContextClassLoader().getResource(dataName);
        if (dataURL == null) throw new RuntimeException("No data file found.");

        try (BufferedReader reader = new BufferedReader(new FileReader(dataURL.getPath(), StandardCharsets.UTF_8))) {
            return DataReader.parseData(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading file: " + dataURL.getPath());
        }
    }

    private static DataInstance parseData(BufferedReader reader) throws IOException {
        ArrayList<int[]> positions = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.trim().split(" +");
            if (data.length != 3)
                throw new RuntimeException("Each line in the parsed data should consist of 3 ints, separated by spaces.");

            // int id = Integer.parseInt(data[0]);
            int x = Integer.parseInt(data[1]);
            int y = Integer.parseInt(data[2]);

            positions.add(new int[]{x, y});
        }

        return new DataInstance(positions);
    }
}
