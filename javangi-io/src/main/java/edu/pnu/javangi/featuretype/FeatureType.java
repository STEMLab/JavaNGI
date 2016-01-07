package edu.pnu.javangi.featuretype;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import com.vividsolutions.jts.geom.Geometry;

public interface FeatureType {
	int getLayerID();
	String getLayerName();
	int getVersion();
	ArrayList<String> getNgiHeaderLines();
	ArrayList<String> getNdaHeaderLines();
	ArrayList<String> getPoint_text();
	ArrayList<Geometry> getGeom();
	ArrayList<String> getGattrs();
	ArrayList<Integer> getRecordID();
	ArrayList<String> getUFID();
	ArrayList<String> getWkts();
	String getMetaDataTableName();
	String getDataTableName();
	
	void setLayerID(int layerID);
	void importDataFromFile(Scanner ngiScanner, Scanner ndaScanner);
	void exportDataToFile(PrintWriter ngiWriter, PrintWriter ndaWriter);
	String[] getInsertQuery(String fileName);
	String[] getCreateSchema();
	void importDataFromDB(String tableType, ResultSet rs);
}
