package lee.code.colors.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "players")
public class PlayerTable {

    @DatabaseField(id = true, canBeNull = false)
    private UUID uniqueId;

    @DatabaseField(columnName = "color")
    private String color;

    @DatabaseField(columnName = "prefix")
    private String prefix;

    @DatabaseField(columnName = "suffix")
    private String suffix;

    @DatabaseField(columnName = "priority")
    private String priority;

    public PlayerTable(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.color = ChatColor.YELLOW.name();
        this.priority = "z";
    }
}