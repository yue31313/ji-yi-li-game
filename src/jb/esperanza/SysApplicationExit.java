package jb.esperanza;

import java.util.LinkedList;   
import java.util.List;   
import android.app.Activity;   
import android.app.Application;   
/** 
 * һ���� �����������к�̨activity 
 * @author Administrator 
 * 
 */  
public class SysApplicationExit extends Application {  
    //����list��������ÿһ��activity�ǹؼ�  
    private List<Activity> mList = new LinkedList<Activity>();  
    //Ϊ��ʵ��ÿ��ʹ�ø���ʱ�������µĶ���������ľ�̬����  
    private static SysApplicationExit instance;   
    //���췽��  
    private SysApplicationExit()
    {
    	
    }  
    //ʵ����һ��  
    public synchronized static SysApplicationExit getInstance()
    {   
        if (null == instance) 
        {   
            instance = new SysApplicationExit();   
        }   
        return instance;   
    }   
    // add Activity    
    public void addActivity(Activity activity) 
    {   
        mList.add(activity);   
    }   
    //�ر�ÿһ��list�ڵ�activity  
    public void exit() 
    {   
        try 
        {   
            for (Activity activity:mList) 
            {   
                if (activity != null)   
                    activity.finish();   
            }   
        } 
        catch (Exception e) 
        {   
            e.printStackTrace();   
        } 
        finally 
        {   
            System.exit(0);   
        }   
    }   
    //ɱ����  
    public void onLowMemory() 
    {   
        super.onLowMemory();       
        System.gc();   
    }    
}  

