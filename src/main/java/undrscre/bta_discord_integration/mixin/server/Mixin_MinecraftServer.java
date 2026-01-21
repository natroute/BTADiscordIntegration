package undrscre.bta_discord_integration.mixin.server;

import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Shadow;
import undrscre.bta_discord_integration.server.DiscordChatRelay;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftServer.class, remap = false)
public class Mixin_MinecraftServer {

    @Shadow
    public static Logger LOGGER;

    @Inject(
            method = "initiateShutdown",
            at = @At("HEAD")
    )
    public void sendStopMessage(CallbackInfo ci) {
        try {
            DiscordChatRelay.updateMemberCount(false, 0);
            DiscordChatRelay.sendServerStoppedMessage();
            undrscre.bta_discord_integration.mod.shutdownScheduler();
        } catch (Exception e) {
            LOGGER.error("Exception while sending stop message", e);
        }
    }
}
