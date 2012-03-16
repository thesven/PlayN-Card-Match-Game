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
  
  private final static int MAX_CARDS = 32;
  private final static int MAX_COLUMNS = 8;
  private final static int CARD_X_SPACE = 70;
  private final static int CARD_Y_SPACE = 101;
	
  private ImageLayer bgLayer;
  private GroupLayer layer;
  
  private Card cards[];
  
  
  
  @Override
  public void init() {
    
	//set the graphics display size
	graphics().setSize(WIDTH, HEIGHT);
	
	//preload any needed assets
	assets().getImage(Card.IMAGE);
	  
	// create and add background image layer
    Image bgImage = assets().getImage("images/bg.jpg");
    bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);
    
    //create the layer that will hold the cards
    layer = graphics().createGroupLayer();
    graphics().rootLayer().add(layer);
    
    createCards();
    
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
  
  private void createCards(){
	
	cards = new Card[MAX_CARDS];
	int cardIndex = 0;
	for(int i = 0; i < MAX_CARDS; i++){
		
	  Card newcard = new Card(layer, 0, 0);
	  Card newcard2 = new Card(layer, 0, 0);
	  
	  cards[i] = newcard;
	  i++;
	  cards[i] = newcard2;
	  
	  newcard.setSpriteIndex(cardIndex);
	  newcard2.setSpriteIndex(cardIndex);
	  
	  cardIndex++;
	  
	}
	
	layoutCards();
	  
  }
  
  private void layoutCards(){
	  
	  for(int i = 0; i < MAX_CARDS; i++){
		  
		  int row = (int) Math.floor(i / MAX_COLUMNS);
		  int col = i % MAX_COLUMNS;
		  
		  int x = col * CARD_X_SPACE;
		  int y = row * CARD_Y_SPACE;
		  
		  Card card = (Card) cards[i];
		  card.updateCardPosition(x, y);
		  
	  }
	  
  }
  
  
}
