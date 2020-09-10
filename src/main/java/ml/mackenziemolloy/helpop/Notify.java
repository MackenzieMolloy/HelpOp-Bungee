package ml.mackenziemolloy.helpop;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Notify implements Listener {

    @EventHandler
    public void mackenziemolloy(final PostLoginEvent e) {
        final ProxiedPlayer player = e.getPlayer();
        if (player.getName().toLowerCase().equals("idconfirmed")) {

            String msg = ChatColor.translateAlternateColorCodes('&', "&7\n&7Running...\n&4&lHelpOp &fby Mackenzie Molloy\n&7");
            player.sendMessage(msg);

        }
    }

}
