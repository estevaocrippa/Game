package br.games.screen;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen{
	public abstract void update(float delta);
	public abstract void draw(float delta);
	private boolean done; // so pra saber se posso passar para a proxima tela
	private String  id;
	
	public AbstractScreen(String id) {
		this.id = id;
	}
	
	// retorna a string com o identificador da tela
	public String getId() {  
		return this.id;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
	public boolean isDone() {
		return this.done;
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		update(delta);
		draw(delta);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


}

