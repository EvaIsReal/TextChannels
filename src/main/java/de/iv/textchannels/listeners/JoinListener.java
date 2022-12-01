package de.iv.textchannels.listeners;

import de.iv.ILib;
import de.iv.textchannels.TextChannel;
import de.iv.textchannels.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
            TextChannel tc = UserManager.getSelectedTextChannel(p.getUniqueId().toString());
            p.sendMessage(ILib.color("&2Selected Text-Channel: &a" + tc.getName() + " &8[" + tc.getId() + "]"));
        } catch (IndexOutOfBoundsException ex) {
            UserManager.selectTextChannel(p.getUniqueId().toString(), 0);
            p.sendMessage(ILib.color("&cYour selected Text-Channel has been deleted while you were away."),
                    ILib.color("&cYou have been moved to &aGlobal &8[0]"));
        }
    }

}
