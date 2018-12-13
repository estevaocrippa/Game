package br.games.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import br.games.model.GamePad;
import br.games.util.Commands;
import br.games.util.Utils;

public class CreditsScreen extends AbstractScreen implements InputProcessor {
	private Texture currenttexture;
	private SpriteBatch spriteBatch;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private BitmapFont font;
	private BitmapFont num_font;
	private Music   music;
	private float time;
	private boolean visible;
	public GamePad gamepad;
	public static boolean restart_pronto;

	public CreditsScreen(String id) {
		super(id);
		spriteBatch = new SpriteBatch();
		currenttexture = new Texture(Gdx.files.internal("CreditsScreen.jpg"));
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		font = new BitmapFont(Gdx.files.internal("fonts/grease.fnt"));
		font.setColor(Color.YELLOW);
		num_font = new BitmapFont(Gdx.files.internal("fonts/grease.fnt"));
		num_font.setColor(Color.RED);
		music = Gdx.audio.newMusic(Gdx.files.internal("Music/music3.wav"));
		music.setLooping(false);
		music.play();
		visible = false;
		time=0;
		restart_pronto = false;
		Commands.comandos[Commands.RESTARTING] = false;
		
		gamepad = new GamePad();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		spriteBatch.dispose();
		currenttexture.dispose();
		music.dispose();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if(Commands.comandos[Commands.RESTARTING]) {
			restart_pronto = true;
			music.stop();
			setDone(true);
		}
		if(Commands.comandos[Commands.SAIR]) {
			System.exit(0);
		}
		time += delta;
		if (time >= 0.5f) {
			visible = !visible;
			time=0;
		}
	}

	@Override
	public void draw(float delta) {
		
		// TODO Auto-generated method stub
		// tornando meu plano cartesiano canônico 2D
		viewMatrix.setToOrtho2D(0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		// spritebatch, siga esse padrão de plano cartesiano
		spriteBatch.setProjectionMatrix(viewMatrix);
		// qualquer transformação linear (escala, rotação), ficara armazenada na
		// tranMatrix
		spriteBatch.setTransformMatrix(tranMatrix);
		spriteBatch.begin();
		spriteBatch.draw(currenttexture, 0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT, 0, 0, currenttexture.getWidth(),
				currenttexture.getHeight(), false, false);
		font.draw(spriteBatch, "SCORE:" + GameScreen.pontuacao, 120, 400);
//		if(GameScreen.pontuacao > MyGame.getMaxPont()) {
//			if(visible) {
//				num_font.draw(spriteBatch, "new record!", 250, 300);
//			}
//			MyGame.setMaxPont(GameScreen.pontuacao);
//		}
		if(Commands.comandos[Commands.RESTARTING]) {
			spriteBatch.draw(gamepad.botoes[GamePad.BTN_JOGAR_NOV_PRESSIONADO].getImagem(), gamepad.botoes[GamePad.BTN_JOGAR_NOV_PRESSIONADO].getX(), gamepad.botoes[GamePad.BTN_JOGAR_NOV_PRESSIONADO].getY());
		}
		else {
			spriteBatch.draw(gamepad.botoes[GamePad.BTN_JOGAR_NOV_SOLTO].getImagem(), gamepad.botoes[GamePad.BTN_JOGAR_NOV_SOLTO].getX(), gamepad.botoes[GamePad.BTN_JOGAR_NOV_SOLTO].getY());
		}
		if(Commands.comandos[Commands.SAIR]) {
			spriteBatch.draw(gamepad.botoes[GamePad.BTN_SAIR_PRESSIONADO].getImagem(), gamepad.botoes[GamePad.BTN_SAIR_PRESSIONADO].getX(), gamepad.botoes[GamePad.BTN_SAIR_PRESSIONADO].getY());
		}
		else {
			spriteBatch.draw(gamepad.botoes[GamePad.BTN_SAIR_SOLTO].getImagem(), gamepad.botoes[GamePad.BTN_SAIR_SOLTO].getX(), gamepad.botoes[GamePad.BTN_SAIR_SOLTO].getY());
		}

		spriteBatch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if (gamepad.botoes[GamePad.BTN_JOGAR_NOV_SOLTO].isTouch(Utils.convertCoordinates(screenX, screenY))) {
        	Commands.comandos[Commands.RESTARTING] = true;
        	return true;
        }
		if (gamepad.botoes[GamePad.BTN_SAIR_SOLTO].isTouch(Utils.convertCoordinates(screenX, screenY))) {
        	Commands.comandos[Commands.SAIR] = true;
        	return true;
        }
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if (gamepad.botoes[GamePad.BTN_JOGAR_NOV_SOLTO].isTouch(Utils.convertCoordinates(screenX, screenY))) {
        	Commands.comandos[Commands.RESTARTING] = false;
        	return true;
        }
		if (gamepad.botoes[GamePad.BTN_SAIR_SOLTO].isTouch(Utils.convertCoordinates(screenX, screenY))) {
        	Commands.comandos[Commands.SAIR] = false;
        	return true;
        }
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
