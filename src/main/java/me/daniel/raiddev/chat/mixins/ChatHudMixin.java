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
    @Inject(
            method = "drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;IIIZ)I",
            at = @At("HEAD")
    )
    private void drawFullBackground(TextRenderer textRenderer, OrderedText text, int x, int y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen != null) {
            if (y > client.getWindow().getScaledHeight() - 25) return;
        }
        StringBuilder sb = new StringBuilder();
        text.accept((index, style, codePoint) -> {
            sb.append(Character.toChars(codePoint));
            return true;
        });
        String content = sb.toString().toLowerCase();
        if (content.isEmpty()) return;
        for (String word : WhiteListConfig.getWords()) {
            String target = word.toLowerCase().trim();
            if (!target.isEmpty() && content.contains(target)) {
                ((DrawContext)(Object)this).fill(0, y - 1, 328, y + 8, 0x88FF0000);
                break;
            }
        }
    }
}
