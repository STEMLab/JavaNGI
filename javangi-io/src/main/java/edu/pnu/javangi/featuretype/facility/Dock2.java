package edu.pnu.javangi.featuretype.facility;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class Dock2 extends AbstractFeatureType{ // ����(dock) C0040000
	private ArrayList<String> purpose;				// �뵵
	private ArrayList<String> material;				// ����
	private ArrayList<String> managementAgency;		// �������
		
	public Dock2(){
		super();
		layerName = "C0040000";
		purpose = new ArrayList<String>();
		material = new ArrayList<String>();
		managementAgency = new ArrayList<String>();
		
		metaDataTableName = "dock2metadata";
		dataTableName = "dock2data";
	}
	
	public Dock2(int layerID, String layerName){
		super(layerID, layerName);
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

	public ArrayList<String> getMaterial() {
		return material;
	}

	public int getNumMaterial(){
		return material.size();
	}
	
	public String getMaterialN(int n){
		return material.get(n);
	}

	public ArrayList<String> getManagementAgency() {
		return managementAgency;
	}

	public int getNumManagementAgency(){
		return managementAgency.size();
	}
	
	public String getManagementAgencyN(int n){
		return managementAgency.get(n);
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
		purpose.add(recordDataSplit[1]);
		material.add(recordDataSplit[2]);
		managementAgency.add(recordDataSplit[3]);
		UFID.add(recordDataSplit[4]);
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
					+ purpose.get(i) + "', '" + material.get(i) + "', '" + managementAgency.get(i) + "', '"
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
				+ "  purpose text,  material text, managementagency text, "
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
					+ purpose.get(i) + ", " + material.get(i) + ", " + managementAgency.get(i) + ", "
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
					String _purpose = rs.getString("purpose");
					String _material = rs.getString("material");
					String _managementAgency = rs.getString("managementagency");
					
					purpose.add(_purpose);
					material.add(_material);
					managementAgency.add(_managementAgency);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
