package edu.pnu.javangi.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.geotools.data.shapefile.shp.ShapeHandler;
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

import edu.pnu.javangi.featuretype.FeatureType;

public class SHPConvertUtil {
        private static String folderPath = null;

        private static ArrayList<FeatureType> features = null;

        public static void convertNGIToSHP(String _folderPath,
                        ArrayList<FeatureType> _features) {
                folderPath = _folderPath;
                features = _features;

                for (FeatureType feature : features) {
                        convertFeatureToSHP(feature);
                }
        }

        private static void convertFeatureToSHP(FeatureType feature) {
                String baseFileName = folderPath + "/" + feature.getLayerName() + "_";
                
                ArrayList<Point> pointTexts = new ArrayList<Point>();
                ArrayList<Point> points = new ArrayList<Point>();
                ArrayList<LineString> lineStrings = new ArrayList<LineString>();
                ArrayList<Polygon> polygons = new ArrayList<Polygon>();

                ArrayList<String> texts = feature.getPoint_text();
                ArrayList<Geometry> geometries = feature.getGeom();
                for (int i = 0; i < geometries.size(); i++) {
                        Geometry geometry = geometries.get(i);
                        String pointText = texts.get(i);
                        
                        if(!pointText.equalsIgnoreCase("EMPTY")) {
                                pointTexts.add((Point) geometry);
                        } else if (geometry.getGeometryType().equalsIgnoreCase("POINT")) {
                                points.add((Point) geometry);
                        } else if (geometry.getGeometryType().equalsIgnoreCase("LINESTRING")) {
                                lineStrings.add((LineString) geometry);
                        } else if(geometry.getGeometryType().equalsIgnoreCase("POLYGON")) {
                                polygons.add((Polygon) geometry);
                        }
                }
                
                if(pointTexts.size() > 0) {
                        convertGeometriesToSHP(pointTexts, baseFileName, "TEXT");
                }
                if(points.size() > 0) {
                        convertGeometriesToSHP(points, baseFileName, "POINT");
                }
                if(lineStrings.size() > 0) {
                        convertGeometriesToSHP(lineStrings, baseFileName, "LINE");
                }
                if(polygons.size() > 0) {
                        convertGeometriesToSHP(polygons, baseFileName, "POLYGON");
                }
        }
        
        private static void convertGeometriesToSHP(ArrayList<? extends Geometry> geometries, String baseFileName, String type) {
                ShapeType shapeType = null;
                
                if(type.equalsIgnoreCase("POINT") || type.equalsIgnoreCase("TEXT")) {
                        shapeType = ShapeType.POINT;
                } else if(type.equalsIgnoreCase("LINE")) {
                        shapeType = ShapeType.ARC;
                } else if(type.equalsIgnoreCase("POLYGON")) {
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
                        int shapeFileLength = 100; // Header
                        
                        ShapeHandler handler = null;
                        GeometryCollection collection = null;
                        if(shapeType == ShapeType.POINT) {
                                handler = ShapeType.POINT.getShapeHandler(geometryFactory);
                                Point[] pointArr = new Point[geometries.size()];
                                geometries.toArray(pointArr);
                                collection = geometryFactory.createGeometryCollection((Point[]) pointArr);
                        } else if(shapeType == ShapeType.ARC) {
                                handler = ShapeType.ARC.getShapeHandler(geometryFactory);
                                MultiLineString[] multiLineStringArr = new MultiLineString[geometries.size()];
                                for(int i = 0; i < geometries.size(); i++) {
                                        multiLineStringArr[i] = geometryFactory.createMultiLineString(new LineString[]{(LineString) geometries.get(i)});
                                }
                                collection = geometryFactory.createGeometryCollection((MultiLineString[]) multiLineStringArr);
                        } else if(shapeType == ShapeType.POLYGON) {
                                handler = ShapeType.POLYGON.getShapeHandler(geometryFactory);
                                Polygon[] polygonArr = new Polygon[geometries.size()];
                                geometries.toArray(polygonArr);
                                collection = geometryFactory.createMultiPolygon((Polygon[]) polygonArr);
                        }
                        writer.write(collection, shapeType);
                } catch(FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

}
