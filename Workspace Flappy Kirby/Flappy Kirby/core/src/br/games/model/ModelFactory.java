package br.games.model;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.ModelLoader.ModelParameters;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.utils.UBJsonReader;

public class ModelFactory {
	
	private static HashMap<String, Model> modelos = new HashMap<String,Model>();
	
	static {
		ModelLoader<ModelParameters> loader;
		loader = new G3dModelLoader(new UBJsonReader());
		System.out.println("Carregando modelos...");
	    modelos.put("KIRBY", loader.loadModel(Gdx.files.internal("Kirby/Kirby.g3db")));
	    modelos.put("PIPE", loader.loadModel(Gdx.files.internal("Pipe/Pipe.g3db")));
	    modelos.put("KIRBY_SLEEPING", loader.loadModel(Gdx.files.internal("Kirby/Sleeping/Kirby_Sleeping.g3db")));
	    modelos.put("CENARIO", loader.loadModel(Gdx.files.internal("Cenario/cenario.g3db")));
		System.out.println("Modelos carregados!");
	}
	
	public static Model getModelbyName(String name) {
		return modelos.get(name);
	}

}

