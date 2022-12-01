package de.iv.textchannels;

import de.iv.ILib;
import de.iv.textchannels.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class MessageRouter implements Listener {


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        //Player sends a message
        e.setCancelled(true);
        Player p = e.getPlayer();
        String msg = e.getMessage();

        TextChannel sel = UserManager.getSelectedTextChannel(p.getUniqueId().toString());
        if(sel != null) {
            Bukkit.getOnlinePlayers().forEach(n -> {
                if(Objects.requireNonNull(UserManager.getSelectedTextChannel(n.getUniqueId().toString())).getId() == sel.getId()) {
                    n.sendMessage(ILib.color(sel.getPrefix() + " &2&l" + p.getName() + " &8| &7" + msg));
                }
            });
        }


    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!UserManager.exists(p.getUniqueId().toString())) {
            UserManager.createUserData(p.getUniqueId().toString());
        }
    }



}
