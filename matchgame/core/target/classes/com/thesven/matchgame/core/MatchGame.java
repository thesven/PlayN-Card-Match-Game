package com.thesven.matchgame.core;

import static playn.core.PlayN.*;

import com.thesven.matchgame.core.cards.Card;
import com.thesven.matchgame.core.game.MatchGameCore;
import com.thesven.matchgame.core.game.MatchGameCoreCallback;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Pointer.Event;

public class MatchGame implements Game, Pointer.Listener {

	private final static int WIDTH = 640;
	private final static int HEIGHT = 480;

	private final static int MAX_CARDS = 32;
	private final static int MAX_COLUMNS = 8;
	private final static int CARD_X_SPACE = 70;
	private final static int CARD_Y_SPACE = 101;
	private final static int CARD_X_OFFSET = 40;
	private final static int CARD_Y_OFFSET = 38;

	private ImageLayer bgLayer;
	private GroupLayer gameLayer;
	
	private MatchGameCore game;


	@Override
	public void init() {

		//set the graphics display size
		graphics().setSize(WIDTH, HEIGHT);

		//preload any needed assets
		assets().getImage(Card.IMAGE);
		assets().getImage("images/bg.jpg");

		// create and add background image layer
		Image bgImage = assets().getImage("images/bg.jpg");
		bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);

		//create the layer that will hold the cards
		gameLayer = graphics().createGroupLayer();
		graphics().rootLayer().add(gameLayer);
		
		game = new MatchGameCore(gameLayer, MAX_CARDS, MAX_COLUMNS, CARD_X_SPACE, CARD_Y_SPACE, CARD_X_OFFSET, CARD_Y_OFFSET, new MatchGameCoreCallback() {
			
			@Override
			public void onSuccess(String result) {
				
			}
			
			@Override
			public void onFailure(Throwable cause) {
				
			}
			
			@Override
			public void onStartMatchSearch() {
				pointer().setListener(null);
			}
			
			@Override
			public void onMatch() {
				
			}
			
			@Override
			public void onEndMatchSearch() {
				setPointerActive();
			}
		});
		game.init();

		pointer().setListener(this);

	}


	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything here!
	}

	@Override
	public void update(float delta) {
		
		if(game != null) game.update(delta);
		
	}

	@Override
	public int updateRate() {
		return 333;
	}

	@Override
	public void onPointerStart(Event event) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onPointerEnd(Event event) {
		
		//if the game is not null check to see if a card has been hit
		if(game != null){
			game.checkForCardHit(event.x(), event.y());	
		}
		
	}


	@Override
	public void onPointerDrag(Event event) {
		// TODO Auto-generated method stub

	}
	
	private void setPointerActive(){
		pointer().setListener(this);
	}

}
