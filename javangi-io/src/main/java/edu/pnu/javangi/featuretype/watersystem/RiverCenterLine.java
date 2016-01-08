package edu.pnu.javangi.featuretype.watersystem;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class RiverCenterLine extends AbstractFeatureType{       // 하천중심선 E0020000
	private ArrayList<Long> riverNumber;           // 하천번호
	private ArrayList<String> name;                // 명칭
	private ArrayList<String> classification;      // 구분
	private ArrayList<String> form;                // 형태
	private ArrayList<String> state;               // 상태
		
	public RiverCenterLine(){
		super();
		layerName = "E0020000";
		riverNumber = new ArrayList<Long>();
		name = new ArrayList<String>();
		classification = new ArrayList<String>();
		form = new ArrayList<String>();
		state = new ArrayList<String>();
		
		metaDataTableName = "rivercenterlinemetadata";
		dataTableName = "rivercenterlinedata";
	}
	
	public RiverCenterLine(int layerID, String layerName){
		super(layerID, layerName);
	}

	public ArrayList<Long> getRiverNumber() {
		return riverNumber;
	}

	public int getNumRiverNumber(){
		return riverNumber.size();
	}
	
	public Long getRiverNumberN(int n){
		return riverNumber.get(n);
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

	public ArrayList<String> getForm() {
		return form;
	}

	public int getNumForm(){
		return form.size();
	}
	
	public String getFormN(int n){
		return form.get(n);
	}

	public ArrayList<String> getState() {
		return state;
	}

	public int getNumState(){
		return state.size();
	}
	
	public String getStateN(int n){
		return state.get(n);
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
		riverNumber.add(Long.valueOf(recordDataSplit[1]));
		name.add(recordDataSplit[2]);
		classification.add(recordDataSplit[3]);
		form.add(recordDataSplit[4]);
		state.add(recordDataSplit[5]);
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
			query = "insert into " + dataTableName + " values('" + fileName + "', " + layerID + ", " + recordID.get(i) + ", "
					+ riverNumber.get(i) + ", '" + name.get(i) + "', '" + classification.get(i) + "', '" + form.get(i) + "', '" + state.get(i) + "', '"
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
				+ "rivernumber int8, name text, classification text, form text, state text, "
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
					+ riverNumber.get(i) + ", " + name.get(i) + ", " + classification.get(i) + ", "
					+ form.get(i) + ", " + state.get(i) + ", "
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
					Long _riverNumber = rs.getLong("rivernumber");
					String _name = rs.getString("name");
					String _classification = rs.getString("classification");
					String _form = rs.getString("form");
					String _state = rs.getString("state");
					
					riverNumber.add(_riverNumber);
					name.add(_name);
					classification.add(_classification);
					form.add(_form);
					state.add(_state);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
