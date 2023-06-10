package cipher;

import java.io.File;
import java.io.IOException;

public class CipherInit {

    File file = new File("temp.txt");

    public void initBefore() throws IOException {
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }

    public void initAfter() {
        if (file.exists()) {
            file.delete();
        }
    }
}
