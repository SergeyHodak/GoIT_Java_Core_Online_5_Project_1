package body.bank.nbu;

import java.io.IOException;

public class ParserTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        ParserNBU parser = new ParserNBU();
        System.out.println(parser.buy(Currency.USD));
    }
}
