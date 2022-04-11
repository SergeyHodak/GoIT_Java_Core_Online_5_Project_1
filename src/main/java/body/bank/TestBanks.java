package body.bank;

import body.bank.PrivatBank.PrivatBank;
import body.bank.monobank.Monobank;
import body.bank.nbu.NBU;
import java.io.IOException;

public class TestBanks {
    public static void main(String[] args) throws IOException, InterruptedException {
        Currency[] currencies = {Currency.USD, Currency.EUR};

        PrivatBank pb =  new PrivatBank();
        float[] buyPB = pb.getBuy(currencies);
        float[] salePB = pb.getSale(currencies);
        System.out.println("Курс валют в ПриватБанк:");
        for (int i = 0; i < currencies.length; i++) {
            System.out.println(currencies[i].name() + "/UAH\n" +
                    "Покупка: " + buyPB[i] + '\n' +
                    "Продажа: " + salePB[i] + '\n');
        }

        NBU nbu =  new NBU();
        float[] buyNBU = nbu.getBuy(currencies);
        float[] saleNBU = nbu.getSale(currencies);
        System.out.println("Курс валют в НБУ:");
        for (int i = 0; i < currencies.length; i++) {
            System.out.println(currencies[i].name() + "/UAH\n" +
                    "Покупка: " + buyNBU[i] + '\n' +
                    "Продажа: " + saleNBU[i] + '\n');
        }

        Monobank mb =  new Monobank();
        float[] buyMB = mb.getBuy(currencies);
        float[] saleMB = mb.getSale(currencies);
        System.out.println("Курс валют в МоноБанк:");
        for (int i = 0; i < currencies.length; i++) {
            System.out.println(currencies[i].name() + "/UAH\n" +
                    "Покупка: " + buyMB[i] + '\n' +
                    "Продажа: " + saleMB[i] + '\n');
        }
    }
}