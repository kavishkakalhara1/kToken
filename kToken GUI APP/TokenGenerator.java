import java.io.*;
import java.util.zip.GZIPOutputStream;

public class TokenGenerator {

    public static String encodeToToken(File inputFile) throws IOException {
        String tokenFileName = inputFile.getName() + ".ktoken";
        File outputTokenFile = new File(inputFile.getParent(), tokenFileName);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputTokenFile);
             GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
             DataOutputStream dos = new DataOutputStream(gzipOS)) {

            dos.writeUTF(inputFile.getName());
            dos.writeLong(inputFile.length());

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, len);
            }
        }
        return outputTokenFile.getAbsolutePath(); // Return token path for GUI feedback
    }
}
