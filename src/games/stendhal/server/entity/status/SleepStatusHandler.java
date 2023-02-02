package games.stendhal.server.entity.status;

//import games.stendhal.common.NotificationType;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.Entity;
//import games.stendhal.server.entity.RPEntity;

public class SleepStatusHandler implements StatusHandler<SleepStatus> {
	/**
	 * @param status
	 * 		Status to inflict
	 * @param statusList
	 * 		StatusList
	 * @param attacker
	 * 		the attacker
	 */
	@Override
	public void inflict(SleepStatus status, StatusList statusList, Entity attacker) {
		if (!statusList.hasStatus(status.getStatusType())) {//so that sleep can't be called twice at the same time
//			RPEntity entity = statusList.getEntity();
//			if (entity == null) {
//				return;
//			}
			statusList.addInternal(status);
			statusList.activateStatusAttribute("sleeping");
		//--------------
		//Turn Notifier to be implemented
		//--------------
		// activate the turnListener, if this is the first instance of this status
		// note: the turnListener is called one last time after the last instance was consumed to cleanup attributes.
		// So even with count==1, there might still be a listener which needs to be removed
			TurnListener turnListener = new SleepStatusTurnListener(statusList);
			TurnNotifier.get().dontNotify(turnListener);
			TurnNotifier.get().notifyInTurns(0, turnListener);
		}
	}


	/**
	 * removes a status
	 *
	 * @param status
	 * 		inflicted status
	 * @param statusList
	 * 		StatusList
	 */
	@Override
	public void remove(SleepStatus status, StatusList statusList) {
		statusList.removeInternal(status);
	}
}