package jb.esperanza;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityEventSource;
import android.widget.Button;

public class DemoView extends View 
{

	private static final String TAG = "sodoku";
	private Demostration demostration;
	protected boolean[] map; 
	private int ScreenWidth,ScreenHeight;
	public int state=0,amount=0,language; 
	private int level = 1;
	protected boolean selection[] = new boolean[(6+level)*(6+level)];
	public boolean fail=false;
    //private final Demostration Demostration;
	//Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.background2); 
    //Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, ScreenWidth,ScreenHeight, true);      
    public DemoView(Context context)
    {      
       super(context);
       Log.d("demoView", level+"");
       this.demostration = (Demostration) context;//强制转换 
       setFocusable(true);
       setFocusableInTouchMode(true);
       //selection = new boolean[(6+level)*(6+level)];
       map = demostration.puzzle;
       //map = Demostration.getPuzzle();
       state = demostration.state;
       language = demostration.language;
       //amount_Set = Demostration.amount;
      
    }
    private float width;
    private float height;
    private int selX;
    private int selY;
    private final Rect selRect = new Rect();
    
    
    protected void onSizeChanged(int w,int h,int oldw,int oldh)
    {
    	ScreenWidth = w;
    	ScreenHeight = h;
    	width = w / (5f*(5+level)/5);// to avoid (5+level)f can not be complemented
    	height = h / (5f*(5+level)/5);
    	getRect(selX,selY,selRect);
    	Log.d(TAG, "onSizeChanged:width "+width+",height "+height);
    	super.onSizeChanged(w, h, oldw, oldh);
    }
    
