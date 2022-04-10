package body.bank.nbu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

@Data
public class JsonNBU {
        private int r030;
        private String txt;
        private float rate;
        private String cc;
        private String exchangedate;

        @Override
        public String toString() {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(this);
        }
}
