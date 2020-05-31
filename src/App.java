public class App {
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("os.name"));
        Graphics gra = new Graphics();
        gra.setSize(600,250);
        gra.setDefaultCloseOperation(3);
        gra.setVisible(true);
    }
}
