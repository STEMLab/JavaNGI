package edu.pnu.javangi.featuretype.watersystem;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class IrrigationCanal extends AbstractFeatureType{       // 용수로 E0060000
	private ArrayList<String> name;                        // 명칭
	private ArrayList<String> form;                        // 형태
	private ArrayList<String> purpose;                     // 용도
	private ArrayList<Double> extension;                   // 연장
	private ArrayList<Double> width;                       // 폭
	private ArrayList<String> managementAgency;            // 관리기관
		
	public IrrigationCanal(){
		super();
		layerName = "E0060000";
		name = new ArrayList<String>();
		form = new ArrayList<String>();
		purpose = new ArrayList<String>();
		extension  = new ArrayList<Double>();
		width = new ArrayList<Double>();
		managementAgency = new ArrayList<String>();
		
		metaDataTableName = "irrigationcanalmetadata";
		dataTableName = "irrigationcanaldata";
	}
	
	public IrrigationCanal(int layerID, String layerName){
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

	public ArrayList<String> getPurpose() {
		return purpose;
	}

	public int getNumPurpose(){
		return purpose.size();
	}
	
	public String getPurposeN(int n){
		return purpose.get(n);
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

	public ArrayList<Double> getWidth() {
		return width;
	}

	public int getNumWidth(){
		return width.size();
	}
	
	public Double getWidthN(int n){
		return width.get(n);
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
			recordNumSplit[i] = trimSpaceAndQuotes(recordNumSplit[i]);
		}
		for(int i=0; i<recordDataSplit.length; i++){
			recordDataSplit[i] = trimSpaceAndQuotes(recordDataSplit[i]);
		}
		
		recordID.add(Integer.valueOf(recordDataSplit[0]));
		name.add(recordDataSplit[1]);
		form.add(recordDataSplit[2]);
		purpose.add(recordDataSplit[3]);
		extension.add(Double.valueOf(recordDataSplit[4]));
		width.add(Double.valueOf(recordDataSplit[5]));
		managementAgency.add(recordDataSplit[6]);
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
					+ name.get(i) + "', '" + form.get(i) + "', '" + purpose.get(i) + "', " + extension.get(i) + ", " + width.get(i) + ", '" + managementAgency.get(i) + "', '"
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
				+ "name text, form text, purpose text, extension double precision, width double precision, managementAgency text, "
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
					+ name.get(i) + ", " + form.get(i) + ", " + purpose.get(i) + ", " + extension.get(i) + ", " + width.get(i) + ", " + managementAgency.get(i) + ", "
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
					String _purpose = rs.getString("purpose");
					Double _extension = rs.getDouble("extension");
					Double _width = rs.getDouble("width");
					String _management = rs.getString("managementagency");
					
					name.add(_name);
					form.add(_form);
					purpose.add(_purpose);
					extension.add(_extension);
					width.add(_width);
					managementAgency.add(_management);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
