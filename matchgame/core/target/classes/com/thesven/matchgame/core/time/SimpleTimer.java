package com.thesven.matchgame.core.time;

import static playn.core.PlayN.*;

import playn.core.util.Callback;

public class SimpleTimer {
  
  public boolean running = false;
	
  private double startTime;
  private double elapseTime;
  private double endTime;
  private Callback<String> timerCallback;
	
  public SimpleTimer(double waitTime, Callback<String> callback) {
	 
	elapseTime = waitTime;
	timerCallback = callback;
  
  }
  
  public void start(){
	 
	startTime = currentTime();
	endTime = startTime + elapseTime;
	running = true;
	  
  }

  public void update(float delta) {
    
    if(running){
      if(currentTime() >= endTime){
	    running = false;
		timerCallback.onSuccess("Time has elapsed");
      }
	}
	  
  }
  
}
