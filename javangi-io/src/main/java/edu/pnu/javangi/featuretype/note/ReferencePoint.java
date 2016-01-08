package edu.pnu.javangi.featuretype.note;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class ReferencePoint extends AbstractFeatureType{        // 기준점 H0020000
	private ArrayList<String> classification;      // 구분
	private ArrayList<String> grade;               // 등급
	private ArrayList<String> number;              // 번호
	private ArrayList<String> coordinate;          // 좌표
	private ArrayList<Double> elevation;           // 표고
		
	public ReferencePoint(){
		super();
		layerName = "H0020000";
		classification = new ArrayList<String>();
		grade = new ArrayList<String>();
		number = new ArrayList<String>();
		coordinate = new ArrayList<String>();
		elevation = new ArrayList<Double>();
		
		metaDataTableName = "referencepointmetadata";
		dataTableName = "referencepointdata";
	}
	
	public ReferencePoint(int layerID, String layerName){
		super(layerID, layerName);
	}

	public ArrayList<String> getClassification() {
		return classification;
	}

	public int getNumClassification(){
		return classification.size();
	}
	
	public String getClassificationN(int n){
		return classification.get(n);
	}

	public ArrayList<String> getGrade() {
		return grade;
	}

	public int getNumGrade(){
		return grade.size();
	}
	
	public String getGradeN(int n){
		return grade.get(n);
	}

	public ArrayList<String> getNumber() {
		return number;
	}

	public int getNumNumber(){
		return number.size();
	}
	
	public String getNumberN(int n){
		return number.get(n);
	}

	public ArrayList<String> getCoordinate() {
		return coordinate;
	}

	public int getNumCoordinate(){
		return coordinate.size();
	}
	
	public String getCoordinateN(int n){
		return coordinate.get(n);
	}

	public ArrayList<Double> getElevation() {
		return elevation;
	}

	public int getNumElevation(){
		return elevation.size();
	}
	
	public Double getElevationN(int n){
		return elevation.get(n);
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
		classification.add(recordDataSplit[1]);
		grade.add(recordDataSplit[2]);
		number.add(recordDataSplit[3]);
		coordinate.add(recordDataSplit[4]);
		elevation.add(Double.valueOf(recordDataSplit[5]));
		UFID.add(recordDataSplit[6]);
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
					+ classification.get(i) + "', '" + grade.get(i) + "', '" + number.get(i) + "', '"
					+ coordinate.get(i) + "', " + elevation.get(i) + ", '"
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
				+ "classification text, grade text, number text, coordinate text, elevation double precision, "
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
					+ classification.get(i) + ", " + grade.get(i) + ", " + number.get(i) + ", "
					+ coordinate.get(i) + ", " + elevation.get(i) + ", "
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
					String _classification = rs.getString("classification");
					String _grade = rs.getString("grade");
					String _number = rs.getString("number");
					String _coordinate = rs.getString("coordinate");
					Double _elevation = rs.getDouble("elevation");
					
					classification.add(_classification);
					grade.add(_grade);
					number.add(_number);
					coordinate.add(_coordinate);
					elevation.add(_elevation);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
