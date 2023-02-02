package games.stendhal.server.maps.deniran.cityinterior.furnitureshop;

import static org.junit.Assert.*;

import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;


public class FurnitureSellerNPCTest {

	@Test
	public void test() {
		 StendhalRPZone zone = new StendhalRPZone("John");
		  final StendhalRPWorld world = StendhalRPWorld.get();
		  world.addRPZone(zone);
		  FurnitureSellerNPC npc = new FurnitureSellerNPC();
		  npc.configureZone(zone,null);
		 
		  ShopSign sign1 = (ShopSign) zone.getEntityAt(5, 6);
		  ShopSign sign2 = (ShopSign) zone.getEntityAt(10, 6);
		  
		  assertFalse(sign1 == null);
		  assertFalse(sign2 == null);
		  
		  String message1 = sign1.getShopName();
		  String message2 = sign2.getShopName();

		  assertEquals("deniranfurniturebuy",message1);
		  assertEquals("deniranfurnituresell",message2);
		 

	}

}