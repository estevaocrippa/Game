package br.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;

import br.games.screen.AbstractScreen;
import br.games.screen.CreditsScreen;
import br.games.screen.GameScreen;
import br.games.screen.InstructionsScreen;
import br.games.screen.StartScreen;

public class MyGame extends Game {
	private AbstractScreen currentScreen;
	public static AssetManager assetManager;
	private static int max_pontuacao;
	
	@Override
	public void create () {
		assetManager  = new AssetManager();
		assetManager.load("Kirby/Kirby.g3db", Model.class);
		assetManager.load("Pipe/Pipe.g3db", Model.class);
		assetManager.load("Kirby/Sleeping/Kirby_Sleeping.g3db", Model.class);
		assetManager.load("Cenario/cenario.g3db", Model.class);
		currentScreen = new StartScreen("START");
		setScreen(currentScreen);
		max_pontuacao = 0;
	}

	@Override
	public void render () {
		currentScreen.render(Gdx.graphics.getDeltaTime());
		if (currentScreen.isDone()) {
			if (currentScreen.getId().equals("START")) {
				currentScreen = new InstructionsScreen("INSTRUCTIONS");
			}
			else if(currentScreen.getId().equals("INSTRUCTIONS")){
				currentScreen = new GameScreen("GAME");
			}
			else if(currentScreen.getId().equals("GAME")) {
				currentScreen = new CreditsScreen("CREDITS");
			}
			else if(currentScreen.getId().equals("CREDITS")) {
				if(CreditsScreen.restart_pronto) {
					currentScreen = new GameScreen("GAME");
				}
			}
			else {
				currentScreen = new StartScreen("START");
			}
		}
	}
	
	public static void setMaxPont(int p) {
		max_pontuacao = p;
	}
	public static int getMaxPont() {
		return max_pontuacao;
	}

}
