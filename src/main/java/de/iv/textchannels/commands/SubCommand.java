package de.iv.textchannels.commands;

import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public abstract class SubCommand {

    public abstract String name();
    public abstract List<String> aliases();
    public abstract String syntax();
    public abstract String description();



}
