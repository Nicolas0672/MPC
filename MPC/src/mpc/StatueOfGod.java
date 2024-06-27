package mpc;

public class StatueOfGod extends Monster {

	private boolean isCritical;
	
	public StatueOfGod () 
	{
		super(300, 100, 80, 4);
		this.isCritical = false;
	}
	
	public boolean checkForCritical()
	{
		if(Math.random() < 0.2)
		{
			isCritical = true;
			applyCriticalHit();
		}
		return isCritical;
	}
	
	public void applyCriticalHit()
	{
		if(isCritical)
		{
			attackPower *= 1.5;
		}
	}
	
	public void resetAttackPower(int baseAttack)
	{
		if(isCritical)
		{
			attackPower = baseAttack;
		}
	}
	
	@Override
	public void attackPlayer(Player player)
	{
		checkForCritical();
		int baseAttack = attackPower;
		player.takeDamage(attackPower, this);
		resetAttackPower(baseAttack);
	}
	
	public boolean isDefeat()
	{
		return health <= 0;
	}
	
	

}
