package ml.mackenziemolloy.helpop;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.CommandSender;
import java.util.HashMap;
import net.md_5.bungee.api.plugin.Command;

public class HelpOp extends Command
{
    public HashMap<String, Long> cooldowns;

    public HelpOp() {
        super("helpop");
        this.cooldowns = new HashMap<String, Long>();
    }

    public void execute(final CommandSender sender, final String[] args) {
        final String prefix = Main.cg.getString("Prefix").replace("&", "§");
        if (sender instanceof ProxiedPlayer) {
            if (sender.hasPermission("helpop.use")) {
                final ProxiedPlayer player = (ProxiedPlayer)sender;
                if (args.length >= 1) {
                    if (Main.cg.getBoolean("HelpOp_Enabled")) {
                        final int c = Main.cg.getInt("HelpOp_Cooldown");
                        if (this.cooldowns.containsKey(player.getName())) {
                            final long secondsLeft = this.cooldowns.get(player.getName()) / 1000L + c - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                player.sendMessage(String.valueOf(String.valueOf(prefix)) + Main.cg.getString("Cooldown_Wait").replace("&", "§").replace("%player%", player.getName()).replace("%time%", new StringBuilder(String.valueOf(secondsLeft)).toString()));
                                return;
                            }
                        }
                        String mensaje = "";
                        for (int i = 0; i < args.length; ++i) {
                            mensaje = String.valueOf(String.valueOf(mensaje)) + args[i] + " ";
                        }
                        final String format = Main.cg.getString("HelpOp_Format").replace("&", "§").replace("%player%", player.getName()).replace("%server%", player.getServer().getInfo().getName()).replace("%msg%", mensaje);
                        player.sendMessage(String.valueOf(String.valueOf(prefix)) + Main.cg.getString("HelpOp_Sent").replace("&", "§"));

                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd:MM HH:mm:ss");


                        try {
                            Main.writer.append("\n[" + formatter.format(date) + "] (" + player.getServer().getInfo().getName() + ") " + player.getName() + ": " + mensaje);
                            Main.writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        //Main.helpopLogs.set("[" + date + "] " + player.getName(), mensaje);




                        for (final ProxiedPlayer staffs : Main.getInstance().getProxy().getPlayers()) {
                            if (staffs.hasPermission("helpop.receive")) {
                                staffs.sendMessage(format);
                            }
                        }
                        this.cooldowns.put(player.getName(), System.currentTimeMillis());
                    }
                    else {
                        player.sendMessage(String.valueOf(String.valueOf(prefix)) + Main.cg.getString("HelpOp_Disabled").replace("&", "§"));
                    }
                }
                else {
                    player.sendMessage(String.valueOf(String.valueOf(prefix)) + Main.cg.getString("HelpOp_Usage").replace("&", "§"));
                }
            }
            else {
                sender.sendMessage(String.valueOf(String.valueOf(prefix)) + Main.cg.getString("No_Permission").replace("&", "§"));
            }
        }
        else {
            sender.sendMessage(String.valueOf(String.valueOf(prefix)) + "command only for players!");
        }
    }
}
