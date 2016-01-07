/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2016, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package edu.pnu.javangi.importexport;

import java.io.IOException;

import org.junit.Test;

import edu.pnu.javangi.util.JavaNGI;
import edu.pnu.javangi.util.SHPConvertUtil;

/**
 * @author Donguk Seo
 *
 */
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
                
                SHPConvertUtil.convertNGIToSHP("E:\\du_program\\NGI\\sample data\\test", javaNGI.getFeatureTypeList());
        }
}
