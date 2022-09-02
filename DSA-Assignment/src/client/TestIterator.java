package client;

import adt.ArrayList;
import adt.ListInterface;
import adt.Queue;
import adt.QueueI;
import adt.*;
import entity.*;

import java.io.IOException;
import java.util.Iterator;

public class TestIterator {

    //Test the file handling
    public static void main(String[] args) throws IOException{

        QueueI<Order> items = Order.readOrderFromFile();
        ListInterface<Item> menuItems = Item.readItemfromFile();

//        Iterator iterator = items.iterator();
//
//        while(iterator.hasNext())
//            System.out.println(iterator.next());

        Iterator test = menuItems.iterator();

        while(test.hasNext())
            System.out.println(test.next());

        Order.overwriteFile(items);

    }


}
