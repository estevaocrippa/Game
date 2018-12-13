package br.games.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector3;

public class Kirby {
	private GameObject      estados[];
	private int             estado;
	public static  Vector3         velocidade;
	public  Vector3         objectPosition;
	public float y_inicial;
	public float pos_y;
	public float pos_x;
	public float pos_z;
	public boolean finalizou = false;
	private Music   music;
	int it_cano;
	
	private static final int JUMPING = 0;
	private static final int PARADO = 1;
	private static final int SLEEPING = 2;
	
	private static final float ACELERACAO = 6f;
	
	public Kirby() {
		estados = new GameObject[3];
		estados[JUMPING] = new GameObject(ModelFactory.getModelbyName("KIRBY"));
		estados[PARADO] = new GameObject(ModelFactory.getModelbyName("KIRBY"));
		estados[SLEEPING] = new GameObject(ModelFactory.getModelbyName("KIRBY_SLEEPING"));
		velocidade = new Vector3();
		estado = PARADO;
		objectPosition = new Vector3();
//		estados[JUMPING].transform.getTranslation(objectPosition);
//		pos_y = objectPosition.y;
		
		for(GameObject obj: estados) {
			obj.transform.scale(0.4f, 0.4f, 0.4f);
			obj.transform.translate(100f, y_inicial, -50f);
//			obj.transform.rotate(Vector3.Y, 270f);
		}
		music = Gdx.audio.newMusic(Gdx.files.internal("Music/Kirby_Dying.mp3"));
		music.setLooping(false);
		it_cano = 0;
	}
	
	public void update(float delta) {
		velocidade.x = -0.75f;
		velocidade.z = 0.0f;
		if (estado == JUMPING) {
			velocidade.y += ACELERACAO * delta;
			if (velocidade.y >= 8f)
				velocidade.y = 8;
			for(GameObject estado: estados) {
				estado.transform.translate(velocidade);
			}
			
		}
		if (estado == PARADO) {
			velocidade.y -= ACELERACAO * delta;
			for(GameObject estado: estados) {
				estado.transform.translate(velocidade);
			}
			
		}
		if (estado == SLEEPING) {
			music.play();
			velocidade.x = 0f;
			velocidade.y = -0.75f;
			velocidade.z = 0f;
			for(GameObject estado: estados) {
				estado.transform.translate(velocidade);
			}
			if(objectPosition.y <= -200) {
				finalizou = true;
				music.stop();
			}
		}
		estados[JUMPING].transform.getTranslation(objectPosition);
		pos_x = objectPosition.x;
		pos_y = objectPosition.y;
		pos_z = objectPosition.z;
		estados[estado].update(delta);
//		if (estado == TRAS) {
//			gameObject.transform.translate(tras.cpy().scl(delta));
//		}
//		if (estado == ESQUERDA) {
//			gameObject.transform.translate(esquerda.cpy().scl(delta));
//		}
//		if (estado == DIREITA) {
//			gameObject.transform.translate(direita.cpy().scl(delta));
//		}
	}
	
	public void pular() {
		estado = JUMPING;
	}
	
	public void parar() {
		estado = PARADO;
	}
	
	public void dormir() {
		estado = SLEEPING;
	}
	
	public void setYInicial(float y) {
		this.y_inicial = y;
	}
	
	public float getX() {
		return this.pos_x;
	}
	
	public float getY() {
		return this.pos_y;
	}
	
	public float getZ() {
		return this.pos_z;
	}
	
	public boolean getFinalizar() {
		return this.finalizou;
	}
//	
//	public void virarDireita() {
//		for (GameObject estado: estados) {
//			estado.transform.rotate(Vector3.Y, -1f);
//			estado.setAngle(-1);
//		}
//	}
	public void redimensionar(float x, float y, float z) {
		for (GameObject estado: estados) {
			estado.transform.scale(x,  y,  z);
		}
	}
//	public void atirar() {
//		estado = SHOOT;
//	}
	
	public int getEstado() {
		return estado;
	}
	
	public GameObject getCurrent() {
		return estados[estado];
	}
	
	public boolean Colide(Vector3[] obj, int quant_canos) {
//		int i = 0;
//		boolean trombou = false;
//		for (Vector3 g : obj) {
//			if (i < quant_canos) {
//				if (getX() <= (g.x + 7) && getX() >= (g.x - 7) && getY() <= (g.y + 8)) {
//					trombou = true;
//					break;
//				} else {
//					trombou = false;
//				}
//			}
//			else {
//				if (getX() <= (g.x + 7) && getX() >= (g.x - 7) && getY() >= (g.y - 16)) {
//					trombou = true;
//					break;
//				} else {
//					trombou = false;
//				}
//			}
//			i++;
//		}
//		return trombou;
		boolean trombou = false;
		
		if (getX() <= (obj[it_cano].x + 7) && getX() >= (obj[it_cano].x - 7) && 
			(getY() <= (obj[it_cano].y + 8) || getY() >= (obj[it_cano+quant_canos].y - 16))) {
			trombou = true;
		} else {
			trombou = false;
		}
		if(getX() <= (obj[it_cano].x - 7)) {
			it_cano++;
		}
		return trombou;
	}

}
