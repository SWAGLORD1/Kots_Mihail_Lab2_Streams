import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CompressionOutputStream extends OutputStream {

    private static final int BUFFER_SIZE = 4095;
    private static final int WORD_SIZE = 15;

    private OutputStream target;
    private byte[] buffer;
    private int length;

    public CompressionOutputStream(OutputStream str) {
        buffer = new byte[BUFFER_SIZE];
        target = str;
        length = 0;
    }

    private void emptyBuffer() throws IOException {
        ArrayList<Byte> searchBuffer = new ArrayList<>();
        ArrayList<Byte> currentWord = new ArrayList<>();
        ArrayList<Byte> result = new ArrayList<>();
        int index;
        int position = 0;
        for (int i = 0; i < length; i++) {
            currentWord.add(buffer[i]);
            index = Collections.indexOfSubList(searchBuffer, currentWord);
            if (index != -1 && currentWord.size() <= WORD_SIZE) {
                position = index;
            } else {
                Byte last = currentWord.remove(currentWord.size() - 1);
                int code = position * (WORD_SIZE + 1) + currentWord.size();
                Byte[] codedString = {1, (byte) (code / 256), (byte) (code % 256)};
                if (codedString.length < currentWord.size()) {
                    result.addAll(Arrays.asList(codedString));
                } else {
                    for(byte b : currentWord) {
                        result.add((byte) 0);
                        result.add(b);
                    }
                }
                searchBuffer.addAll(currentWord);
                currentWord.clear();
                currentWord.add(last);
                position = 0;
            }
        }
        int code = position * (WORD_SIZE + 1) + currentWord.size();
        Byte[] codedString = {1, (byte) (code / 256), (byte) (code % 256)};
        if (codedString.length < currentWord.size()) {
            result.addAll(Arrays.asList(codedString));
        } else {
            for(byte b : currentWord) {
                result.add((byte) 0);
                result.add(b);
            }
        }
        for (Byte b : result)
            target.write(b);
        length = 0;
    }

    @Override
    public void write(int b) throws IOException {
        if (length >= BUFFER_SIZE)
            emptyBuffer();
        buffer[length++] = (byte) b;
    }

    public void write(byte b[]) throws IOException {
        write(b,0,b.length);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        for (int i = off; i < off + len; i++)
            write(b[i]);
    }

    @Override
    public void flush() throws IOException {
        emptyBuffer();
        target.flush();
    }

    @Override
    public void close() throws  IOException {
        flush();
        target.close();
    }

}
