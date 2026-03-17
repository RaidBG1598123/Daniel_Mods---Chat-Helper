package me.daniel.raiddev.chat.mixins;
import me.daniel.raiddev.chat.config.WhiteListConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(DrawContext.class)
public abstract class ChatHudMixin {
    @Inject(method = "drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;IIIZ)I", at = @At("HEAD"))
    private void draw(TextRenderer renderer, OrderedText text, int x, int y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        var client = MinecraftClient.getInstance();
        if (x != 4 || (client.currentScreen != null && y > client.getWindow().getScaledHeight() - 25)) return;
        var sb = new StringBuilder();
        text.accept((i, s, cp) -> {
            sb.append(Character.toChars(cp));
            return true;
        });
        String msg = sb.toString().toLowerCase();
        for (String w : WhiteListConfig.getWords()) {
            if (!w.isEmpty() && msg.contains(w.toLowerCase().trim())) {
                ((DrawContext)(Object)this).fill(0, y - 1, 328, y + 8, 0x66FF0000);
                break;
            }
        }
    }
}