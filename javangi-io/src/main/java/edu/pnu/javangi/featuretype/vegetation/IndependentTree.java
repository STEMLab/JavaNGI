package edu.pnu.javangi.featuretype.vegetation;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class IndependentTree extends AbstractFeatureType{       // 독립수 D0030000
	private ArrayList<String> managementNumber;    // 관리번호
	private ArrayList<String> name;                // 명칭
	private ArrayList<String> classification;      // 구분
	private ArrayList<Double> diameter;            // 직경(둘레)
	private ArrayList<Double> height;              // 높이
	private ArrayList<Double> age;                 // 수령
	private ArrayList<String> species;             // 수종
	private ArrayList<String> etc;                 // 기타
		
	public IndependentTree(){
		super();
		layerName = "D0030000";
		managementNumber = new ArrayList<String>();
		name = new ArrayList<String>();
		classification = new ArrayList<String>();
		diameter = new ArrayList<Double>();
		height = new ArrayList<Double>();
		age = new ArrayList<Double>();
		species = new ArrayList<String>();
		etc = new ArrayList<String>();
		
		metaDataTableName = "independenttreemetadata";
		dataTableName = "independenttreedata";
	}
	
	public IndependentTree(int layerID, String layerName){
		super(layerID, layerName);
	}

	public ArrayList<String> getManagementNumber() {
		return managementNumber;
	}

	public int getNumManagementNumber(){
		return managementNumber.size();
	}
	
	public String getManagementNumberN(int n){
		return managementNumber.get(n);
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

	public ArrayList<Double> getDiameter() {
		return diameter;
	}

	public int getNumDiameter(){
		return diameter.size();
	}
	
	public Double getDiameterN(int n){
		return diameter.get(n);
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

	public ArrayList<Double> getAge() {
		return age;
	}

	public int getNumAge(){
		return age.size();
	}
	
	public Double getAgeN(int n){
		return age.get(n);
	}

	public ArrayList<String> getSpecies() {
		return species;
	}

	public int getNumSpecies(){
		return species.size();
	}
	
	public String getSpeciesN(int n){
		return species.get(n);
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
		managementNumber.add(recordDataSplit[1]);
		name.add(recordDataSplit[2]);
		classification.add(recordDataSplit[3]);
		diameter.add(Double.valueOf(recordDataSplit[4]));
		height.add(Double.valueOf(recordDataSplit[5]));
		age.add(Double.valueOf(recordDataSplit[6]));
		species.add(recordDataSplit[7]);
		etc.add(recordDataSplit[8]);
		UFID.add(recordDataSplit[9]);
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
					+ managementNumber.get(i) + "', '" +  name + "', '" + classification.get(i) + "', "
					+ diameter.get(i) + ", " + height.get(i) + ", " + age.get(i) + ", '" + species.get(i) + "', '" + etc.get(i) + "', '"
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
				+ "managementnumber text, name text, classification text, diameter double precision, "
				+ "height double precision, age double precision, species text, etc text, "
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
					+ managementNumber.get(i) + ", " + name.get(i) + ", " + classification.get(i) + ", "
					+ diameter.get(i) + ", " + height.get(i) + ", " + age.get(i) + ", " + species.get(i) + ", " + etc.get(i) + ", "
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
					String _managementNumber = rs.getString("managementnumber");
					String _name = rs.getString("name");
					String _classification = rs.getString("classification");
					Double _diameter = rs.getDouble("diameter");
					Double _height = rs.getDouble("height");
					Double _age = rs.getDouble("age");
					String _species = rs.getString("species");
					String _etc = rs.getString("etc");
					
					managementNumber.add(_managementNumber);
					name.add(_name);
					classification.add(_classification);
					diameter.add(_diameter);
					height.add(_height);
					age.add(_age);
					species.add(_species);
					etc.add(_etc);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
