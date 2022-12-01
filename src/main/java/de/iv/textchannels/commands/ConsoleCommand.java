package de.iv.textchannels.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleCommand extends SubCommand{

    public abstract void executeFromConsole(CommandSender sender, String[] args);
}
