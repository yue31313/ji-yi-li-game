package jb.esperanza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Result extends Activity implements OnClickListener 
{

    protected int score,level,TotalScore,amount,error,language;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		View startButton = this.findViewById(R.id.result_start);
		startButton.setOnClickListener(this);
		View exitButton = this.findViewById(R.id.result_exit);
		exitButton.setOnClickListener(this);
		
		Intent i = getIntent();
	   	String result = i.getStringExtra("result");
	   	score = i.getIntExtra("score", 0);
	   	level = i.getIntExtra("level", 1);
	   	TotalScore = i.getIntExtra("TotalScore", 0);
	   	amount = i.getIntExtra("amount", 0);
	   	error = i.getIntExtra("Error", 0);
	   	language = i.getIntExtra("language", 0);
	   	//if(result=="success")
	   	if(result.compareTo("success")==0)
	   	{
	   		setAmountLevel();
	   		Log.i("Score_Result", "Working");
	   	}
	   	EditText editText = (EditText) this.findViewById(R.id.editText);
	   	
	   	if(language == 1)
	   	{
	   	    editText.setText(result+"  Your score: "+score);
	   	}
	   	else
	   	{
	   		if(result.compareTo("success")==0)
	   		{
	   		    editText.setText("成功！  得分: "+score);
	   		}
	   		else
	   		{
	   			editText.setText("失败！  得分: "+score);
	   		}
	   	}
	   	SysApplicationExit.getInstance().addActivity(this);
	   	editText.setInputType(InputType.TYPE_NULL);
 
		/*
		Log.d("TAG","clicked on ");
    	Intent intent = new Intent(MainActivity.this,Game.class);
    	intent.putExtra(Game.KEY_DIFFICULTY,1);
    	startActivity(intent);*/
	}

	private void setAmountLevel() 
	{
		// TODO Auto-generated method stub
		double temp = level*Math.log10(level)/Math.log10(2);
		amount ++;
		if(amount>temp)
			level++;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) 
	{  
		// TODO Auto-generated method stub
		switch (v.getId())
    	{
    	    case R.id.result_start:
    	    	if(error>3)
    	    	{
    	    		Intent j = new Intent(this, MainActivity.class);
        	    	startActivity(j);
    	    	}
    	    	else
    	    	{
    	    	    Intent i = new Intent(this, Game.class);  
    	    	    i.putExtra("level",level);
    	    	    i.putExtra("TotalScore", TotalScore+score);
    	    	    i.putExtra("amount", amount);
    	    	    startActivity(i);
    	    	}
    	    	break;
    	    case R.id.result_exit:
    	    	Intent j = new Intent(this, MainActivity.class);
    	    	startActivity(j);
    	    	break;

    	}
	}


}
