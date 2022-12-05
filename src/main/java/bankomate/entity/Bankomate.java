package bankomate.entity;

import bankomate.exceptions.NoEnoughMoneyException;
import bankomate.exceptions.WrongPinException;
import bankomate.services.*;

import java.io.IOException;

public class Bankomate {
    private Card card;
    private AuthorizationService authorizationService;
    private CardService cardService;
    private IOService ioService;


    public Bankomate(Card card) {
        this.card = card;
        this.authorizationService = new AuthorizationServiceImpl();
        this.cardService = new CardServiceImpl(this.authorizationService, this.card);
        this.ioService = new IOServiceImpl();
    }

    public void run() {
        ioService.write("Введите ПИН-код ");
        ioService.write("Введите 'exit' для выхода");
        readPin();
        operationChoose();
    }

    private void readPin() {
        String pin;
        try {
            if (!(pin = ioService.read()).equals("exit")) {
                Integer pinInt = Integer.parseInt(pin);
                authorizationService.logIn(card, pinInt);
            }
        } catch (IOException e) {
            ioService.writeUnknownError();
            readPin();
        } catch (WrongPinException e) {
            ioService.write(e.getMessage());
            readPin();
        }
    }

    private void operationChoose() {
        ioService.write("Выберите операцию:");
        ioService.write("Введите 1 для просмотра баланса");
        ioService.write("Введите 2 для снятия наличных");
        ioService.write("Введите 3 для пополнения счета");
        ioService.write("Введите 4 для смены ПИН-кода");
        ioService.write("Введите 5 для перевода средств на другую карту");
        ioService.write("Введите 'exit' для выхода");
        Integer operation = readOperation();
        switch (operation) {
            case 1:
                ioService.write(cardService.viewCashAmount());
                ifExit();
                authorizationService.logOut();
                break;
            case 2:
                cashIssue();
                ifExit();
                authorizationService.logOut();
                break;
            case 3:
                addCash();
                ifExit();
                authorizationService.logOut();
                break;
            case 4:
                changePin();
                ifExit();
                authorizationService.logOut();
                break;
            case 5:
                perevod();
                ifExit();
                authorizationService.logOut();
                break;
            default:
                authorizationService.logOut();
        }
    }

    private void addCash() {
        ioService.write("Введите сумму пополнения");
        cardService.addCash(ioService.readInt());
    }

    private void perevod() {
        ioService.write("Введите номер пополняемой карты");
        Integer cardNumber = ioService.readInt();
        ioService.write("Введите сумму перевода");
        Integer amount = ioService.readInt();
        try {
            cardService.perevod(amount, cardNumber);
        } catch (NoEnoughMoneyException e) {
            ioService.write(e.getMessage());
            perevod();
        }

    }

    private void changePin() {
        ioService.write("Введите текущий ПИН-код");
        Integer oldPin = ioService.readInt();
        ioService.write("Введите новый ПИН-код");
        Integer newPin = ioService.readInt();
        try {
            cardService.pinChange(oldPin, newPin);
        } catch (WrongPinException e) {
            ioService.write(e.getMessage());
            changePin();
        }

    }

    private void cashIssue() {
        try {
            ioService.write("Введите сумму");
            cardService.cashIssue(ioService.readInt());
        } catch (NoEnoughMoneyException e) {
            ioService.write(e.getMessage());
            cashIssue();
        }
    }

    private void ifExit() {
        ioService.write("Желаете ли продолжить? y/n");
        try {
            if (ioService.read().equals("y")) {
                operationChoose();
            }
        } catch (IOException e) {
            ioService.writeUnknownError();
            ifExit();
        }
    }

    private int readOperation() {
        String operation;
        try {
            if (!(operation = ioService.read()).equals("exit")) {
                return Integer.parseInt(operation);
            }
        } catch (IOException e) {
            ioService.writeUnknownError();
            readOperation();
        }
        return 0;
    }
}