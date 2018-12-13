package br.games.util;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import br.games.model.GameObject;

public class ChasingCamera extends PerspectiveCamera {

	private GameObject objectToFollow;
	private int mode;
	public static final int ZOOM_IN = 0;
	public static final int ZOOM_OUT = 1;
	private float offsetZoomIn;
	private float offsetZoomOut;
	private float currentOffset;
	private float currentOffsetZ;
	private Vector3 objectPosition;
	private float offsetZOut = 0.0f;
	private float offsetZIn  = 0.0f;

	public ChasingCamera(float fov, float width, float height, float offsetZoomIn, float offsetZoomOut) {
		super(fov, width, height);
		this.offsetZoomIn = offsetZoomIn;
		this.offsetZoomOut = offsetZoomOut;
		mode = ZOOM_OUT;
		objectPosition = new Vector3();
	}

	public void setObjectToFollow(GameObject obj) {
		objectToFollow = obj;
	}
	
	public void setOffsetZIn(float offsetZ) {
		this.offsetZIn = offsetZ;
	}
	
	public void setOffsetZOut(float offsetZ) {
		this.offsetZOut = offsetZ;
	}

	public void update() {
		
		// defino meu offset de acordo com o modo
		currentOffset = (mode == ZOOM_IN) ? offsetZoomIn : offsetZoomOut;
		currentOffsetZ = (mode == ZOOM_IN)? offsetZIn: offsetZOut;
		// vou pegar a posicao do objeto a ser seguido
		if (objectToFollow != null) {
			objectToFollow.transform.getTranslation(objectPosition);
			// defino a nova posicao
			float angulo = objectToFollow.getAngle();
			
			
			float newX, newY, newZ;
			newX = objectPosition.x + (float)(currentOffset*Math.sin(Math.toRadians(angulo)));
			newY = objectPosition.y;
			newZ = objectPosition.z - currentOffsetZ;
			this.position.set(newX, newY, newZ);
			// defino para onde a camera irá olhar
			this.lookAt(objectPosition.x, objectPosition.y, objectPosition.z + currentOffsetZ);
			// chamo o update da classe pai
		}
		super.update();
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
}
