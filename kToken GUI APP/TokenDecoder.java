import java.io.*;
import java.util.zip.GZIPInputStream;

public class TokenDecoder {

    public static String decodeToken(File tokenFile) throws IOException {
        String decodedFilePath;
        
        try (FileInputStream fis = new FileInputStream(tokenFile);
             GZIPInputStream gzipIS = new GZIPInputStream(fis);
             DataInputStream dis = new DataInputStream(gzipIS)) {

            String originalFileName = dis.readUTF();
            long originalFileSize = dis.readLong();
            File outputFile = new File(tokenFile.getParent(), originalFileName);
            decodedFilePath = outputFile.getAbsolutePath();

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                int len;
                long totalBytesRead = 0;

                while ((len = dis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    totalBytesRead += len;

                    if (totalBytesRead >= originalFileSize) break;
                }
            }
        }
        return decodedFilePath; // Return decoded file path for GUI feedback
    }
}
