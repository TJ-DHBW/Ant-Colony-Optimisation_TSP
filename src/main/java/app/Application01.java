package app;

import data.DataInstance;
import data.DataReader;

public class Application01 {
    public static void main(String[] args) {
        // TODO: Application01
        DataInstance dataInstance = DataReader.readData(Configuration.INSTANCE.dataName);
    }
}
