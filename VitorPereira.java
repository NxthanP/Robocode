package X1;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.Color;

public class VitorPereira extends Robot {

	boolean peek; // Não gira se houver um robô lá
	double moveAmount; // Quanto se move

    //run: Move-se pelas paredes
	public void run() {
		// Definir as cores
		setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(Color.white);
		setBulletColor(Color.white);
		setScanColor(Color.white);

		// Inicializa o moveAmount com o valor máximo possível para este campo de batalha.
		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		// Inicializa peek como falso
		peek = false;

		// Vira para a esquerda para encarar a parede.
		// getHeading() % 90 significa o resto da divisão de
		// getHeading() por 90.
		turnLeft(getHeading() % 90);
		ahead(moveAmount);
		// Vira a arma para a direita em 90 graus.
		peek = true;
		turnGunRight(90);
		turnRight(90);

		while (true) {
			// Olha antes de virar quando ahead() completar.
			peek = true;
			// Move-se pela parede
			ahead(moveAmount);
			// Não olhe agora
			peek = false;
			// Vira para a próxima parede
			turnRight(90);
		}
	}

	//	 Move-se um pouco para longe.
	public void onHitRobot(HitRobotEvent e) {
		// Se ele estiver na nossa frente, andamos um pouco para trás.
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} // caso contrário, ele está atrás de nós, então vamos um pouco para frente.
		else {
			ahead(100);
		}
	}

	//Atira
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);
		// Observe que o scan é chamado automaticamente quando o robô está em movimento.
		// Chamando-o manualmente aqui, garantimos que geramos outro evento de scan se houver um robô na próxima
		// parede, para que não comecemos a subir até que ele tenha ido embora.
		if (peek) {
			scan();
		}
	}
}
