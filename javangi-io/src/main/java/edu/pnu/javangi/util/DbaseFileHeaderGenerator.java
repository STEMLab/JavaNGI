package edu.pnu.javangi.util;

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
        public static DbaseFileHeader createDefaultDbaseHeader(
                        AbstractFeatureType feature) {
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

        public static DbaseFileHeader createDbaseHeaderWithTextColumn(
                        AbstractFeatureType feature) {
                DbaseFileHeader header = createDbaseHeader(feature);

                try {
                        header.addColumn("TEXT", 'C', 254, 0);
                } catch (DbaseFileException e) {
                        e.printStackTrace();
                }

                return header;
        }

        public static DbaseFileHeader createDbaseHeader(
                        AbstractFeatureType feature) {
                DbaseFileHeader header = createDefaultDbaseHeader(feature);

                try {
                        if (feature instanceof AdministrationBoundary) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof EtcBoundary) {
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof WaterwardBoundary) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                        } else if (feature instanceof Building) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("종류", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("주기", 'C', 254, 0);
                                header.addColumn("층수", 'N', 9, 0);
                        } else if (feature instanceof Wall) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof AmusementFacility) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof Beach) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof BulletinBoard) {
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof Camp) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof Castle) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof Cemetery) {

                        } else if (feature instanceof CemeterySystem) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof CentralReservation) {
                                header.addColumn("구조", 'C', 254, 0);
                        } else if (feature instanceof Chimney) {

                        } else if (feature instanceof CommunicationPole) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof Controller) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof CulturalAssets) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                        } else if (feature instanceof Culvert) {

                        } else if (feature instanceof Dam) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("폭", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                                header.addColumn("면적", 'N', 9, 2);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Dock) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof Dock2) {
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Embankment) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                                header.addColumn("상/하구분", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                        } else if (feature instanceof FishFarm) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof FishingPlace) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof Fountain) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof GasStation) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("종류", 'C', 254, 0);
                        } else if (feature instanceof GuardFence) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof Heliport) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof HistoricSite) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof Hydrant) {
                                header.addColumn("형태", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Lighthouse) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Lighting) {
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof Mailbox) {

                        } else if (feature instanceof Manhole) {
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof Mine) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("상태", 'C', 254, 0);
                                header.addColumn("주 생산광물", 'C', 254, 0);
                        } else if (feature instanceof Observatory) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof ParkingLot) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("수용량", 'N', 9, 2);
                        } else if (feature instanceof PayPhone) {

                        } else if (feature instanceof Pier) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof Pit) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof PowerPole) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof Reflector) {

                        } else if (feature instanceof ServiceStation) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof Sluice) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof Spa) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("주성분", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof Stairway) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구조", 'C', 254, 0);
                                header.addColumn("폭", 'N', 9, 2);
                        } else if (feature instanceof Statue) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof Stone) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("높이", 'N', 9, 2);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof StorageTank) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("면적", 'N', 9, 2);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Tank) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Tower) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof TrafficLight) {
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof TrafficSign) {
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof TubularWell) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof UndergroundVent) {
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Underpass) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("폭", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof UnderpassEntrance) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof WellWaterSprings) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                        } else if (feature instanceof Wharf) {
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Yard) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof BankingCutting) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("상/하구분", 'C', 254, 0);
                        } else if (feature instanceof CaveEntrance) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                        } else if (feature instanceof ContourLine) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("등고수치", 'N', 9, 2);
                        } else if (feature instanceof RetainingWall) {
                                header.addColumn("종류", 'C', 254, 0);
                                header.addColumn("상하구분", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                                header.addColumn("재질", 'C', 254, 0);
                        } else if (feature instanceof SpotElevation) {
                                header.addColumn("수치", 'N', 9, 2);
                        } else if (feature instanceof Lattice) {

                        } else if (feature instanceof Mountain) {
                                header.addColumn("산명", 'C', 254, 0);
                                header.addColumn("높이", 'N', 9, 2);
                        } else if (feature instanceof NeatLine) {
                                header.addColumn("도엽명", 'C', 254, 0);
                                header.addColumn("도엽코드", 'C', 254, 0);
                        } else if (feature instanceof PlaceName) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                        } else if (feature instanceof ReferencePoint) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("등급", 'C', 254, 0);
                                header.addColumn("번호", 'N', 9, 0);
                                header.addColumn("좌표", 'C', 254, 0);
                                header.addColumn("표고", 'N', 9, 2);
                        } else if (feature instanceof Bridge) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("종류", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("폭", 'N', 9, 2);
                                header.addColumn("설치년도", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                                header.addColumn("하천명", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof Crossroad) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("종류", 'C', 254, 0);
                        } else if (feature instanceof Crosswalk) {

                        } else if (feature instanceof InterChange) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof PedestrianOverpass) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("폭", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                                header.addColumn("형태", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof Platform) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof PlatformRoof) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof Railway) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof RailwayBoundary) {

                        } else if (feature instanceof RailwayCenterLine) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("구조", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof RailwayStation) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof RailwayTurntable) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("관리기관", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof RiverPort) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("행선지", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof RiverPortRoute) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof RoadBoundary) {

                        } else if (feature instanceof RoadCenterLine) {
                                header.addColumn("도로번호", 'C', 254, 0);
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("도로구분", 'C', 254, 0);
                                header.addColumn("시점", 'C', 254, 0);
                                header.addColumn("종점", 'C', 254, 0);
                                header.addColumn("포장재질", 'C', 254, 0);
                                header.addColumn("분리대유무", 'C', 254, 0);
                                header.addColumn("차로수", 'N', 9, 0);
                                header.addColumn("도로폭", 'N', 9, 0);
                                header.addColumn("일방통행", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof SafetyZone) {
                                header.addColumn("구조", 'C', 254, 0);
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof SafetyZoneSpeedBump) {

                        } else if (feature instanceof SideWalk) {
                                header.addColumn("폭", 'N', 9, 2);
                                header.addColumn("재질", 'C', 254, 0);
                                header.addColumn("자전거도로유무", 'C', 254, 0);
                                header.addColumn("종류", 'C', 254, 0);
                        } else if (feature instanceof SolidCrossSection) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                                header.addColumn("통과하중", 'N', 9, 2);
                                header.addColumn("재질", 'C', 254, 0);
                                header.addColumn("보도", 'C', 254, 0);
                        } else if (feature instanceof Stop) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                        } else if (feature instanceof TollGate) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof Tunnel) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("폭", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                        } else if (feature instanceof TunnelEntrance) {
                                header.addColumn("명칭", 'C', 254, 0);
                        } else if (feature instanceof Farm) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof FarmlandSystem) {
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof IndependentTree) {
                                header.addColumn("관리번호", 'C', 254, 0);
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("직경(둘레)", 'N', 9, 2);
                                header.addColumn("높이", 'N', 9, 2);
                                header.addColumn("수령", 'N', 9, 2);
                                header.addColumn("수종", 'C', 254, 0);
                                header.addColumn("기타", 'C', 254, 0);
                        } else if (feature instanceof TributarySystem) {
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof ActualWidthRiver) {

                        } else if (feature instanceof Coastline) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                        } else if (feature instanceof IrrigationCanal) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("연장", 'N', 9, 2);
                                header.addColumn("폭", 'N', 9, 2);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof Isobath) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("등심수치", 'N', 9, 2);
                        } else if (feature instanceof LakeReservoir) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("면적", 'N', 9, 2);
                                header.addColumn("관리기관", 'C', 254, 0);
                        } else if (feature instanceof RiverBoundary) {

                        } else if (feature instanceof RiverCenterLine) {
                                header.addColumn("하천번호", 'N', 9, 0);
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                                header.addColumn("상태", 'C', 254, 0);
                        } else if (feature instanceof WaterDirection) {

                        } else if (feature instanceof Waterfall) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("높이", 'N', 9, 2);
                                header.addColumn("기타", 'C', 254, 0);
                        }
                } catch (DbaseFileException e) {
                        e.printStackTrace();
                }

                return header;
        }
}
