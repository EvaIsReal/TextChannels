package de.iv.textchannels.commands.conversations;

import de.iv.ILib;
import de.iv.textchannels.TextChannel;
import de.iv.textchannels.Uni;
import de.iv.textchannels.user.UserManager;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class TextChannelPasswordConversation {

    public static class PasswordPrompt extends StringPrompt {

        private TextChannel textChannel;
        private Player p;

        public PasswordPrompt(TextChannel textChannel, Player p) {
            this.textChannel = textChannel;
            this.p = p;
        }

        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color(Uni.PREFIX + "&aThis channel is password secured. Please type in the password. \nYou can exit this conversation with 'exit'");
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            if(textChannel.getPassword().equals(input)) {
                context.setSessionData("tc", textChannel);
                UserManager.selectTextChannel(p.getUniqueId().toString(), textChannel.getId());
                return new SuccessPrompt();
            }
            else return new FailurePrompt();
        }
    }

    private static class SuccessPrompt extends MessagePrompt {
        @Override
        protected Prompt getNextPrompt(ConversationContext context) {
            return Prompt.END_OF_CONVERSATION;
        }

        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color(Uni.PREFIX + "&aSuccess! Changed Text-Channel to " + ((TextChannel)context.getSessionData("tc")).getName());
        }
    }

    private static class FailurePrompt extends MessagePrompt {
        @Override
        protected Prompt getNextPrompt(ConversationContext context) {
            return Prompt.END_OF_CONVERSATION;
        }

        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color(Uni.PREFIX + "&cFailed! Perhaps your password is wrong.");
        }
    }

}
