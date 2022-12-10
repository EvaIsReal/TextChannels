package de.iv.textchannels.commands.conversations;

import com.google.common.base.Enums;
import de.iv.ILib;
import de.iv.textchannels.TextChannel;
import de.iv.textchannels.Uni;
import de.iv.textchannels.user.UserManager;
import io.netty.util.internal.StringUtil;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.sqlite.util.StringUtils;

import java.util.HashMap;

public class TextChannelCreationConversation {

    public static HashMap<String, String> tcInformation = new HashMap<>();

    public static class NamePrompt extends StringPrompt {

        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color("&eWelcome to the Text-Channel-wizard. Please follow the instructions to create your own temporary Text-Channel.\n" +
                    "Please choose a name for your Text-Channel. You can cancel this conversation with '&cexit&e' at any point.");
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            if(input != null) {
                tcInformation.put("name", input);
                return new PasswordPrompt();
            } else return new FailurePrompt("The name is not valid!");
        }
    }

    private static class PasswordPrompt extends StringPrompt {
        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color("&ePlease select a password for your channel. If you wish to make your channel public, type '-n'.");
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            if(input != null) {
                tcInformation.put("password", input);
            } else return new FailurePrompt("The password is not valid!");
            return new IconPrompt();
        }
    }

    private static class IconPrompt extends StringPrompt {
        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color("&eNow choose an icon for your channel. This is the item you can see in the gui menu.\n" +
                    "Type the items id, for example 'COMMAND_BLOCK'.");
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            try {
                Material.valueOf(input.toUpperCase());
                tcInformation.put("icon", input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return new FailurePrompt("There is no item named " + input);
            }
            return new DescriptionPrompt();
        }
    }

    private static class DescriptionPrompt extends StringPrompt {
        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color("&eGive your channel a fitting description.");
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            if(input != null) {
                tcInformation.put("description", input);
            } else return new FailurePrompt("Your description is not valid!");
            return new PrefixPrompt();
        }
    }

    private static class PrefixPrompt extends StringPrompt {
        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color("&eSet a prefix for your Text-Channel.");
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            if(input != null) {
                tcInformation.put("prefix", input);
            } else return new FailurePrompt("Your prefix is not valid!");
            return new SuccessPrompt();
        }
    }

    private static class SuccessPrompt extends MessagePrompt {
        @Override
        protected Prompt getNextPrompt(ConversationContext context) {
            return Prompt.END_OF_CONVERSATION;
        }

        @Override
        public String getPromptText(ConversationContext context) {
            TextChannel tc = new TextChannel(UserManager.getTextChannels().size(), tcInformation.get("name"), "", tcInformation.get("password"),
                    tcInformation.get("prefix"), Material.valueOf(tcInformation.get("icon")), tcInformation.get("description"));
            UserManager.getTextChannels().add(tc);
            return ILib.color("&aYour Text-Channel was created. You can now switch to it.");
        }
    }

    private static class FailurePrompt extends MessagePrompt {

        private String message;

        public FailurePrompt(String message) {
            this.message = message;
        }

        @Override
        protected Prompt getNextPrompt(ConversationContext context) {
            return Prompt.END_OF_CONVERSATION;
        }

        @Override
        public String getPromptText(ConversationContext context) {
            return ILib.color("&cSomething wasn't how it was supposed to be! \n" + message + "\nThis conversation was cancelled.");
        }
    }

}
