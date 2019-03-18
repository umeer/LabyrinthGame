public class main {
   
    public static void main(String[] args) {
          
        final GameConsole gui = new GameConsole();
        gui.setVisible(true);

        gui.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                System.exit(0);
            }
        });
    }
}

 enum BlockType {

        EMPTY,
        USER,
        KEY,
        DOOR,
        WALL,
        END,
        EXTRA // if the extra is present on the map, before finishing you need to collect it
  }