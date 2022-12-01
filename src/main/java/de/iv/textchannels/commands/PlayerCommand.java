package de.iv.textchannels.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class PlayerCommand extends SubCommand {

    public abstract void executeFromPlayer(Player sender, String[] args);

}
