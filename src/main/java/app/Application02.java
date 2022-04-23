package app;

import data.DataInstance;
import data.DataReader;

public class Application02 {
    public static void main(String[] args) {
        // TODO: Application02
        DataInstance dataInstance = DataReader.readData(Configuration.INSTANCE.dataName);
    }
}
