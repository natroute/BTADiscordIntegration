package undrscre.bta_discord_integration.mixin.server;

import com.mojang.brigadier.arguments.ArgumentTypeString;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.commands.CommandSay;
import net.minecraft.server.net.command.ConsoleCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import undrscre.bta_discord_integration.server.DiscordChatRelay;

@Mixin(value = CommandSay.class, remap = false)
public class Mixin_CommandSay {
    @Inject(
            method = "lambda$register$0",
            at = @At("RETURN")
    )
    private static void processSayCommand(CommandContext<CommandSource> c, CallbackInfoReturnable<Integer> cir) {
        CommandSource source = c.getSource();
        String name = source instanceof ConsoleCommandSource ? "<console>" : source.getName();
        String message = ArgumentTypeString.getString(c, "message");
        DiscordChatRelay.sendToDiscord(name, message);
    }
}
