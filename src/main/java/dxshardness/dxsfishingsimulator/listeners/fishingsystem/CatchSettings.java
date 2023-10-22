package dxshardness.dxsfishingsimulator.listeners.fishingsystem;

import dxshardness.dxsfishingsimulator.DxsFishingSimulator;
import dxshardness.dxsfishingsimulator.data.DatabaseManager;
import dxshardness.dxsfishingsimulator.utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CatchSettings implements Listener {
    @EventHandler
    public void catchItems(PlayerFishEvent event) {
        // Создаём все нужные поля
        ItemStack rippedJacket = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta jacketMeta = rippedJacket.getItemMeta();
        jacketMeta.setDisplayName(ChatUtil.format("&eРваная куртка"));
        jacketMeta.setLore(
                Arrays.asList(
                        " ",
                        ChatUtil.format("&5*&eЛегендарный предмет")
                )
        );
        rippedJacket.setItemMeta(jacketMeta);
        Player player = event.getPlayer();


        // Делаем провреку, есть ли у игрока предмет, текстура которого будет снастями, а так же будет кастомное название
        if (!player.getInventory().contains(Material.COAL)) {
            // Если нет, то мы отменяем слушатель, пишем игроку чтоб купил снасти
            event.setCancelled(true);
            ChatUtil.sendMessage(player, "&cКупи приманку для рыбы у Кирюхи!");
        }
        else {
            // Исполняем код, если у игрока есть снасти

            // удаляем у него 1 предмет снастей
            deleteGearValue(player);
            // Не отменяем слушать
            event.setCancelled(false);
            // Делаем проверку, что если игрок начал рыбачить, то он пока что ничего не получает
            if (event.getState() == PlayerFishEvent.State.FISHING) {
                return;
            } else if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) { // Если игрок что-то вылавливает, то

                // Если у игрока есть улов, то мы удаляем его
                if (event.getCaught() != null) {
                    event.getCaught().remove();
                }

                // Пишем логику, при которой игрок с определённым шансом получит какой-то предмет
                double randomValue = Math.random();
                // Достаём из конфига значение без доп бустов
                double dropChanceNoBoost = DxsFishingSimulator.getInstance().getConfig().getDouble("drop_chance_no_boost.crap");

                // Добавляем в БД, то что игрок что-то поймал
                DatabaseManager.instance.incrementCatchCount(player);
                    if (randomValue <= dropChanceNoBoost) {
                        // Если игроку выпал шанс, хлам, то он получит 1 монету (Vault)
                        ChatUtil.sendMessage(player, "&e+1 монета");
                        DxsFishingSimulator.getInstance().balance.depositPlayer(player, 1.0);
                        player.getWorld()
                                .dropItemNaturally(player.getLocation()
                                        , new ItemStack(Material.BLACK_DYE, 1));
                    } else if (randomValue <= dropChanceNoBoost + DxsFishingSimulator.getInstance().getConfig().getDouble("drop_chance_no_boost.pufferfish")) { // Аналогично с остальными предметами
                        ChatUtil.sendMessage(player, "&e+5 монет");
                        DxsFishingSimulator.getInstance().balance.depositPlayer(player, 5.0);
                        player.getWorld()
                                .dropItemNaturally(player.getLocation()
                                        , new ItemStack(Material.PUFFERFISH, 1));

                    } else if (randomValue <= dropChanceNoBoost + DxsFishingSimulator.getInstance().getConfig().getDouble("drop_chance_no_boost.pufferfish")
                            + DxsFishingSimulator.getInstance().getConfig().getDouble("drop_chance_no_boost.cooked_cod")) {
                        ChatUtil.sendMessage(player, "&e+15 монет");
                        DxsFishingSimulator.getInstance().balance.depositPlayer(player, 15.0);
                        player.getWorld()
                                .dropItemNaturally(player.getLocation()
                                        , new ItemStack(Material.COOKED_COD, 1));
                    } else if (randomValue <= dropChanceNoBoost + DxsFishingSimulator.getInstance().getConfig().getDouble("drop_chance_no_boost.pufferfish")
                            + DxsFishingSimulator.getInstance().getConfig().getDouble("drop_chance_no_boost.cooked_cod")
                            + DxsFishingSimulator.getInstance().getConfig().getDouble("drop_chance_no_boost.ripped_jacket")) {
                        ChatUtil.sendMessage(player, "&e+30 монет");
                        DxsFishingSimulator.getInstance().balance.depositPlayer(player, 30.0);
                        player.getWorld()
                                .dropItemNaturally(player.getLocation()
                                        , rippedJacket);
                    } else {
                        ChatUtil.sendMessage(player, "&cОу, не повезло, тут пусто! Ничего повезёт в следующий раз.");
                    }
            }
        }
    }

    // Метод, который удаляет у игрока из инвентаря одну еденицу снастей (-1 уголь)
    private void deleteGearValue(Player player) {
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

        Inventory playerInventory = player.getInventory();

        for (int i = 0; i < playerInventory.getSize(); i++) {
            ItemStack item = playerInventory.getItem(i);

            if (item != null && item.isSimilar(gear)) {
                int newAmount = item.getAmount() - 1;
                if (newAmount > 0) {
                    item.setAmount(newAmount);
                } else {
                    playerInventory.setItem(i, null);
                }
                break;
            }
        }
    }
}