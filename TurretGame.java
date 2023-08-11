class Player {
    Vector2 position;
    static final double RADIUS = 2.0;
}

class Bullet {
    Vector2 position;
    Vector2 velocity;
    boolean alive;
    static final double RADIUS = 1.0;
}

class Turret {
    Vector2 position;
    int framesSinceFired;
    static final Vector2 SIZE = new Vector2(8.0, 8.0);
}

class TurretGame extends App {
    Player player;
    Turret turret;

    Bullet[] bullets;
    int indexOfNextBullet;

    void setup() {
        bullets = new Bullet[256];
        for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
            bullets[bulletIndex] = new Bullet();
        }
        indexOfNextBullet = 0;

        turret = new Turret();
        turret.position = new Vector2(0.0, 0.0);

        player = new Player();
        player.position = new Vector2(0.0, -32.0);
    }

    void loop() {
        if (keyHeld('W')) { player.position.y += 1.0; }
        if (keyHeld('A')) { player.position.x -= 1.0; }
        if (keyHeld('S')) { player.position.y -= 1.0; }
        if (keyHeld('D')) { player.position.x += 1.0; }

        drawCircle(player.position, player.RADIUS, Vector3.red);


        if (turret.framesSinceFired++ == 16) {
            turret.framesSinceFired = 0;

            Bullet bullet = bullets[indexOfNextBullet];
            indexOfNextBullet = (indexOfNextBullet + 1) % bullets.length;

            bullet.position = turret.position;
            bullet.velocity = (player.position.minus(turret.position)).direction();
            bullet.alive = true;
        }

        drawCenterRectangle(turret.position, turret.SIZE, Vector3.cyan);


        for (Bullet bullet : bullets) {
            if (!bullet.alive) { continue; }
            bullet.position = bullet.position.plus(bullet.velocity);
            drawCircle(bullet.position, bullet.RADIUS, Vector3.yellow);
        }
    }

    public static void main(String[] arguments) { new TopDownShooter().run(); }
}
