package com.radimous.skinfighters.mixins;

import com.google.common.base.Strings;
import com.radimous.skinfighters.Config;
import com.radimous.skinfighters.SkinFighters;
import iskallia.vault.entity.entity.FighterEntity;
import iskallia.vault.init.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(value = FighterEntity.class)
public abstract class FighterEntityMixin extends Entity {
    @Unique
    private final Random skinFighters$random = new Random();

    public FighterEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "finalizeSpawn", at = @At(value = "RETURN", target = "Liskallia/vault/entity/entity/FighterEntity;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/entity/SpawnGroupData;"))

    public void customName(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        List<? extends String> names = SkinFighters.getNames();
        if (!names.isEmpty() && skinFighters$random.nextInt(100) < Config.SKIN_CHANCE.get()) {
            String name = names.get(skinFighters$random.nextInt(0, names.size()));
            String star = String.valueOf('✦');
            int count = 0;

            if (!Config.DISABLE_STARS.get())
                count = Math.max(ModEntities.VAULT_FIGHTER_TYPES.indexOf(this.getType()), 0);

            MutableComponent customName = new TextComponent("")
                    .append(new TextComponent(Strings.repeat(star, count)).withStyle(ChatFormatting.GOLD))
                    .append(count > 0 ? " " : "")
                    .append(new TextComponent(name));

            this.setCustomName(customName);

            // Minimal server-side fix: sync nameplate visibility to *all* clients
            this.setCustomNameVisible(true);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Liskallia/vault/util/SkinProfile;updateSkin(Ljava/lang/String;)V"))
    public void makeNametagsVisible(CallbackInfo ci) {
        this.setCustomNameVisible(true);
    }
}
