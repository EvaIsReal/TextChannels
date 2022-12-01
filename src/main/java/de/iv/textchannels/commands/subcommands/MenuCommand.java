package de.iv.textchannels.commands.subcommands;

import de.iv.iutils.exceptions.ManagerSetupException;
import de.iv.iutils.exceptions.MenuManagerException;
import de.iv.iutils.menus.MenuManager;
import de.iv.textchannels.Main;
import de.iv.textchannels.commands.PlayerCommand;
import de.iv.textchannels.gui.BaseMenu;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class MenuCommand extends PlayerCommand {

    @Override
    public void executeFromPlayer(Player p, String[] args) {
        try {
            MenuManager.openMenu(BaseMenu.class, p);
        } catch (MenuManagerException | ManagerSetupException e) {
            e.printStackTrace();
        }

        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), task -> {
            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
        }, 3L);
    }

    @Override
    public String name() {
        return "menu";
    }

    @Override
    public List<String> aliases() {
        return List.of(".m");
    }

    @Override
    public String syntax() {
        return "menu";
    }

    @Override
    public String description() {
        return "Opens a control-gui";
    }
}
