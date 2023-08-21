package dev.aprilvee.xiaoheic.cultivation.cultivatemethods;

import dev.aprilvee.xiaoheic.capability.SpiritProvider;
import dev.aprilvee.xiaoheic.client.gui.AbstractCultivationScreen;
import dev.aprilvee.xiaoheic.client.gui.BreathingCScreen;
import dev.aprilvee.xiaoheic.cultivation.Cultivation;
import dev.aprilvee.xiaoheic.cultivation.EnvironmentQi;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SpriteBreathing implements ICultivateMethod {
	float[] enviroqi;
	int qirange = 5;
	float score = 1;
	float finalscore = 0;
	int lifetime = 0;

	@Override
	public void startCultivation(Player player) {
		enviroqi = EnvironmentQi.getQi(EnvironmentQi.getEnviroQi(player.blockPosition(), player.level(), qirange), qirange);
	}

	@Override
	public void stopCultivation(Player player) {
		player.getCapability(SpiritProvider.SPIRITCAP).ifPresent(sp -> {sp.currentcultivation = new EmptyMethod();});
		score = Math.min(0,score);
		//process score
		finalscore = (float) (score * (Math.pow(enviroqi[0] + enviroqi[1],0.8f)/4 + 1));
		Cultivation.addCXP(player, 50);
		player.sendSystemMessage(Component.literal("cultivated!!!!"));
	}

	@Override
	public void minigameTick(Player player) {
		if(lifetime<400){
			//do minigame tick
		}else{
		stopCultivation(player);}
		lifetime++;
	}

	@Override
	public AbstractCultivationScreen createScreen() {
		return new BreathingCScreen();
	}

	@Override
	public void receiveInput(KeyMapping key) {

	}

	@Override
	public void succeed(Player player) {
		this.addScore(1);
	}

	@Override
	public void fail(Player player) {
		this.addScore(-1);
	}

	@Override
	public void win(Player player) {

	}

	@Override
	public void lose(Player player) {

	}

	@Override
	public float getScore() {
		return score;
	}

	@Override
	public void addScore(float add) {
		score += add;
	}

	@Override
	public ICultivateMethod create() {
		return new SpriteBreathing();
	}

	@Override
	public int getIndex() {
		return 1;
	}
}
