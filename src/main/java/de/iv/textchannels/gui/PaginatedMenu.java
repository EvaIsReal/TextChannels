package de.iv.textchannels.gui;

import de.iv.ILib;
import de.iv.iutils.items.ItemBuilder;
import de.iv.iutils.menus.InventoryMapper;
import de.iv.iutils.menus.Menu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatedMenu extends Menu {

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    public void setMaxItemsPerPage(int maxItemsPerPage) {
        this.maxItemsPerPage = maxItemsPerPage;
    }


    protected int page = 0;

    protected int maxItemsPerPage = 28;

    protected int index = 0;

    public PaginatedMenu(InventoryMapper mapper) {
        super(mapper);
    }

    public void setBorderItems() {
        ItemStack left, right, close;

        right = new ItemBuilder(Material.DARK_OAK_BUTTON).setName(ILib.color("&anext")).addToPDC("menu_item", "BROWSER_NEXT").build();
        left = new ItemBuilder(Material.DARK_OAK_BUTTON).setName(ILib.color("&aprevious")).addToPDC("menu_item", "BROWSER_PREV").build();
        close = new ItemBuilder(Material.BARRIER).setName(ILib.color("&4close")).addToPDC("menu_item", "BROWSER-CLOSE").build();

        inventory.setItem(48, left);
        inventory.setItem(49, close);
        inventory.setItem(50, right);

        for(int i = 0; i < 10; i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
        inventory.setItem(17, super.FILLER_GLASS);
        inventory.setItem(18, super.FILLER_GLASS);
        inventory.setItem(26, super.FILLER_GLASS);
        inventory.setItem(27, super.FILLER_GLASS);
        inventory.setItem(35, super.FILLER_GLASS);
        inventory.setItem(36, super.FILLER_GLASS);

        for(int i = 44; i < 54; i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }


    }

}