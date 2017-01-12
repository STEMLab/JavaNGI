package edu.pnu.javangi.importexport;
import java.io.IOException;

import edu.pnu.javangi.util.JavaNGI;

public class ImportDataFromDB {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException {
        // TODO Auto-generated method stub

        ImportDataFromDB ngiApp = new ImportDataFromDB();
        ngiApp.run();
    }

    public void run() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException {
        String outputNGI = "E:\\du_program\\NGI\\sample data\\test_output_0107.ngi";
        String outputNDA = "E:\\du_program\\NGI\\sample data\\test_output_0107.nda";

        String url = "jdbc:postgresql://localhost/ngi_db";
        String user = "postgres";
        String password = "stem9987";

        JavaNGI javaNGI = new JavaNGI();

        javaNGI.connectToDB(url, user, password);

        javaNGI.importDataFromDB("test");
        System.out.println("PostGIS -> JavaNGI");

        javaNGI.exportDataToNGIFile(outputNGI, outputNDA);
        System.out.println("JavaNGI -> .NGI file");
    }
}
