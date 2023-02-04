package xyz.rgnt.smp.mojang;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DummyPlayer extends Player {

    public DummyPlayer(final ServerPlayer player) {
        super(player.getLevel(), player.getLevel().getSharedSpawnPos(), player.getLevel().getSharedSpawnAngle(),
                player.gameProfile,
                player.getProfilePublicKey()
        );
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
