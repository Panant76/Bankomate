package bankomate.services;

import bankomate.entity.Card;
import bankomate.exceptions.NoEnoughMoneyException;
import bankomate.exceptions.WrongPinException;

public interface CardService {
    void cashIssue(int amount) throws NoEnoughMoneyException;

    String viewCashAmount();

    String pinChange(int oldPin, int newPin) throws WrongPinException;

    String addCash(int amount);
    String perevod(int amount,long cardNumber) throws NoEnoughMoneyException;
}
