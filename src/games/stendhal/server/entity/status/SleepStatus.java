package games.stendhal.server.entity.status;

public class SleepStatus extends ConsumableStatus {

	/**
	 * eat
	 *
	 * @param amount     total amount
	 * @param frequency  frequency of events
	 * @param regen      hp change on each event
	 */
	public SleepStatus(int amount, int frequency, int regen) {
		super("sleeping", amount, frequency, regen);
	}


	/**
	 * returns the status type
	 *
	 * @return StatusType
	 */
	@Override
	public StatusType getStatusType() {
		return StatusType.SLEEPING;
	}
}
