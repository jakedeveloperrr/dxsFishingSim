package dxshardness.dxsfishingsimulator.npc.trader;

import dxshardness.dxsfishingsimulator.DxsFishingSimulator;
import dxshardness.dxsfishingsimulator.data.DatabaseManager;
import dxshardness.dxsfishingsimulator.utils.ChatUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TraderMenuListener implements Listener {

    // Делаем логику, кликов в менюшке у НПС
    @EventHandler
    public void clickOnTraderMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Economy balance = DxsFishingSimulator.getInstance().balance;

        ItemStack gear = new ItemStack(Material.COAL, 5);
        ItemMeta gearMeta = gear.getItemMeta();
        gearMeta.setDisplayName(ChatUtil.format("&rПриманка для рыбы"));
        gearMeta.setLore(
                Arrays.asList(
                        " ",
                        ChatUtil.format("&7Можно купить в магазине Кирюхи")
                )
        );
        gear.setItemMeta(gearMeta);

        ItemStack clickItem = event.getCurrentItem();
        // Делаем проверку, на то что инвентарь нам подходит
        if (event.getView().getTitle().equals("Магазинчик Кирюхи")) {
            if (event.getCurrentItem().getType() != null) {
                switch (event.getCurrentItem().getType()) {
                    // Делаем проверку, что нажатый предмет - бумага
                    case PAPER:
                        // Делаем проверку что нажатый предмет имеет нужное название
                        if (clickItem.getItemMeta().getDisplayName().equalsIgnoreCase("Приобрести снасти")) {
                            // Пишем логику
                            // Делаем проверку, что у игрока есть деньги (vault)
                            if (balance.getBalance(player) >= 10.0) {
                                // Если есть, то даём игроку 5 снастей, забираем деньги у игрока и даём отзыв о покупке игроку
                                player.getInventory().addItem(gear);
                                balance.withdrawPlayer(player, 10.0);
                                ChatUtil.sendMessage(player, "&7[&cКирюха&7]&a Спасибо за покупку, приходи чаще!");
                            }
                            // Если нет, то сообщаем об этом игроку
                            else {
                                ChatUtil.sendMessage(player, "&7[&cКирюха&7]&c Приходи когда будут деньги...");
                            }
                        }
                        // Аналогично
                        else if (clickItem.getItemMeta().getDisplayName().equalsIgnoreCase("Буст рыбалки")) {
                            ChatUtil.sendMessage(player, "&cВременно недоступно");
                        }
                        break;
                }
            } else {
                player.closeInventory();
            }
        }
    }
}
