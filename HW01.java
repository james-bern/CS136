class HW01 extends Cow {
	public static void main(String[] arguments) {
		System.out.println("Hello World");
        while (begin_frame()) {
            draw_set_color(0.0, 0.0, 1.0);
            fill_circle(8.0, 4.5, 1.0);
        }
	}
}
