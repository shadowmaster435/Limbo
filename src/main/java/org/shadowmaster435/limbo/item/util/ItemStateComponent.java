package org.shadowmaster435.limbo.item.util.item_state;

import net.minecraft.client.item.TooltipData;

import java.util.HashMap;

public class ItemStateComponent implements TooltipData {
    private HashMap<String, Object> values;
    public static final ItemStateComponent DEFAULT = new ItemStateComponent();

    public ItemStateComponent() {
        this.values = new HashMap<>();
    }
    public ItemStateComponent(HashMap<String, Object> values) {
        this.values = values;
    }

    public HashMap<String, Object> get_values() {
        return values;
    }

    public <T> void set(String state_name, T value) {
        values.put(state_name, value);
    }

    public <T> T get(String state_name, T fallback_value) {
        return (T) values.getOrDefault(state_name, fallback_value);
    }

    public <T> T get(String state_name) {
        return (T) values.getOrDefault(state_name, null);
    }

    public static class Builder {
        private HashMap<String, Object> result = new HashMap<>();

        public Builder() {}

        public Builder(HashMap<String, Object> previous) {
            this.result = previous;
        }

        public static Builder start_with(ItemStateComponent component) {
            return new Builder(component.values);
        }

        public <T> Builder add(String name, T default_value) {
            this.result.put(name, default_value);
            return new Builder(this.result);
        }

        public ItemStateComponent build() {
            return new ItemStateComponent(result);
        }
    }

}
