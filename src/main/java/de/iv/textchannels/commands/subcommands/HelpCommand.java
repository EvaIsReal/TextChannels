package de.iv.textchannels.commands.subcommands;

import de.iv.ILib;
import de.iv.textchannels.Main;
import de.iv.textchannels.commands.CommandManager;
import de.iv.textchannels.commands.ConsoleCommand;
import de.iv.textchannels.commands.PlayerCommand;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HelpCommand extends ConsoleCommand {

    @Override
    public String name() {
        return "help";
    }

    @Override
    public List<String> aliases() {
        return List.of(".h");
    }

    @Override
    public String syntax() {
        return "help";
    }

    @Override
    public String description() {
        return "Shows handy things";
    }

    @Override
    public void executeFromConsole(CommandSender sender, String[] args) {
        sender.sendMessage(ILib.color("&6&m&l------------------------------------------------").replace('-', ' '));
        sender.sendMessage("");
        CommandManager.getSubCommands().forEach(subCommand -> {
            sender.sendMessage(ILib.color("&e/textchannels " + subCommand.syntax() + " &8| &6" + subCommand.description()));
            sender.sendMessage("");
        });
        sender.sendMessage(ILib.color("&6&m&l------------------------------------------------").replace('-', ' '));

        if(sender instanceof Player p) {
            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
            Main.get().getServer().getScheduler().runTaskLater(Main.get(), task -> {
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
            }, 3L);
        }
    }
}
