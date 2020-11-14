import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class DNAReader {

    private final String filePath;

    public DNAReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> take(int countOfPortions) {
        List<String> portions = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String line = reader.readLine();
            for (int i = 0; i < line.length() - countOfPortions + 1; i++) {
                portions.add(line.substring(i, i + countOfPortions));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return portions;
    }
}
