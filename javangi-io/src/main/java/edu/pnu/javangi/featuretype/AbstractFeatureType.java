package edu.pnu.javangi.featuretype;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.postgis.PGgeometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import edu.pnu.javangi.util.GeometryParsingUtil;
import edu.pnu.javangi.util.GeometryToNGIFormatUtil;



public class AbstractFeatureType implements FeatureType {
	protected int layerID;
	protected String layerName;
	protected int version;
	protected ArrayList<String> ngiHeaderLines;
	protected ArrayList<String> ndaHeaderLines;
	
	protected ArrayList<String> point_text;
	protected ArrayList<Geometry> geom;
	protected ArrayList<String> gattrs;
	protected ArrayList<Integer> recordID;
	protected ArrayList<String> UFID;
	protected ArrayList<String> wkts;
	
	protected String metaDataTableName;
	protected String dataTableName;
	
	protected String[] schema;
	
	public AbstractFeatureType(){
		ngiHeaderLines = new ArrayList<String>();
		ndaHeaderLines = new ArrayList<String>();
		point_text = new ArrayList<String>();
		geom = new ArrayList<Geometry>();
		gattrs = new ArrayList<String>();
		recordID = new ArrayList<Integer>();
		UFID = new ArrayList<String>();
		wkts = new ArrayList<String>();
		
		schema = new String[2];
	}
	public AbstractFeatureType(int layerID, String layerName){
		this.layerID = layerID;
		this.layerName = layerName;
		
		recordID = new ArrayList<Integer>();
		UFID = new ArrayList<String>();
	}
	
	public int getLayerID() {
		return layerID;
	}
	public String getLayerName() {
		return layerName;
	}
	public int getVersion() {
		return version;
	}
	public ArrayList<String> getNgiHeaderLines() {
		return ngiHeaderLines;
	}
	public ArrayList<String> getNdaHeaderLines() {
		return ndaHeaderLines;
	}
	public ArrayList<String> getPoint_text() {
		return point_text;
	}
	public ArrayList<Geometry> getGeom() {
		return geom;
	}
	public ArrayList<String> getGattrs() {
		return gattrs;
	}
	public ArrayList<Integer> getRecordID() {
		return recordID;
	}
	public ArrayList<String> getUFID() {
		return UFID;
	}
	public ArrayList<String> getWkts() {
		return wkts;
	}
	public String getMetaDataTableName() {
		return metaDataTableName;
	}
	public String getDataTableName() {
		return dataTableName;
	}
	
	public String trimSpace(String str){
		int startIdx = 0;
		int endIdx = str.length() - 1;
		
		//for(startIdx=0; str.charAt(startIdx)!=' '; startIdx++);
		for(startIdx=0; str.charAt(startIdx)==' '; startIdx++);
		for(endIdx=str.length()-1; str.charAt(endIdx)==' '; endIdx--);
		
		return str.substring(startIdx, endIdx+1);
	}

