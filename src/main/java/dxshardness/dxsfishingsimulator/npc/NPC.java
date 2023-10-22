package dxshardness.dxsfishingsimulator.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.mojang.authlib.GameProfile;
import dxshardness.dxsfishingsimulator.DxsFishingSimulator;
import dxshardness.dxsfishingsimulator.npc.trader.TraderMenu;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class NPC extends ServerPlayer {
    // Создание НПС (делал по гайду, частично)
    public NPC(Location location, GameProfile gameProfile) {

        super(
                ((CraftServer) DxsFishingSimulator.getInstance().getServer()).getServer(),
                ((CraftWorld) location.getWorld()).getHandle(),
                gameProfile
        );

        setPos(location.getX(), location.getY(), location.getZ());

        // Отправляем игрокам пакет, который создаёт НПС на определённом месте
        // При перезаходе он исчезает, так что лучше всего, прописать логику созданию НПС, при входе игрока на сервер
        for (Player player: location.getWorld().getPlayers()) {
            ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

            serverPlayer.connection.send(
                    new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, this)
            );
            serverPlayer.connection.send(
                    new ClientboundAddPlayerPacket(this)
            );
            serverPlayer.connection.send(
                    new ClientboundTeleportEntityPacket(this)
            );
            // Через ProtocolLib делаем логику нажатия по НПС
            DxsFishingSimulator.getInstance().getProtocolManager().addPacketListener(
                    new PacketAdapter(
                            DxsFishingSimulator.getInstance(),
                            PacketType.Play.Client.USE_ENTITY
                    ) {
                        @Override
                        public void onPacketReceiving(PacketEvent event) {
                            // Сравниваем айди, если айди нпс, сходится с getid, то пишем, логику нажатия, а именно открытие менюшки
                            int npcId = event.getPacket().getIntegers().read(0);
                            if (npcId == getId()) {
                                Bukkit.getScheduler().runTask(DxsFishingSimulator.getInstance(), () -> {
                                    TraderMenu.instance.openTradeMenu(event.getPlayer());
                                });
                            }
                        }
                    }
            );
        }
    }


}
