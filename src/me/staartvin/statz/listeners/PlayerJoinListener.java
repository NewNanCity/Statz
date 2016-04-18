package me.staartvin.statz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.staartvin.statz.Statz;
import me.staartvin.statz.datamanager.PlayerStat;
import me.staartvin.statz.datamanager.player.PlayerInfo;
import me.staartvin.statz.util.StatzUtil;

public class PlayerJoinListener implements Listener {

	private final Statz plugin;

	public PlayerJoinListener(final Statz plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {

		final PlayerStat stat = PlayerStat.JOINS;

		// Get player
		final Player player = event.getPlayer();

		// Update name in database.
		plugin.getSqlConnector().setObjects(plugin.getSqlConnector().getSQLiteTable("players"),
				StatzUtil.makeQuery("uuid", player.getUniqueId().toString(), "playerName", player.getName()));

		// Get player info.
		final PlayerInfo info = plugin.getDataManager().getPlayerInfo(player.getUniqueId(), stat);

		// Get current value of stat.
		int currentValue = 0;

		// Check if it is valid!
		if (info.isValid()) {
			currentValue = Integer.parseInt(info.getResults().get(0).get("value").toString());
		}

		// Update value to new stat.
		plugin.getDataManager().setPlayerInfo(player.getUniqueId(), stat,
				StatzUtil.makeQuery("uuid", player.getUniqueId().toString(), "value", (currentValue + 1)));
	}
}