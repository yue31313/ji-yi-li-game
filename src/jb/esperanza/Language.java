package jb.esperanza;

public class Language 
{

	public static String getInstraction(int language,int sentence)
	{
		if(language == 1)
		{
			switch(sentence)
			{
			case 1:
				return "level: ";
			case 2:
				return "Score: ";
			case 3:
				return "Now click the tiles that are different.";
			case 4:
				return "Remember the position of tiles that are different";
			case 5:
				return "Now click the next step to start the game";
			}
		}
		else
		{
			switch(sentence)
			{
			case 1:
				return "成就： ";
			case 2:
			    return "得分： ";
			case 3:
				return "选择少数花纹的图形。";
			case 4:
			    return "记住少数花纹的图形的位置。";
			case 5:
				return "点击下一步图标开始游戏。";
			}
		}
		return "";
	}

}
