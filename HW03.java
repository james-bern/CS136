class Main extends App {
    
    static class Player {
        Vector2 position;
        double radius;
        Vector3 color;
    }
    
    static class Turret {
        Vector2 position;
        double radius;
        Vector3 color;
        int framesSinceFired;
    }
    
    static class Bullet {
        Vector2 position;
        Vector2 velocity;
        double radius;
        Vector3 color;
        boolean alive;
        int age;
        int type;
        
        static final int TYPE_PLAYER = 0;
        static final int TYPE_TURRET = 1;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    Player player;
    
    Turret turret;
    
    // bullets is a big, fixed-size array of Bullet objects (slots)
    // initially all bullets are "dead," i.e. not updated or drawn
    // think of dead bullets as empty slots in the bullets array
    Bullet[] bullets;
    
    ////////////////////////////////////////////////////////////////////////////
    
    void setup() {
        player = new Player();
        player.position = new Vector2(0.0, -40.0);
        player.radius = 4.0;
        player.color = Vector3.cyan;
        
        turret = new Turret();
        turret.position = new Vector2(0.0, 0.0);
        turret.radius = 8.0;
        turret.color = Vector3.red;
        
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
            
            // fire bullet
            for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
                Bullet bullet = bullets[bulletIndex];
                if (!bullet.alive) { // write to first empty ("dead") slot in bullets array
                    bullet.position = turret.position;
                    bullet.velocity = Vector2.directionVectorFrom(turret.position, player.position);
                    bullets[bulletIndex].radius = 2.0;
                    bullet.color = turret.color;
                    bullet.alive = true;
                    bullet.age = 0;
                    bullet.type = Bullet.TYPE_TURRET;
                    
                    break;
                }
            }
        }
        drawCircle(turret.position, turret.radius, Vector3.red);
        
        // bullets
        for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
            Bullet bullet = bullets[bulletIndex];
            
            if (!bullet.alive) { continue; } // skip dead bullets
            
            // kill bullets that are too old (they're probably off-screen)
            if (bullet.age++ > 128) {
                bullet.alive = false;
            }
            
            // "physics"
            bullet.position = bullet.position.plus(bullet.velocity);
            
            // draw
            drawCircle(bullet.position, bullet.radius, bullet.color);
        }
    }
    
    public static void main(String[] arguments) {
        App app = new Main();
        app.setWindowBackgroundColor(0.0, 0.0, 0.0);
        app.setWindowSizeInWorldUnits(128.0, 128.0);
        app.setWindowCenterInWorldUnits(0.0, 0.0);
        app.setWindowHeightInPixels(512);
        app.setWindowTopLeftCornerInPixels(64, 64);
        app.run();
    }
}
