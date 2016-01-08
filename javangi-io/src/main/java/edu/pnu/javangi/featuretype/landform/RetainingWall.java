package edu.pnu.javangi.featuretype.landform;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class RetainingWall extends AbstractFeatureType{ // 옹벽 F0040000
	private ArrayList<String> type;        // 종류
	private ArrayList<String> topBottom;   // 상하구분
	private ArrayList<Double> extension;   // 연장
	private ArrayList<Double> height;      // 높이
	private ArrayList<String> material;    // 재질
		
	public RetainingWall(){
		super();
		layerName = "F0040000";
		type = new ArrayList<String>();
		topBottom = new ArrayList<String>();
		extension = new ArrayList<Double>();
		height = new ArrayList<Double>();
		material = new ArrayList<String>();
		
		metaDataTableName = "retainingwallmetadata";
		dataTableName = "retainingwalldata";
	}
	
	public RetainingWall(int layerID, String layerName){
		super(layerID, layerName);
	}

	public ArrayList<String> getType() {
		return type;
	}

	public int getNumType(){
		return type.size();
	}
	
	public String getTypeN(int n){
		return type.get(n);
	}

	public ArrayList<String> getTopBottom() {
		return topBottom;
	}

	public int getNumTopBottom(){
		return topBottom.size();
	}
	
	public String getTopBottomN(int n){
		return topBottom.get(n);
	}

	public ArrayList<Double> getExtension() {
		return extension;
	}

	public int getNumExtension(){
		return extension.size();
	}
	
	public Double getExtensionN(int n){
		return extension.get(n);
	}

	public ArrayList<Double> getHeight() {
		return height;
	}

	public int getNumHeight(){
		return height.size();
	}
	
	public Double getHeightN(int n){
		return height.get(n);
	}

	public ArrayList<String> getMaterial() {
		return material;
	}

	public int getNumMaterial(){
		return material.size();
	}
	
	public String getMaterialN(int n){
		return material.get(n);
	}

	@Override
	public void importDataFromFile(Scanner ngiScanner, Scanner ndaScanner) {
		// TODO Auto-generated method stub
		super.importDataFromFile(ngiScanner, ndaScanner);
		
		while(ndaScanner.hasNextLine()){
			String str = ndaScanner.nextLine();
			
			if(str.equals("<END>")){
				str = ndaScanner.nextLine(); // "<LAYER_END>"
				break;
			}
			if(str.equals("<DATA>")) continue;
			
			String str2 = ndaScanner.nextLine();
			parsingNDAData(str, str2);
		}
	}
	
	public void parsingNDAData(String recordNum, String recordData){
		String[] recordNumSplit = recordNum.split(" ");
		String[] recordDataSplit = recordData.split(",");
		
		for(int i=0; i<recordNumSplit.length; i++){
			recordNumSplit[i] = trimSpaceAndQuotes(recordNumSplit[i]);
		}
		for(int i=0; i<recordDataSplit.length; i++){
			recordDataSplit[i] = trimSpaceAndQuotes(recordDataSplit[i]);
		}
		
		recordID.add(Integer.valueOf(recordDataSplit[0]));
		type.add(recordDataSplit[1]);
		topBottom.add(recordDataSplit[2]);
		extension.add(Double.valueOf(recordDataSplit[3]));
		height.add(Double.valueOf(recordDataSplit[4]));
		material.add(recordDataSplit[5]);
		UFID.add(recordDataSplit[3]);
	}

	@Override
	public String[] getInsertQuery(String fileName) {
		// TODO Auto-generated method stub
		String[] _querys = super.getInsertQuery(fileName);
		String[] querys = new String[_querys.length + recordID.size()];
		String query;

		int index = 0;
		
		for(index=0; index<_querys.length; index++){
			querys[index] = _querys[index];
		}		
		
		for(int i=0; i<recordID.size(); i++){
			query = "insert into " + dataTableName + " values('" + fileName + "', " + layerID + ", " + recordID.get(i) + ", '"
					+ type.get(i) + "', '" + topBottom.get(i) + "', " + extension.get(i) + ", " + height.get(i) + ", '" + material.get(i) + "', '"
					+ UFID.get(i) + "', " + wkts.get(i) + ", '" + gattrs.get(i) + "')";
			
			querys[index++] = query;
		}
		
		return querys;
	}

	@Override
	public void setLayerID(int layerID) {
		// TODO Auto-generated method stub
		super.setLayerID(layerID);
	}
	
	@Override
	public String[] getCreateSchema() {
		// TODO Auto-generated method stub
		super.getCreateSchema();
		
		schema[1] = "CREATE TABLE IF NOT EXISTS " + dataTableName + "(  "
				+ "filename text NOT NULL,  layerid bigint NOT NULL,  recordid bigint NOT NULL, "
				+ "type text, topbottom text, extension double precision, height double precision, material text, "
				+ "ufid text,  shape geometry,  gattr text, "
				+ "CONSTRAINT " + dataTableName + "_primarykey PRIMARY KEY (filename, layerid, recordid))";
		
		return schema;
	}

	@Override
	public void exportDataToFile(PrintWriter ngiWriter, PrintWriter ndaWriter) {
		// TODO Auto-generated method stub
		super.exportDataToFile(ngiWriter, ndaWriter);
		
		// NDA export
		ndaWriter.println("<DATA>");
		for(int i=0; i<recordID.size(); i++){
			ndaWriter.println("$RECORD " + recordID.get(i));
			ndaWriter.println(recordID.get(i) + ", "
					+ type.get(i) + ", " + topBottom.get(i) + ", " + extension.get(i) + ", " + height.get(i) + ", " + material.get(i) + ", "
					+ UFID.get(i));
		}
		ndaWriter.println("<END>");
		ndaWriter.println("<LAYER_END>");
	}

	@Override
	public void importDataFromDB(String tableType, ResultSet rs) {
		// TODO Auto-generated method stub
		super.importDataFromDB(tableType, rs);
		
		if(tableType.equals("Data")){
			try{
				rs.beforeFirst();
				while(rs.next()){
					String _type = rs.getString("type");
					String _topBottom = rs.getString("topbottom");
					Double _extension = rs.getDouble("extension");
					Double _height = rs.getDouble("height");
					String _material = rs.getString("material");
					
					type.add(_type);
					topBottom.add(_topBottom);
					extension.add(_extension);
					height.add(_height);
					material.add(_material);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
