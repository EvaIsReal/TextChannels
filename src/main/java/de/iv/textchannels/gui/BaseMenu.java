package de.iv.textchannels.gui;

import de.iv.ILib;
import de.iv.iutils.exceptions.ManagerSetupException;
import de.iv.iutils.exceptions.MenuManagerException;
import de.iv.iutils.items.ItemBuilder;
import de.iv.iutils.menus.InventoryMapper;
import de.iv.iutils.menus.Menu;
import de.iv.iutils.menus.MenuManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.ChatPaginator;

public class BaseMenu extends Menu {

    public BaseMenu(InventoryMapper mapper) {
        super(mapper);
    }

    @Override
    public String getMenuName() {
        return ILib.color("&eMenu");
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {
        try {
            switch (inventoryClickEvent.getCurrentItem().getType()) {
                case BARRIER -> inventoryClickEvent.getWhoClicked().closeInventory();
                case COMPARATOR -> {
                    MenuManager.openMenu(ChangeMenu.class, (Player) inventoryClickEvent.getWhoClicked());
                }
                case PLAYER_HEAD -> {
                    MenuManager.openMenu(BrowserMenu.class, p);
                }
            }
        } catch (MenuManagerException | ManagerSetupException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMenuItems() {

        inventory.setItem(11, new ItemBuilder(Material.COMPARATOR).setName(ILib.color("&eChange Text-Channel")).build());
        inventory.setItem(15, new ItemBuilder(Material.FILLED_MAP).setName(ILib.color("&eCurrent Text-Channel")).build());
        inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName(ILib.color("&4close")).build());
        inventory.setItem(35, new ItemBuilder(Material.PLAYER_HEAD).setName(ILib.color("&aBrowse Text-Channels"))
                .setSkullData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTFiMzE4OGZkNDQ5MDJmNzI2MDJiZDdjMjE0MWY1YTcwNjczYTQxMWFkYjNkODE4NjJjNjllNTM2MTY2YiJ9fX0=")
                .setLore(ChatPaginator.wordWrap(ILib.color("&7Browse all available Text-Channels"), 25))
                .addToPDC("menu_item", "TC_BROWSER")
                .build());
        setFillerGlass(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
