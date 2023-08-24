package lee.code.colors.scoreboard.system;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lee.code.colors.Colors;
import lee.code.colors.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BoardBuilder {

    private final Colors colors;
    private final Player player;
    private final UUID uniqueId;
    private final String name;
    private String prefix;
    private String suffix;
    private ChatColor nameColor;
    private CollisionRule collisionRule;
    private String priority;

    public BoardBuilder(Colors colors, Player player) {
        this.colors = colors;
        this.player = player;
        this.uniqueId = player.getUniqueId();
        this.name = player.getName();
    }

    public BoardBuilder prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public BoardBuilder suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public BoardBuilder nameColor(ChatColor nameColor) {
        this.nameColor = nameColor;
        return this;
    }

    public BoardBuilder collisionRule(CollisionRule collisionRule) {
        this.collisionRule = collisionRule;
        return this;
    }

    public BoardBuilder priority(String priority) {
        this.priority = priority;
        return this;
    }

    private BoardData createBoard() {
        final BoardData boardData = new BoardData(uniqueId);
        boardData.setTeamName(priority + player.getName());
        boardData.setCollisionRule(collisionRule);
        if (nameColor != null) boardData.setColor(nameColor);
        if (prefix != null) boardData.setPrefix(WrappedChatComponent.fromJson(CoreUtil.serializeColorComponentJson(prefix)));
        if (suffix != null) boardData.setSuffix(WrappedChatComponent.fromJson(CoreUtil.serializeColorComponentJson(suffix)));
        boardData.setPlayers(Collections.singletonList(name));
        return boardData;
    }

    private void updatePlayerData() {
        final StringBuilder nameBuilder = new StringBuilder();
        if (prefix != null) nameBuilder.append(prefix).append(" ");
        if (nameColor != null) nameBuilder.append("&").append(nameColor.getChar()).append(name);
        else nameBuilder.append(name);
        if (suffix != null) nameBuilder.append(" ").append(suffix);
        player.displayName(CoreUtil.parseColorComponent(nameBuilder.toString()));
        player.playerListName(CoreUtil.parseColorComponent(nameBuilder.toString()));
    }

    private void updatePackets(BoardData boardData) {
        Bukkit.getAsyncScheduler().runDelayed(colors, scheduledTask -> {
            for (BoardData board : colors.getScoreboardManager().getAllBoards()) board.sendPackets(player);
            boardData.broadcastPackets();
        }, 1, TimeUnit.SECONDS);
    }

    public void create() {
        final BoardData boardData = createBoard();
        updatePlayerData();
        updatePackets(boardData);
        colors.getScoreboardManager().setPlayerBoard(uniqueId, boardData);
    }
}
