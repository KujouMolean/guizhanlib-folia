package net.guizhanss.guizhanlib.minecraft.helper.enchantments;

import lombok.experimental.UtilityClass;
import net.guizhanss.guizhanlib.minecraft.LanguageHelper;
import net.guizhanss.guizhanlib.utils.StringUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.enchantments.Enchantment;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * 附魔({@link Enchantment})名称与等级
 *
 * @author ybw0014
 */
@UtilityClass
public class EnchantmentHelper {
    /**
     * 返回附魔({@link Enchantment})的中文名称与附魔等级
     *
     * @param enchantment {@link Enchantment} 附魔
     * @param level 附魔等级
     * @return 附魔的中文名称与等级
     */
    public static @Nonnull String getEnchantmentDisplayName(@Nonnull Enchantment enchantment, int level) {
        Validate.notNull(enchantment, "附魔不能为空");

        String name = getEnchantmentName(enchantment);
        String enchLevel = getEnchantmentLevelName(level);
        return name + (enchLevel.length() > 0 ? " " + enchLevel : "");
    }

    /**
     * 返回附魔({@link Enchantment})的中文名称与附魔等级
     *
     * @param entry {@code Map.Entry<Enchantment, Integer>} 附魔与附魔等级
     * @return 附魔名称以及等级
     */
    public static @Nonnull String getEnchantmentDisplayName(@Nonnull Map.Entry<Enchantment, Integer> entry) {
        return getEnchantmentDisplayName(entry.getKey(), entry.getValue());
    }

    /**
     * 返回附魔({@link Enchantment})的中文名称
     *
     * @param enchantment {@link Enchantment} 附魔
     * @return 附魔的中文名称,如果获取失败则返回键名
     */
    public static @Nonnull String getEnchantmentName(@Nonnull Enchantment enchantment) {
        return getEnchantmentName(enchantment, true);
    }

    /**
     * 返回附魔({@link Enchantment})的中文名称。
     * 如果获取失败则根据设置返回键名或英文
     *
     * @param enchantment {@link Enchantment} 附魔
     * @param returnKey 是否返回键名
     *
     * @return 附魔的中文名称
     */
    public static @Nonnull String getEnchantmentName(@Nonnull Enchantment enchantment, boolean returnKey) {
        String enchantKey = getEnchantmentKey(enchantment);
        String enchant = LanguageHelper.getLangOrNull(enchantKey);

        if (enchant == null) {
            if (returnKey ) {
                enchant = enchantKey;
            } else {
                enchant = StringUtil.humanize(enchantment.getKey().getKey());
            }
        }

        return enchant;
    }

    /**
     * 获取附魔({@link Enchantment})的键名
     *
     * @param enchantment {@link Enchantment} 附魔
     *
     * @return 附魔的键名
     */
    public static @Nonnull String getEnchantmentKey(@Nonnull Enchantment enchantment) {
        Validate.notNull(enchantment, "附魔不能为空");
        return "enchantment.minecraft." + enchantment.getKey().getKey();
    }

    /**
     * 返回附魔等级的名称
     *
     * @param level 附魔等级
     *
     * @return 附魔等级的名称
     */
    public static @Nonnull String getEnchantmentLevelName(int level) {
        return LanguageHelper.getLangOrKey(getEnchantmentLevelKey(level));
    }

    /**
     * 获取附魔等级的键名
     *
     * @param level 附魔等级
     *
     * @return 附魔等级的键名
     */
    public static @Nonnull String getEnchantmentLevelKey(int level) {
        return "enchantment.level." + level;
    }
}
