package edu.pnu.javangi.featuretype.traffic;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class SolidCrossSection extends AbstractFeatureType{     // 입체교차부 A0090000
	private ArrayList<String> name;                // 명칭
	private ArrayList<String> classification;      // 구분
	private ArrayList<Double> extension;           // 연장
	private ArrayList<Double> height;              // 높이
	private ArrayList<Double> passingLoad;         // 통과하중
	private ArrayList<String> material;            // 재질
	private ArrayList<String> sideWalk;            // 보도
	
	public SolidCrossSection(){
		super();
		layerName = "A0090000";
		name = new ArrayList<String>();
		classification = new ArrayList<String>();
		extension = new ArrayList<Double>();
		height = new ArrayList<Double>();
		passingLoad = new ArrayList<Double>();
		material = new ArrayList<String>();
		sideWalk = new ArrayList<String>();
		
		metaDataTableName = "solidcrosssectionmetadata";
		dataTableName = "solidcrosssectiondata";
	}
	
	public SolidCrossSection(int layerID, String layerName){
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

	public ArrayList<Double> getPassingLoad() {
		return passingLoad;
	}

	public int getNumPassingLoad(){
		return passingLoad.size();
	}
	
	public Double getPassingLoadN(int n){
		return passingLoad.get(n);
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

	public ArrayList<String> getSideWalk() {
		return sideWalk;
	}

	public int getNumSidewalk(){
		return sideWalk.size();
	}
	
	public String getSideWalkN(int n){
		return sideWalk.get(n);
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
		extension.add(Double.valueOf(recordDataSplit[3]));
		height.add(Double.valueOf(recordDataSplit[4]));
		passingLoad.add(Double.valueOf(recordDataSplit[5]));
		material.add(recordDataSplit[6]);
		sideWalk.add(recordDataSplit[7]);
		UFID.add(recordDataSplit[8]);
	}

	@Override
	public void setLayerID(int layerID) {
		// TODO Auto-generated method stub
		super.setLayerID(layerID);
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
					+ name.get(i) + "', '" + classification.get(i) + "', " + extension.get(i) + ", " + height.get(i) + ", " + passingLoad.get(i)
					+ ", '" + material.get(i) + "', '" + sideWalk.get(i) + "', '" + UFID.get(i) + "', "
					+ wkts.get(i) + ", '" + gattrs.get(i) + "')";
			
			querys[index++] = query;
		}
		
		return querys;
	}

	@Override
	public String[] getCreateSchema() {
		// TODO Auto-generated method stub
		super.getCreateSchema();
		
		schema[1] = "CREATE TABLE IF NOT EXISTS " + dataTableName + "(  "
				+ "filename text NOT NULL,  layerid bigint NOT NULL,  recordid bigint NOT NULL,"
				+ "  name text,  classification text, extension double precision, height double precision, passingload double precision, "
				+" material text, sidewalk text, "
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
					+ name.get(i) + ", " + classification.get(i) + ", " + extension.get(i) + ", "	+ height.get(i) + ", " + passingLoad.get(i) + ", "
					+ material.get(i) + ", " + sideWalk.get(i) + ", "
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
					Double _extension = rs.getDouble("extension");
					Double _height = rs.getDouble("height");
					Double _passingLoad = rs.getDouble("passingload");
					String _material = rs.getString("material");
					String _sideWalk = rs.getString("sidewalk");
					
					name.add(_name);
					classification.add(_classification);
					extension.add(_extension);
					height.add(_height);
					passingLoad.add(_passingLoad);
					material.add(_material);
					sideWalk.add(_sideWalk);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
