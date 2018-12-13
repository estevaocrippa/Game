package br.games.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Matrix4;

import br.games.model.Cenario;
import br.games.model.GameObject;
import br.games.model.GamePad;
import br.games.model.Kirby;
import br.games.model.Pipe;
import br.games.util.ChasingCamera;
import br.games.util.Commands;
import br.games.util.Utils;

public class GameScreen extends AbstractScreen implements InputProcessor{
	private ChasingCamera camera;
	private Kirby kirby;
	private Pipe pipe;
	private Cenario cenario;
	private Environment environment;
	private ModelBatch modelBatch;
	private SpriteBatch spriteBatch;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private BitmapFont font;
	private boolean trombou;
	public static int pontuacao;
	private Music   music;
	public GamePad gamepad;
	private int num_trombos;

	public GameScreen(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		kirby = new Kirby();
		cenario = new Cenario();
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		
		camera = new ChasingCamera(67.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 10, 10);
		camera.near = 0.01f;
		camera.far = 1000f;
		camera.setOffsetZIn(80);
		camera.setOffsetZOut(80);
		
		camera.setObjectToFollow(kirby.getCurrent());
		
		spriteBatch = new SpriteBatch();
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		font = new BitmapFont(Gdx.files.internal("fonts/coaster.fnt"));

		font.setColor(Color.YELLOW);
		
		pipe = new Pipe();
		
		kirby.setYInicial(pipe.getY(0 + pipe.getNumCanos()) + 30);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("Music/Dreams.mp3"));
		music.setLooping(true);
		music.play();
		
		Commands.comandos[Commands.JUMPING] = false;
		Commands.comandos[Commands.SLEEPING] = false;
		
		gamepad = new GamePad();
		Gdx.input.setInputProcessor(this);
		pontuacao = 0;
		num_trombos = 0;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		spriteBatch.dispose();
		modelBatch.dispose();
		music.dispose();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if(Commands.comandos[Commands.JUMPING] && !Commands.comandos[Commands.SLEEPING]) {
			kirby.pular();
		}
		if(!Commands.comandos[Commands.JUMPING] && !Commands.comandos[Commands.SLEEPING]) { 
			kirby.parar();
		}
		kirby.update(delta);
		pipe.update(delta);
		
//		checkColisions();
		trombou = kirby.Colide(pipe.getObjectPosition(), pipe.getNumCanos());
		if(trombou) {
			num_trombos++;
			music.stop();
			camera.setObjectToFollow(null);
			kirby.dormir();
			Commands.comandos[Commands.SLEEPING] = true;
			kirby.update(delta);
		}
		if(num_trombos < 1) {
			pontuacao += 1;
		}
		if(kirby.getFinalizar()) {
			setDone(true);
		}
	}

	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub
		viewMatrix.setToOrtho2D(0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(tranMatrix);
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 0);

		if(kirby != null && pipe != null && cenario != null) {
			modelBatch.begin(camera);
			for(GameObject obj: cenario.cenarios) {
				modelBatch.render(obj, environment);
				obj.update(delta);
			}
//			modelBatch.render(cenario.cenarios[0], environment);
			for(GameObject obj: pipe.estados) {
				modelBatch.render(obj, environment);
				obj.update(delta);
			}
			modelBatch.render(kirby.getCurrent(), environment);
			modelBatch.end();
			camera.update();
		}
		
		spriteBatch.begin();
		font.draw(spriteBatch, "Score: " + pontuacao, 100,500);
		if(Commands.comandos[Commands.JUMPING]) {
			spriteBatch.draw(gamepad.botoes[GamePad.BTN_SALTAR_PRESSIONADO].getImagem(), gamepad.botoes[GamePad.BTN_SALTAR_PRESSIONADO].getX(), gamepad.botoes[GamePad.BTN_SALTAR_PRESSIONADO].getY());
		}
		else {
			spriteBatch.draw(gamepad.botoes[GamePad.BTN_SALTAR_SOLTO].getImagem(), gamepad.botoes[GamePad.BTN_SALTAR_SOLTO].getX(), gamepad.botoes[GamePad.BTN_SALTAR_SOLTO].getY());
		}
//		font.draw(spriteBatch, "Kirby: \n "+ kirby.getX() + "\n" + kirby.getY() + "\n" + kirby.getZ(), 100,400);
//		font.draw(spriteBatch, "Pos_cano: \n "+ pipe.getX(0) + "\n" + pipe.getY(0) + "\n" + pipe.getZ(0), 300,400);
//		font.draw(spriteBatch, "Pos_cano: \n "+ cenario.getX(0) + "\n" + cenario.getY(0) + "\n" + cenario.getZ(0), 300,400);
//		font.draw(spriteBatch, "Pos_cano: \n "+ cenario.getX(1) + "\n" + cenario.getY(1) + "\n" + cenario.getZ(1), 500,400);
//		font.draw(spriteBatch, "Pos_cano: \n "+ pipe.getX(0 + pipe.getNumCanos()) + "\n" + pipe.getY(0 + pipe.getNumCanos()) + "\n" + pipe.getZ(0 + pipe.getNumCanos()), 300,400);
//		for (Vector3 vec : pipe.getObjectPosition()) {
//			font.draw(spriteBatch, ".", vec.x, vec.y);
//		}
		spriteBatch.end();
		
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Input.Keys.SPACE) {
			Commands.comandos[Commands.JUMPING] = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Input.Keys.SPACE) {
			Commands.comandos[Commands.JUMPING] = false;
			return true;
		}
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
		if (gamepad.botoes[GamePad.BTN_SALTAR_SOLTO].isTouch(Utils.convertCoordinates(screenX, screenY))) {
        	Commands.comandos[Commands.JUMPING] = true;
        	return true;
        }
//		if (Gdx.input.justTouched()) {
//        	Commands.comandos[Commands.JUMPING] = true;
//        	return true;
//        }
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if (gamepad.botoes[GamePad.BTN_SALTAR_SOLTO].isTouch(Utils.convertCoordinates(screenX, screenY))) {
        	Commands.comandos[Commands.JUMPING] = false;
        	return true;
        }
//		if (Gdx.input.justTouched()) {
//        	Commands.comandos[Commands.JUMPING] = false;
//        	return true;
//        }
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
