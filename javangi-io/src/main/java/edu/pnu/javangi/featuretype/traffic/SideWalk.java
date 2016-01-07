package edu.pnu.javangi.featuretype.traffic;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class SideWalk extends AbstractFeatureType { // �ε�(����) A0033320
	private ArrayList<Double> width;			// ��
	private ArrayList<String> material;		// ����
	private ArrayList<String> bicycleRoad;		// �����ŵ�������
	private ArrayList<String> type;			// ����
	
	public SideWalk(){
		super();
		layerName = "A0033320";
		width = new ArrayList<Double>();
		material = new ArrayList<String>();
		bicycleRoad = new ArrayList<String>();
		type = new ArrayList<String>();
		
		metaDataTableName = "sidewalkmetadata";
		dataTableName = "sidewalkdata";
	}
	
	public SideWalk(int layerID, String layerName){
		super(layerID, layerName);
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

	public ArrayList<String> getMaterial() {
		return material;
	}

	public int getNumMaterial(){
		return material.size();
	}
	
	public String getMaterialN(int n){
		return material.get(n);
	}

	public ArrayList<String> getBicycleRoad() {
		return bicycleRoad;
	}

	public int getNumBicycleRoad(){
		return bicycleRoad.size();
	}
	
	public String getBicycleRoadN(int n){
		return bicycleRoad.get(n);
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
		width.add(Double.valueOf(recordDataSplit[1]));
		material.add(recordDataSplit[2]);
		bicycleRoad.add(recordDataSplit[3]);
		type.add(recordDataSplit[4]);
		UFID.add(recordDataSplit[5]);
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
			query = "insert into " + dataTableName + " values('" + fileName + "', " + layerID + ", " + recordID.get(i) + ", "
					+ width.get(i) + ", '" + material.get(i) + "', '" + bicycleRoad.get(i) + "', '" + type.get(i) + "', '"
					+ UFID.get(i) + "', " + wkts.get(i) + ", '" + gattrs.get(i) + "')";
			
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
				+ "  width double precision,  mateiral text, bicycleRoad text, type text, "
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
					+ width.get(i) + ", " + material.get(i) + ", " + bicycleRoad.get(i) + ", "	+ type.get(i) + ", "
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
					Double _width = rs.getDouble("width");
					String _material = rs.getString("material");
					String _bicycleRoad = rs.getString("bicycleRoad");
					String _type = rs.getString("type");
					
					width.add(_width);
					material.add(_material);
					bicycleRoad.add(_bicycleRoad);
					type.add(_type);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
