package src;


public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    private int aResult;
    private int aDiceValue, aNbRoll, aModifier;

    public Roll(String formula) {
        this.aResult = 0;
        if(!formula.contains("d") || (formula.contains("+") && formula.contains("-")))
        {
            System.out.println("Formule incorrecte !");
            this.aResult = -1;
            return;
        }
        int index = 1;
        if(formula.indexOf("d") == 0)
        {
            this.aNbRoll = 1;
        }
        String[] parts1 = formula.split("d");
        String[] parts2 = parts1[index].split("\\+");
        String[] parts2b = parts1[index].split("-");


        int diceValue, nbRoll, modifier;

        if(parts1.length > 2 || parts2.length > 2 || parts2b.length > 2)
        {
            System.out.println("Trop de d ou de + ou de - dans la formule");
            this.aResult = -1;
            return;
        }
        try
        {
            if(this.aNbRoll != 1)
                this.aNbRoll = Integer.parseInt(parts1[0]);

            if(parts2.length > 1)
            {
                this.aModifier = Integer.parseInt(parts2[1]);
                this.aDiceValue = Integer.parseInt(parts2[0]);
            }
            if(parts2b.length > 1)
            {
                this.aModifier = Integer.parseInt(parts2b[1]) * -1;
                this.aDiceValue = Integer.parseInt(parts2b[0]);
            }
            else
            {
                this.aDiceValue = Integer.parseInt(parts2[0]);
            }

        }
        catch(NumberFormatException e)
        {
            System.out.println("Nombre invalide");

            this.aResult = -1;
            return;
        }
        if(this.aNbRoll <= 0 || this.aDiceValue <= 0)
        {
            this.aResult = -1;
            return;
        }
        Dice vDice = new Dice(aDiceValue);
        for(int i =0;i<this.aNbRoll;i++)
        {
            this.aResult += vDice.rollDice();

        }
        this.aResult += this.aModifier;
        if(this.aResult < 0)
            this.aResult = 0;
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.aResult = 0;
        if(diceValue <= 0 || nbRoll <= 0)
        {
            this.aResult = -1;
            return;
        }
        this.aDiceValue = diceValue;
        this.aNbRoll = nbRoll;
        this.aModifier = modifier;
        Dice vDice = new Dice(aDiceValue);
        for(int i =0;i<nbRoll;i++)
        {
            aResult += vDice.rollDice();
        }
        this.aResult += this.aModifier;
        if(this.aResult < 0)
            this.aResult = 0;
    }

    public int makeRoll(RollType rollType) {
        if(aResult < 0)
        {
            return -1;
        }
        if(rollType == RollType.NORMAL)
            return this.aResult;
        else
        {
            int vResult = this.aResult;
            Roll vRoll = new Roll(aDiceValue,aNbRoll,aModifier);
            if(rollType == RollType.ADVANTAGE)
            {
                if(vRoll.getResult() > this.aResult)
                    return vRoll.getResult();
                else
                    return this.aResult;
            }
            else if(rollType == RollType.DISADVANTAGE)
            {
                if(vRoll.getResult() < this.aResult)
                    return vRoll.getResult();
                else
                    return this.aResult;
            }
            else
            {
                return -1;
            }
        }
    }

    public int getResult()
    {
        return this.aResult;
    }

}
