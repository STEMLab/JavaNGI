package edu.pnu.javangi.featuretype.facility;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class Tower extends AbstractFeatureType{        // 탑 C0340000
	private ArrayList<String> name;                // 명칭
	private ArrayList<String> classification;      // 구분
	private ArrayList<String> material;            // 재질
	private ArrayList<String> etc;                 // 기타
		
	public Tower(){
		super();
		layerName = "C0340000";
		name = new ArrayList<String>();
		classification = new ArrayList<String>();
		material = new ArrayList<String>();
		etc = new ArrayList<String>();
		
		metaDataTableName = "towermetadata";
		dataTableName = "towerdata";
	}
	
	public Tower(int layerID, String layerName){
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

	public ArrayList<String> getMaterial() {
		return material;
	}

	public int getNumMaterial(){
		return material.size();
	}
	
	public String getMaterialN(int n){
		return material.get(n);
	}

	public ArrayList<String> getEtc() {
		return etc;
	}

	public int getNumEtc(){
		return etc.size();
	}
	
	public String getEtcN(int n){
		return etc.get(n);
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
			recordNumSplit[i] = trimSpace(recordNumSplit[i]);
		}
		for(int i=0; i<recordDataSplit.length; i++){
			recordDataSplit[i] = trimSpace(recordDataSplit[i]);
		}
		
		recordID.add(Integer.valueOf(recordDataSplit[0]));
		name.add(recordDataSplit[1]);
		classification.add(recordDataSplit[2]);
		material.add(recordDataSplit[3]);
		etc.add(recordDataSplit[4]);
		UFID.add(recordDataSplit[5]);
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
					+ name.get(i) + "', '" + classification.get(i) + "', '" + material.get(i) + "', '" + etc.get(i) + "', '"
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
				+ "filename text NOT NULL,  layerid bigint NOT NULL,  recordid bigint NOT NULL,"
				+ "  name text,  classification text, material text, etc text, "
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
					+ name.get(i) + ", " + classification.get(i) + ", " + material.get(i) + ", " + etc.get(i) + ", "
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
					String _material = rs.getString("material");
					String _etc = rs.getString("etc");
					
					name.add(_name);
					classification.add(_classification);
					material.add(_material);
					etc.add(_etc);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

}
