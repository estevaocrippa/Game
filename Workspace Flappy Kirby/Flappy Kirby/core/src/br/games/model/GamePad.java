package br.games.model;

public class GamePad {
	public Botao botoes[];
	public static final int BTN_SALTAR_PRESSIONADO = 0;
	public static final int BTN_SALTAR_SOLTO = 1;
	public static final int BTN_INICIAR_PRESSIONADO = 2;
	public static final int BTN_INICIAR_SOLTO = 3;
	public static final int BTN_JOGAR_NOV_PRESSIONADO = 4;
	public static final int BTN_JOGAR_NOV_SOLTO = 5;
	public static final int BTN_SAIR_PRESSIONADO = 6;
	public static final int BTN_SAIR_SOLTO = 7;
	
	public GamePad() {
		botoes  = new Botao[8];
		botoes[BTN_SALTAR_PRESSIONADO] = new Botao("Gamepad/BTN_Saltar_Pressionado.jpg",500,50);
		botoes[BTN_SALTAR_SOLTO] = new Botao("Gamepad/BTN_Saltar_Solto.jpg", 500, 50);
		botoes[BTN_INICIAR_PRESSIONADO] = new Botao("Gamepad/BTN_Iniciar_game_pressionado.jpg", 290, 250);
		botoes[BTN_INICIAR_SOLTO] = new Botao("Gamepad/BTN_Iniciar_game_solto.jpg", 290, 250);
		botoes[BTN_JOGAR_NOV_PRESSIONADO] = new Botao("Gamepad/BTN_Jogar_nov_pressionado.jpg", 500, 50);
		botoes[BTN_JOGAR_NOV_SOLTO] = new Botao("Gamepad/BTN_Jogar_nov_solto.jpg", 500, 50);
		botoes[BTN_SAIR_PRESSIONADO] = new Botao("Gamepad/BTN_Sair_Pressionado.jpg", 100, 50);
		botoes[BTN_SAIR_SOLTO] = new Botao("Gamepad/BTN_Sair_Solto.jpg", 100, 50);
	}
	
	public Botao[] getBotoes() {
		return botoes;
	}
	
}
