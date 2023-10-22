package dxshardness.dxsfishingsimulator.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AllInventoriesSettings implements Listener {

    // Кто бы чо не говорил, а мне удобно в отдельном классе, отменять клики в инвентарях, которые существуют в плагине
    @EventHandler
    public void clickInMenus(InventoryClickEvent event) {

        if (event.getView().getTitle().equals("Магазинчик Кирюхи")) {
            event.setCancelled(true);
        }
        if (event.getView().getTitle().startsWith("Статистика игрока")) {
            event.setCancelled(true);
        }
    }
}
