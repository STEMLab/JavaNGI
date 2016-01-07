package edu.pnu.javangi.util;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GeometryToNGIFormatUtil {        
        public static String CoordinateToNGIFormat(Coordinate coord) {
                String toText = coord.x + " " + coord.y;
                return toText;
        }
        
        public static String PointToNGIFormat(Point point) {
                String toText = CoordinateToNGIFormat(point.getCoordinate());
                return toText;                
        }
        
        public static String LineStringToNGIFormat(LineString lineString) {
                String toText = lineString.getNumPoints() + "\r"; // \n
                
                for(int i=0; i<lineString.getNumPoints(); i++){
                        toText += GeometryToNGIFormatUtil.PointToNGIFormat(lineString.getPointN(i));
                        
                        if(i != lineString.getNumPoints() - 1) {
                                toText += "\r";
                        }
                }
                return toText;
        }
        
        public static String LinearRingToNGIFormat(LinearRing linearRing) {
                return LineStringToNGIFormat(linearRing);
        }
        
        public static String PolygonToNGIFormat(Polygon polygon) {
                String toText = "NUMPARTS " + (1 + polygon.getNumInteriorRing()) + "\n";
                
                LinearRing exterior = (LinearRing) polygon.getExteriorRing();
                toText += LinearRingToNGIFormat(exterior);
                
                if(polygon.getNumInteriorRing() > 0) {
                        toText += "\r";
                        for(int i = 0; i < polygon.getNumInteriorRing(); i++) {
                                toText += LinearRingToNGIFormat((LinearRing) polygon.getInteriorRingN(i));
                                
                                if(i < polygon.getNumInteriorRing() -1) {
                                        toText += "\r";
                                }
                        }
                }
                
                return toText;
        }
}
