package de.iv.textchannels.commands.subcommands;

import de.iv.textchannels.Main;
import de.iv.textchannels.Uni;
import de.iv.textchannels.commands.ConsoleCommand;
import de.iv.textchannels.commands.PermissionCommand;
import de.iv.textchannels.commands.SubCommand;
import de.iv.textchannels.commands.conversations.TextChannelCreationConversation;
import de.iv.textchannels.listeners.ConversationAboundedListener;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;

import java.util.List;

public class CreateCommand extends ConsoleCommand implements PermissionCommand {

    @Override
    public void executeFromConsole(CommandSender sender, String[] args) {
        ConversationFactory factory = new ConversationFactory(Main.get())
                .withEscapeSequence("exit")
                .withFirstPrompt(new TextChannelCreationConversation.NamePrompt())
                .withTimeout(30)
                .addConversationAbandonedListener(new ConversationAboundedListener())
                .withLocalEcho(false)
                .withPrefix(context -> Uni.PREFIX);
        factory.buildConversation((Conversable) sender).begin();
    }

    @Override
    public String name() {
        return "create";
    }

    @Override
    public List<String> aliases() {
        return List.of(".c");
    }

    @Override
    public String syntax() {
        return "create";
    }

    @Override
    public String description() {
        return "Starts a creation-prompt for a Text-Channel";
    }

    @Override
    public String permission() {
        return "tc_create";
    }
}
