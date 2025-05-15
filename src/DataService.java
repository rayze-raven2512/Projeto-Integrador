import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private static final String FILE_NAME = "notas.json";
    private static final Gson gson = new Gson();

    public static void salvarNotas(List<Nota> notas) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(notas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Nota> carregarNotas() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<ArrayList<Nota>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>(); // Se o arquivo não existir, volta lista vazia
        }
    }
}
