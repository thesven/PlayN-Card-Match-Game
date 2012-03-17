package com.thesven.matchgame.core;

import static playn.core.PlayN.*;

import java.util.Random;

import com.thesven.matchgame.core.cards.Card;
import com.thesven.matchgame.core.time.SimpleTimer;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.util.Callback;

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
	private GroupLayer layer;

	private Card cards[];
	private Card selectedCardA;
	private Card selectedCardB;
	private SimpleTimer time;


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

		pointer().setListener(this);

	}


	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything here!
	}

	@Override
	public void update(float delta) {
		if(time != null){
			time.update(delta);
		}
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

			int x = (col * CARD_X_SPACE) + CARD_X_OFFSET;
			int y = (row * CARD_Y_SPACE) + CARD_Y_OFFSET;

			Card card = (Card) cards[i];
			card.updateCardPosition(x, y);

		}

	}

	private void checkForCardHit(float x, float y) {

		pointer().setListener(null);

		for(int i = 0; i < MAX_CARDS; i++){

			Card card = (Card) cards[i];
			if(!card.removed && !card.showingFront){

				if(card.checkCardClick(x, y)){
					if(selectedCardA == null){
						log().debug("Setting Card A "+card.spriteIndex);
						selectedCardA = card;
						pointer().setListener(this);
						return;
					} else if (selectedCardA != null && selectedCardB == null){
						log().debug("Setting Card B "+card.spriteIndex);
						selectedCardB = card;
						break;
					}

				}

			}

		}

		if(selectedCardA != null && selectedCardB != null){

			log().debug("you should check for a match now ... starting the timer");

			time = new SimpleTimer(1000, new Callback<String>(){
				@Override
				public void onSuccess(String result) {
					checkForMatch(selectedCardA, selectedCardB);
				}
				@Override
				public void onFailure(Throwable cause) {

				}
			});

			time.start();

		}


	}


	private void checkForMatch(Card cardA, Card cardB) {

		log().debug("Checking for a matching pair");

		if(cardA.spriteIndex == cardB.spriteIndex){
			// you have found a match
			cardA.removeCard(layer);
			cardB.removeCard(layer);
		} else {
			cardA.displayBack();
			cardB.displayBack();
		}

		selectedCardA = null;
		selectedCardB = null;
		time = null;
		pointer().setListener(this);
	}


}
