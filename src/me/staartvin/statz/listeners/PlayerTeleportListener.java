package me.staartvin.statz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.staartvin.statz.Statz;
import me.staartvin.statz.datamanager.PlayerStat;
import me.staartvin.statz.util.StatzUtil;

public class PlayerTeleportListener implements Listener {

	private final Statz plugin;

	public PlayerTeleportListener(final Statz plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTeleport(final PlayerTeleportEvent event) {

		final PlayerStat stat = PlayerStat.TELEPORTS;

		// Get player
		final Player player = event.getPlayer();

		// Do general check
		if (!plugin.doGeneralCheck(player))
			return;

		//		// Get player info.
		//		final PlayerInfo info = plugin.getDataManager().getPlayerInfo(player.getUniqueId(), stat,
		//				StatzUtil.makeQuery("caught", material, "world", player.getWorld().getName()));
		//
		//		// Get current value of stat.
		//		int currentValue = 0;
		//
		//		// Check if it is valid!
		//		if (info.isValid()) {
		//			currentValue += info.getTotalValue();
		//		}

		// Update value to new stat.
		plugin.getDataManager().setPlayerInfo(player.getUniqueId(), stat,
				StatzUtil.makeQuery("uuid", player.getUniqueId().toString(), "value", 1, "world",
						event.getFrom().getWorld().getName(), "destWorld", event.getTo().getWorld().getName(), "cause", event.getCause().toString()));

	}
}
