package edu.pnu.javangi.util.shpexporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileWriter;
import org.geotools.data.shapefile.shp.ShapeType;
import org.geotools.data.shapefile.shp.ShapefileWriter;
import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import edu.pnu.javangi.featuretype.AbstractFeatureType;
import edu.pnu.javangi.featuretype.FeatureType;

public class ShapeExporter {

        public static void convertNGIToShape(String _folderPath, ArrayList<FeatureType> _features) {
                File dir = new File(_folderPath);
                
                convertNGIToShape(dir, _features);
        }
        public static void convertNGIToShape(File dir, ArrayList<FeatureType> features) {
                for (FeatureType feature : features) {
                        convertFeatureToShapeWithDBase(dir, feature);
                }
        }

        private static void convertFeatureToShapeWithDBase(File dir, FeatureType feature) {
                String baseFileName = dir.getPath() + "/" + feature.getLayerName() + "_";

                /*
                 * DBF File Contents
                 */
                ArrayList<Object> pointTextsDBF = new ArrayList<Object>();
                ArrayList<Object> pointsDBF = new ArrayList<Object>();
                ArrayList<Object> lineStringsDBF = new ArrayList<Object>();
                ArrayList<Object> polygonsDBF = new ArrayList<Object>();

                /*
                 * Shape File Contents
                 */
                ArrayList<Point> pointTexts = new ArrayList<Point>();
                ArrayList<Point> points = new ArrayList<Point>();
                ArrayList<LineString> lineStrings = new ArrayList<LineString>();
                ArrayList<Polygon> polygons = new ArrayList<Polygon>();

                ArrayList<String> texts = feature.getPoint_text();
                ArrayList<Geometry> geometries = feature.getGeom();
                for (int i = 0; i < geometries.size(); i++) {
                        Geometry geometry = geometries.get(i);
                        String pointText = texts.get(i);

                        if (!pointText.equalsIgnoreCase("EMPTY")) {
                                pointTexts.add((Point) geometry);
                                pointTextsDBF.add(DbaseFileRecordGenerator.createDBFContents(
                                                (AbstractFeatureType) feature, i));
                        } else if (geometry.getGeometryType().equalsIgnoreCase("POINT")) {
                                points.add((Point) geometry);
                                pointsDBF.add(DbaseFileRecordGenerator.createDBFContents(
                                                (AbstractFeatureType) feature, i));
                        } else if (geometry.getGeometryType().equalsIgnoreCase("LINESTRING")) {
                                lineStrings.add((LineString) geometry);
                                lineStringsDBF.add(DbaseFileRecordGenerator.createDBFContents(
                                                (AbstractFeatureType) feature, i));
                        } else if (geometry.getGeometryType().equalsIgnoreCase("POLYGON")) {
                                polygons.add((Polygon) geometry);
                                polygonsDBF.add(DbaseFileRecordGenerator.createDBFContents(
                                                (AbstractFeatureType) feature, i));
                        }
                }

                DbaseFileHeader dbfHeader = (DbaseFileHeader) DbaseFileHeaderGenerator
                                .createDbaseHeader((AbstractFeatureType) feature);
                if (pointTexts.size() > 0) {
                        DbaseFileHeader dbfHeaderText = DbaseFileHeaderGenerator
                                        .createDbaseHeaderWithTextColumn((AbstractFeatureType) feature);
                        convertGeometriesToSHP(pointTexts, baseFileName, "TEXT");
                        convertContentsToDBF(dbfHeaderText, pointTextsDBF, baseFileName, "TEXT");
                }
                if (points.size() > 0) {
                        convertGeometriesToSHP(points, baseFileName, "POINT");
                        convertContentsToDBF(dbfHeader, pointsDBF, baseFileName, "POINT");
                }
                if (lineStrings.size() > 0) {
                        convertGeometriesToSHP(lineStrings, baseFileName, "LINE");
                        convertContentsToDBF(dbfHeader, lineStringsDBF, baseFileName, "LINE");
                }
                if (polygons.size() > 0) {
                        convertGeometriesToSHP(polygons, baseFileName, "POLYGON");
                        convertContentsToDBF(dbfHeader, polygonsDBF, baseFileName, "POLYGON");
                }

        }

        private static void convertGeometriesToSHP(ArrayList<? extends Geometry> geometries,
                        String baseFileName, String type) {
                ShapeType shapeType = null;

                if (type.equalsIgnoreCase("POINT") || type.equalsIgnoreCase("TEXT")) {
                        shapeType = ShapeType.POINT;
                } else if (type.equalsIgnoreCase("LINE")) {
                        shapeType = ShapeType.ARC;
                } else if (type.equalsIgnoreCase("POLYGON")) {
                        shapeType = ShapeType.POLYGON;
                }

                File shpFile = null;
                File shxFile = null;
                FileOutputStream shpFOS = null;
                FileOutputStream shxFOS = null;
                ShapefileWriter writer = null;

                try {
                        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
                        shpFile = new File(baseFileName + type.toLowerCase() + ".shp");
                        shxFile = new File(baseFileName + type.toLowerCase() + ".shx");
                        shpFOS = new FileOutputStream(shpFile);
                        shxFOS = new FileOutputStream(shxFile);
                        writer = new ShapefileWriter(shpFOS.getChannel(), shxFOS.getChannel());

                        GeometryCollection collection = null;
                        if (shapeType == ShapeType.POINT) {
                                Point[] pointArr = new Point[geometries.size()];
                                geometries.toArray(pointArr);
                                collection = geometryFactory
                                                .createGeometryCollection((Point[]) pointArr);
                        } else if (shapeType == ShapeType.ARC) {
                                MultiLineString[] multiLineStringArr = new MultiLineString[geometries
                                                .size()];
                                for (int i = 0; i < geometries.size(); i++) {
                                        multiLineStringArr[i] = geometryFactory
                                                        .createMultiLineString(new LineString[] { (LineString) geometries
                                                                        .get(i) });
                                }
                                collection = geometryFactory
                                                .createGeometryCollection((MultiLineString[]) multiLineStringArr);
                        } else if (shapeType == ShapeType.POLYGON) {
                                Polygon[] polygonArr = new Polygon[geometries.size()];
                                geometries.toArray(polygonArr);
                                collection = geometryFactory
                                                .createMultiPolygon((Polygon[]) polygonArr);
                        }
                        writer.write(collection, shapeType);

                        writer.close();
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        private static void convertContentsToDBF(DbaseFileHeader header,
                        ArrayList<Object> contents, String baseFileName, String type) {
                File dbfFile = null;
                WritableByteChannel channel = null;
                DbaseFileWriter writer = null;

                dbfFile = new File(baseFileName + type.toLowerCase() + ".dbf");
                try {
                        header.setNumRecords(contents.size());
                        channel = new FileOutputStream((dbfFile)).getChannel();
                        writer = new DbaseFileWriter(header, channel, Charset.forName("UTF-8"));

                        for (int i = 0; i < contents.size(); i++) {
                                writer.write((Object[]) contents.get(i));
                        }

                        writer.close();
                } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

}
