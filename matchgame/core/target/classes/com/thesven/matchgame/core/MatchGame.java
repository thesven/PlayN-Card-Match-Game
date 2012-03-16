package com.thesven.matchgame.core;

import static playn.core.PlayN.*;

import java.util.Random;
import java.util.Timer;

import com.thesven.matchgame.core.cards.Card;

import playn.core.Analytics;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Analytics.Category;
import playn.core.Pointer.Event;

public class MatchGame implements Game, Pointer.Listener {
  
  private final static int WIDTH = 640;
  private final static int HEIGHT = 480;
  
  private final static int MAX_CARDS = 32;
  private final static int MAX_COLUMNS = 8;
  private final static int CARD_X_SPACE = 70;
  private final static int CARD_Y_SPACE = 101;
	
  private ImageLayer bgLayer;
  private GroupLayer layer;
  
  private Card cards[];
  private Card selectedCardA;
  private Card selectedCardB;
  
  private Timer checkTimer;
  
  
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
    
    checkTimer = new Timer();
    
    createCards();
    
    pointer().setListener(this);
    
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
  
  @Override
  public void onPointerStart(Event event) {
  	// TODO Auto-generated method stub
  	
  }


  @Override
  public void onPointerEnd(Event event) {
  	
    //analytics().logEvent(new Analytics.Category(1, ""), "");
  	checkForCardHit(event.x(), event.y());
  	
    
  }


@Override
  public void onPointerDrag(Event event) {
  	// TODO Auto-generated method stub
  	
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
	
	//shuffle the cards
	Random rgen = new Random();
	for(int j = 0; j < MAX_CARDS; j++){
	  int randomPosition = rgen.nextInt(MAX_CARDS);
	  Card temp = cards[j];
	  cards[j] = cards[randomPosition];
	  cards[randomPosition] = temp;
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
  
  private void checkForCardHit(float x, float y) {
	// TODO Auto-generated method stub
	for(int i = 0; i < MAX_CARDS; i++){
		
		Card card = (Card) cards[i];
		if(card.checkCardClick(x, y)){
			
			if(selectedCardA == null){
				analytics().logEvent(new Analytics.Category(1, "setting card"), "setting card a");
				selectedCardA = card;
				return;
			} else if (selectedCardA != null && selectedCardB == null){
				analytics().logEvent(new Analytics.Category(1, "setting card"), "setting card b");
				selectedCardB = card;
			}
			
			if(selectedCardA != null && selectedCardB != null){
				log().debug("checking yo");
				checkForMatch(selectedCardA, selectedCardB);
			}
			
		}
		
	}
  }


  private void checkForMatch(Card cardA, Card cardB) {
	 
	  analytics().logEvent(new Analytics.Category(1, "checking for match"), "checking to see if a match exists");
	  
	  if(cardA.spriteIndex == cardB.spriteIndex){
		  // you have found a match
	  } else {
		  cardA.displayBack();
		  cardB.displayBack();
	  }
	  
	  selectedCardA = null;
	  selectedCardB = null;
	
  }
  
  
}
