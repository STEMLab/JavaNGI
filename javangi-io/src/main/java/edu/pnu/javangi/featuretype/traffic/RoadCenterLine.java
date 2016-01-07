package edu.pnu.javangi.featuretype.traffic;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.AbstractFeatureType;

public class RoadCenterLine extends AbstractFeatureType{ // �����߽ɼ� A0020000
	private ArrayList<String> number;				// ���ι�ȣ
	private ArrayList<String> name;				// ��Ī
	private ArrayList<String> classification;				// ���α���
	private ArrayList<String> startPoint;			// ����
	private ArrayList<String> endPoint;			// ����
	private ArrayList<String> packaging;			// ��������
	private ArrayList<String> centralReservation;	// �и�������
	private ArrayList<Long> numberOfRoad;			// ���μ�
	private ArrayList<Double> width;				// ������
	private ArrayList<String> oneSidePassing;		// �Ϲ�����
	private ArrayList<String> etc;					// ��Ÿ
	
	public RoadCenterLine(){
		super();
		layerName = "A0020000";
		number = new ArrayList<String>();
		name = new ArrayList<String>();
		classification = new ArrayList<String>();
		startPoint = new ArrayList<String>();
		endPoint = new ArrayList<String>();
		packaging = new ArrayList<String>();
		centralReservation = new ArrayList<String>();
		numberOfRoad = new ArrayList<Long>();
		width = new ArrayList<Double>();
		oneSidePassing = new ArrayList<String>();
		etc = new ArrayList<String>();
		
		metaDataTableName = "roadcenterlinemetadata";
		dataTableName = "roadcenterlinedata";
	}
	
	public RoadCenterLine(int layerID, String layerName){
		super(layerID, layerName);
	}

	public ArrayList<String> getNumber() {
		return number;
	}

	public int getNumNumber(){
		return number.size();
	}
	
	public String getNumberN(int n){
		return number.get(n);
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

	public ArrayList<String> getStartPoint() {
		return startPoint;
	}

	public int getNumStartPoint(){
		return startPoint.size();
	}
	
	public String getStartPointN(int n){
		return startPoint.get(n);
	}

	public ArrayList<String> getEndPoint() {
		return endPoint;
	}

	public int getNumEndPoint(){
		return endPoint.size();
	}
	
	public String getEndPointN(int n){
		return endPoint.get(n);
	}

	public ArrayList<String> getPackaging() {
		return packaging;
	}

	public int getNumPackaging(){
		return packaging.size();
	}
	
	public String getPackagingN(int n){
		return packaging.get(n);
	}

	public ArrayList<String> getCentralReservation() {
		return centralReservation;
	}

	public ArrayList<Long> getNumberOfRoad() {
		return numberOfRoad;
	}

	public ArrayList<Double> getWidth() {
		return width;
	}

	public ArrayList<String> getOneSidePassing() {
		return oneSidePassing;
	}

	public ArrayList<String> getEtc() {
		return etc;
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
		number.add(recordDataSplit[1]);
		name.add(recordDataSplit[2]);
		classification.add(recordDataSplit[3]);
		startPoint.add(recordDataSplit[4]);
		endPoint.add(recordDataSplit[5]);
		packaging.add(recordDataSplit[6]);
		centralReservation.add(recordDataSplit[7]);
		numberOfRoad.add(Long.valueOf(recordDataSplit[8]));
		width.add(Double.valueOf(recordDataSplit[9]));
		oneSidePassing.add(recordDataSplit[10]);
		etc.add(recordDataSplit[11]);
		UFID.add(recordDataSplit[12]);
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
					+ number.get(i) + "', '" + name.get(i) + "', '" + classification.get(i) + "', '" + startPoint.get(i) + "', '"
					+ endPoint.get(i) + "', '" + packaging.get(i) + "', '" + centralReservation.get(i) + "', " + numberOfRoad.get(i) + ", "
					+ width.get(i) + ", '" + oneSidePassing.get(i) + "', '" + etc.get(i) + "', '" + UFID.get(i) + "', "
					+ wkts.get(i) + ", '" + gattrs.get(i) + "')";
			
			querys[index++] = query;
		}
		
		return querys;
	}


	@Override
	public String[] getCreateSchema() {
		// TODO Auto-generated method stub
		super.getCreateSchema();
		
		schema[1] = "CREATE TABLE IF NOT EXISTS roadcenterlinedata(  filename text,  layerid bigint,  recordid bigint, "
				+ "\"number\" text,  name text,  classification text,"
				+ "  \"startpoint\" text,  \"endpoint\" text,  packaging text,  \"centralreservation\" text,  numberofroad int8,"
				+ "  width double precision,  onesidepassing text,  etc text, "
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
					+ number.get(i) + ", " + name.get(i) + ", " + classification.get(i) + ", " + startPoint.get(i) + ", "	+ endPoint.get(i) + ", " 
					+ packaging.get(i) + ", " + centralReservation.get(i) + ", " + numberOfRoad.get(i) + ", " +
					+ width.get(i) + ", " + oneSidePassing.get(i) + ", " + etc.get(i) + ", "
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
					String _number = rs.getString("number");
					String _name = rs.getString("name");
					String _classification = rs.getString("classification");
					String _startPoint = rs.getString("startpoint");
					String _endPoint = rs.getString("endpoint");
					String _packaging = rs.getString("packaging");
					String _centralReservation = rs.getString("centralreservation");
					Long _numberOfRoad = rs.getLong("numberofroad");
					Double _width = rs.getDouble("width");
					String _oneSidePassing = rs.getString("onesidepassing");
					String _etc = rs.getString("etc");
					
					number.add(_number);
					name.add(_name);
					classification.add(_classification);
					startPoint.add(_startPoint);
					endPoint.add(_endPoint);
					packaging.add(_packaging);
					centralReservation.add(_centralReservation);
					numberOfRoad.add(_numberOfRoad);
					width.add(_width);
					oneSidePassing.add(_oneSidePassing);
					etc.add(_etc);
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
}
