package br.games.model;

import com.badlogic.gdx.math.Vector3;

public class Cenario {
	public GameObject      cenarios[];
	private int estado;
	private int num_cenarios;
	private Vector3 objectPosition[];
	
	public Cenario() {
		num_cenarios = 29;
		cenarios = new GameObject[num_cenarios];
		objectPosition = new Vector3[num_cenarios];
		float x = 0;
		for(int i = 0; i < num_cenarios; i++) {
			cenarios[i] = new GameObject((ModelFactory.getModelbyName("CENARIO")));
			objectPosition[i] = new Vector3();
			cenarios[i].transform.translate(-x, 0f, 0);
			x += 795;
		}
		
	}
	public void update(float delta) {
		for(int i = 0; i < num_cenarios; i++) {
			cenarios[i].transform.getTranslation(objectPosition[i]);
		}
		cenarios[estado].update(delta);
	}
	
	public Vector3[] getObjectPosition() {
		return objectPosition;
	}
	
	public float getX(int i) {
		return objectPosition[i].x;
	}
	
	public float getY(int i) {
		return objectPosition[i].y;
	}
	
	public float getZ(int i) {
		return objectPosition[i].z;
	}
	
	public int getNumCenarios() {
		return this.num_cenarios;
	}
	
	public int getEstado() {
		return estado;
	}
	
	public GameObject getCurrent() {
		return this.cenarios[estado];
	}

}
