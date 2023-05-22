import java.awt.*;
import java.util.*;
import javax.swing.*;

class Jim {
    static Random random = new Random();
    static boolean is_between(double c, double a, double b) {
        return a < c && c < b;
    }
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
    public static double lerp(double t, double a, double b) {
        return (1.0 - t) * a + t * b;
    }
    public static double rand() {
        return random.nextDouble();
    }
    public static double random_double(double a, double b) {
        return lerp(rand(), a, b);
    }
    public static boolean is_zero(double a) {
        return Math.abs(a) < .00001;
    }
}


class HelicopterGame extends App {
    public static void main(String[] args) { new HelicopterGame().startGameLoop(); }

    boolean initialized;
    int frame;
    double time;
    ArrayList<Bullet> bullets;
    ArrayList<Explosion> explosions;
    ArrayList<Helicopter> helicopters;
    Helicopter player;

    @Override public void updateAndDraw() {
        if (key_pressed('R') || !initialized) {
            initialized = true;
            frame = 0;
            time = 0.0;
            bullets = new ArrayList<Bullet>();
            explosions = new ArrayList<Explosion>();
            helicopters = new ArrayList<Helicopter>();
            player = new Helicopter(); {
                player.position = new Vector2(300.0, 300.0);
                player.velocity = new Vector2();
                player.acceleration = new Vector2();
                player.facing = 1;
                player.color = Color.CYAN;
                player._FORNOW_dead_flag = false;
                player._FORNOW_jFramesSinceFlippedFacing = 64;
            }
            helicopters.add(player);
        } else {
            ++frame;
            time += .0167;
        }

        {
            { // player
                ++player._FORNOW_jFramesSinceFlippedFacing;
                player.acceleration = new Vector2();
                if (key_held('S')) { player.acceleration.y -= 1; }
                if (key_held('W')) { player.acceleration.y += 1; }
                {
                    int tmp = player.facing;
                    if (key_held('A')) { player.acceleration.x -= 1; player.facing = -1; }
                    if (key_held('D')) { player.acceleration.x += 1; player.facing =  1; }
                    if (tmp != player.facing) {
                        player._FORNOW_jFramesSinceFlippedFacing = 0;
                    }
                }
                player.velocity = magClamp(add(player.velocity, scalarMultiply(0.2, player.acceleration)), 7.0);
                player.position = add(player.position, player.velocity);

                {
                    final double eps = 16.0;
                    {
                        double tmp = player.position.x;
                        player.position.x = Jim.clamp(player.position.x, eps, _windowWidthInPixels - eps - 16);
                        if (!Jim.is_zero(tmp - player.position.x)) {
                            player.velocity.x *= -.85;
                        }
                    }
                    {
                        double tmp = player.position.y;
                        player.position.y = Jim.clamp(player.position.y, eps + 30, _windowHeightInPixels - eps);
                        if (!Jim.is_zero(tmp - player.position.y)) {
                            player.velocity.y *= -.85;
                        }
                    }
                }

                player.velocity = scalarMultiply(.99, player.velocity);

                if (key_pressed('J')) { 
                    player.fire(this, null, 2.0);
                }
                if (key_pressed('K')) { 
                    ArrayList<Bullet> dead_bullets = new ArrayList<Bullet>();
                    for (Bullet bullet : bullets) {
                        if (bullet.helicopter != player) {
                            dead_bullets.add(bullet);
                            spawn_explosion(bullet.position, bullet.helicopter.color);
                        }
                    }
                    bullets.removeAll(dead_bullets);
                    int N = 128;
                    for (int i = 0; i < N; i++) {
                        double theta = (double) (i) / N * 2 * Math.PI;
                        player.fire(this, scalarMultiply(10.0, new Vector2(Math.cos(theta), Math.sin(theta))), 2.0);
                    }
                }
            }
            { // update
                if ((player.age % 120 == 0) && helicopters.size() < 10) {
                    Vector2 position = new Vector2(-25.0, Jim.random_double(0.0, _windowHeightInPixels));
                    if (Jim.rand() > 0.5) {
                        position.x = _windowWidthInPixels - position.x;
                    }
                    spawn_enemy_helicopter(position);
                }

                for (Bullet bullet : bullets) {
                    ++bullet.age;
                    bullet.position = bullet.position.plus(bullet.velocity);
                }

                { // explosions
                    ArrayList<Explosion> dead_explosions = new ArrayList<Explosion>();
                    for (Explosion explosion : explosions) {
                        ++explosion.age;
                        if (explosion.age > 16) {
                            dead_explosions.add(explosion);
                        }
                    }
                    explosions.removeAll(dead_explosions);
                }

                for (Helicopter helicopter : helicopters) {
                    ++helicopter.age;

                    if (helicopter == player) {
                        continue;
                    }

                    helicopter.position.y += Jim.clamp(player.position.y - helicopter.position.y, -0.5, 0.5);
                    helicopter.position.x += Jim.clamp(player.position.x - helicopter.position.x, -1, 1);
                    helicopter.facing = (player.position.x > helicopter.position.x) ? 1 : -1;

                    if (helicopter.age % 45 == 0) {
                        helicopter.fire(this, scalarMultiply(5.0, normalized(player.position.minus(helicopter.position))), 1.0);
                    }
                }


                { // collide
                    ArrayList<Bullet> dead_bullets = new ArrayList<Bullet>();
                    ArrayList<Helicopter> dead_helicopters = new ArrayList<Helicopter>();
                    for (Bullet bullet : bullets) {
                        if (bullet.age > 256) {
                            dead_bullets.add(bullet);
                            continue;
                        }
                        for (Helicopter helicopter : helicopters) {
                            if (helicopter._FORNOW_dead_flag) {
                                continue;
                            }
                            if (bullet.helicopter == helicopter || (helicopter != player && bullet.helicopter != player)) {
                                continue;
                            }
                            if (Jim.is_between(bullet.position.x, helicopter.position.x - helicopter.size.x / 2 - bullet.size / 2, helicopter.position.x + helicopter.size.x / 2 + bullet.size / 2)
                                    && Jim.is_between(bullet.position.y, helicopter.position.y - helicopter.size.y / 2 - bullet.size / 2, helicopter.position.y + helicopter.size.y / 2 + bullet.size / 2)
                               ) {
                                helicopter._FORNOW_dead_flag = true;
                                dead_helicopters.add(helicopter);
                                dead_bullets.add(bullet);
                                spawn_explosion(helicopter.position, helicopter.color);
                                continue;
                               }
                        }
                    }
                    bullets.removeAll(dead_bullets);
                    helicopters.removeAll(dead_helicopters);
                }
            }
            { // draw
                for (Bullet bullet : bullets) {
                    bullet.draw(graphics);
                }
                for (Explosion explosion : explosions) {
                    explosion.draw(graphics);
                }
                for (Helicopter helicopter : helicopters) {
                    helicopter.draw(graphics);
                }
            }
        }

    }

