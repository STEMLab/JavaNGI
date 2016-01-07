package edu.pnu.javangi.util;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GeometryParsingUtil {
        private static final GeometryFactory geometryFactory = JTSFactoryFinder
                        .getGeometryFactory();

        public static Coordinate parseCoordinate(String text) {
                double x, y;

                String[] strSplit = text.split(" ");
                x = Double.parseDouble(strSplit[0]);
                y = Double.parseDouble(strSplit[1]);

                return new Coordinate(x, y);
        }

        public static Point parsePoint(String text) {
                Coordinate coordinate = parseCoordinate(text);
                return geometryFactory.createPoint(coordinate);
        }
        
}
