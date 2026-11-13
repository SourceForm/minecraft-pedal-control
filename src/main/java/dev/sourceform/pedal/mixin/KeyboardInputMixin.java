package dev.sourceform.pedal.mixin;

import dev.sourceform.pedal.PedalControl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends ClientInput {
    @Inject(method="tick", at=@At("TAIL"))
    private void pedalcontrol$tick(CallbackInfo ci) {
        if (Minecraft.getInstance().gui.screen() != null) return;
        boolean[] pedals = PedalControl.read();
        Input old = keyPresses;
        boolean left = old.left() || pedals[0], right = old.right() || pedals[1];
        keyPresses = new Input(old.forward(), old.backward(), left, right, old.jump(), old.shift(), old.sprint());
        float x = (left ? 1 : 0) - (right ? 1 : 0);
        float y = (old.forward() ? 1 : 0) - (old.backward() ? 1 : 0);
        moveVector = new Vec2(x, y).normalized();
    }
}
