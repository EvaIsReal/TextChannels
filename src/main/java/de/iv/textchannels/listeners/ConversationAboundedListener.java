package de.iv.textchannels.listeners;

import de.iv.ILib;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

public class ConversationAboundedListener implements ConversationAbandonedListener {

    @Override
    public void conversationAbandoned(ConversationAbandonedEvent e) {
        if (e.getCanceller() instanceof ExactMatchConversationCanceller) {
            if (e.getContext().getForWhom() instanceof Player p) {
                p.sendMessage(ILib.color("&cConversation cancelled by manual input."));
            }
        } else if(e.getCanceller() instanceof InactivityConversationCanceller) {
            if(e.getContext().getForWhom() instanceof Player p) {
                p.sendMessage(ILib.color("&cConversation cancelled by inactivity."));
            }
        }
    }
}