	public void importDataFromFile(Scanner ngiScanner, Scanner ndaScanner) {
		String ngiStr = ngiScanner.nextLine();
		String ndaStr = ndaScanner.nextLine(); // <HEADER>
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		
		while(!ngiStr.equals("<END>")){
			ngiStr = ngiScanner.nextLine();
			ngiHeaderLines.add(ngiStr);
		}
		ngiHeaderLines.remove(ngiHeaderLines.size() - 1);
		
		while(!ndaStr.equals("<END>")){
			ndaStr = ndaScanner.nextLine();
			ndaHeaderLines.add(ndaStr);
		}
		ndaHeaderLines.remove(ndaHeaderLines.size() - 1);
		
		/////// NGI DATA
		
		while(ngiScanner.hasNextLine()){
			String str = ngiScanner.nextLine();
			
			if(str.equals("<END>")){
				str = ngiScanner.nextLine(); // "<LAYER_END>"
				break;
			}
			if(str.equals("<DATA>")) continue;
			
			String line = ngiScanner.nextLine();
			
			String wkt = "ST_GeomFromText('";
			if(line.equals("POINT")){
				line = ngiScanner.nextLine();
				
				//ST_GeomFromText('POINT (100 0)',-1)
				Point point = GeometryParsingUtil.parsePoint(line);
				
				wkt += point.toText() + "' )"; 

				point_text.add("empty");
				geom.add(point);
			}else if(line.equals("LINESTRING")){
				line = ngiScanner.nextLine(); // numOfLine
				int numOfLine = Integer.valueOf(line);
				
				ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
				for(int i=0; i<numOfLine; i++){
					line = ngiScanner.nextLine();
					
					coords.add(GeometryParsingUtil.parseCoordinate(line));
					//ST_GeomFromText('LINESTRING (100 0, 50 50)', -1)
				}
				
				if(coords.size() == 1) {
				        coords.add((Coordinate) coords.get(0).clone());
				}
                                Coordinate[] coordArr = new Coordinate[coords.size()];
                                coords.toArray(coordArr);
				LineString lineString = geometryFactory.createLineString(coordArr);
				
				wkt += lineString.toText() + "' )";
				
				point_text.add("empty");
				geom.add(lineString);
			}else if(line.equals("POLYGON")){
			        LinearRing exterior = null;
			        LinearRing[] interior = null;
				line = ngiScanner.nextLine();
				
				String[] lineSplit = line.split(" ");
				int numParts = Integer.valueOf(lineSplit[1]);
				if(numParts > 1) {
				        interior = new LinearRing[numParts - 1];
				}
				
				for(int i=0; i<numParts; i++){
					line = ngiScanner.nextLine();
					int numOfVertex = Integer.valueOf(line);
					
					//ST_GeomFromText('POLYGON((0 0,4 0,4 4,0 4,0 0),(1 1,2 1,2 2,1 2,1 1))', -1)
					ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
	                                for(int j=0; j<numOfVertex; j++){
	                                        line = ngiScanner.nextLine();
	                                        
	                                        coords.add(GeometryParsingUtil.parseCoordinate(line));
	                                        //ST_GeomFromText('LINESTRING (100 0, 50 50)', -1)
	                                }
	                                if(!coords.get(0).equals2D(coords.get(numOfVertex - 1))) {
	                                        coords.add((Coordinate) coords.get(0).clone());	                                        
	                                }
	                                
	                                Coordinate[] coordArr = new Coordinate[coords.size()];
	                                coords.toArray(coordArr);
	                                LinearRing linearRing = geometryFactory.createLinearRing(coordArr);
	                                
	                                if(i == 0) exterior = linearRing;
	                                else interior[i - 1] = linearRing;
				}
				Polygon polygon = geometryFactory.createPolygon(exterior, interior);
				wkt += polygon.toText() + "' )";
				
				point_text.add("empty");
				geom.add(polygon);
			}else if(line.substring(0, 4).equals("TEXT")){
				point_text.add(line);
				line = ngiScanner.nextLine();
				
				Point point = GeometryParsingUtil.parsePoint(line);
				
				//ST_GeomFromText('POINT (100 0)',-1)
				wkt += point.toText() + "' )";
				
				geom.add(point);
			}
			
			str = ngiScanner.nextLine();
			gattrs.add(str);
			wkts.add(wkt);
		}
	}
	
	public void setLayerID(int layerID) {
		// TODO Auto-generated method stub
		this.layerID = layerID;
	}

	public String[] getInsertQuery(String fileName) {
		// TODO Auto-generated method stub
		String[] querys = new String[ngiHeaderLines.size() + ndaHeaderLines.size() + 1];
		int index = 0;
		
		String query;
		query = "insert into " + metaDataTableName + "(filename, layerid, type, val) values('" + fileName + "', " + layerID + ", 'LayerID', " + layerID + ")";
		querys[index++] = query;
		for(int i=0; i<ngiHeaderLines.size(); i++){
			query = "insert into " + metaDataTableName + "(filename, layerid, type, val) values('" + fileName + "', " + layerID + ", 'ngiHeader";
			if(ngiHeaderLines.get(i).equals("$END")){
				query += "END" + i;
			}
			query += "', '" + ngiHeaderLines.get(i) + "')";
			
			querys[index++] = query;
		}
		for(int i=0; i<ndaHeaderLines.size(); i++){
			query = "insert into " + metaDataTableName + "(filename, layerid, type, val) values('" + fileName + "', " + layerID + ", 'ndaHeader";
			if(ndaHeaderLines.get(i).equals("$END")){
				query += "END" + i;
			}
			query += "', '" + ndaHeaderLines.get(i) + "')";
			
			querys[index++] = query;
		}
		
		return querys;
	}
	public String[] getCreateSchema() {
		schema[0] = "CREATE TABLE IF NOT EXISTS " + metaDataTableName + "(  "
				+ "index serial, filename text,  layerid bigint,  type text,  val text"
				+", CONSTRAINT " + metaDataTableName + "_primarykey PRIMARY KEY (filename, layerid, type, val))";
		
		return schema;
	}
	
