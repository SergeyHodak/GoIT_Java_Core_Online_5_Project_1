import body.response.DataCaching;

public class TestBanks {
    public static void main(String[] args){

        DataCaching dataCache = DataCaching.getInstance();
        Thread dcThread = new Thread(dataCache);
        dcThread.start();

    }
}
