package app;

import data.DataInstance;
import data.DataReader;

public class Application03 {
    public static void main(String[] args) {
        // TODO: Application03
        DataInstance dataInstance = DataReader.readData(Configuration.INSTANCE.dataName);
    }
}
