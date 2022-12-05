package bankomate;

import bankomate.entity.Bankomate;
import bankomate.entity.Card;

public class Main {
    public static void main(String[] args) {
        Card card=new Card(2345,7777,"BYN",200);
        Bankomate bankomate=new Bankomate(card);
        bankomate.run();
    }
}
