package edu.pnu.javangi.importexport;

import java.io.IOException;

import org.junit.Test;

import edu.pnu.javangi.util.JavaNGI;
import edu.pnu.javangi.util.shpexporter.ShapeExporter;

public class ConvertToSHPTest {

        @Test
        public void convertTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
                String NGIFilePath = "E:\\du_program\\NGI\\sample data\\test.ngi";
                String NDAFilePath = "E:\\du_program\\NGI\\sample data\\test.nda";

                String url = "jdbc:postgresql://localhost/ngi_db";
                String user = "postgres";
                String password = "stem9987";

                JavaNGI javaNGI = new JavaNGI();
                javaNGI.importDataFromFile(NGIFilePath, NDAFilePath);
                
                ShapeExporter.convertNGIToShape("E:\\du_program\\NGI\\sample data\\test", javaNGI.getFeatureTypeList());
        }
}
