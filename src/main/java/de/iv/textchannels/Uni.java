package de.iv.textchannels;

import de.iv.ILib;
import de.iv.iutils.files.FileManager;
import de.iv.textchannels.io.IOManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Uni {

    static FileConfiguration cfg = IOManager.getConfig("settings.yml").getCfg();

    public static final String PREFIX = ILib.color(Objects.requireNonNull(cfg.getString("plugin_prefix")));

    public static <T> boolean listHas(List<T> list, Predicate<? super T> searchPredicate) {
        for (T object : list) {
            if(searchPredicate.test(object)) {
                return true;
            }
        }
        return false;
    }

}
