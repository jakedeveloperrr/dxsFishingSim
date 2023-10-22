package dxshardness.dxsfishingsimulator.npc.trader;

import dxshardness.dxsfishingsimulator.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TraderMenu {

    public static TraderMenu instance = new TraderMenu();

    // кастомка (текстурпаком)
    public void openTradeMenu(Player player) {
        // Реализация менюшки, которая открывается по нажаатию на НПС - "Кирюха"
        Inventory menu = Bukkit.createInventory(null, 36, "Магазинчик Кирюхи");
        ItemStack baitItem = new ItemStack(Material.PAPER);
        ItemStack rodUp = new ItemStack(Material.PAPER);

        ItemMeta firstMeta = baitItem.getItemMeta();
        firstMeta.setCustomModelData(10000);
        firstMeta.setDisplayName(ChatUtil.format("&rПриобрести снасти"));
        firstMeta.setLore(
                Arrays.asList(
                        " ",
                        ChatUtil.format("&6Стоимость: 10 монет")
                )
        );

        ItemMeta secondMeta = rodUp.getItemMeta();
        secondMeta.setCustomModelData(10000);
        secondMeta.setDisplayName(ChatUtil.format("&rБуст рыбалки"));
        secondMeta.setLore(
                Arrays.asList(
                        " ",
                        ChatUtil.format("&6Стоимость: 500 монет"),
                        ChatUtil.format("&cПока недоступно!")
                )
        );

        baitItem.setItemMeta(firstMeta);
        rodUp.setItemMeta(secondMeta);

        // Я не знаю почему именно так, но пусть будет чо
        menu.setItem(10, baitItem);
        menu.setItem(11, baitItem);
        menu.setItem(12, baitItem);
        menu.setItem(19, baitItem);
        menu.setItem(20, baitItem);
        menu.setItem(21, baitItem);
        menu.setItem(28, baitItem);
        menu.setItem(29, baitItem);
        menu.setItem(30, baitItem);

        menu.setItem(14, rodUp);
        menu.setItem(15, rodUp);
        menu.setItem(16, rodUp);
        menu.setItem(23, rodUp);
        menu.setItem(24, rodUp);
        menu.setItem(25, rodUp);
        menu.setItem(32, rodUp);
        menu.setItem(33, rodUp);
        menu.setItem(34, rodUp);


        // open menu
        player.openInventory(menu);
    }
}
