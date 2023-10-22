package dxshardness.dxsfishingsimulator.data.stat.cmds;

import dxshardness.dxsfishingsimulator.data.stat.StatisticMenu;
import dxshardness.dxsfishingsimulator.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OpenStatisticMenu implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // cmd          arg[0]
        // /statfishing dxshardness
        if (sender instanceof Player player) {
            if (args.length > 1) {
                ChatUtil.sendMessage(player, "&cИспользуй команду для просмотра статистики так: /statfishing nickname");
            }
            else if (args.length == 0) {
                StatisticMenu.instance.openStatMenu(player);
            }
            else {
                Player target = Bukkit.getPlayer(args[0]);
                // Открываем меню статистики игркоку
                StatisticMenu.instance.openStatMenu(target);
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return null;
        }
        String nullargs = " ";
        if (args.length > 1) {
            return Collections.singletonList(nullargs);
        }
        return null;
    }
}
