package com.thesven.matchgame.core.time;

import static playn.core.PlayN.*;

import playn.core.util.Callback;

public class Time {
  
  public boolean running = false;
	
  private double startTime;
  private double elapseTime;
  private double endTime;
  private Callback<String> timerCallback;
	
  public Time(double waitTime, Callback<String> callback) {
	 
    log().debug("creating a timer");
	elapseTime = waitTime;
	timerCallback = callback;
  
  }
  
  public void start(){
	 
    log().debug("starting the timer");
	startTime = currentTime();
	endTime = startTime + elapseTime;
	running = true;
	  
  }

  public void update(float delta) {
    
    if(running){
      if(currentTime() >= endTime){
	    running = false;
		timerCallback.onSuccess("Time has elapsed");
		log().debug("Time has elapsed");
      }
	}
	  
  }
  
}
