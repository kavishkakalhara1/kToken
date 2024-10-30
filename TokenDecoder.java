import java.io.*;
import java.util.zip.GZIPInputStream;

public class TokenDecoder {

    // Method to decode a .ktoken file back to the original file
    public static void decodeToken(File tokenFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(tokenFile);
             GZIPInputStream gzipIS = new GZIPInputStream(fis);
             DataInputStream dis = new DataInputStream(gzipIS)) {

            // Read the original file name and size
            String originalFileName = dis.readUTF();
            long originalFileSize = dis.readLong();

            File outputFile = new File(tokenFile.getParent(), originalFileName);
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {

                byte[] buffer = new byte[1024];
                int len;
                long totalBytesRead = 0;

                while ((len = dis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    totalBytesRead += len;

                    if (totalBytesRead >= originalFileSize) {
                        break; // Stop if we've read the expected number of bytes
                    }
                }
                System.out.println("File decoded: " + outputFile.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        // Example usage:
        if (args.length != 1) {
            System.err.println("Usage: java TokenDecoder <token-file-path>");
            System.exit(1);
        }
        File tokenFile = new File(args[0]);
        if (!tokenFile.exists() || !tokenFile.getName().endsWith(".ktoken")) {
            System.err.println("Invalid token file.");
            System.exit(1);
        }
        try {
            decodeToken(tokenFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
