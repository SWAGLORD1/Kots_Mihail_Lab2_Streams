import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class CompressionInputStream extends InputStream {

    private static final int BUFFER_SIZE = 4095;
    private static final int WORD_SIZE = 15;
    private static final int BYTE_SIZE = 256;

    private InputStream source;
    private byte[] buffer;
    private int length;
    private int count;


    public CompressionInputStream(InputStream str) {
        buffer = new byte[BUFFER_SIZE];
        source = str;
        length = 0;
        count = 0;
    }

    private void refillBuffer() throws IOException {
        ArrayList<Byte> result = new ArrayList<>();
        int c = source.read();
        while ((result.size() < BUFFER_SIZE) && (c != -1)) {
            if (c != 0) {
                for (int i = 0; i < c; i++) {
                    int d = source.read();
                    if (d != -1)
                        result.add((byte) d);
                    else
                        throw new IOException("File is not an archive");
                }
            } else {
                int a = source.read();
                int b = source.read();
                if (a == -1 || b == -1)
                    throw new IOException("File is not an archive");
                if (a < 0)
                    a += BYTE_SIZE;
                if (b < 0)
                    b += BYTE_SIZE;
                int place = (a * BYTE_SIZE + b) / (WORD_SIZE + 1);
                int size = (a * BYTE_SIZE + b) % (WORD_SIZE + 1);
                result.addAll(result.subList(place, place + size));
            }
            if(result.size() < BUFFER_SIZE)
                c = source.read();
        }
        length = result.size();
        count = 0;
        int i = 0;
        for (Byte b : result)
            buffer[i++] = b;
    }

    @Override
    public int read() throws IOException {
        if (count >= length)
            refillBuffer();
        if (length == 0)
            return -1;
        return buffer[count++];
    }

    @Override
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        int oldCount = count;
        for (int i = off; i < off + len; i++) {
            int d = read();
            if (d == -1)
                break;
            else
                b[i] = (byte) d;
        }
        if (count - oldCount > 0)
            return count - oldCount;
        else
            return -1;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

}
