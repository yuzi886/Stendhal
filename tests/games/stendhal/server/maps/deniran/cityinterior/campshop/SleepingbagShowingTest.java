package games.stendhal.server.maps.deniran.cityinterior.campshop;
import games.stendhal.server.core.engine.StendhalRPZone;

import static org.junit.Assert.*;

import org.junit.*;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;
import games.stendhal.server.entity.npc.SpeakerNPC;
//import games.stendhal.server.maps.deniran.cityinterior.campshop.CampshopNPC;
//import games.stendhal.server.maps.deniran.cityinterior.dressshop.CampshopNPC;
import utilities.ZonePlayerAndNPCTestImpl;
import utilities.QuestHelper;


public class SleepingbagShowingTest extends ZonePlayerAndNPCTestImpl{
 
 private CampShopNPC configurator;
 private static final String ZONE_NAME = "deniran_camp_shop";
 private static final String npc = "Pierre";
 private static SpeakerNPC Pierre;
 private ShopSign sell;
 private ShopSign buy;
 public final StendhalRPZone ZONE = new StendhalRPZone(ZONE_NAME);
 
 @BeforeClass
 public static void setUpBeforeClass() throws Exception{
  QuestHelper.setUpBeforeClass();
  setupZone(ZONE_NAME);
 }    
 
 public void setUp() throws Exception{
  setZoneForPlayer(ZONE_NAME);
  setNpcNames(npc);
  configurator = CampShopNPC.getCampshopNPC();
  addZoneConfigurator(configurator, ZONE_NAME);
  super.setUp();
 }

 @Test
 public void exitence_of_sleeping_bag() {
  sell = configurator.outfitCatalog(configurator.createOutfitList());

  String sleepingbag = "";
  try {
   sleepingbag = configurator.createOutfitList().get(3).getLabel();
  }catch(IndexOutOfBoundsException e) {
   sleepingbag = "the item doesn't exit";
  }finally {
   assertEquals("sleepingbag", sleepingbag);
  };
 }

}
