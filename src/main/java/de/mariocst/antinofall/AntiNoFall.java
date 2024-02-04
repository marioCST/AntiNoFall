package de.mariocst.antinofall;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.ItemID;
import cn.nukkit.network.protocol.PlayerActionPacket;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.network.protocol.types.AuthInputAction;
import cn.nukkit.plugin.PluginBase;

@SuppressWarnings("unused")
public class AntiNoFall extends PluginBase implements Listener {
    private final String prefix = "§8[§4Anti§6NoFall§8] §f";

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        this.log("Enabled AntiNoFall");
    }

    @Override
    public void onDisable() {
        this.log("Enabled AntiNoFall");
    }

    public void log(String msg) {
        this.getLogger().info(this.prefix + msg);
    }

    public void warn(String msg) {
        this.getLogger().warning(this.prefix + msg);
    }

    private void check(Player player) {
        if (player.getInventory().getChestplate().getId() != ItemID.ELYTRA) {
            this.warn(player.getName() + " tried to use NoFall!");
            player.kick("Flying is not enabled on this server.");
        }
    }

    @EventHandler
    public void onPacketReceive(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();

        if (player.isOp())
            return;

        if (event.getPacket() instanceof PlayerActionPacket packet) {
            if (packet.action == PlayerActionPacket.ACTION_START_GLIDE)
                this.check(player);

            return;
        }

        if (event.getPacket() instanceof PlayerAuthInputPacket packet) {
            if (packet.getInputData().contains(AuthInputAction.START_GLIDING))
                this.check(player);
        }
    }
}
