package mpc;

public class Werewolf extends Monster
{
	
	public Werewolf()
	{
		super(100, 30, 40,2);
	}
	
	@Override
	public void attackPlayer(Player player)
	{
		player.takeDamage(attackPower, this);
		player.poison();
	}


}