	public void exportDataToFile(PrintWriter ngiWriter, PrintWriter ndaWriter) {
		// TODO Auto-generated method stub
		// NGI export
		ngiWriter.println("<LAYER_START>");
		
		ngiWriter.println("$LAYER_ID");
		ngiWriter.println(layerID);
		ngiWriter.println("$END");
		
		ngiWriter.println("$LAYER_NAME");
		ngiWriter.println("\"" + layerName + "\"");
		ngiWriter.println("$END");
		
		// NGI HEADER export
		ngiWriter.println("<HEADER>");
		for(int i=0; i<ngiHeaderLines.size(); i++){
			ngiWriter.println(ngiHeaderLines.get(i));
		}
		ngiWriter.println("<END>");
		
		// NGI DATA export
		ngiWriter.println("<DATA>");
		for(int i=0; i<recordID.size(); i++){
			ngiWriter.println("$RECORD " + recordID.get(i));
			
			String geomType;
			Geometry geometry = geom.get(i);
			if(point_text.get(i).equals("empty")){
				geomType = geometry.getGeometryType().toUpperCase();
				ngiWriter.println(geomType);
			}else{
				geomType = "TEXT";
				ngiWriter.println(point_text.get(i));
			}
			
			if(geomType.equalsIgnoreCase("POINT") || geomType.equalsIgnoreCase("TEXT")) {
			        ngiWriter.println(GeometryToNGIFormatUtil.PointToNGIFormat((Point) geometry));
			}else if(geomType.equalsIgnoreCase("LINESTRING")){
                                ngiWriter.println(GeometryToNGIFormatUtil.LineStringToNGIFormat((LineString) geometry));
                        }else if(geomType.equalsIgnoreCase("POLYGON")){
                                ngiWriter.println(GeometryToNGIFormatUtil.PolygonToNGIFormat((Polygon) geometry));
                        }

			ngiWriter.println(gattrs.get(i));
		}
		ngiWriter.println("<END>");
		ngiWriter.println("<LAYER_END>");
		
		// NDA export
		ndaWriter.println("<LAYER_START>");
		
		ndaWriter.println("$LAYER_ID");
		ndaWriter.println(layerID);
		ndaWriter.println("$END");
		
		ndaWriter.println("$LAYER_NAME");
		ndaWriter.println("\"" + layerName + "\"");
		ndaWriter.println("$END");
		
		// NDA HEADER export
		ndaWriter.println("<HEADER>");
		for(int i=0; i<ndaHeaderLines.size(); i++){
			ndaWriter.println(ndaHeaderLines.get(i));
		}
		ndaWriter.println("<END>");
	}
	
	public void importDataFromDB(String tableType, ResultSet rs) {
		// TODO Auto-generated method stub
		if(tableType.equals("MetaData")){
			try {
				while(rs.next()){
					String type = rs.getString("type");
					String value = rs.getString("val");
					
					if(type.equals("LayerID")){
						layerID = Integer.valueOf(value);
					}else if(type.startsWith("ngiHeader")){
						ngiHeaderLines.add(value);
					}else if(type.startsWith("ndaHeader")){
						ndaHeaderLines.add(value);
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tableType.equals("Data")){
			try {
				while(rs.next()){
					int _recordID = rs.getInt("recordid");
					String _ufid = rs.getString("ufid");
					String _gattr = rs.getString("gattr");
					String _pointtext = null;
					try{
						_pointtext = rs.getString("pointtext");
					}catch(SQLException e){
						_pointtext = "empty";
					}
					
					recordID.add(_recordID);
					UFID.add(_ufid);
					gattrs.add(_gattr);
					point_text.add("empty");
					
					PGgeometry pgGeom = (PGgeometry) rs.getObject("shape");
					org.postgis.Geometry shp = (org.postgis.Geometry) pgGeom.getGeometry();
					Geometry geometry = null;
					
					GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
					WKTReader reader = new WKTReader(geometryFactory);
					geometry = reader.read(shp.toString());
					
					geom.add(geometry);
					//wkts.add("ST_GeomFromText('" + geometry.toWKT() + "', -1)");
					wkts.add("ST_GeomFromWKB(ST_AsEWKB('" + geometry.toText() + "'::geometry))");
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
		}
	}
}
