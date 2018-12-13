package br.games.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import br.games.MyGame;
import br.games.util.Utils;

public class StartScreen extends AbstractScreen {
	private Texture currenttexture;
	private int currentSprite;
	private float speed = 0;
	private float max_speed = 0.1f;
	private SpriteBatch spriteBatch;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private BitmapFont title_font;
	private BitmapFont font;
	private boolean loaded = false;
	private int progress;
	private float time=0;
	private boolean visible=false;
	public static Music   music;

	public StartScreen(String id) {
		super(id);
		spriteBatch = new SpriteBatch();
		currenttexture = new Texture(Gdx.files.internal("StartScreen1.jpg"));
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		currentSprite = 0;
		title_font = new BitmapFont(Gdx.files.internal("fonts/grease.fnt"));
		title_font.setColor(Color.RED);
		font = new BitmapFont(Gdx.files.internal("fonts/best_prices.fnt"));
		font.setColor(Color.YELLOW);
		progress = 0;
		music = Gdx.audio.newMusic(Gdx.files.internal("Music/Lost.mp3"));
		music.setLooping(true);
		music.play();
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
		if (loaded) {
			if (Gdx.input.justTouched()) {
				setDone(true);
			}
		}
		speed += Gdx.graphics.getDeltaTime();
		if (speed >= max_speed) {
			currentSprite = (currentSprite + 1) % 4;
			speed = 0;
		}
		progress = (int)(MyGame.assetManager.getProgress()*100);
		MyGame.assetManager.update();
		if (progress == 100) {
			loaded = true;
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
		title_font.draw(spriteBatch, "FLAPPY KIRBY", 70, 550);
		if (!loaded)
			font.draw(spriteBatch, "LOADING... "+progress+"%", 180, 100);
		else {
			if (visible) {
				font.draw(spriteBatch,"TOUCH TO START!", 110,100);
			}
		}

		spriteBatch.end();
	}
}
