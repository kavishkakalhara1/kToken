import java.io.*;
import java.nio.file.*;
import java.util.zip.GZIPOutputStream;

public class TokenGenerator {

    // Method to encode a file to a .ktoken
    public static void encodeToToken(File inputFile) throws IOException {
        String tokenFileName = inputFile.getName() + ".ktoken";
        File outputTokenFile = new File(inputFile.getParent(), tokenFileName);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputTokenFile);
             GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
             DataOutputStream dos = new DataOutputStream(gzipOS)) {

            // Store original file name and size in token for decoding
            dos.writeUTF(inputFile.getName());
            dos.writeLong(inputFile.length());

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, len);
            }
            System.out.println("Token generated: " + outputTokenFile.getAbsolutePath());
        }
    }
    
    public static void main(String[] args) {
        // Example usage:
        if (args.length != 1) {
            System.err.println("Usage: java TokenGenerator <file-path>");
            System.exit(1);
        }
        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            System.err.println("Input file does not exist.");
            System.exit(1);
        }
        try {
            encodeToToken(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
