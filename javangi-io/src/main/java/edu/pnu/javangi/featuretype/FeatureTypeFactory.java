package edu.pnu.javangi.featuretype;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeatureTypeFactory {
	private static String[] trafficLayerName = {"A0010000", "A0020000", "A0033320", "A0043325", "A0053326", "A0053328", "A0063321", "A0070000",
										"A0080000", "A0090000",	"A0100000", "A0110020", "A0123373", "A0131122",	"A0140000",	"A0150000",
										"A0160024", "A0171119", "A0180000", "A0191221", "A0201222", "A0210000", "A0222257", "C0540000"}; // ????
	private static String[] trafficClassName = {"RoadBoundary", "RoadCenterLine", "SideWalk", "Crosswalk", "SafetyZone", "SafetyZoneSpeedBump", "PedestrianOverpass", "Bridge",
										"Crossroad", "SolidCrossSection", "InterChange", "Tunnel", "TunnelEntrance", "RailwayStation", "Stop", "Railway",
										"RailwayBoundary", "RailwayCenterLine", "RailwayTurntable", "Platform", "PlatformRoof", "RiverPort", "RiverPortRoute", "TollGate"};
	private static String[] buildingLayerName = {"B0010000", "B0020000"};
	private static String[] buildingClassName = {"Building", "Wall"};
	private static String[] facilityLayerName = {"C0380000", "C0152261", "C0400000", "C0270000", "C0325315", "C0285311", "C0520000", "C0486353", 
										"C0230000", "C0503375", "C0310000", "C0070000", "C0010000", "C0032254", "C0040000", "C0050000", "C0130000",
										"C0142263", "C0116313", "C0423365", "C0530000", "C0556354", "C0305316", "C0250000", "C0160000", "C0220000",
										"C0373362", "C0240000", "C0190000", "C0260000", "C0430000", "C0363361", "C0080000", "C0210000", "C0236242", "C0290000",
										"C0513369", "C0443363", "C0060000", "C0125335", "C0390000", "C0350000", "C0330000", "C0170000", "C0186115", "C0340000",
										"C0493376", "C0410000", "C0106312", "C0470000", "C0453322", "C0463374", "C0090000", "C0025336", "C0200000"};
	private static String[] facilityClassName = {"AmusementFacility", "Beach", "BulletinBoard", "Camp", "Castle", "Cemetery", "CentralReservation", "Chimney",
										"CommunicationPole", "Controller", "CulturalAssets", "Culvert", "Dam", "Dock", "Dock2", "Embankment", "FishFarm",
										"FishingPlace", "Fountain", "GasStation", "GuardFence", "Heliport", "HistoricSite", "Hydrant", "Lighthouse","Lighting",
										"Mailbox", "Manhole", "Mine", "Observatory", "ParkingLot", "PayPhone", "Pier", "Pit", "PowerPole", "CemeterySystem",
										"Reflector", "ServiceStation", "Sluice", "Spa", "Stairway", "Statue", "Stone", "StorageTank", "Tank", "Tower",
										"TrafficLight", "TrafficSign", "TubularWell", "UndergroundVent", "Underpass", "UnderpassEntrance", "WellWaterSprings",
										"Wharf", "Yard"};
	private static String[] vegetationLayerName = {"D0040000", "D0010000", "D0030000", "D0020000"};
	private static String[] vegetationClassName = {"Farm", "FarmlandSystem", "IndependentTree", "TributarySystem"};
	private static String[] watersystemLayerName = {"E0032111", "E0080000", "E0060000", "E0082123", "E0052114", "E0010001", "E0020000", "E0042326", "E0072325"};
	private static String[] watersystemClassName = {"ActualWidthRiver", "Coastline", "IrrigationCanal", "Isobath", "LakeReservoir", "RiverBoundary", "RiverCenterLine",
										"WaterDirection", "Waterfall"};
	private static String[] landformLayerName = {"F0030000", "F0057215", "F0010000", "F0040000", "F0020000"};
	private static String[] landformClassName = {"BankingCutting", "CaveEntrance", "ContourLine", "RetainingWall", "SpotElevation"};
	private static String[] boundaryLayerName = {"G0010000", "G0030000", "G0020000"};
	private static String[] boundaryClassName = {"AdministrationBoundary", "EtcBoundary", "WaterwardBoundary"};
	private static String[] noteLayerName = {"H0037335", "H0059153", "H0010000", "H0040000", "H0020000"};
	private static String[] noteClassName = {"Lattice", "Mountain", "NeatLine", "PlaceName", "ReferencePoint"};
	private static String[] classNamePrefix = {"edu.pnu.javangi.featuretype.traffic.", "edu.pnu.javangi.featuretype.building.", "edu.pnu.javangi.featuretype.facility.",
											"edu.pnu.javangi.featuretype.vegetation.", "edu.pnu.javangi.featuretype.watersystem.", "edu.pnu.javangi.featuretype.landform.",
											"edu.pnu.javangi.featuretype.boundary.", "edu.pnu.javangi.featuretype.note."};
	private static Map<String, String> layerNameMap = new HashMap<String, String>() {
		{
			for(int i=0; i<trafficLayerName.length; i++){
				put(trafficLayerName[i], classNamePrefix[0] + trafficClassName[i]);
			}
			for(int i=0; i<buildingLayerName.length; i++){
				put(buildingLayerName[i], classNamePrefix[1] + buildingClassName[i]);
			}
			for(int i=0; i<facilityLayerName.length; i++){
				put(facilityLayerName[i], classNamePrefix[2] + facilityClassName[i]);
			}
			for(int i=0; i<vegetationLayerName.length; i++){
				put(vegetationLayerName[i], classNamePrefix[3] + vegetationClassName[i]);
			}
			for(int i=0; i<watersystemLayerName.length; i++){
				put(watersystemLayerName[i], classNamePrefix[4] + watersystemClassName[i]);
			}
			for(int i=0; i<landformLayerName.length; i++){
				put(landformLayerName[i], classNamePrefix[5] + landformClassName[i]);
			}
			for(int i=0; i<boundaryLayerName.length; i++){
				put(boundaryLayerName[i], classNamePrefix[6] + boundaryClassName[i]);
			}
			for(int i=0; i<noteLayerName.length; i++){
				put(noteLayerName[i], classNamePrefix[7] + noteClassName[i]);
			}
			
		}
	};
	private static Map<String, String> classNameMap = new HashMap<String, String>() {
		{
			for(int i=0; i<trafficClassName.length; i++){
				put(trafficClassName[i], classNamePrefix[0] + trafficClassName[i]);
			}
			for(int i=0; i<buildingClassName.length; i++){
				put(buildingClassName[i], classNamePrefix[1] + buildingClassName[i]);
			}
			for(int i=0; i<facilityClassName.length; i++){
				put(facilityClassName[i], classNamePrefix[2] + facilityClassName[i]);
			}
			for(int i=0; i<vegetationClassName.length; i++){
				put(vegetationClassName[i], classNamePrefix[3] + vegetationClassName[i]);
			}
			for(int i=0; i<watersystemClassName.length; i++){
				put(watersystemClassName[i], classNamePrefix[4] + watersystemClassName[i]);
			}
			for(int i=0; i<landformClassName.length; i++){
				put(landformClassName[i], classNamePrefix[5] + landformClassName[i]);
			}
			for(int i=0; i<boundaryClassName.length; i++){
				put(boundaryClassName[i], classNamePrefix[6] + boundaryClassName[i]);
			}
			for(int i=0; i<noteClassName.length; i++){
				put(noteClassName[i], classNamePrefix[7] + noteClassName[i]);
			}
		}
	};
	
	public FeatureTypeFactory(){
	}
	
	public void testfile(){
		String NGIFilePath = "G:\\du_program\\NGI\\sample data\\test2.ngi";
		String NDAFilePath = "G:\\du_program\\NGI\\sample data\\test2.nda";
		
		try {
			FileWriter ngiWriter = new FileWriter(NGIFilePath);
			FileWriter ndaWriter = new FileWriter(NDAFilePath);
			
			String str1 = "<LAYER_START>\n$LAYER_ID\n11\n$END\n$LAYER_NAME\n";
			String str2 = "\n$END\n<HEADER>\n<END>\n<DATA>\n<END>\n<LAYER_END>\n";
			
			String temp;
			for(int i=0;i<trafficLayerName.length; i++){
				temp = str1 + "\"" + trafficLayerName[i] + "\""+ str2;
				ngiWriter.write(temp);
				ndaWriter.write(temp);
			}
			for(int i=0;i<buildingLayerName.length; i++){
				temp = str1 + "\"" + buildingLayerName[i] + "\"" + str2;
				ngiWriter.write(temp);
				ndaWriter.write(temp);
			}
			for(int i=0;i<facilityLayerName.length; i++){
				temp = str1 + "\"" + facilityLayerName[i] + "\"" + str2;
				ngiWriter.write(temp);
				ndaWriter.write(temp);
			}
			
			ngiWriter.close();
			ndaWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	public void hashMapInit(){
		map = new HashMap<String, String>();
		
		for(int i=0; i<trafficLayerName.length; i++){
			map.put(trafficLayerName[i], classNamePrefix[0] + trafficClassName[i]);
		}
		for(int i=0; i<buildingLayerName.length; i++){
			map.put(buildingLayerName[i], classNamePrefix[1] + buildingClassName[i]);
		}
		for(int i=0; i<facilityLayerName.length; i++){
			map.put(facilityLayerName[i], classNamePrefix[2] + facilityClassName[i]);
		}
	}
	*/
	public static FeatureType createFeatureType(String key) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		String className;
		Class featureType;
		
		if(classNameMap.containsKey(key)){
			className = (String)classNameMap.get(key);
			featureType = Class.forName(className);
			
			return (FeatureType) featureType.newInstance();
		}else{
			if(key.charAt(0) == '\"') key = key.substring(1);
			if(key.charAt(key.length() - 1) == '\"') key = key.substring(0, key.length() - 1);
			className = (String)layerNameMap.get(key);
			
			if(className == null) return null;
			featureType = Class.forName(className);
			
			return (FeatureType) featureType.newInstance();
		}
	}
	
	public static ArrayList<String> getAllFeatureTypeSchema() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		ArrayList<String> schemas = new ArrayList<String>();
		
		for(int i=0; i<trafficLayerName.length; i++){
			String[] featureSchema = getFeatureTypeSchema(trafficLayerName[i]);
			
			schemas.add(featureSchema[0]);
			schemas.add(featureSchema[1]);
		}
		
		for(int i=0; i<buildingLayerName.length; i++){
			String[] featureSchema = getFeatureTypeSchema(buildingLayerName[i]);
			
			schemas.add(featureSchema[0]);
			schemas.add(featureSchema[1]);
		}
		
		for(int i=0; i<facilityLayerName.length; i++){
			String[] featureSchema = getFeatureTypeSchema(facilityLayerName[i]);
			
			schemas.add(featureSchema[0]);
			schemas.add(featureSchema[1]);
		}
		
		for(int i=0; i<vegetationLayerName.length; i++){
			String[] featureSchema = getFeatureTypeSchema(vegetationLayerName[i]);
			
			schemas.add(featureSchema[0]);
			schemas.add(featureSchema[1]);
		}
		
		for(int i=0; i<watersystemLayerName.length; i++){
			String[] featureSchema = getFeatureTypeSchema(watersystemLayerName[i]);
			
			schemas.add(featureSchema[0]);
			schemas.add(featureSchema[1]);
		}
		
		for(int i=0; i<landformLayerName.length; i++){
			String[] featureSchema = getFeatureTypeSchema(landformLayerName[i]);
			
			schemas.add(featureSchema[0]);
			schemas.add(featureSchema[1]);
		}
		
		for(int i=0; i<boundaryLayerName.length; i++){
			String[] featureSchema = getFeatureTypeSchema(boundaryLayerName[i]);
			
			schemas.add(featureSchema[0]);
			schemas.add(featureSchema[1]);
		}
		
		for(int i=0; i<noteLayerName.length; i++){
			String[] featureSchema = getFeatureTypeSchema(noteLayerName[i]);
			
			schemas.add(featureSchema[0]);
			schemas.add(featureSchema[1]);
		}
		
		return schemas;
	}
	
	public static String[] getFeatureTypeSchema(String layerName) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		String className = layerNameMap.get(layerName);
		
		Class tempFeatureType = Class.forName(className);
		FeatureType featureType = (FeatureType) tempFeatureType.newInstance();
		
		return featureType.getCreateSchema();
	}
	
	public static String[] getClassNameArray(String group){
		if(group.equals("traffic")) return trafficClassName;
		else if(group.equals("building")) return buildingClassName;
		else if(group.equals("facility")) return facilityClassName;
		else if(group.equals("vegetation")) return vegetationClassName;
		else if(group.equals("watersystem")) return watersystemClassName;
		else if(group.equals("landform")) return landformClassName;
		else if(group.equals("boundary")) return boundaryClassName;
		else if(group.equals("note")) return noteClassName;
		
		return null;
	}
}