import messages.Frame;

/**
 * Created by Emil Edholm on 2016-11-01.
 */
public class Jifx {
    public static void main(String[] args) {
        Frame f = new Frame.Builder(0xFFFF ).tagged(true).source(0xFFFFFFFFL).build();
        System.out.println(f.toString());
    }
}
