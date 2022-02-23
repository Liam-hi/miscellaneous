import java.util.*;

public class Menu {
    public static void main(String[] args) {

        // Creating an array list of objects.

        List<GameInventoryItem> inventory =new ArrayList<GameInventoryItem>();

        // So the user can interact with the menu bar.

        Scanner input = new Scanner(System.in);

        // The while loop executes the menu bar repeatedly.

        int choice;

        while(true) {


            System.out.print("\n");
            System.out.print("-------------------------------------\n");
            System.out.print("              Inventory:             \n");
            System.out.print("-------------------------------------\n");
            System.out.print("1.) View Inventory \n");
            System.out.print("2.) Add an item to Inventory\n");
            System.out.print("3.) Delete an item from Inventory\n");
            System.out.print("4.) Product description              \n");
            System.out.print("-------------------------------------\n");
            System.out.print("Edit listing and product information:\n");
            System.out.print("-------------------------------------\n");
            System.out.print("5.) Change 'Game Description' for an item \n");
            System.out.print("6.) Change 'Release Year' for an item \n");
            System.out.print("7.) Change 'Condition' for an item \n");
            System.out.print("8.) Change 'Item Status' for an item \n");
            System.out.print("-------------------------------------\n");
            System.out.print("           Search Inventory:         \n");
            System.out.print("-------------------------------------\n");
            System.out.print("9.) Search by 'Condition'             \n");
            System.out.print("10.) Search by 'Relaese Year'          \n");
            System.out.print("-------------------------------------\n");
            System.out.print("10.) Exit\n");
            System.out.print("\n Choice: ");

            choice = input.nextInt();
            Scanner in = new Scanner(System.in);

            switch (choice) {

                case 1:
                    System.out.println(" ");
                    System.out.println("Inventory:" +' ' + inventory.size() + ' ' + "item(s)");
                    System.out.println(" ");
                    System.out.println("Stock:\n");

                    // view the objects by iteration.

                    for (int i = 0; i < inventory.size(); i++) {
                        System.out.println(inventory.get(i).getName());

                        ///System.out.println(inventory.size());
                    }

                    break;

                case 2:

                    // Initialization:

                    GameInventoryItem items = new GameInventoryItem();
                    System.out.println("Name:");
                    items.setName(in.nextLine());
                    System.out.println("Game Description:");
                    items.setNote(in.nextLine());
                    System.out.println("Release:");
                    items.setYear(in.nextLine());
                    System.out.println("Condition:");
                    items.setCondition(in.nextLine());
                    System.out.println("Status:");
                    items.setStat(in.nextLine());

                    inventory.add(items);
                    ///System.out.println(inventory);

                    break;

                case 3:

                    // The iterator allows us to cycle through collections

                    Iterator<GameInventoryItem> deleteitem = inventory.iterator();

                    System.out.println("Delete:");

                    String userinput = in.nextLine();

                    while (deleteitem.hasNext()) {
                        GameInventoryItem user = deleteitem.next();
                        if (user.getName().equals(userinput)){
                            deleteitem.remove();
                        }
                    }

                    break;

                case 4:
                    Iterator<GameInventoryItem> search = inventory.iterator();

                    // Same logic as case 3.

                    System.out.println("Name:");

                    String userinput2 = in.nextLine();

                    while (search.hasNext()) {
                        GameInventoryItem user = search.next();
                        if (user.getName().equals(userinput2)) {
                            System.out.println(" ");
                            System.out.println("Item name:" + " " + user.getName());
                            System.out.println("Game description:" + " " +  user.getNote());
                            System.out.println("Release date:" + " " + user.getYear());
                            System.out.println("Condition:" + " " + user.getCondition());
                            System.out.println("Status:" + " " + user.getStat());

                        }
                    }

                    break;

                case 5:

                    // Same logic as case 3.

                    Iterator<GameInventoryItem> search2 = inventory.iterator();

                    System.out.println("Name:");

                    String userinput3 = in.nextLine();

                    System.out.println("Note:");

                    String userinput4 = in.nextLine();

                    while (search2.hasNext()) {
                        GameInventoryItem user = search2.next();
                        if (user.getName().equals(userinput3)) {
                            user.setNote(userinput4);

                        }
                    }

                    break;

                case 6:

                    // Same principle as case 5.

                    Iterator<GameInventoryItem> search3 = inventory.iterator();

                    System.out.println("Name:");

                    String ui1 = in.nextLine();

                    System.out.println("Year:");

                    String ui12 = in.nextLine();

                    while (search3.hasNext()) {
                        GameInventoryItem user = search3.next();
                        if (user.getName().equals(ui1)) {
                            user.setYear(ui12);

                        }
                    }

                    break;

                case 7:

                    Iterator<GameInventoryItem> search4 = inventory.iterator();

                    System.out.println("Name:");

                    String uicond = in.nextLine();

                    System.out.println("Condition:");

                    String uicond2 = in.nextLine();

                    while (search4.hasNext()) {
                        GameInventoryItem user = search4.next();
                        if (user.getName().equals(uicond)) {
                            user.setCondition(uicond2);

                        }
                    }

                    break;

                case 8:

                    Iterator<GameInventoryItem> search5 = inventory.iterator();

                    System.out.println("Name:");

                    String uistat = in.nextLine();

                    System.out.println("Status:");

                    String uistat2 = in.nextLine();

                    while (search5.hasNext()) {
                        GameInventoryItem user = search5.next();
                        if (user.getName().equals(uistat)) {
                            user.setStat(uistat2);

                        }
                    }

                    break;

                case 9:

                    Iterator<GameInventoryItem> search6 = inventory.iterator();

                    System.out.println("Condition:");

                    String uicondcond = in.nextLine();


                    while (search6.hasNext()) {
                        GameInventoryItem user = search6.next();
                        if (user.getCondition().equals(uicondcond)) {
                            System.out.println(user.getName());

                        }
                    }

                    break;

                case 10:

                    // Kill the loop.

                    System.out.println("Exiting Program...");
                    System.exit(0);
                    break;
                default :
                    System.out.println("Invalid token");
                    break;


            }

        }

    }
}
