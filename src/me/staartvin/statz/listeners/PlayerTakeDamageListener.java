package me.staartvin.statz.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.staartvin.statz.Statz;
import me.staartvin.statz.datamanager.PlayerStat;
import me.staartvin.statz.datamanager.player.PlayerInfo;
import me.staartvin.statz.util.StatzUtil;

public class PlayerTakeDamageListener implements Listener {

	private final Statz plugin;

	public PlayerTakeDamageListener(final Statz plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDamage(final EntityDamageEvent event) {

		final PlayerStat stat = PlayerStat.DAMAGE_TAKEN;

		if (!(event.getEntity() instanceof Player)) {
			// It was not a player that got damage
			return;
		}
		
		// Get player
		final Player player = (Player) event.getEntity();

		// Get player info.
		final PlayerInfo info = plugin.getDataManager().getPlayerInfo(player.getUniqueId(), stat);

		// Get current value of stat.
		int currentValue = 0;
		
		// Check if it is valid!
		if (info.isValid()) {
			for (HashMap<String, String> map : info.getResults()) {
				if (map.get("world") != null
						&& map.get("world").toString().equalsIgnoreCase(player.getWorld().getName())
						&& map.get("cause") != null && map.get("cause").toString().equalsIgnoreCase(event.getCause().toString())) {
					currentValue += Double.parseDouble(map.get("value").toString());
				}
			}
		}

		// Update value to new stat.
		plugin.getDataManager().setPlayerInfo(player.getUniqueId(), stat,
				StatzUtil.makeQuery("uuid", player.getUniqueId().toString(), "value", (currentValue + event.getDamage()), "cause",
						event.getCause().toString(), "world", player.getWorld().getName()));

	}
}