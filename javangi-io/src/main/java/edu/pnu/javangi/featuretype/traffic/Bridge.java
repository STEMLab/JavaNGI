package edu.pnu.javangi.featuretype.traffic;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class Bridge extends AbstractFeatureType{        // 교량 A0070000
	private ArrayList<String> name;                // 명칭
	private ArrayList<String> type;                // 종류
	private ArrayList<Double> extension;           // 연장
	private ArrayList<Double> width;               // 폭
	private ArrayList<String> installationYear;    // 설치년도
	private ArrayList<String> material;            // 재질
	private ArrayList<String> riverName;           // 하천명
	private ArrayList<String> etc;                 // 기타
		
	public Bridge(){
		super();
		layerName = "A0070000";
		name = new ArrayList<String>();
		type = new ArrayList<String>();
		extension = new ArrayList<Double>();
		width = new ArrayList<Double>();
		installationYear = new ArrayList<String>();
		material = new ArrayList<String>();
		riverName = new ArrayList<String>();
		etc = new ArrayList<String>();
		
		metaDataTableName = "bridgemetadata";
		dataTableName = "bridgedata";
	}
	
	public Bridge(int layerID, String layerName){
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

	public ArrayList<String> getType() {
		return type;
	}

	public int getNumType(){
		return type.size();
	}
	
	public String getTypeN(int n){
		return type.get(n);
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

	public ArrayList<String> getInstallationYear() {
		return installationYear;
	}

	public int getNumInstallationYear(){
		return installationYear.size();
	}
	
	public String getInstallationYearN(int n){
		return installationYear.get(n);
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

	public ArrayList<String> getRiverName() {
		return riverName;
	}

	public int getNumRiverName(){
		return riverName.size();
	}
	
	public String getRiverNameN(int n){
		return riverName.get(n);
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
			recordNumSplit[i] = trimSpaceAndQuotes(recordNumSplit[i]);
		}
		for(int i=0; i<recordDataSplit.length; i++){
			recordDataSplit[i] = trimSpaceAndQuotes(recordDataSplit[i]);
		}
		
		recordID.add(Integer.valueOf(recordDataSplit[0]));
		name.add(recordDataSplit[1]);
		type.add(recordDataSplit[2]);
		extension.add(Double.valueOf(recordDataSplit[3]));
		width.add(Double.valueOf(recordDataSplit[4]));
		installationYear.add(recordDataSplit[5]);
		material.add(recordDataSplit[6]);
		riverName.add(recordDataSplit[7]);
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
					+ name.get(i) + "', '" + type.get(i) + "', " + extension.get(i) + ", " + width.get(i) + ", '" + installationYear.get(i)
					+ "', '" + material.get(i) + "', '" + riverName.get(i) + "', '" + etc.get(i) + "', '" + UFID.get(i) + "', "
					+ wkts.get(i) + ", '" + gattrs.get(i) + "')";
			
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
				+ "  name text,  type text,  extension double precision,  width double precision,  installationyear text,  material text,"
				+ "  rivername text,  etc text,  ufid text,  shape geometry,  gattr text, "
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
					+ name.get(i) + ", " + type.get(i) + ", " + extension.get(i) + ", "	+ width.get(i) + ", " + installationYear.get(i) + ", "
					+ material.get(i) + ", " + riverName.get(i) + ", " + etc.get(i) + ", "
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
					String _type = rs.getString("type");
					Double _extension = rs.getDouble("extension");
					Double _width = rs.getDouble("width");
					String _installationYear = rs.getString("installationYear");
					String _material = rs.getString("material");
					String _riverName = rs.getString("riverName");
					String _etc = rs.getString("etc");
					
					name.add(_name);
					type.add(_type);
					extension.add(_extension);
					width.add(_width);
					installationYear.add(_installationYear);
					material.add(_material);
					riverName.add(_riverName);
					etc.add(_etc);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
