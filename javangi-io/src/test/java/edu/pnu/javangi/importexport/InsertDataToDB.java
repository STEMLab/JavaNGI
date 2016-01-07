package edu.pnu.javangi.importexport;
import java.io.IOException;

import edu.pnu.javangi.util.JavaNGI;

public class InsertDataToDB {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException {
        // TODO Auto-generated method stub

        InsertDataToDB ngiApp = new InsertDataToDB();
        ngiApp.run();
    }

    public void run() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException {
        String NGIFilePath = "E:\\du_program\\NGI\\sample data\\test.ngi";
        String NDAFilePath = "E:\\du_program\\NGI\\sample data\\test.nda";

        String url = "jdbc:postgresql://localhost/ngi_db";
        String user = "postgres";
        String password = "stem9987";

        JavaNGI javaNGI = new JavaNGI();
        javaNGI.importDataFromFile(NGIFilePath, NDAFilePath);
        System.out.println(".NGI file -> JavaNGI");

        javaNGI.connectToDB(url, user, password);
        javaNGI.createSchema();
        javaNGI.insertDataToDB();
        System.out.println("JavaNGI -> PostGIS");

    }
}
