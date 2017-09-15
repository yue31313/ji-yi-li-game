package jb.esperanza;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener 
{

	private int language = 1; // 1 english, 0 chinese
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View startButton = this.findViewById(R.id.start);
		startButton.setOnClickListener(this);
		View exitButton = this.findViewById(R.id.exit);
		exitButton.setOnClickListener(this);
		View demoButton = this.findViewById(R.id.demo);
		demoButton.setOnClickListener(this);
		Switch switchTest = (Switch) findViewById(R.id.language);  
		switchTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
		{  
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
		    {  
			    language = 0;
		        //Toast.makeText(MainActivity.this, isChecked + "",  
		        //Toast.LENGTH_SHORT).show();  
            }  
	    });  


		
		SysApplicationExit.getInstance().addActivity(this);
		/*
		Log.d("TAG","clicked on ");
    	Intent intent = new Intent(MainActivity.this,Game.class);
    	intent.putExtra(Game.KEY_DIFFICULTY,1);
    	startActivity(intent);*/
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
    	    case R.id.start:
    	    	Intent i = new Intent(this, Game.class);
    	    	i.putExtra("level", 1);
    	    	i.putExtra("TotalScore", 0);
    	    	i.putExtra("amount", 1);
    	    	i.putExtra("Error", 0);
    	    	i.putExtra("language", language);
    	    	startActivity(i);
    	    	break;
    	    case R.id.exit: 
    	    	SysApplicationExit.getInstance().exit();  
    	    	break;
    	    case R.id.demo:
    	    	Intent i1 = new Intent(this, Demostration.class);
    	    	i1.putExtra("level", 1);
    	    	i1.putExtra("TotalScore", 0);
    	    	i1.putExtra("amount", 1);
    	    	i1.putExtra("language", language);
    	    	startActivity(i1);
    	    	break;
    	}
	}


}
