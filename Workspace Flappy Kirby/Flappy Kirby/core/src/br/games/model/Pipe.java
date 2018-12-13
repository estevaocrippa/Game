package br.games.model;

import java.util.Random;

import com.badlogic.gdx.math.Vector3;

public class Pipe {
	public GameObject      estados[];
	private int             estado;
	private int             num_canos;
	private Vector3 objectPosition[];
	
	public Pipe() {
		num_canos = 800;
		estados = new GameObject[2*num_canos];
		objectPosition = new Vector3[2*num_canos];
		for(int i = 0; i < 2*num_canos; i++) {
			estados[i] = new GameObject(ModelFactory.getModelbyName("PIPE"));
			objectPosition[i] = new Vector3();
		}
		
		float x = 10;
		for(int i = 0; i < num_canos; i++) {
			Random random = new Random();
			int y_rand = random.nextInt(20);
			int y_rand_max = random.nextInt(4);
			estados[i].transform.translate(-x, (float)-y_rand, -50f);
			estados[i+num_canos].transform.translate(-x, (float)(-y_rand + (35 +y_rand_max)), -50f);
			estados[i+num_canos].transform.rotate(Vector3.Z, 180f);
			x += 30;
		}
		
	}
	public void update(float delta) {
		for(int i = 0; i < num_canos; i++) {
			estados[i].transform.getTranslation(objectPosition[i]);
//			System.out.println("(" + objectPosition[i].x + ", " + objectPosition[i].y + ", " + objectPosition[i].z + ")");
			estados[i+num_canos].transform.getTranslation(objectPosition[i+num_canos]);
		}
		estados[estado].update(delta);
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
	
	public int getNumCanos() {
		return num_canos;
	}
	
	public int getEstado() {
		return estado;
	}
	
	public GameObject getCurrent() {
		return estados[estado];
	}

}
