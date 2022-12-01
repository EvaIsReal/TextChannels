package de.iv.textchannels.commands;

import de.iv.textchannels.Main;
import de.iv.textchannels.commands.subcommands.HelpCommand;
import de.iv.textchannels.commands.subcommands.MenuCommand;
import de.iv.textchannels.exception.CommandExecuteException;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CommandManager implements CommandExecutor, TabCompleter {

    private static ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(Main plugin) {
        subCommands.add(new HelpCommand());
        subCommands.add(new MenuCommand());

        plugin.getCommand("textchannels").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0) {
            for (SubCommand subCommand : subCommands) {
                if(args[0].equals(subCommand.name()) | subCommand.aliases().contains(args[0])) {
                    if(!(sender instanceof Player p)) {
                        // Sender is not a player
                        if(subCommand instanceof PlayerCommand) {
                            try {
                                throw new CommandExecuteException("Instance of PlayerCommand cannot be executed from a non-player sender!");
                            } catch (CommandExecuteException e) {
                                Main.get().getLogger().log(Level.SEVERE, e.getMessage());
                                return false;
                            }
                        } else if (subCommand instanceof ConsoleCommand) {
                            ((ConsoleCommand) subCommand).executeFromConsole((CommandSender) sender, args);
                            return true;
                        }
                    } else if(subCommand instanceof PlayerCommand) {
                        ((PlayerCommand)subCommand).executeFromPlayer((Player) sender, args);
                        return true;
                    } else {
                        assert subCommand instanceof ConsoleCommand;
                        ((ConsoleCommand)subCommand).executeFromConsole((CommandSender) sender, args);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return subCommands.stream().map(SubCommand::name)
                .filter(s -> s.startsWith(args[0]))
                .collect(Collectors.toList());
    }

    public static ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
