package edu.pnu.javangi.shpwriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.geotools.data.shapefile.shp.ShapeType;
import org.geotools.data.shapefile.shp.ShapefileWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;


public class ShpWriterTest {
 
    private ShapefileWriter writer;
 
    @Before
    public void setUp() throws IOException {
        File shpFile = new File("E:\\stem\\donguk.seo\\자료\\shape\\writertest\\test.shp");
        File shxFile = new File("E:\\stem\\donguk.seo\\자료\\shape\\writertest\\testC.shx");
 
        FileOutputStream shpFOS = new FileOutputStream(shpFile);
        FileOutputStream shxFOS = new FileOutputStream(shxFile);
 
        this.writer = new ShapefileWriter(shpFOS.getChannel(), shxFOS.getChannel());
    }
 
    @After
    public void close() throws IOException {
        this.writer.close();
    }
 
    @Test
    public void shpWrite() throws IOException {
        Scanner scanner = new Scanner(new File("E:\\stem\\donguk.seo\\자료\\shape\\writertest\\test.txt"));
        List<Coordinate> coords = new ArrayList<Coordinate>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",");
            double[] xy = {Double.parseDouble(values[0]), Double.parseDouble(values[1])};
            coords.add(new Coordinate(xy[0], xy[1]));
        }
 
        GeometryFactory gf = new GeometryFactory();
        LineString lineString = gf.createLineString(coords.toArray(new Coordinate[coords.size()]));
        MultiLineString mls = gf.createMultiLineString(new LineString[]{lineString});
        System.out.println("mls = " + mls);
 
        this.writer.writeHeaders(mls.getEnvelopeInternal(), ShapeType.ARC, mls.getNumGeometries(), 100);
        this.writer.writeGeometry(mls);
    }
}