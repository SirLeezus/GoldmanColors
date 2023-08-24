package lee.code.colors.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import lee.code.colors.Colors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.concurrent.TimeUnit;

public class HealthListener implements Listener {

    private final Colors colors;

    public HealthListener(Colors colors) {
        this.colors = colors;
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (e.getEntity() instanceof Player player) {
            if (!colors.getScoreboardManager().hasPlayerBoard(player.getUniqueId())) return;
            final int newAbsorption = e.getDamage() > player.getAbsorptionAmount() ? 0 : (int) Math.ceil(player.getAbsorptionAmount() - e.getDamage());
            final int newHealth = (int) Math.min(Math.max(Math.ceil(player.getHealth() - e.getFinalDamage()), 0), 20) + newAbsorption;
            colors.getScoreboardManager().getPlayerBoard(player.getUniqueId()).updateHealthPacket(newHealth);
        }
    }

    @EventHandler
    public void onPostRespawn(PlayerPostRespawnEvent e) {
        if (!colors.getScoreboardManager().hasPlayerBoard(e.getPlayer().getUniqueId())) return;
        colors.getScoreboardManager().getPlayerBoard(e.getPlayer().getUniqueId()).updateHealthPacket(20);
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        if (!colors.getScoreboardManager().hasPlayerBoard(e.getPlayer().getUniqueId())) return;
        colors.getScoreboardManager().getPlayerBoard(e.getPlayer().getUniqueId()).updateHealthPacket();
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.isCancelled()) return;
        Bukkit.getAsyncScheduler().runDelayed(colors, scheduledTask -> {
            if (!colors.getScoreboardManager().hasPlayerBoard(e.getPlayer().getUniqueId())) return;
            colors.getScoreboardManager().getPlayerBoard(e.getPlayer().getUniqueId()).updateHealthPacket();
        }, 500, TimeUnit.MILLISECONDS);
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onRegainHealth(EntityRegainHealthEvent e) {
        if (e.isCancelled()) return;
        if (e.getEntity() instanceof Player player) {
            if (!colors.getScoreboardManager().hasPlayerBoard(player.getUniqueId())) return;
            final int newHealth = (int) Math.min(Math.max(Math.ceil(player.getHealth() + e.getAmount()), 0), 20) + (int) player.getAbsorptionAmount();
            colors.getScoreboardManager().getPlayerBoard(player.getUniqueId()).updateHealthPacket(newHealth);
        }
    }
}
