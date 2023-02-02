package games.stendhal.server.entity.creature;

import java.util.*;

import org.apache.log4j.Logger;

import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.*;

/**
 * A Rat is a domestic animal that can be owned by a player
 * Can be fed with cheese and consume healing potions
 * They attack any creature which is attacking them or the player
 * Have a chance to steal extra loot for the player from a creature the player has slain
 */

public class Rat extends Pet {
	
	/** The Logger Instance */
	private static final Logger logger = Logger.getLogger(Rat.class);
	
	@Override
	void setUp() {
		HP = 100;
		incHP = 3;
		lv_cap = 10;
		ATK = 5;
		DEF = 20;
		XP = 100;
		baseSpeed = 0.9;
		
		setAtk(ATK);
		setDef(DEF);
		setXP(XP);
		setBaseHP(HP);
		setHP(HP);
	}
	
	public static void generateRPClass() {
		try {
			final RPClass rat = new RPClass("pet_rat");
			rat.isA("pet");
		} catch (final SyntaxException e) {
			logger.error("cannot generate RPClass", e);
		}
	}
	
	/**
	 * Creates a new wild rat.
	 */
	public Rat() {
		this(null);
	}
	
	/**
	 * Creates a new rat that may be owned by a player.
	 * @param owner The player who should own the rat
	 */
	public Rat(final Player owner) {
		super();
		setOwner(owner);
		setUp();
		setRPClass("pet_rat");
		put("type", "pet_rat");
		
		if (owner != null) {
			owner.getZone().add(this);
			owner.setPet(this);
		}
		
		update();
	}
	
	/**
	 * Creates a rat based on an existing rat RPObject, and assigns it to a player
	 * 
	 * @param object object containing the rat's data
	 * @param owner The player who should own the rat
	 */
	
	public Rat(final RPObject object, final Player owner) {
		super(object, owner);
		setRPClass("pet_rat");
		put("type", "pet_rat");
		update();
	}
	
	@Override
	protected List<String> getFoodNames() {
		return Arrays.asList("cheese");
	}
	
	/**
	 * Approaches a nearby corpse and obtains a duplicate of a random item that the corpse has
	 * @param c The Corpse to loot
	 * @return True if the rat obtains an item, false if not
	 */
	
	@Override
	public boolean stealFromCreatureCorpse() {
		List<Corpse> c = new ArrayList<Corpse>();
		int width = getZone().getWidth();
		int height = getZone().getHeight();
		for(int i = 0; i <= width;i++) {
			for(int j = 0; j <= height;j++) {
				 List<Corpse> corpsesAtPoint = getZone().getEntitiesAt(i, j, Corpse.class);
				 if (!corpsesAtPoint.isEmpty()) {
					 if (this.getOwner().nextTo(corpsesAtPoint.get(0), 6)) {
						 System.out.println("Corpse near player found");
						 c.add(corpsesAtPoint.get(0));
						 break;
					 } else {
						 System.out.println("Corpse found, but too far away");
						 continue;
					 }
				 }
			}
		}
		if (c.isEmpty()) {
			return false;
		}
		Corpse firstCorpse = c.get(0);
		if (firstCorpse.getKiller().equals(this.getOwner().getName()) || firstCorpse.getKiller().equals(this.getName())) {
			List<Node> positionOfCorpse = new ArrayList<Node>();
			positionOfCorpse.add(new Node(this.getX(), this.getY()));
			positionOfCorpse.add(new Node(firstCorpse.getX(), firstCorpse.getY()));
			
			FixedPath pathToCorpse = new FixedPath(positionOfCorpse, false);
			this.setPath(pathToCorpse);

			RPSlot loot = firstCorpse.getSlot("content");
			if (loot.isEmpty()) {
				System.out.println("Corpse is empty");
				this.getOwner().sendPrivateText("Your rat rummages through the corpse, but it's empty.");
				this.moveToOwner();
				return false;
			}
			else {
				System.out.println("Corpse has items");
				Item obtainedLoot = (Item) loot.getFirst();
				this.getOwner().sendPrivateText("Your rat rummages through the corpse and retrieves an item!");
				this.moveToOwner();
				this.getOwner().equip("bag", obtainedLoot);
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean canSteal() {
		return true;
	}
	
}