    static Vector2 add(Vector2 a, Vector2 b) { return new Vector2(a.x + b.x, a.y + b.y); }
    static Vector2 subtract(Vector2 a, Vector2 b) { return new Vector2(a.x - b.x, a.y - b.y); }
    static double squaredNorm(Vector2 a) { return a.x * a.x + a.y * a.y; }
    static double norm(Vector2 a) { return Math.sqrt(squaredNorm(a)); }
    static Vector2 scalarMultiply(double k, Vector2 a) { return new Vector2(k * a.x, k * a.y); }
    static Vector2 normalized(Vector2 a) { return scalarMultiply(1.0 / norm(a), a); }
    static Vector2 magClamp(Vector2 a, double k) {
        double sn_a = squaredNorm(a);
        if (Jim.is_zero(sn_a)) {
            return a;
        }
        double n_a = Math.sqrt(sn_a);
        return scalarMultiply(Jim.clamp(n_a, -k, k) / n_a, a);

    }
    static double distanceSquared(Vector2 a, Vector2 b) { return squaredNorm(subtract(a, b)); }
    static double distance(Vector2 a, Vector2 b) { return norm(subtract(a, b)); }

    class Explosion {
        Vector2 position;
        int age;
        Color color;
        void draw(Graphics graphics) {
            double size = 24.0 + 6.0 * this.age;
            Color color = Color.WHITE;
            if (this.age > 6) {
                color = new Color(this.color.getRed() / 255.0f, this.color.getGreen() / 255.0f, this.color.getBlue() / 255.0f, 1.0f - (float) (age - 6) / 10.0f);
            }
            drawCenteredSquare(color, this.position, new Vector2(size, size));
        }
    }

    class Bullet {
        Vector2 position;
        Vector2 velocity;
        int age;
        double size;
        Helicopter helicopter;
        void draw(Graphics graphics) {
            drawCenteredSquare(helicopter.color, this.position, new Vector2(this.size, this.size));
        }
    }

    class Helicopter {
        Vector2 position;
        Vector2 velocity;
        Vector2 acceleration;
        int facing;
        int _FORNOW_jFramesSinceFlippedFacing;
        int age;
        boolean _FORNOW_dead_flag;
        Color color;
        static Vector2 size = new Vector2(32.0, 16.0);
        void draw(Graphics graphics) {
            drawCenteredSquare(color, this.position, Helicopter.size);
            drawCenteredSquare(color, this.position.plus(new Vector2(0.0, 12.0)), new Vector2(50.0 * Math.sin(15 * facing * time), 2.0));

            double f = Jim.clamp(_FORNOW_jFramesSinceFlippedFacing / 8.0, 0.0, 1.0);
            drawCenteredSquare(color, this.position.plus(new Vector2(-this.facing * f * 25.0, -5.0)), new Vector2(f * 16.0, 3.0));
            drawCenteredSquare(color, this.position.plus(new Vector2(-this.facing * f * 35.0, -1.5)), new Vector2(f * 4.0, 8.0));

        }
        void fire(HelicopterGame app, Vector2 velocity, double size_multiplier) {
            Bullet bullet = new Bullet(); {
                bullet.position = this.position.plus(new Vector2(this.facing * 15.0, -5.0));
                bullet.velocity = (velocity != null) ? velocity : new Vector2(this.facing * 10.0, 0.0);
                bullet.helicopter = this;
                bullet.size = size_multiplier * 8.0;
            }
            app.bullets.add(bullet);
        }
    }


    void spawn_explosion(Vector2 position, Color color) {
        Explosion explosion = new Explosion(); {
            explosion.position = new Vector2(position);
            explosion.age = 0;
            explosion.color = new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
        }
        explosions.add(explosion);
    }
    void spawn_enemy_helicopter(Vector2 position) {
        Helicopter enemy = new Helicopter(); {
            enemy.position = new Vector2(position);
            enemy.facing = 1;
            enemy.color = Color.RED;
            enemy._FORNOW_dead_flag = false;
            enemy._FORNOW_jFramesSinceFlippedFacing = 64;
        }
        helicopters.add(enemy);
    }
}
