class Player {
    Vector2 position;

    static final double radius = 2.0;
}

class Bullet {
    Vector2 position;
    Vector2 velocity;
    boolean alive;
    int age;

    static final double radius = 1.0;
}

class Turret {
    Vector2 position;
    int framesSinceFired;

    static final double radius = 8.0;
}

class TurretGame extends App {

    Player player;

    Turret turret;

    // bullets is a big, fixed-size array of Bullet objects (slots)
    // initially all bullets are "dead," i.e. not updated or drawn
    // think of dead bullets as empty slots in the bullets array
    Bullet[] bullets;

    void setup() {
        player = new Player();
        player.position = new Vector2(0.0, -32.0);

        turret = new Turret();
        turret.position = new Vector2(0.0, 0.0);

        bullets = new Bullet[256];
        for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
            bullets[bulletIndex] = new Bullet();
        }
    }

    void loop() {
        // player
        if (keyHeld('W')) { player.position.y += 1.0; }
        if (keyHeld('A')) { player.position.x -= 1.0; }
        if (keyHeld('S')) { player.position.y -= 1.0; }
        if (keyHeld('D')) { player.position.x += 1.0; }
        drawCircle(player.position, player.radius, Vector3.cyan);

        // turret
        if (turret.framesSinceFired++ == 16) {
            turret.framesSinceFired = 0;

            // create bullet (in first empty slot)
            for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
                Bullet bullet = bullets[bulletIndex];
                if (!bullet.alive) {
                    bullet.alive = true;
                    bullet.age = 0;
                    bullet.position = turret.position;
                    bullet.velocity = Vector2.directionFrom(turret.position, player.position);
                    break;
                }
            }
        }
        drawCircle(turret.position, turret.radius, Vector3.red);

        // bullets
        for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
            Bullet bullet = bullets[bulletIndex];
            if (!bullet.alive) { continue; }
            if (bullet.age++ > 128) {
                bullet.alive = false;
                continue;
            }
            bullet.position = bullet.position.plus(bullet.velocity);
            drawCircle(bullet.position, bullet.radius, Vector3.red);
        }
    }

    public static void main(String[] arguments) { new TurretGame().run(); }
}
