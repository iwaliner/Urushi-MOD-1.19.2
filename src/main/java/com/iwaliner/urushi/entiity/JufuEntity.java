package com.iwaliner.urushi.entiity;

import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

public class JufuEntity extends ThrowableItemProjectile  {
    private int life;

    public JufuEntity(EntityType<?> entityType, Level level) {
        super(EntityRegister.Jufu.get(), level);
        this.setNoGravity(true);
        this.life=0;
    }
    public JufuEntity(Level level, Player player) {
        super(EntityRegister.Jufu.get(), player,level);
        this.setNoGravity(true);
        this.life=0;

    }

    /**これがないと完全無色透明なエンティティになってしまう。このメソッドを書いた後はClientSetup.classでrenderを登録。*/
    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    private void onHitEntityEvent(LivingEntity entity){
        if(this.getItemRaw().getItem()==ItemAndBlockRegister.freezing_jufu.get()) {

            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 5, 20), this);

            BlockState state=ItemAndBlockRegister.freezing_display.get().defaultBlockState();
            JufuEffectDisplayEntity ice = JufuEffectDisplayEntity.create(level,entity, state,5,0);
            ice.startRiding(entity);
            level.addFreshEntity(ice);


        }else if(this.getItemRaw().getItem()==ItemAndBlockRegister.knockback_jufu.get()) {

            entity.move(MoverType.SELF, new Vec3(this.getDeltaMovement().x*6D,0.2D,this.getDeltaMovement().z*6D));

        }else if(this.getItemRaw().getItem()==ItemAndBlockRegister.jump_jufu.get()) {

            entity.move(MoverType.SELF, entity.getDeltaMovement().add(0D,8D,0D));

        }

    }
    private void onHitBlockEvent(BlockPos pos){
        if(this.getItemRaw().getItem()==ItemAndBlockRegister.liana_jufu.get()) {
            double vx=this.getDeltaMovement().x;
            double vz=this.getDeltaMovement().z;
            BlockState state= Blocks.AZALEA_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT,true);
            Direction facing=null;
            if(Mth.abs((float) vx)>Mth.abs((float) vz)){
                facing = vx>0? Direction.EAST: Direction.WEST;
            }else{
                facing = vz>0? Direction.SOUTH: Direction.NORTH;
            }
            for(int i=-2;i<=2;i++){
                for(int j=0;j<=1;j++){
                    if(level.getBlockState(pos.relative(facing.getClockWise(),i).above(j)).getMaterial()== Material.AIR) {
                        level.setBlockAndUpdate(pos.relative(facing.getClockWise(), i).above(j), state);
                    }
                }
            }
        }else if(this.getItemRaw().getItem()==ItemAndBlockRegister.growing_jufu.get()){
            for(int i=-5;i<=5;i++){
                for(int j=-5;j<=5;j++){
                    for(int k=-5;k<=5;k++){
                        BlockState state=level.getBlockState(pos.offset(i,j,k));
                        if(state.getBlock() instanceof GrassBlock){
                            continue;
                        }
                        if(state.getBlock() instanceof BonemealableBlock){
                            BonemealableBlock bonemealableblock = (BonemealableBlock)state.getBlock();
                            if (bonemealableblock.isValidBonemealTarget(level, pos.offset(i,j,k), state, level.isClientSide)) {
                                if (level instanceof ServerLevel) {
                                  //  if (bonemealableblock.isBonemealSuccess(level, level.random, pos.offset(i, j, k), state)) {
                                    bonemealableblock.performBonemeal((ServerLevel) level, level.random, pos.offset(i, j, k), state);
                                        if (!level.isClientSide) {
                                            level.levelEvent(1505, pos.offset(i, j, k), 0);
                                  //      }
                                    }
                                }
                            }
                            }
                    }
                }
            }
        }else if(this.getItemRaw().getItem()==ItemAndBlockRegister.mountain_creation_jufu.get()){
            for(int i=-5;i<=5;i++){
                for(int j=-5;j<=5;j++) {
                    for (int k = -5; k <= 5; k++) {
                        BlockPos offsetPos=pos.offset(i,j,k);
                        float distance=Mth.sqrt(Mth.square(pos.getX()-offsetPos.getX())+Mth.square(pos.getY()-offsetPos.getY())+Mth.square(pos.getZ()-offsetPos.getZ()));
                        if(distance<5f&&level.getBlockState(offsetPos.above(3)).getMaterial()==Material.AIR){
                          FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, offsetPos.above(3), Blocks.GRASS_BLOCK.defaultBlockState());
                            fallingblockentity.dropItem=false;
                        }
                    }
                }
            }
        }else if(this.getItemRaw().getItem()==ItemAndBlockRegister.fluid_erasion_jufu.get()){
          loop:  for(int j=-5;j<=50;j++) {
                for(int i=-5;i<=5;i++){
                    for (int k = -5; k <= 5; k++) {
                        BlockPos offsetPos=pos.offset(i,j,k);
                     /*   if(j>5&&!level.getFluidState(pos.above(j+1)).isSource()){
                            level.setBlock(offsetPos,Blocks.AIR.defaultBlockState(),18);
                            break loop;
                        }*/
                        if(!level.getFluidState(offsetPos).isEmpty()) {
                            if (level.getBlockState(offsetPos).getBlock() instanceof SimpleWaterloggedBlock && level.getBlockState(offsetPos).getValue(BlockStateProperties.WATERLOGGED)) {
                                level.setBlock(offsetPos, level.getBlockState(offsetPos).setValue(BlockStateProperties.WATERLOGGED, false), 18);

                            } else {
                                level.setBlock(offsetPos, Blocks.AIR.defaultBlockState(), 18);
                            }
                        }
                    }
                }
            }
        }else if(!this.level.isClientSide){
            this.level.broadcastEntityEvent(this, (byte)102);
            this.discard();
            this.markHurt();
            this.spawnAtLocation(this.getItemRaw());

        }
    }
    public void handleEntityEvent(byte b) {
        if (b == 101) {
            if(this.getItemRaw().getItem()==ItemAndBlockRegister.knockback_jufu.get()||this.getItemRaw().getItem()==ItemAndBlockRegister.jump_jufu.get()) {
                for (int i = 0; i < 8; ++i) {
                    this.level.playSound((Player)null, this, SoundEvents.GENERIC_EXPLODE, SoundSource.MUSIC, 1.0F, 1.0F);
                    this.level.addParticle(ParticleTypes.EXPLOSION, this.getX() - 0.5D + 0.1D * this.random.nextInt(11), this.getY() - 0.5D + 0.1D * this.random.nextInt(11), this.getZ() - 0.5D + 0.1D * this.random.nextInt(11), 0.0D, 0.0D, 0.0D);
                }
            }else if(this.getItemRaw().getItem()==ItemAndBlockRegister.freezing_jufu.get()) {
                for (int i = 0; i < 20; ++i) {
                    this.level.playSound((Player)null, this, SoundEvents.GENERIC_EXPLODE, SoundSource.MUSIC, 1.0F, 1.0F);
                    this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX() - 0.5D + 0.1D * this.random.nextInt(11), this.getY() - 0.5D + 0.1D * this.random.nextInt(11), this.getZ() - 0.5D + 0.1D * this.random.nextInt(11), 0.0D, 0.0D, 0.0D);
                }
            }
        }else if(b==102){
                for (int i = 0; i < 8; ++i) {
                    ParticleOptions particleoption = new ItemParticleOption(ParticleTypes.ITEM, this.getItemRaw());
                    this.level.addParticle(particleoption, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                }

        }

    }
    protected void onHit(HitResult result) {
        super.onHit(result);

        ParticleOptions particleoption = new ItemParticleOption(ParticleTypes.ITEM, this.getItemRaw());

        // same function with for(int i = 0; i < 8; ++i) {
        // when i is not used inside the loop, using decrement loop will be faster than ++i
        // speed: i--(--i) faster than ++i faster than i++, this relates to binary code compile process
        // https://stackoverflow.com/questions/16476125/why-does-decrement-loop-runs-faster-than-increment-loop
        for(int i = 8; i > 0; --i) {
            this.level.addParticle(particleoption, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

      //  if (!this.isRemoved()) {
            AABB axisalignedbb =this.getBoundingBox() .inflate(1.2D, 1.2D, 1.2D);
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, axisalignedbb);
            boolean flag=false;
            if(!list.isEmpty()) {
                for (LivingEntity entity : list) {
                    if(entity instanceof Player){
                        continue;
                    }else {
                       this.onHitEntityEvent(entity);
                        if(!this.level.isClientSide) {
                            this.level.broadcastEntityEvent(this, (byte) 101);
                        }
                       flag=true;

                    }
                }
            }
            if(!flag){
                this.onHitBlockEvent(this.blockPosition());


            }
            this.discard();


    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);


        /*    BlockPos offsetPos = result.getBlockPos();
            BlockState state = level.getBlockState(offsetPos);
            BlockState blockstate8 = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, Direction.DOWN).setValue(MovingPistonBlock.TYPE, PistonType.DEFAULT);
            level.setBlockAndUpdate(offsetPos, Blocks.AIR.defaultBlockState());
            level.setBlock(offsetPos.above(), blockstate8, 68);
            level.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(offsetPos.above(), blockstate8, state, Direction.DOWN, false, false));
        */
        
    }


    @Override
    public void tick() {
        super.tick();
        ++this.life;
        if(this.isInFluidType()){
            this.setNoGravity(false);
        }

        if(this.life>200){
            if(!this.level.isClientSide){
                this.level.broadcastEntityEvent(this, (byte)102);
                this.discard();
                this.markHurt();
                this.spawnAtLocation(this.getItemRaw());
            }
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemAndBlockRegister.raw_urushi_ball.get();
    }
}
