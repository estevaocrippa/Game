package br.games.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Botao {
	private int posX;
	private int posY;
	private int altura;
	private int largura;
	private Texture imagem;
	
	public Botao(String imgStr, int x, int y) {
		imagem = new Texture(Gdx.files.internal(imgStr));
		this.posX = x;
		this.posY = y;
		this.largura = imagem.getWidth();
		this.altura  = imagem.getHeight();
	}
	
	public boolean isTouch(Vector2 point) {
		return (point.x >= this.posX && point.x <= this.posX + this.largura)
				&&
			   (point.y >= this.posY && point.y <= this.posY + this.altura);
	}
	public Texture getImagem() {
		return this.imagem;
	}
	public int getX() {
		return posX;
	}
	public int getY() {
		return posY;
	}
	

}