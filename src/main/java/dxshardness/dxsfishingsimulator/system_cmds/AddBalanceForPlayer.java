package dxshardness.dxsfishingsimulator.system_cmds;

import dxshardness.dxsfishingsimulator.DxsFishingSimulator;
import dxshardness.dxsfishingsimulator.utils.ChatUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class AddBalanceForPlayer implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        // Команда для пополнения баланса игроку
        Economy balance = DxsFishingSimulator.getInstance().balance;
        if (sender instanceof Player player) {
            Player target = Bukkit.getPlayer(args[0]);
            double value = Double.parseDouble(args[1]);
            if (player.isOp()) {
                // Делаем миллион проверок, что игрок ввёл команду верно
                if (args.length > 2) {
                    ChatUtil.sendMessage(player, "&cИспользуй команду правильно: /addbalance [nickname] [value]");
                }

                if (!target.isOnline()) {
                    ChatUtil.sendMessage(player, "&cИгрока с никнеймом " + target.getDisplayName() + " нет в сети!");
                }

                if (args[1] == null) {
                    ChatUtil.sendMessage(player, "&cИспользуй команду правильно: /addbalance [nickname] [value]");
                }

                // Выполняем пополнение
                balance.depositPlayer(target, value);

                // Сообщаем отправителю и получателю о том что баланс пополнен
                ChatUtil.sendMessage(player, "&aБаланс игрока " + target.getDisplayName() + " пополнен на сумму " + value + "!");
                ChatUtil.sendMessage(target, "&c*&aПополнен баланс на сумму " + value + "!");
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 2) {
            return Arrays.asList(
                    "value"
            );
        }
        if (args.length > 2) {
            return Arrays.asList(
                    " "
            );
        }
        return null;
    }
}
