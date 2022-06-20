package bankomate.services;

import bankomate.entity.Card;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class IOServiceImpl implements IOService {
    private Card pin,cashAmount;

    public IOServiceImpl(Card pin,Card cashAmount) {
        this.pin = pin;
        this.cashAmount=cashAmount;
    }

    public String read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        return null;
    }

    public String write(String message) {
        System.out.println();
        return null;
    }
}
