package edu.pnu.javangi.util.shpexporter;

import org.geotools.data.shapefile.dbf.DbaseFileException;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;

import edu.pnu.javangi.featuretype.AbstractFeatureType;
import edu.pnu.javangi.featuretype.boundary.AdministrationBoundary;
import edu.pnu.javangi.featuretype.boundary.EtcBoundary;
import edu.pnu.javangi.featuretype.boundary.WaterwardBoundary;
import edu.pnu.javangi.featuretype.building.Building;
import edu.pnu.javangi.featuretype.building.Wall;
import edu.pnu.javangi.featuretype.facility.AmusementFacility;
import edu.pnu.javangi.featuretype.facility.Beach;
import edu.pnu.javangi.featuretype.facility.BulletinBoard;
import edu.pnu.javangi.featuretype.facility.Camp;
import edu.pnu.javangi.featuretype.facility.Castle;
import edu.pnu.javangi.featuretype.facility.Cemetery;
import edu.pnu.javangi.featuretype.facility.CemeterySystem;
import edu.pnu.javangi.featuretype.facility.CentralReservation;
import edu.pnu.javangi.featuretype.facility.Chimney;
import edu.pnu.javangi.featuretype.facility.CommunicationPole;
import edu.pnu.javangi.featuretype.facility.Controller;
import edu.pnu.javangi.featuretype.facility.CulturalAssets;
import edu.pnu.javangi.featuretype.facility.Culvert;
import edu.pnu.javangi.featuretype.facility.Dam;
import edu.pnu.javangi.featuretype.facility.Dock;
import edu.pnu.javangi.featuretype.facility.Dock2;
import edu.pnu.javangi.featuretype.facility.Embankment;
import edu.pnu.javangi.featuretype.facility.FishFarm;
import edu.pnu.javangi.featuretype.facility.FishingPlace;
import edu.pnu.javangi.featuretype.facility.Fountain;
import edu.pnu.javangi.featuretype.facility.GasStation;
import edu.pnu.javangi.featuretype.facility.GuardFence;
import edu.pnu.javangi.featuretype.facility.Heliport;
import edu.pnu.javangi.featuretype.facility.HistoricSite;
import edu.pnu.javangi.featuretype.facility.Hydrant;
import edu.pnu.javangi.featuretype.facility.Lighthouse;
import edu.pnu.javangi.featuretype.facility.Lighting;
import edu.pnu.javangi.featuretype.facility.Mailbox;
import edu.pnu.javangi.featuretype.facility.Manhole;
import edu.pnu.javangi.featuretype.facility.Mine;
import edu.pnu.javangi.featuretype.facility.Observatory;
import edu.pnu.javangi.featuretype.facility.ParkingLot;
import edu.pnu.javangi.featuretype.facility.PayPhone;
import edu.pnu.javangi.featuretype.facility.Pier;
import edu.pnu.javangi.featuretype.facility.Pit;
import edu.pnu.javangi.featuretype.facility.PowerPole;
import edu.pnu.javangi.featuretype.facility.Reflector;
import edu.pnu.javangi.featuretype.facility.ServiceStation;
import edu.pnu.javangi.featuretype.facility.Sluice;
import edu.pnu.javangi.featuretype.facility.Spa;
import edu.pnu.javangi.featuretype.facility.Stairway;
import edu.pnu.javangi.featuretype.facility.Statue;
import edu.pnu.javangi.featuretype.facility.Stone;
import edu.pnu.javangi.featuretype.facility.StorageTank;
import edu.pnu.javangi.featuretype.facility.Tank;
import edu.pnu.javangi.featuretype.facility.Tower;
import edu.pnu.javangi.featuretype.facility.TrafficLight;
import edu.pnu.javangi.featuretype.facility.TrafficSign;
import edu.pnu.javangi.featuretype.facility.TubularWell;
import edu.pnu.javangi.featuretype.facility.UndergroundVent;
import edu.pnu.javangi.featuretype.facility.Underpass;
import edu.pnu.javangi.featuretype.facility.UnderpassEntrance;
import edu.pnu.javangi.featuretype.facility.WellWaterSprings;
import edu.pnu.javangi.featuretype.facility.Wharf;
import edu.pnu.javangi.featuretype.facility.Yard;
import edu.pnu.javangi.featuretype.landform.BankingCutting;
import edu.pnu.javangi.featuretype.landform.CaveEntrance;
import edu.pnu.javangi.featuretype.landform.ContourLine;
import edu.pnu.javangi.featuretype.landform.RetainingWall;
import edu.pnu.javangi.featuretype.landform.SpotElevation;
import edu.pnu.javangi.featuretype.note.Lattice;
import edu.pnu.javangi.featuretype.note.Mountain;
import edu.pnu.javangi.featuretype.note.NeatLine;
import edu.pnu.javangi.featuretype.note.PlaceName;
import edu.pnu.javangi.featuretype.note.ReferencePoint;
import edu.pnu.javangi.featuretype.traffic.Bridge;
import edu.pnu.javangi.featuretype.traffic.Crossroad;
import edu.pnu.javangi.featuretype.traffic.Crosswalk;
import edu.pnu.javangi.featuretype.traffic.InterChange;
import edu.pnu.javangi.featuretype.traffic.PedestrianOverpass;
import edu.pnu.javangi.featuretype.traffic.Platform;
import edu.pnu.javangi.featuretype.traffic.PlatformRoof;
import edu.pnu.javangi.featuretype.traffic.Railway;
import edu.pnu.javangi.featuretype.traffic.RailwayBoundary;
import edu.pnu.javangi.featuretype.traffic.RailwayCenterLine;
import edu.pnu.javangi.featuretype.traffic.RailwayStation;
import edu.pnu.javangi.featuretype.traffic.RailwayTurntable;
import edu.pnu.javangi.featuretype.traffic.RiverPort;
import edu.pnu.javangi.featuretype.traffic.RiverPortRoute;
import edu.pnu.javangi.featuretype.traffic.RoadBoundary;
import edu.pnu.javangi.featuretype.traffic.RoadCenterLine;
import edu.pnu.javangi.featuretype.traffic.SafetyZone;
import edu.pnu.javangi.featuretype.traffic.SafetyZoneSpeedBump;
import edu.pnu.javangi.featuretype.traffic.SideWalk;
import edu.pnu.javangi.featuretype.traffic.SolidCrossSection;
import edu.pnu.javangi.featuretype.traffic.Stop;
import edu.pnu.javangi.featuretype.traffic.TollGate;
import edu.pnu.javangi.featuretype.traffic.Tunnel;
import edu.pnu.javangi.featuretype.traffic.TunnelEntrance;
import edu.pnu.javangi.featuretype.vegetation.Farm;
import edu.pnu.javangi.featuretype.vegetation.FarmlandSystem;
import edu.pnu.javangi.featuretype.vegetation.IndependentTree;
import edu.pnu.javangi.featuretype.vegetation.TributarySystem;
import edu.pnu.javangi.featuretype.watersystem.ActualWidthRiver;
import edu.pnu.javangi.featuretype.watersystem.Coastline;
import edu.pnu.javangi.featuretype.watersystem.IrrigationCanal;
import edu.pnu.javangi.featuretype.watersystem.Isobath;
import edu.pnu.javangi.featuretype.watersystem.LakeReservoir;
import edu.pnu.javangi.featuretype.watersystem.RiverBoundary;
import edu.pnu.javangi.featuretype.watersystem.RiverCenterLine;
import edu.pnu.javangi.featuretype.watersystem.WaterDirection;
import edu.pnu.javangi.featuretype.watersystem.Waterfall;

