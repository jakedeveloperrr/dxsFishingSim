package dxshardness.dxsfishingsimulator.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dxshardness.dxsfishingsimulator.DxsFishingSimulator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SpawnNPCs implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // прикольная фича 16 джавы, удобно
        if (sender instanceof Player player) {

            if (player.isOp()) {
                if (args[0].equalsIgnoreCase("kirill_npc")) {
                    // Делаем профиль нпс, имя, скин и т.п
                    GameProfile gameProfile = new GameProfile(
                            UUID.randomUUID(),
                            "Кирюха"
                    );
                    gameProfile.getProperties().put(
                            "textures",
                            new Property(
                                    "textures",
                                    "eyJ0aW1lc3RhbXAiOjE1NTY4NDUwNjkxNjIsInByb2ZpbGVJZCI6ImIwZDczMmZlMDBmNzQwN2U5ZTdmNzQ2MzAxY2Q5OGNhIiwicHJvZmlsZU5hbWUiOiJPUHBscyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGVkNzg5ZDY1ODMxODcxOWM3YmY3MTJmZTlkZWQ5N2FjZjk4NDcyMGQ3YzkxYTI0YTUwMjBmNWIxYmFhZWYxNiJ9fX0=",
                                    "WYXCevyrC70+naIhvMOBcW8fpUkbdMFRs9WSbl+1amS09PKZwhOedDl27NsUCYP1TyhGu+0R30v/GN56TJ11JxCP6vbpAygYOtJAJxqWzYA7yyNTzabAhbFmiVoexeLnSXhcWx1Ea4yixuuIrUKuUbKyYMJBHP07oAGjeQYTUH5V3Cw6txV+WPCni3UeQDBLHERl6uf8ILLsMCbfqalHX4gNnmau1ACxObIpgYaT7JqJiCkhRWcx4DS9vSjKEmYcFWKZzsxRR4fyh3aXo09harFEmFGLOmvgnWqXGSE5hRpwrQZYtUWyS58lyocOqgdDbBsrsupe2GwUYRJp65HyXhwH1haMHELsi7pVu/UeP/ESRrFSVwemUuOZItiAO929sMoARqPH5UpxwMu6QEmKw5K4tIQutOIJrM+3n6Amdk9W9BNvSV7cB/VUpPkFaTxzKz+CarAnM4F36oMA+v4gmz08i698Zy8TTmTXICVk+9/peQpoIc/YxDoLgqLyWjFPz+Pag56VrcmnVxXQADe9OzgH0QKzRESZ4QatzeSkWq6WRjRUMVMoPP2LTiyKqi1jqPx3zUXTuT2xbSzhLKN8A2N4WfukRyUAjbYecgOleS1RKiG+gHvbob5ulBchBnCeMiWsYdS5whQFSFaUZKgjU/0oTqQ1l77kNLuPuSkh65M="
                            )
                    );
                    // Вытаскиваем из конфига координаты x, y, z
                    double x = DxsFishingSimulator.getInstance().getConfig().getDouble("npc_spawn_coordinate.kirill.x");
                    double y = DxsFishingSimulator.getInstance().getConfig().getDouble("npc_spawn_coordinate.kirill.y");
                    double z = DxsFishingSimulator.getInstance().getConfig().getDouble("npc_spawn_coordinate.kirill.z");
                    // Задаём локацию
                    Location spawnLocation = new Location(player.getWorld(), x, y, z);

                    // Создаём НПСшника
                    NPC npc = new NPC(spawnLocation, gameProfile);
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return Arrays.asList(
                    // Делаем подскази для отправителя команды
                    "kirill_npc", "???_npc"
            );
        }

        if (args.length > 1) {
            return Arrays.asList(
                    " "
            );
        }
        return null;
    }
}
