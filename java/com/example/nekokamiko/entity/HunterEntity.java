package com.example.nekokamiko.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class HunterEntity extends MonsterEntity {
    private int attackTimer;

    public HunterEntity(EntityType<? extends HunterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.ATTACK_SPEED, 6);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0F));
    }

    public void tick() {
        super.tick();
        PlayerEntity target = this.level.getNearestPlayer(this, 30.0);
        if (target != null) {
            if (this.canSee(target)) {
                // 視界にプレイヤーがいる場合は追跡する
                this.getNavigation().moveTo(target, 1.0);
                if (this.attackTimer <= 0 && this.distanceTo(target) < 1.0) {
                    // プレイヤーに攻撃する
                    this.attackTimer = 20;
                    this.swing(Hand.MAIN_HAND);
                    this.doHurtTarget(target);
                }
            } else {
                // 見えなくなった場合は追跡をやめる
                if (!this.getNavigation().isInProgress()) {
                    double targetX = this.getX() + this.getRandom().nextDouble() * 10 - 5;
                    double targetZ = this.getZ() + this.getRandom().nextDouble() * 10 - 5;
                    this.getNavigation().moveTo(targetX, this.getY(), targetZ, 0.3);
                }
            }
        } else {
            // プレイヤーがいない場合はランダムな位置に向かって歩行する
            if (!this.getNavigation().isInProgress()) {
                double targetX = this.getX() + this.getRandom().nextDouble() * 10 - 5;
                double targetZ = this.getZ() + this.getRandom().nextDouble() * 10 - 5;
                this.getNavigation().moveTo(targetX, this.getY(), targetZ, 1.0);
            }
        }
        if (this.attackTimer > 0) {
            this.attackTimer--;
        }
    }
    // プレイヤーを攻撃する
    private void doHurtTarget(PlayerEntity player) {
        player.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }


    @Override
    public void handleEntityEvent(byte event) {
        if (event == 4) {
            this.attackTimer = 10;
        } else {
            super.handleEntityEvent(event);
        }
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }


}