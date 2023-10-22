package dxshardness.dxsfishingsimulator.listeners;

import dxshardness.dxsfishingsimulator.utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PlayerJoinOnServer implements Listener {
    // Создаём метод, логика которого, выдать игроку 20 едениц снастей, при первом заходе на сервер
    @EventHandler
    public void givePlayerGears(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ItemStack gear = new ItemStack(Material.COAL, 20);
        ItemMeta gearMeta = gear.getItemMeta();
        gearMeta.setDisplayName(ChatUtil.format("&rПриманка для рыбы"));
        gearMeta.setLore(
                Arrays.asList(
                        " ",
                        ChatUtil.format("&7Можно купить в магазине Кирюхи")
                )
        );
        gear.setItemMeta(gearMeta);

        if (!player.hasPlayedBefore()) {
           player.getInventory().addItem(gear);
        }
    }
}