public class DbaseFileHeaderGenerator {
        public static DbaseFileHeader createDefaultDbaseHeader(AbstractFeatureType feature) {
                DbaseFileHeader header = new DbaseFileHeader();
                try {
                        header.addColumn("LAYER_ID", 'N', 9, 0);
                        header.addColumn("LAYER_NAME", 'C', 254, 0);
                        header.addColumn("RECORD_ID", 'N', 9, 0);
                        header.addColumn("UFID", 'C', 254, 0);
                } catch (DbaseFileException e) {
                        e.printStackTrace();
                }

                return header;
        }

        public static DbaseFileHeader createDbaseHeaderWithTextColumn(AbstractFeatureType feature) {
                DbaseFileHeader header = createDbaseHeader(feature);

                try {
                        header.addColumn("TEXT", 'C', 254, 0);
                } catch (DbaseFileException e) {
                        e.printStackTrace();
                }

                return header;
        }

        public static DbaseFileHeader createDbaseHeader(AbstractFeatureType feature) {
                DbaseFileHeader header = createDefaultDbaseHeader(feature);

                try {
                        // 명칭 : NAME 구분 : CLASS 형태 : FORM 종류 : TYPE
                        // 용도 : PURPOSE 주기 : CYCLE 층수 : FLOORS 재질 : MATERIAL 관리기관 : MANAGEMENT
                        // 폭 : WIDTH 높이 : HEIGHT 면적 : AREA 상/하구분 : TOPBOTTOM 연장 : EXTENSION
                        // 상태 : STATE 주생산광물 : MINERAL 수용량 : CAPACITY 주성분 : INGREDIENT 기타 : ETC
                        // 구조 : STRUCTURE 등고수치 : VALUE 산명 : MOUNTAIN 도엽명 : NEAT_Name
                        // 도엽코드 : NEAT_CODE 등급 : GRADE 번호 : NUMBER 좌표 : COORDINATE
                        // 표고 : ELEVATION 종류 : TYPE 설치년도 : INSTALL 하천명: RIVERNAME
                        // 행선지 : DESTINATION 도로번호 : ROADNUMBER 시점 : START 종점 : END
                        // 포장재질 : PACKAGING 분리대유무 : MEDIAN 차로수 : NUMROAD 도로폭 : WIDTH
                        // 일방통행 : ONEWAY 자전거도로유무 : BICYCLE 통과하중 : PASSLOAD
                        // 보도 : SIDEWALK 관리번호 : MANAGENUM 직경(둘레) : DIAMETER 수령 : TREEAGE
                        // 수종 : SPECIES 등심수치 : VALUE
                        if (feature instanceof AdministrationBoundary) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof EtcBoundary) {
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof WaterwardBoundary) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("FORM", 'C', 254, 0);
                        } else if (feature instanceof Building) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("TYPE", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("CYCLE", 'C', 254, 0);
                                header.addColumn("FLOORS", 'N', 9, 0);
                        } else if (feature instanceof Wall) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof AmusementFacility) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof Beach) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof BulletinBoard) {
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof Camp) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof Castle) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof Cemetery) {

                        } else if (feature instanceof CemeterySystem) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof CentralReservation) {
                                header.addColumn("STRUCTURE", 'C', 254, 0);
                        } else if (feature instanceof Chimney) {

                        } else if (feature instanceof CommunicationPole) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof Controller) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof CulturalAssets) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("FORM", 'C', 254, 0);
                        } else if (feature instanceof Culvert) {

                        } else if (feature instanceof Dam) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("WIDTH", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                                header.addColumn("AREA", 'N', 9, 2);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Dock) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof Dock2) {
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Embankment) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                                header.addColumn("TOPBOTTOM", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                        } else if (feature instanceof FishFarm) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof FishingPlace) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof Fountain) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof GasStation) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("TYPE", 'C', 254, 0);
                        } else if (feature instanceof GuardFence) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof Heliport) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof HistoricSite) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof Hydrant) {
                                header.addColumn("FORM", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Lighthouse) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("FORM", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Lighting) {
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof Mailbox) {

                        } else if (feature instanceof Manhole) {
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof Mine) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("STATE", 'C', 254, 0);
                                header.addColumn("INGREDIENT", 'C', 254, 0);
                        } else if (feature instanceof Observatory) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof ParkingLot) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("CAPACITY", 'N', 9, 2);
                        } else if (feature instanceof PayPhone) {

                        } else if (feature instanceof Pier) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof Pit) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof PowerPole) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof Reflector) {

                        } else if (feature instanceof ServiceStation) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof Sluice) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof Spa) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("INGREDIENT", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof Stairway) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("STRUCTURE", 'C', 254, 0); // 구조
                                header.addColumn("WIDTH", 'N', 9, 2);
                        } else if (feature instanceof Statue) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof Stone) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof StorageTank) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("AREA", 'N', 9, 2);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Tank) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Tower) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof TrafficLight) {
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof TrafficSign) {
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof TubularWell) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof UndergroundVent) {
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Underpass) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("WIDTH", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof UnderpassEntrance) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof WellWaterSprings) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("FORM", 'C', 254, 0);
                        } else if (feature instanceof Wharf) {
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Yard) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof BankingCutting) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("TOPBOTTOM", 'C', 254, 0);
                        } else if (feature instanceof CaveEntrance) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("FORM", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                        } else if (feature instanceof ContourLine) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("VALUE", 'N', 9, 2);
                        } else if (feature instanceof RetainingWall) {
                                header.addColumn("TYPE", 'C', 254, 0);
                                header.addColumn("TOPBOTTOM", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                        } else if (feature instanceof SpotElevation) {
                                header.addColumn("VALUE", 'N', 9, 2);
                        } else if (feature instanceof Lattice) {

                        } else if (feature instanceof Mountain) {
                                header.addColumn("MOUNTAIN", 'C', 254, 0);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                        } else if (feature instanceof NeatLine) {
                                header.addColumn("NEAT_Name", 'C', 254, 0);
                                header.addColumn("NEAT_CODE", 'C', 254, 0);
                        } else if (feature instanceof PlaceName) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("FORM", 'C', 254, 0);
                        } else if (feature instanceof ReferencePoint) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("GRADE", 'C', 254, 0);
                                header.addColumn("NUMBER", 'C', 254, 0);
                                header.addColumn("COORDINATE", 'C', 254, 0);
                                header.addColumn("ELEVATION", 'N', 9, 2);
                        } else if (feature instanceof Bridge) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("TYPE", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("WIDTH", 'N', 9, 2);
                                header.addColumn("INSTALL", 'C', 254, 0);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                                header.addColumn("RIVERNAME", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof Crossroad) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("TYPE", 'C', 254, 0);
                        } else if (feature instanceof Crosswalk) {

                        } else if (feature instanceof InterChange) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof PedestrianOverpass) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("WIDTH", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                                header.addColumn("FORM", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof Platform) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof PlatformRoof) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof Railway) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof RailwayBoundary) {

                        } else if (feature instanceof RailwayCenterLine) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("STRUCTURE", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof RailwayStation) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof RailwayTurntable) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof RiverPort) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("DESTINATION", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof RiverPortRoute) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof RoadBoundary) {

                        } else if (feature instanceof RoadCenterLine) {
                                header.addColumn("ROADNUMBER", 'C', 254, 0);
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("도로CLASS", 'C', 254, 0);
                                header.addColumn("START", 'C', 254, 0);
                                header.addColumn("END", 'C', 254, 0);
                                header.addColumn("PACKAGING", 'C', 254, 0);
                                header.addColumn("MEDIAN", 'C', 254, 0);
                                header.addColumn("NUMOFROAD", 'N', 9, 0);
                                header.addColumn("WIDTH", 'N', 9, 0);
                                header.addColumn("ONEWAY", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof SafetyZone) {
                                header.addColumn("STRUCTURE", 'C', 254, 0);
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof SafetyZoneSpeedBump) {

                        } else if (feature instanceof SideWalk) {
                                header.addColumn("WIDTH", 'N', 9, 2);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                                header.addColumn("BICYCLE", 'C', 254, 0);
                                header.addColumn("TYPE", 'C', 254, 0);
                        } else if (feature instanceof SolidCrossSection) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                                header.addColumn("PASSLOAD", 'N', 9, 2);
                                header.addColumn("MATERIAL", 'C', 254, 0);
                                header.addColumn("SIDEWALK", 'C', 254, 0);
                        } else if (feature instanceof Stop) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                        } else if (feature instanceof TollGate) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof Tunnel) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("WIDTH", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                        } else if (feature instanceof TunnelEntrance) {
                                header.addColumn("NAME", 'C', 254, 0);
                        } else if (feature instanceof Farm) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof FarmlandSystem) {
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof IndependentTree) {
                                header.addColumn("MANAGENUM", 'C', 254, 0);
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("DIAMETER", 'N', 9, 2);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                                header.addColumn("TREEAGE", 'N', 9, 2);
                                header.addColumn("SPECIES", 'C', 254, 0);
                                header.addColumn("ETC", 'C', 254, 0);
                        } else if (feature instanceof TributarySystem) {
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof ActualWidthRiver) {

                        } else if (feature instanceof Coastline) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                        } else if (feature instanceof IrrigationCanal) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("FORM", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("EXTENSION", 'N', 9, 2);
                                header.addColumn("WIDTH", 'N', 9, 2);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof Isobath) {
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("VALUE", 'N', 9, 2);
                        } else if (feature instanceof LakeReservoir) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("PURPOSE", 'C', 254, 0);
                                header.addColumn("AREA", 'N', 9, 2);
                                header.addColumn("MANAGEMENT", 'C', 254, 0);
                        } else if (feature instanceof RiverBoundary) {

                        } else if (feature instanceof RiverCenterLine) {
                                header.addColumn("RIVERNUM", 'N', 9, 0);
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("CLASS", 'C', 254, 0);
                                header.addColumn("FORM", 'C', 254, 0);
                                header.addColumn("STATE", 'C', 254, 0);
                        } else if (feature instanceof WaterDirection) {

                        } else if (feature instanceof Waterfall) {
                                header.addColumn("NAME", 'C', 254, 0);
                                header.addColumn("HEIGHT", 'N', 9, 2);
                                header.addColumn("ETC", 'C', 254, 0);
                        }
                } catch (DbaseFileException e) {
                        e.printStackTrace();
                }

                return header;
        }
}