    // set the selected the rectangle
	private boolean getRect(int x,int y,Rect rect)
	{
		if(x<(3+level) && x>=2 && y<(3+level) && y>=2)
		{
		    rect.set((int)(x*width),(int)(y*height),(int)(x*width+width),(int)(y*height+height));
		    selection[x*(5+level)+y]=true;
		    amount++;
		    return true;
		}
		else
		{
			Log.i("TEST NEXT STEP","X:"+x+"Y:"+y);
			return false;
		}
	}

	
	protected void onDraw(Canvas canvas)
    {
		//draw the background
		
        Log.d("onDraw", "painting");
        // add a button
        Button b=new Button(getContext());
        b.setText("Next Step");
        b.draw(canvas);
        
        Paint background_pic = new Paint();
        background_pic.setAlpha(80);
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.background2); 
        Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, ScreenWidth,ScreenHeight, true);          
        canvas.drawBitmap(mBitmap, 0, 0, background_pic);
                
        Paint title_pic = new Paint();
        title_pic.setAlpha(255);
        Bitmap title_bmp=BitmapFactory.decodeResource(getResources(),R.drawable.title); 
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f); //长和宽放大缩小的比例
        Bitmap title_mbmp = title_bmp.createBitmap(title_bmp,0,0,title_bmp.getWidth(),
        		title_bmp.getHeight(),matrix,true);          
        canvas.drawBitmap(title_mbmp, 0, 0, title_pic);
    	
        
    	 
    }
	
	protected void dispatchDraw(Canvas canvas) 
	{  
	     // TODO Auto-generated method stub  
	     //  
	     //可以在这里先做一些处理，包括对传入的canvas  
	     //  
	     super.dispatchDraw(canvas);         //这里会调用drawChild绘制子视图
	  // write the intructions 
	    	Paint text = new Paint();
	    	text.setColor(getResources().getColor(R.color.puzzle_dark));
	    	text.setTextSize(38);//设置字体大小 
	    	text.setTypeface(Typeface.SANS_SERIF);//设置字体类型
	    	canvas.drawText(Language.getInstraction(language, 1)+0, width/3, 2*height/3, text);
	    	//canvas.drawText("Score: "+demostration.TotalScore, width,2*height/3, text);
	    	
	        //Add the next step button
	    	Bitmap nextStep=BitmapFactory.decodeResource(getResources(),R.drawable.arrow); 
	    	RectF rec_nextstep = new RectF(3*width,(float)(5.2)*height,4*width,(float)(5.7)*height);
	    	canvas.drawBitmap(nextStep, null, rec_nextstep, null);
	    	//canvas.drawText("Remember the tiles that appeared", 3.5*width,5.5*height, text);
	    	
	    	//draw the number/the system randomly picked area 
	    	// define color and style for number with "foreground"
	    	 Bitmap selected_pic=BitmapFactory.decodeResource(getResources(),R.drawable.target); 
	    	 Bitmap normal_pic=BitmapFactory.decodeResource(getResources(),R.drawable.normal); 
	         //Bitmap rect_bmp = Bitmap.createScaledBitmap(rect_pic, ScreenWidth,ScreenHeight, true);   
	    	if(state == 1 || state == 3)
	    	{ 	
	    	    Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
	    	    foreground.setAlpha(20);
	    	    foreground.setShadowLayer(10.0f, 4.0f, 4.0f, 0xFF000000); // add shadow
	    	    foreground.setColor(getResources().getColor(R.color.tile_randompick));
	        	foreground.setStyle(Style.FILL);
	    	    //foreground.setTextSize(height*0.75f);
	        	//foreground.setTextScaleX(width/height);
	        	//foreground.setTextAlign(Paint.Align.CENTER);
	    	
	        	//draw the number in the center of the tile
	         	FontMetrics fm = foreground.getFontMetrics();
	    	
	    	    float x = width/2;
	     	
	    	    float y = height/2 - (fm.ascent + fm.descent)/2;
	    	
	    	
	    	    for(int i=2;i<3+level;i++)
	    	    {
	    		    for(int j=2;j<3+level;j++)
	    		    {
					    if(map[i*(5+level)+j])
					    {
	    			    	//canvas.drawRect(i*width+6f*((5+level))/6,j*height+6f*((5+level))/6,(i+1)*width-10f*((5+level))/6,(j+1)*height-10f*((5+level))/6,foreground);
					    	RectF rectangle = new RectF(i*width+6f*((5+level))/6,j*height+6f*((5+level))/6,(i+1)*width-10f*((5+level))/6,(j+1)*height-10f*((5+level))/6);
					    	canvas.drawRoundRect (rectangle, 6, 6, foreground);
					    	canvas.drawBitmap(selected_pic, null, rectangle, null);
		            		//canvas.drawRoundRect (rectangle, 6, 6, foreground);
					    }
					    else
					    {
					    	RectF rectangle = new RectF(i*width+6f*((5+level))/6,j*height+6f*((5+level))/6,(i+1)*width-10f*((5+level))/6,(j+1)*height-10f*((5+level))/6);
					    	canvas.drawRoundRect (rectangle, 6, 6, foreground);
					    	canvas.drawBitmap(normal_pic, null, rectangle, null);
					    }
	    		    }
	    	    }
	    	}
	    	
	    	if(state == 2 || state == 3)
	    	{
	        	/////draw the selection
	    	
	            Log.d(TAG, "selRect="+selRect);
	        	Paint selected = new Paint();
	        	//selected.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000); // add shadow
	        	selected.setAlpha(255);
	        	selected.setAntiAlias(true);
	    	    selected.setColor(getResources().getColor(R.color.red));
	    	    selected.setStrokeWidth(6f);
	    	    selected.setStyle(Paint.Style.STROKE);
	    	    for(int i = 2;i<3+level;i++)
	               for(int j = 2;j<3+level;j++)
	               {
	            	   if(selection[i*(5+level)+j])
	            	   {
	            		   //canvas.drawRect(i*width,j*height,(i+1)*width,(j+1)*height,selected);
	            		   canvas.drawCircle(width*(2*i+1)/2, height*(2*j+1)/2, width/5, selected);
	            		   /*
	            		   RectF rectangle = new RectF(i*width,j*height,(i+1)*width,(j+1)*height);
	            		   canvas.drawRoundRect (rectangle, 6, 6, selected);
	                       */
	            	   }
	               }
	    	    //canvas.drawRect(selRect,selected);
	    	}
	    	
	    	if(state == 2)
	    	{
	    		canvas.drawText(Language.getInstraction(language, 3), ScreenWidth/4,getHeight()-height, text);
	    	}
	    	
	    	if(state == 1)
	    	{
	    		canvas.drawText(Language.getInstraction(language, 4), ScreenWidth/4,getHeight()-height, text);
	    	}
	    	if(state == 3)
	    	{
	    		canvas.drawText(Language.getInstraction(language, 5), ScreenWidth/4,getHeight()-height, text);
	    	}
	    	//Draw the board  
	    	Paint dark = new Paint();
	    	dark.setColor(getResources().getColor(R.color.puzzle_background));
	    	
	    	Paint hilite = new Paint();
	    	hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
	    	hilite.setStrokeWidth(10f); 
	    	
	    	Paint light = new Paint();
	    	light.setColor(getResources().getColor(R.color.puzzle_light));
	    	light.setStrokeWidth(10f); 

	    	//draw the lines inside the ractangle   	
	    	for(int i=3;i<3+level;i++)
	    	{
	    		canvas.drawLine(2*width,i*height, getWidth()-2*width, i*height, light );
	    		canvas.drawLine(2*width,i*height+1, getWidth()-2*width, i*height+1, hilite );
	    		canvas.drawLine(i*width,2*height, i*width, getHeight()-2*height,light );
	    		canvas.drawLine(i*width+1,2*height, i*width+1, getHeight()-2*height,hilite );
	    	}
	    	
	    	// draw the boundary
	    	Paint background = new Paint();
	    	background.setAntiAlias(true); // 抗锯齿
	        background.setStrokeWidth(10f); // 线条宽度
	        background.setStyle(Paint.Style.STROKE); // 画轮廓
	        background.setColor(Color.YELLOW); // 颜色
	        //background.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000); // add shadow
	        background.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
	    	//background.setColor(getResources().getColor(R.color.puzzle_background));
	    	canvas.drawRect(2*width,2*height,getWidth()-2*width,getHeight()-2*height,background);
	     //  
	     //所有子视图都绘制完成后这里还可以做一些处理，比如绘制阴影什么的  
	     //  
	        
	}  

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d(TAG,"onKeyDown:keycode" + keyCode + "event=" + event);
		switch(keyCode)
		{
		
		    // the keyBoard can not be open
		    //Log.d("Key Listening", "Functioning");
		case 1:
			break;
		default:	
		    return super.onKeyDown(keyCode, event);
		}
		return(true);
	}
	
	//set the selected area
	//use the method in Demostration to calculate the score
	private void select(int x,int y)
	{
		invalidate(selRect);
		Log.i("selection", "current state"+amount);
		Log.i("select", "x: "+x+"y: "+y);
		selX = Math.max(x, 0);
		selY = Math.max(y, 0);
		if(getRect(selX,selY,selRect))
		{
			if(amount == demostration.amount )
			{
				demostration.calculate();
				demostration.setState(3);
			}
		    invalidate(selRect); // will automatically change the selRect on the onDraw()
		}
		else
		{
			if(x==3 && y == 5)
			{
				demostration.setState(state+1);
			}
		}
	}
	
	public void setSelectedTile(int tile)
	{
		 
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		Log.i("TEST", "Work");
		//if(state !=1)
		{
		    if(event.getAction()!=MotionEvent.ACTION_DOWN) 
		    {
			    return super.onTouchEvent(event);
     		}
		    Log.i("TEST1", "Work");
		    select((int)(event.getX()/width),(int)(event.getY()/height));
    		//  Demostration.showKeypadOrError(selX,selY);
		    Log.d(TAG, "onTouchEvent:x "+selX+", y "+selY);
		}
		return true;
	}
	public class View implements Drawable.Callback, KeyEvent.Callback, AccessibilityEventSource   
	{  
	    //...  
	    //Invalidates the specified Drawable.  
	    //默认实现，重新绘制该视图本身  
	    public void invalidateDrawable(Drawable drawable) {  
	        if (verifyDrawable(drawable)) { //是否是同一个Drawable对象，通常为真  
	            final Rect dirty = drawable.getBounds();  
	            //final int scrollX = mScrollX;  
	            //final int scrollY = mScrollY;  
	            //重新请求绘制该View，即重新调用该View的draw()方法  ...  
	            //invalidate(dirty.left + scrollX, dirty.top + scrollY,  
	              //      dirty.right + scrollX, dirty.bottom + scrollY);  
	        }  
	    }  
	    //...  

		public boolean onKeyDown(int arg0, KeyEvent arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onKeyLongPress(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onKeyUp(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			return false;
		}

		public void sendAccessibilityEvent(int arg0) {
			// TODO Auto-generated method stub
			
		}

		public void sendAccessibilityEventUnchecked(AccessibilityEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void scheduleDrawable(Drawable arg0, Runnable arg1, long arg2) {
			// TODO Auto-generated method stub
			
		}

		public void unscheduleDrawable(Drawable arg0, Runnable arg1) {
			// TODO Auto-generated method stub
			
		}
	}  

}
