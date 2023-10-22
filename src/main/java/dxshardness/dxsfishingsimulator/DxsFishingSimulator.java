package dxshardness.dxsfishingsimulator;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dxshardness.dxsfishingsimulator.data.stat.cmds.OpenStatisticMenu;
import dxshardness.dxsfishingsimulator.listeners.AllInventoriesSettings;
import dxshardness.dxsfishingsimulator.listeners.fishingsystem.CatchSettings;
import dxshardness.dxsfishingsimulator.npc.SpawnNPCs;
import dxshardness.dxsfishingsimulator.npc.trader.TraderMenuListener;
import dxshardness.dxsfishingsimulator.system_cmds.AddBalanceForPlayer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class DxsFishingSimulator extends JavaPlugin {
    // Делаем статичное поле, для обращения к основному классу
    private static DxsFishingSimulator instance;
    // Поле для работы с ProtocolLibary
    private ProtocolManager protocolManager;
    // Поле для работы с Vault
    public Economy balance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        PluginManager manager = getServer().getPluginManager();
        protocolManager = ProtocolLibrary.getProtocolManager();

        sendConsoleMessage("Plugin FishingSimulator - enable");

        if (!setupEconomy()) {
            manager.disablePlugin(this);
            sendConsoleMessage("Install Vault!");
        }

        // register commands

        // /spawn_npc <args>
        getCommand("spawn_npc").setExecutor(new SpawnNPCs());
        getCommand("spawn_npc").setTabCompleter(new SpawnNPCs());

        // /statfishing (null args)
        // /statfishing nick
        getCommand("statfishing").setExecutor(new OpenStatisticMenu());
        getCommand("statfishing").setTabCompleter(new OpenStatisticMenu());

        // /addbalance <nick> <value>
        getCommand("addbalance").setExecutor(new AddBalanceForPlayer());
        getCommand("addbalance").setTabCompleter(new AddBalanceForPlayer());

        // register listeners
        manager.registerEvents(new AllInventoriesSettings(), this);
        manager.registerEvents(new TraderMenuListener(), this);
        manager.registerEvents(new CatchSettings(), this);
    }

    // Метод для отправки сообщения в консоли
    public void sendConsoleMessage(String msg) {
        getLogger().info(msg);
    }

    // getters

    public static DxsFishingSimulator getInstance() {
        return instance;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public boolean setupEconomy() {
        RegisteredServiceProvider<Economy> registeredServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (registeredServiceProvider == null)
            return false;

        balance = registeredServiceProvider.getProvider();
        return true;
    }
    @Override
    public void onDisable() {
        sendConsoleMessage("Plugin FishingSimulator - disable");
    }
}
