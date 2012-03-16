package com.thesven.matchgame.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import com.thesven.matchgame.core.cards.Card;

public class MatchGame implements Game {
  
  private final static int WIDTH = 640;
  private final static int HEIGHT = 480;
  
	
  private ImageLayer bgLayer;
  private GroupLayer layer;
  
  private Card testCard;
  
  
  @Override
  public void init() {
    
	//set the graphics display size
	graphics().setSize(WIDTH, HEIGHT);
	  
	// create and add background image layer
    Image bgImage = assets().getImage("images/bg.jpg");
    bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);
    
    //create the layer that will hold the cards
    layer = graphics().createGroupLayer();
    graphics().rootLayer().add(layer);
    
    testCard = new Card(layer, 200, 200);
    
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public int updateRate() {
    return 25;
  }
}
