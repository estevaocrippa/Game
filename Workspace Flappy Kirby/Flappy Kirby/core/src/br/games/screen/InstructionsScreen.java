package br.games.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import br.games.MyGame;
import br.games.model.GamePad;
import br.games.util.Commands;
import br.games.util.Utils;

public class InstructionsScreen extends AbstractScreen implements InputProcessor{
	private Texture currenttexture;
	private Texture botao;
	private Texture direcao_botao;
	private SpriteBatch spriteBatch;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private BitmapFont title_font;
	private BitmapFont font;
	private Music   music;
	public GamePad gamepad;
	private boolean inicio_pronto;

	public InstructionsScreen(String id) {
		super(id);
		spriteBatch = new SpriteBatch();
		currenttexture = new Texture(Gdx.files.internal("InstructionsScreen.jpg"));
		botao = new Texture(Gdx.files.internal("botao_aleatorio.jpg"));
		direcao_botao = new Texture(Gdx.files.internal("direcao_botao.jpg"));
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		font = new BitmapFont(Gdx.files.internal("fonts/best_prices.fnt"));
		font.setColor(Color.YELLOW);
		title_font = new BitmapFont(Gdx.files.internal("fonts/grease.fnt"));
		title_font.setColor(Color.RED);
		music = StartScreen.music;
		
		gamepad = new GamePad();
		Gdx.input.setInputProcessor(this);
		
		inicio_pronto = false;
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
		if(Commands.comandos[Commands.STARTING]) {
			inicio_pronto = true;
			setDone(true);
			music.stop();
		}
		MyGame.assetManager.update();
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
		spriteBatch.draw(botao, 500, 50, 0, 0, botao.getWidth(), botao.getHeight());
		spriteBatch.draw(direcao_botao, 220, 35, 0, 0, direcao_botao.getWidth(), direcao_botao.getHeight());
		if(Commands.comandos[Commands.STARTING]) {
			spriteBatch.draw(gamepad.botoes[GamePad.BTN_INICIAR_PRESSIONADO].getImagem(), gamepad.botoes[GamePad.BTN_INICIAR_PRESSIONADO].getX(), gamepad.botoes[GamePad.BTN_INICIAR_PRESSIONADO].getY());
		}
		else {
			spriteBatch.draw(gamepad.botoes[GamePad.BTN_INICIAR_SOLTO].getImagem(), gamepad.botoes[GamePad.BTN_INICIAR_SOLTO].getX(), gamepad.botoes[GamePad.BTN_INICIAR_SOLTO].getY());
		}

		spriteBatch.end();
	}
	
	public boolean vaiIniciar() {
		return this.inicio_pronto;
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
		if (gamepad.botoes[GamePad.BTN_INICIAR_SOLTO].isTouch(Utils.convertCoordinates(screenX, screenY))) {
        	Commands.comandos[Commands.STARTING] = true;
        	return true;
        }
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if (gamepad.botoes[GamePad.BTN_INICIAR_SOLTO].isTouch(Utils.convertCoordinates(screenX, screenY))) {
        	Commands.comandos[Commands.STARTING] = false;
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
