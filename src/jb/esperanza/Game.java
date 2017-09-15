package jb.esperanza;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Game extends Activity 
{

    private static final String TAG = "sodoku.game";
	private Timer animationTimer;
	protected static int level,TotalScore,language;
	protected static int score=0;
	private boolean fail=true;  
    public boolean puzzle[] = new boolean[9 * 9];// decide whether the rectangle is chosen or not   
    public int state,timing;
    /* state the current state of the game
     *  state 1: the system pick rectangle and user memorize time
     *  stage 2: system block all the evidence and let user pick 
     *  state 3: system exam the user performence and show the correct answer
     */   
    public int amount;
    // set the total amount rectangle that system choose 
    // will be upgrated to the "level" or "difficulty"
    private PuzzleView puzzleView;
    private int error;
    
    protected void onCreate(Bundle savedInstaneceState)
    {
    	super.onCreate(savedInstaneceState);
    	Log.d(TAG,"onCreate");
    	Intent i = getIntent();
    	level = i.getIntExtra("level", 1);
    	//score = i.getIntExtra("score", 0);
    	TotalScore = i.getIntExtra("TotalScore", 0);
    	amount = i.getIntExtra("amount", 0);
    	error = i.getIntExtra("Error", 0);
    	language = i.getIntExtra("language", 0);
    	
    	setPuzzle(); 
       	puzzleView = new PuzzleView(this);
    	setContentView(puzzleView);
    	puzzleView.requestFocus();
    	setState(1);
    	declare();
    	
    	SysApplicationExit.getInstance().addActivity(this);
    }


	//declare the timer to let it recall in every second
    private void declare() 
    {
		// TODO Auto-generated method stub
    	timing = 0;
    	animationTimer = new Timer (); 
	    animationTimer.schedule(task,3000,300);//(int)1000/level);//setSnake(), 1000);//(snake,1000);//(setSnake(), 1000);
    
	}
  
    public void setState(int stage)
    // set the current result
    {
		// TODO Auto-generated method stub
		puzzleView.state = stage;
		if(stage == 3)
		{
			showResult();
		}
	}

	/*
	//private MediaPlayer music;
	protected void onResume() 
	{
	    Log.d("Music", "Working");	
    	super.onResume();
     	Music.play(this,R.raw.music);
	}
	*/
	
    private void showResult() 
    {
		// TODO Auto-generated method stub
		//boolean result = this.fail;
		Intent i = new Intent(this, Result.class);
		if(!fail)
		{
			i.putExtra("result", "success");
			Log.i("Result_final", "success");
		}
		else
		{
			i.putExtra("result", "fail");
			error++;
		}
		i.putExtra("Error", error);
	    i.putExtra("score", score);
	    i.putExtra("level", level);
	    i.putExtra("TotalScore", TotalScore);
	    i.putExtra("amount", amount);
	    i.putExtra("language", language);
	    startActivity(i);
	}
/*
	protected boolean check(int x,int y)
    {  
		score++;
    	return !puzzle[x*(5+level)+y];
    }
  */  
	protected void calculate() 
	{
		// TODO Auto-generated method stub
		boolean[] temp = puzzleView.selection; 
		int sum=0;
		for(int i=2;i<3+level;i++)
		{
			for(int j=2;j<3+level;j++)
			{
				if(temp[i*(5+level)+j])
					if(puzzle[i*(5+level)+j])
					{
					   Log.i("Score", "Working");	
					   sum++;
					}
			}
		}
		Log.i("Score", ""+sum);
		if(sum == amount)
		{
			fail = false;
			Log.i("result", "success");
		}
		else
		{
			fail = true;
			Log.i("Result", "fail");
		}
		score = sum;
		//sum=sum/2;
	}

	private void setPuzzle() 
	//system will randomly pick the rectangles 
	{
		// TODO Auto-generated method stub
		puzzle = new boolean[(5+level) * (5+level)]; // new array are setted as false 
		//amount = level;
		int i = 0; 
		//puzzle[4*(5+level)+5]=true;
		Random r = new Random();
		while(i<amount)
		{
	    	int k = Math.abs((r.nextInt()%(level+1)))+2;
	    	int t = Math.abs((r.nextInt()%(level+1)))+2;
	    	if(!puzzle[k*(5+level)+t])
	    	{
	    	   puzzle[k*(5+level)+t] = true;		
	    	   i++;
	    	}
	    	Log.i("puzzle map x y", ""+k+t);
	    	Log.d("puzzle map x y", ""+k+t);
		}
		Log.i("puzzle map", "Working");
		
	}

	private static final String PREF_PUZZLE = "puzzle" ;
	protected static final int DIFFICULTY_CONTINUE = -1;
	/*
	//@Override
	protected void onPause() 
	{
		Music.stop(this);
    	super.onPause();
     	
    	
    	getPreferences(MODE_PRIVATE).edit().putString(PREF_PUZZLE,toPuzzleString(puzzle)).commit();
	}
	
	protected void onStop()
	{	
		Music.stop(this);
		super.onStop();
		
	}
	*/	
	private void setTile(int x,int y,int value)
	{
	    puzzle[y*9+x] = true;	
	}
	
	private final int used[][][] = new int[9][9][];
	public int[] getusedTiles(int x, int y) 
	{
		// TODO Auto-generated method stub
		return used[x][y];
	}
	
	// funciton: just used to implement the timer
	Handler handler = new Handler()
	{   
	   public void handleMessage(Message msg) 
	   {   
		   if(timing == 5)
			   setState(2);
		   timing ++;
	 	   Log.d("timer", "working"+timing);
	   	   puzzleView.invalidate();     
	   }  
    };

		
    TimerTask task = new TimerTask()
    {   
    	public void run() 
    	{   
       	    //Log.d("timer", "working");
    	    Message message = new Message();   
    	    message.what = 1;    
    	    handler.sendMessage(message);
	    	      
    	}   
    };
	
}
