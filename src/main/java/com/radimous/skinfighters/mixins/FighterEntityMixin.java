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
    private static final String BOSS_SUMMONED_TAG = "boss_summoned";

    @Unique
    private static final int NAME_VISIBILITY_DELAY_TICKS = 5;

    @Unique
    private final Random skinFighters$random = new Random();

    @Unique
    private int skinFighters$ticksSinceSpawn = -1;

    public FighterEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    public void applySkinOnSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason,
                                 SpawnGroupData spawnData, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        List<? extends String> names = SkinFighters.getNames();
        if (!names.isEmpty() && skinFighters$random.nextInt(100) < Config.SKIN_CHANCE.get()) {
            String name = names.get(skinFighters$random.nextInt(0, names.size()));
            int starCount = 0;
            if (!Config.DISABLE_STARS.get()) {
                starCount = Math.max(ModEntities.VAULT_FIGHTER_TYPES.indexOf(this.getType()), 0);
            }
            MutableComponent customName = new TextComponent("")
                .append(new TextComponent(Strings.repeat("✦", starCount)).withStyle(ChatFormatting.GOLD))
                .append(starCount > 0 ? " " : "")
                .append(new TextComponent(name));
            this.setCustomName(customName);
            skinFighters$ticksSinceSpawn = 0;
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void updateNameVisibility(CallbackInfo ci) {
        if (this.level.isClientSide || skinFighters$ticksSinceSpawn < 0) {
            return;
        }

        if (skinFighters$ticksSinceSpawn < NAME_VISIBILITY_DELAY_TICKS) {
            skinFighters$ticksSinceSpawn++;
            return;
        }

        if (this.getTags().contains(BOSS_SUMMONED_TAG)) {
            skinFighters$ticksSinceSpawn = -1;
            return;
        }

        this.setCustomNameVisible(true);
        skinFighters$ticksSinceSpawn = -1;
    }
}