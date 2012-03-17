package com.thesven.matchgame.core.cards;

import static playn.core.PlayN.*;

import playn.core.GroupLayer;
import playn.core.ResourceCallback;
import playn.core.Sound;

import com.thesven.matchgame.core.sprites.Sprite;
import com.thesven.matchgame.core.sprites.SpriteLoader;

public class Card {
  public static String IMAGE = "images/CardDeck.png";
  public static String JSON = "sprite-data/card.json";
  public static String FLIP_SOUND = "sound/page-flip-2";
  
  public static int SPRITE_BACK = 52;
  
  public  int spriteIndex;
  
  private Sprite sprite;
  private int xLoc;
  private int yLoc;
  private Sound flipSound;
  private boolean hasLoaded = false;
  
  public boolean removed = false;
  public boolean showingFront = false;

  public Card(final GroupLayer displayLayer, final float x, final float y) {

    sprite = SpriteLoader.getSprite(IMAGE, JSON);
    flipSound = assets().getSound(FLIP_SOUND);
    
    sprite.addCallback(new ResourceCallback<Sprite>() {
      @Override
      public void done(Sprite sprite) {
        sprite.setSprite(SPRITE_BACK);
        displayLayer.add(sprite.layer());
        hasLoaded = true;
      }

      @Override
      public void error(Throwable err) {
        log().error("Error loading image!", err);
      }
    });
    
  }
  
  public void setSpriteIndex(int index){
	  spriteIndex = index;
  }
  
  public void displayFront(){
	  showingFront = true;
	  flipSound.play();
	  sprite.setSprite(spriteIndex);
  }
  
  public void displayBack(){
	  showingFront = false;
	  flipSound.play();
	  sprite.setSprite(SPRITE_BACK);
  }
  
  public void removeCard(final GroupLayer displayLayer) {
	  displayLayer.remove(sprite.layer());
	  removed = true;
  }
  
  public boolean checkCardClick(float x, float y){
	  
	  float curX = xLoc;
	  float curY = yLoc;
	  float curWidth = sprite.layer().width();
	  float curHeight = sprite.layer().height();
	  
	  //check to see if is within x
	  if(x >= curX && x <= (curX + curWidth)){
		  //check to see if is within y
		  if(y >= curY && y <= (curY + curHeight)){
			  displayFront();
			  return true;
		  }
	  }
	  
	  return false;
  }
  
  public void updateCardPosition(int x, int y){
	  xLoc = x;
	  yLoc = y;
	  sprite.layer().setTranslation(x, y);
  }

  public void update(float delta) {
    
  }
  
}
