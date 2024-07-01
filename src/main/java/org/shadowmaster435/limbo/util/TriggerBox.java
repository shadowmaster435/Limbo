package org.shadowmaster435.limbo.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.shadowmaster435.limbo.util.callable.Callable;

public class TriggerBox extends Box {

    private boolean entered = false;
    private boolean exited = false;
    private boolean inside = false;
    private boolean toggled = false;
    private Callable on_exit;
    private Callable on_enter;

    public TriggerBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(x1, y1, z1, x2, y2, z2);
    }
    public TriggerBox(Vec3d min, Vec3d max) {
        super(min, max);
    }

    public TriggerBox(BlockPos min, BlockPos max) {
        super(MiscUtil.to_vec3d(min), MiscUtil.to_vec3d(max));
    }

    public TriggerBox(Vec3i min, Vec3i max) {
        super(MiscUtil.to_vec3d(min), MiscUtil.to_vec3d(max));
    }

    public void bind_enter(Object caller, String method_name) {
        on_enter = new Callable(caller, method_name);
    }

    public void bind_enter(Object caller, String method_name, Class<?> args) {
        on_enter = new Callable(caller, method_name, args);
    }

    public void bind_exit(Object caller, String method_name) {

        on_exit = new Callable(caller, method_name);
    }

    public void bind_exit(Object caller, String method_name, Class<?> args) {

        on_exit = new Callable(caller, method_name, args);
    }

    public void update(PlayerEntity entity) {

        if (entity == null) {
            return;
        }
        inside = contains(entity.getPos());
        if (!toggled && inside) {
            toggled = true;
            if (on_enter != null) {
                on_enter.call();
            }
        }
        if (toggled && !inside) {
            toggled = false;
            if (on_exit != null) {
                on_exit.call();
            }
        }
    }

}
