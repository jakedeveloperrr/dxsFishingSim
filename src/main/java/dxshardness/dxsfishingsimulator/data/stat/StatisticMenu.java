package dxshardness.dxsfishingsimulator.data.stat;

import dxshardness.dxsfishingsimulator.DxsFishingSimulator;
import dxshardness.dxsfishingsimulator.data.DatabaseManager;
import dxshardness.dxsfishingsimulator.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StatisticMenu {
    public static StatisticMenu instance = new StatisticMenu();

    // default menu
    public void openStatMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 27, "Статистика игрока");

        // Создаём предметы для менюшки
        ItemStack catchValue = new ItemStack(Material.FISHING_ROD);
        ItemStack playerBalance = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemStack failedCatchValue = new ItemStack(Material.PUFFERFISH);


        ItemMeta firstMeta = catchValue.getItemMeta();
        ItemMeta secondMeta = playerBalance.getItemMeta();
        ItemMeta thirdMeta = failedCatchValue.getItemMeta();

        // Описываем полностью первый предмет
        firstMeta.setDisplayName(ChatUtil.format("&rКоличество попыток что-то поймать"));
        firstMeta.setLore(
                Arrays.asList(
                        " ",
                        ChatUtil.format("&8Данные о игроке: &e" + player.getDisplayName()),
                        ChatUtil.format("&dВсего попыток на ловлю предметов: " + DatabaseManager.instance.getCatchCount(player.getUniqueId().toString()))
                )
        );

        // Описываем полностью второй предмет
        secondMeta.setDisplayName(ChatUtil.format("&rБаланс"));
        secondMeta.setLore(
                Arrays.asList(
                        " ",
                        ChatUtil.format("&8Данные о игроке: &e" + player.getDisplayName()),
                        ChatUtil.format("&dКоличество денег у игрока: " + DxsFishingSimulator.getInstance().balance.getBalance(player))
                )
        );

        // Описываем полностью третий предмет
        thirdMeta.setDisplayName(ChatUtil.format("&rВ разработке?"));
        thirdMeta.setLore(
                Arrays.asList(
                        " ",
                        ChatUtil.format("&8Данные о игроке: &e" + player.getDisplayName()),
                        ChatUtil.format("&dsus")
                )
        );

        catchValue.setItemMeta(firstMeta);
        playerBalance.setItemMeta(secondMeta);
        failedCatchValue.setItemMeta(thirdMeta);

        // Ставим все предметы в менюшку
        menu.setItem(10, catchValue);
        menu.setItem(13, playerBalance);
        menu.setItem(16, failedCatchValue);

        setDecorationForThreeSlots(menu, 10, 13, 16);

        // Открываем менюшку игрокам
        player.openInventory(menu);
    }

    // Метод вставляющий декорации в меню, с исключением 3 слотов
    private void setDecorationForThreeSlots(Inventory inv, int slot1, int slot2, int slot3) {
        ItemStack decoration = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fourthMeta = decoration.getItemMeta();
        fourthMeta.setDisplayName(" ");
        fourthMeta.setLore(
                Arrays.asList(
                        " "
                )
        );
        decoration.setItemMeta(fourthMeta);

        for (int i = 0; i < inv.getSize(); i++) {
            if (i != slot1 && i != slot2 && i != slot3) {
                inv.setItem(i, decoration);
            }
        }
    }
}
