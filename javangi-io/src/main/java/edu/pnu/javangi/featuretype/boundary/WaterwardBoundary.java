package edu.pnu.javangi.featuretype.boundary;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class WaterwardBoundary extends AbstractFeatureType{     // 수부 지형경계 G0020000
	private ArrayList<String> name;	               // 명칭
	private ArrayList<String> form;                // 형태
		
	public WaterwardBoundary(){
		super();
		layerName = "G0020000";
		name = new ArrayList<String>();
		form = new ArrayList<String>();
		
		metaDataTableName = "waterwardboundarymetadata";
		dataTableName = "waterwardboundarydata";
	}
	
	public WaterwardBoundary(int layerID, String layerName){
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

	public ArrayList<String> getForm() {
		return form;
	}
	
	public int getNumForm(){
		return form.size();
	}
	
	public String getFormN(int n){
		return form.get(n);
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
		form.add(recordDataSplit[2]);
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
					+ name.get(i) + "', '" + form.get(i) + "', '"
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
				+ "name text, form text, "
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
					+ name.get(i) + ", " + form.get(i) + ", "
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
					String _form = rs.getString("form");
					
					name.add(_name);
					form.add(_form);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
