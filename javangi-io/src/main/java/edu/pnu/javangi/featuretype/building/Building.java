package edu.pnu.javangi.featuretype.building;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class Building extends AbstractFeatureType{      // 건물 B0010000
	private ArrayList<String> name;                // 명칭
	private ArrayList<String> classification;      // 구분
	private ArrayList<String> type;                // 종류
	private ArrayList<String> purpose;             // 용도
	private ArrayList<String> cycle;               // 주기
	private ArrayList<Long> floors;                // 층수
		
	public Building(){
		super();
		layerName = "B0010000";
		name = new ArrayList<String>();
		classification = new ArrayList<String>();
		type = new ArrayList<String>();
		purpose = new ArrayList<String>();
		cycle = new ArrayList<String>();
		floors = new ArrayList<Long>();
		
		metaDataTableName = "buildingmetadata";
		dataTableName = "buildingdata";
	}
	
	public Building(int layerID, String layerName){
		super(layerID, layerName);
	}

	public ArrayList<String> getName() {
		return name;
	}

	public int getNumName(){
		return name.size();
	}
	
	public String getNameN(int n){
		return name.get(n);
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

	public ArrayList<String> getType() {
		return type;
	}

	public int getNumType(){
		return type.size();
	}
	
	public String getTypeN(int n){
		return type.get(n);
	}

	public ArrayList<String> getPurpose() {
		return purpose;
	}

	public int getNumPurpose(){
		return purpose.size();
	}
	
	public String getPurposeN(int n){
		return purpose.get(n);
	}

	public ArrayList<String> getCycle() {
		return cycle;
	}

	public int getNumCycle(){
		return cycle.size();
	}
	
	public String getCycleN(int n){
		return cycle.get(n);
	}

	public ArrayList<Long> getFloors() {
		return floors;
	}

	public int getNumFloors(){
		return floors.size();
	}
	
	public Long getFloorsN(int n){
		return floors.get(n);
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
		name.add(recordDataSplit[1]);
		classification.add(recordDataSplit[2]);
		type.add(recordDataSplit[3]);
		purpose.add(recordDataSplit[4]);
		cycle.add(recordDataSplit[5]);
		floors.add(Long.valueOf(recordDataSplit[6]));
		UFID.add(recordDataSplit[7]);
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
					+ name.get(i) + "', '" + classification.get(i) + "', '" + type.get(i) + "', '" + purpose.get(i) + "', '"
					+ cycle.get(i) + "', " + floors.get(i) + ", '" + UFID.get(i) + "', " + wkts.get(i) + ", '" + gattrs.get(i) + "')";
			
			querys[index++] = query;
		}
		
		return querys;
	}

	@Override
	public void setLayerID(int layerID) {
		// TODO Auto-generated method stub
		super.setLayerID(layerID);
	}
	
	public String[] getCreateSchema() {
		// TODO Auto-generated method stub
		super.getCreateSchema();
		
		schema[1] = "CREATE TABLE IF NOT EXISTS " + dataTableName + "(  "
				+ "filename text NOT NULL,  layerid bigint NOT NULL,  recordid bigint NOT NULL,"
				+ "  name text,  classification text,  type text,  purpose text,"
				+ "  cycle text, floors int8, "
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
					+ name.get(i) + ", " + classification.get(i) + ", " + type.get(i) + ", "	+ purpose.get(i) + ", "
					+ cycle.get(i) + ", " + floors.get(i) + ", "
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
					String _name = rs.getString("name");
					String _classification = rs.getString("classification");
					String _type = rs.getString("type");
					String _purpose = rs.getString("purpose");
					String _cycle = rs.getString("cycle");
					Long _floors = rs.getLong("floors");
					
					name.add(_name);
					classification.add(_classification);
					type.add(_type);
					purpose.add(_purpose);
					cycle.add(_cycle);
					floors.add(_floors);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
