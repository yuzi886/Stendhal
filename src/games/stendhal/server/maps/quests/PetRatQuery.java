package games.stendhal.server.maps.quests;
import games.stendhal.server.entity.npc.*;
import games.stendhal.server.entity.npc.action.*;
import games.stendhal.server.entity.player.*;
import java.util.*;

public class PetRatQuery extends AbstractQuest{
	public static final String QUEST_SLOT = "Pet_Rat";

    @Override
    public void addToWorld() {
    	fillQuestInfo(
				"Pet Rat Quest",
				"Byron Mcgalister give secret pet by riddle.",
				false);
    	prepareQuestStep();
    }

    @Override
    public String getSlotName() {
        return QUEST_SLOT;
    }

    @Override
    public String getName() {
        return "PetRatQuery";
    }

    public List<String> getHistory(final Player player) {
        final List<String> res = new ArrayList<String>();
        return res;
    }
    
    public void prepareQuestStep() {
        SpeakerNPC npc = npcs.get("Byron Mcgalister");
        List<ChatAction> reward = new LinkedList<ChatAction>();
        reward.add(new PetOwnerAction());
        reward.add(new SetQuestAction(QUEST_SLOT, "done"));
        String reply = "oh, now l catch some useless animals in my house. If you want it, l can give it to you";
        npc.add(ConversationStates.ATTENDING, "stealing pet",null,ConversationStates.ATTENDING,reply,new MultipleActions(reward));
        
    }

}
