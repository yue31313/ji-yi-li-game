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
				return "�ɾͣ� ";
			case 2:
			    return "�÷֣� ";
			case 3:
				return "ѡ���������Ƶ�ͼ�Ρ�";
			case 4:
			    return "��ס�������Ƶ�ͼ�ε�λ�á�";
			case 5:
				return "�����һ��ͼ�꿪ʼ��Ϸ��";
			}
		}
		return "";
	}

}
