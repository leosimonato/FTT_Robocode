//﻿package sample; //

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;

public class TioSam extends AdvancedRobot {

	int count = 0;
	int power = 0;
	double qtdViraArma;
	String alvo;

	public void run() {

		setColors(Color.BLUE, Color.WHITE, Color.RED);

		alvo = null;
		setAdjustGunForRobotTurn(true);
		qtdViraArma = 20;

		while (true) {

			turnGunRight(qtdViraArma);
			count++;

			if (count > 2) {
				qtdViraArma = -20;
			}

			if (count > 5) {
				qtdViraArma = 20;
			}

			if (count > 11) {
				alvo = null;
			}

		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {

		if (alvo != null && !e.getName().equals(alvo)) {
			return;
		}

		if (alvo == null) {
			alvo = e.getName();
			out.println("Na Mira: " + alvo);
		}

		count = 0;
		power = 3;
		setFireBullet(power);

		if (e.getDistance() > 50) {
			qtdViraArma = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
			
			setTurnGunRight(qtdViraArma);
			turnRight(e.getBearing());
			ahead(e.getDistance() - 40);

			return;
		}

		qtdViraArma = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(qtdViraArma);
		power = 30;
		setFireBullet(power);


		if (e.getDistance() < 50) {
			
			if (e.getBearing() > -60 && e.getBearing() < 60) {
				back(40);
			} else {
				ahead(40);
			}
			
			if (e.getDistance() < 20) {
				power = 100;
				setFireBullet(power);
				setFireBullet(power);
				setFireBullet(power);
			}
			
		}
		
		scan();
		
	}

	public void onWin(WinEvent e) {
		while (true) {
			setTurnRight(10000);
			setMaxVelocity(50);
			ahead(10000);
		}
	}
}