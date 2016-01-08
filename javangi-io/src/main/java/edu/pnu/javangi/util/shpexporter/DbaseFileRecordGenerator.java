package edu.pnu.javangi.util.shpexporter;

import java.util.ArrayList;

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

public class DbaseFileRecordGenerator {
        public static ArrayList<Object> createDefaultDBFContents(
                        AbstractFeatureType feature, int idx) {
                ArrayList<Object> contents = new ArrayList<Object>();

                /*
                 * default content
                 */
                contents.add(feature.getLayerID());
                contents.add(feature.getLayerName());
                contents.add(feature.getRecordID().get(idx));
                contents.add(feature.getUFID().get(idx));

                return contents;
        }

        public static Object createDBFContents(AbstractFeatureType feature,
                        int idx) {
                ArrayList<Object> contents = createDefaultDBFContents(feature,
                                idx);

                if (feature instanceof AdministrationBoundary) {
                        AdministrationBoundary f = (AdministrationBoundary) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof EtcBoundary) {
                        EtcBoundary f = (EtcBoundary) feature;
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof WaterwardBoundary) {
                        WaterwardBoundary f = (WaterwardBoundary) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getFormN(idx));
                } else if (feature instanceof Building) {
                        Building f = (Building) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getTypeN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getCycleN(idx));
                        contents.add(f.getFloorsN(idx));
                } else if (feature instanceof Wall) {
                        Wall f = (Wall) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof AmusementFacility) {
                        AmusementFacility f = (AmusementFacility) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof Beach) {
                        Beach f = (Beach) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof BulletinBoard) {
                        BulletinBoard f = (BulletinBoard) feature;
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof Camp) {
                        Camp f = (Camp) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof Castle) {
                        Castle f = (Castle) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof Cemetery) {

                } else if (feature instanceof CemeterySystem) {
                        CemeterySystem f = (CemeterySystem) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof CentralReservation) {
                        CentralReservation f = (CentralReservation) feature;
                        contents.add(f.getStructureN(idx));
                } else if (feature instanceof Chimney) {

                } else if (feature instanceof CommunicationPole) {
                        CommunicationPole f = (CommunicationPole) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof Controller) {
                        Controller f = (Controller) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof CulturalAssets) {
                        CulturalAssets f = (CulturalAssets) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getFormN(idx));
                } else if (feature instanceof Culvert) {

                } else if (feature instanceof Dam) {
                        Dam f = (Dam) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getWidthN(idx));
                        contents.add(f.getHeightN(idx));
                        contents.add(f.getAreaN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Dock) {
                        Dock f = (Dock) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof Dock2) {
                        Dock2 f = (Dock2) feature;
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getMaterialN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Embankment) {
                        Embankment f = (Embankment) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getMaterialN(idx));
                        contents.add(f.getTopBottomN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getHeightN(idx));
                } else if (feature instanceof FishFarm) {
                        FishFarm f = (FishFarm) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof FishingPlace) {
                        FishingPlace f = (FishingPlace) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof Fountain) {
                        Fountain f = (Fountain) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof GasStation) {
                        GasStation f = (GasStation) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getTypeN(idx));
                } else if (feature instanceof GuardFence) {
                        GuardFence f = (GuardFence) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof Heliport) {
                        Heliport f = (Heliport) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof HistoricSite) {
                        HistoricSite f = (HistoricSite) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof Hydrant) {
                        Hydrant f = (Hydrant) feature;
                        contents.add(f.getFormN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Lighthouse) {
                        Lighthouse f = (Lighthouse) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getFormN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Lighting) {
                        Lighting f = (Lighting) feature;
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof Mailbox) {

                } else if (feature instanceof Manhole) {
                        Manhole f = (Manhole) feature;
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof Mine) {
                        Mine f = (Mine) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getStateN(idx));
                        contents.add(f.getMineralN(idx));
                } else if (feature instanceof Observatory) {
                        Observatory f = (Observatory) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof ParkingLot) {
                        ParkingLot f = (ParkingLot) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getCapacityN(idx));
                } else if (feature instanceof PayPhone) {

                } else if (feature instanceof Pier) {
                        Pier f = (Pier) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof Pit) {
                        Pit f = (Pit) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof PowerPole) {
                        PowerPole f = (PowerPole) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof Reflector) {

                } else if (feature instanceof ServiceStation) {
                        ServiceStation f = (ServiceStation) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof Sluice) {
                        Sluice f = (Sluice) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof Spa) {
                        Spa f = (Spa) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getIngredientN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof Stairway) {
                        Stairway f = (Stairway) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getWidthN(idx));
                } else if (feature instanceof Statue) {
                        Statue f = (Statue) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof Stone) {
                        Stone f = (Stone) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getHeightN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof StorageTank) {
                        StorageTank f = (StorageTank) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getAreaN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Tank) {
                        Tank f = (Tank) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Tower) {
                        Tower f = (Tower) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getMaterialN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof TrafficLight) {
                        TrafficLight f = (TrafficLight) feature;
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof TrafficSign) {
                        TrafficSign f = (TrafficSign) feature;
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof TubularWell) {
                        TubularWell f = (TubularWell) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof UndergroundVent) {
                        UndergroundVent f = (UndergroundVent) feature;
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Underpass) {
                        Underpass f = (Underpass) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getWidthN(idx));
                        contents.add(f.getHeightN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof UnderpassEntrance) {
                        UnderpassEntrance f = (UnderpassEntrance) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof WellWaterSprings) {
                        WellWaterSprings f = (WellWaterSprings) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getFormN(idx));
                } else if (feature instanceof Wharf) {
                        Wharf f = (Wharf) feature;
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getMaterialN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Yard) {
                        Yard f = (Yard) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof BankingCutting) {
                        BankingCutting f = (BankingCutting) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getTopBottomN(idx));
                } else if (feature instanceof CaveEntrance) {
                        CaveEntrance f = (CaveEntrance) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getFormN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getHeightN(idx));
                } else if (feature instanceof ContourLine) {
                        ContourLine f = (ContourLine) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getValueN(idx));
                } else if (feature instanceof RetainingWall) {
                        RetainingWall f = (RetainingWall) feature;
                        contents.add(f.getTypeN(idx));
                        contents.add(f.getTopBottomN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getHeightN(idx));
                        contents.add(f.getMaterialN(idx));
                } else if (feature instanceof SpotElevation) {
                        SpotElevation f = (SpotElevation) feature;
                        contents.add(f.getValueN(idx));
                } else if (feature instanceof Lattice) {

                } else if (feature instanceof Mountain) {
                        Mountain f = (Mountain) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getHeightN(idx));
                } else if (feature instanceof NeatLine) {
                        NeatLine f = (NeatLine) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getCodeN(idx));
                } else if (feature instanceof PlaceName) {
                        PlaceName f = (PlaceName) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getFormN(idx));
                } else if (feature instanceof ReferencePoint) {
                        ReferencePoint f = (ReferencePoint) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getGradeN(idx));
                        contents.add(f.getNumberN(idx));
                        contents.add(f.getCoordinateN(idx));
                        contents.add(f.getElevationN(idx));
                } else if (feature instanceof Bridge) {
                        Bridge f = (Bridge) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getTypeN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getWidthN(idx));
                        contents.add(f.getInstallationYearN(idx));
                        contents.add(f.getMaterialN(idx));
                        contents.add(f.getRiverNameN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof Crossroad) {
                        Crossroad f = (Crossroad) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getTypeN(idx));
                } else if (feature instanceof Crosswalk) {

                } else if (feature instanceof InterChange) {
                        InterChange f = (InterChange) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof PedestrianOverpass) {
                        PedestrianOverpass f = (PedestrianOverpass) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getWidthN(idx));
                        contents.add(f.getHeightN(idx));
                        contents.add(f.getFormN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof Platform) {
                        Platform f = (Platform) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof PlatformRoof) {
                        PlatformRoof f = (PlatformRoof) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof Railway) {
                        Railway f = (Railway) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof RailwayBoundary) {

                } else if (feature instanceof RailwayCenterLine) {
                        RailwayCenterLine f = (RailwayCenterLine) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getStructureN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof RailwayStation) {
                        RailwayStation f = (RailwayStation) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof RailwayTurntable) {
                        RailwayTurntable f = (RailwayTurntable) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof RiverPort) {
                        RiverPort f = (RiverPort) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getDestinationN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof RiverPortRoute) {
                        RiverPortRoute f = (RiverPortRoute) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof RoadBoundary) {

                } else if (feature instanceof RoadCenterLine) {
                        RoadCenterLine f = (RoadCenterLine) feature;
                        contents.add(f.getNumberN(idx));
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getStartPointN(idx));
                        contents.add(f.getEndPointN(idx));
                        contents.add(f.getPackagingN(idx));
                        contents.add(f.getCentralReservation().get(idx));
                        contents.add(f.getNumberOfRoad().get(idx));
                        contents.add(f.getWidth().get(idx));
                        contents.add(f.getOneSidePassing().get(idx));
                        contents.add(f.getEtc().get(idx));
                } else if (feature instanceof SafetyZone) {
                        SafetyZone f = (SafetyZone) feature;
                        contents.add(f.getStructureN(idx));
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof SafetyZoneSpeedBump) {

                } else if (feature instanceof SideWalk) {
                        SideWalk f = (SideWalk) feature;
                        contents.add(f.getWidthN(idx));
                        contents.add(f.getMaterialN(idx));
                        contents.add(f.getBicycleRoadN(idx));
                        contents.add(f.getTypeN(idx));
                } else if (feature instanceof SolidCrossSection) {
                        SolidCrossSection f = (SolidCrossSection) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getHeightN(idx));
                        contents.add(f.getPassingLoadN(idx));
                        contents.add(f.getMaterialN(idx));
                        contents.add(f.getSideWalkN(idx));
                } else if (feature instanceof Stop) {
                        Stop f = (Stop) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                } else if (feature instanceof TollGate) {
                        TollGate f = (TollGate) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof Tunnel) {
                        Tunnel f = (Tunnel) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getWidthN(idx));
                        contents.add(f.getHeightN(idx));
                } else if (feature instanceof TunnelEntrance) {
                        TunnelEntrance f = (TunnelEntrance) feature;
                        contents.add(f.getNameN(idx));
                } else if (feature instanceof Farm) {
                        Farm f = (Farm) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof FarmlandSystem) {
                        FarmlandSystem f = (FarmlandSystem) feature;
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof IndependentTree) {
                        IndependentTree f = (IndependentTree) feature;
                        contents.add(f.getManagementNumberN(idx));
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getDiameterN(idx));
                        contents.add(f.getHeightN(idx));
                        contents.add(f.getAgeN(idx));
                        contents.add(f.getSpeciesN(idx));
                        contents.add(f.getEtcN(idx));
                } else if (feature instanceof TributarySystem) {
                        TributarySystem f = (TributarySystem) feature;
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof ActualWidthRiver) {

                } else if (feature instanceof Coastline) {
                        Coastline f = (Coastline) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                } else if (feature instanceof IrrigationCanal) {
                        IrrigationCanal f = (IrrigationCanal) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getFormN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getExtensionN(idx));
                        contents.add(f.getWidthN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof Isobath) {
                        Isobath f = (Isobath) feature;
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getValueN(idx));
                } else if (feature instanceof LakeReservoir) {
                        LakeReservoir f = (LakeReservoir) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getPurposeN(idx));
                        contents.add(f.getAreaN(idx));
                        contents.add(f.getManagementAgencyN(idx));
                } else if (feature instanceof RiverBoundary) {

                } else if (feature instanceof RiverCenterLine) {
                        RiverCenterLine f = (RiverCenterLine) feature;
                        contents.add(f.getRiverNumberN(idx));
                        contents.add(f.getNameN(idx));
                        contents.add(f.getClassificationN(idx));
                        contents.add(f.getFormN(idx));
                        contents.add(f.getStateN(idx));
                } else if (feature instanceof WaterDirection) {

                } else if (feature instanceof Waterfall) {
                        Waterfall f = (Waterfall) feature;
                        contents.add(f.getNameN(idx));
                        contents.add(f.getHeightN(idx));
                        contents.add(f.getEtcN(idx));
                }

                if (!feature.getPoint_text().get(idx).equalsIgnoreCase("EMPTY")) {
                        contents.add(feature.getPoint_text().get(idx));
                }

                return contents.toArray();
        }

}
