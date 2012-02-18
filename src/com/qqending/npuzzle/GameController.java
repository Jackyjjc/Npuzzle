package com.qqending.npuzzle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.TextView;

public class GameController implements OnClickListener  {
	
	private static final String GAME_SETTING = "GameSetting";
	private static final String BOARD_CONFIG = "boardConfig";
	private static final String LEVEL = "level";
	private static final String NUM_MOVES = "numMoves";
	public static final int EASY = 3;
	public static final int MEDIUM = 4;
	public static final int HARD = 5;
	
	private Board game;
	private SharedPreferences setting;
	private final GamePlay owner;
	private boolean save;
	
	public GameController(GamePlay owner) {
		
		//get the saved settings
		setting = owner.getSharedPreferences(GAME_SETTING, Context.MODE_PRIVATE);
		int difficulty = setting.getInt(LEVEL, MEDIUM);
		
		//create a sample board
		game = new Board(difficulty);
		this.owner = owner;
		save = true;
	}
	
	public void start() {
		
		String configString = setting.getString(BOARD_CONFIG, null);
		int numMoves = setting.getInt(NUM_MOVES, 0);
		int dimension = game.getDimension();
		
		if(configString != null) {
			int[] config = stringToConfig(configString, dimension);
			game = new Board(dimension,config,numMoves);
			owner.createPuzzle(game.getBoardConfig(), dimension, true);
			Toast.makeText(owner, "Game Starts", Toast.LENGTH_SHORT).show();
			
		} else {
			owner.createPuzzle(game.getBoardConfig(), dimension, false);
			
			//show the solution for 3 secs
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					shuffle();
					Toast.makeText(owner, "Game Starts", Toast.LENGTH_SHORT).show();
				}
			}, 3000);
			
		}
	}
	
	public void changeDifficulty(int difficulty) {
		game = new Board(difficulty);
		shuffle();
	}
	
	public void delayedShuffle() {
		shuffle();
	}
	
	public void shuffle() {
		game.shuffle();
		owner.createPuzzle(game.getBoardConfig(), game.getDimension(),true);
		saveGame();
	}
	
	public void changeImage() {
		save = false;
		owner.finish();
	}
	
	public int getNumMoves() {
		return game.getNumMoves();
	}
		
	private String configToString(int[] config) {
		
		String result = "";
		
		for(int i : config) {
			result += String.valueOf(i) + " ";
		}
		
		return result;
	}
	
	private int[] stringToConfig(String s, int dimension) {
				
		int[] result = new int[dimension * dimension];
		int length = result.length;
		
		String[] numbers = s.split("\\s");
		
		for(int i = 0; i < length; i++) {
			result[i] = Integer.parseInt(numbers[i]);
		}
		
		return result;
	}
	
	public void saveGame() {
		
		SharedPreferences setting = owner.getSharedPreferences(GAME_SETTING,Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = setting.edit();
		
		editor.clear();
		
		if(save) {
			editor.putString(BOARD_CONFIG, configToString(game.getBoardConfig()));
			editor.putInt(NUM_MOVES, game.getNumMoves());
		}
		
		editor.putInt(LEVEL, game.getDimension());
		
		editor.commit();
	}
	
	public void onClick(View v) {
		
		int id = v.getId() - GamePlay.BASE_ID;
		
		if(game.isValidMove(id)) {
			game.makeMove(id);
			
			TextView tv = (TextView) owner.findViewById(R.id.numMoves);
			tv.setText(String.valueOf(game.getNumMoves()));
			
			owner.updateLayout(v.getId());
			if(game.isWin()) {
				Intent i = new Intent(owner,YouWin.class);
				i.putExtra("numMoves", game.getNumMoves());
				owner.startActivity(i);
				save = false;
				owner.finish();
			}
		}
		
	}
}