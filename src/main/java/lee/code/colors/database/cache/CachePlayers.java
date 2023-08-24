package lee.code.colors.database.cache;

import lee.code.colors.database.DatabaseManager;
import lee.code.colors.database.handlers.DatabaseHandler;
import lee.code.colors.database.tables.PlayerTable;
import org.bukkit.ChatColor;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CachePlayers extends DatabaseHandler {

    private final ConcurrentHashMap<UUID, PlayerTable> playersCache = new ConcurrentHashMap<>();

    public CachePlayers(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    public PlayerTable getPlayerTable(UUID uuid) {
        return playersCache.get(uuid);
    }

    public void setPlayerTable(PlayerTable playerTable) {
        playersCache.put(playerTable.getUniqueId(), playerTable);
    }

    public boolean hasPlayerData(UUID uuid) {
        return playersCache.containsKey(uuid);
    }

    public void createPlayerData(UUID uuid) {
        final PlayerTable playerTable = new PlayerTable(uuid);
        setPlayerTable(playerTable);
        createPlayerDatabase(playerTable);
    }

    public ChatColor getColor(UUID uuid) {
        return ChatColor.valueOf(getPlayerTable(uuid).getColor());
    }

    public void setColor(UUID uuid, ChatColor color) {
        final PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setColor(color.name());
        updatePlayerDatabase(playerTable);
    }

    public String getPrefix(UUID uuid) {
        return getPlayerTable(uuid).getPrefix();
    }

    public void setPrefix(UUID uuid, String prefix) {
        final PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setPrefix(prefix);
        updatePlayerDatabase(playerTable);
    }

    public String getSuffix(UUID uuid) {
        return getPlayerTable(uuid).getSuffix();
    }

    public void setSuffix(UUID uuid, String suffix) {
        final PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setSuffix(suffix);
        updatePlayerDatabase(playerTable);
    }

    public String getPriority(UUID uuid) {
        return getPlayerTable(uuid).getPriority();
    }

    public void setPriority(UUID uuid, String priority) {
        final PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setPriority(priority);
        updatePlayerDatabase(playerTable);
    }
}
