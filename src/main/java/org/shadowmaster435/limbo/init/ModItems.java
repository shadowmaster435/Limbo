package org.shadowmaster435.limbo.init;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;

public class ModItems {
    public static final Item TENEBRA_TWIG = new Item(new Item.Settings());
    public static final Item ILLUMINUM = new Item(new Item.Settings());

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, "tenebra_twig"), TENEBRA_TWIG);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, "illuminum"), ILLUMINUM);

    }
}
