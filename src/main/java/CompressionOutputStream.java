import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CompressionOutputStream extends OutputStream {

    private static final int BUFFER_SIZE = 4095;
    private static final int WORD_SIZE = 15;
    private static final int BYTE_SIZE = 256;

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
        ArrayList<Byte> uncodedPart = new ArrayList<>();
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
                Byte[] codedString = {0, (byte) (code / BYTE_SIZE), (byte) (code % BYTE_SIZE)};
                if (uncodedPart.toArray().length + currentWord.toArray().length >= BYTE_SIZE - 1) {
                    result.add((byte) uncodedPart.toArray().length);
                    result.addAll(uncodedPart);
                    uncodedPart.clear();
                }
                if (codedString.length < currentWord.size()) {
                    if(!uncodedPart.isEmpty()) {
                        result.add((byte) uncodedPart.toArray().length);
                        result.addAll(uncodedPart);
                        uncodedPart.clear();
                    }
                    result.addAll(Arrays.asList(codedString));
                } else {
                    uncodedPart.addAll(currentWord);
                }
                searchBuffer.addAll(currentWord);
                currentWord.clear();
                currentWord.add(last);
                position = 0;
            }
        }
        int code = position * (WORD_SIZE + 1) + currentWord.size();
        Byte[] codedString = {0, (byte) (code / BYTE_SIZE), (byte) (code % BYTE_SIZE)};
        if (uncodedPart.toArray().length + currentWord.toArray().length >= BYTE_SIZE - 1) {
            result.add((byte) uncodedPart.toArray().length);
            result.addAll(uncodedPart);
            uncodedPart.clear();
        }
        if (codedString.length < currentWord.size()) {
            if(!uncodedPart.isEmpty()) {
                result.add((byte) uncodedPart.toArray().length);
                result.addAll(uncodedPart);
                uncodedPart.clear();
            }
            result.addAll(Arrays.asList(codedString));
        } else {
            uncodedPart.addAll(currentWord);
        }
        if(!uncodedPart.isEmpty()) {
            result.add((byte) uncodedPart.toArray().length);
            result.addAll(uncodedPart);
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
        write(b, 0, b.length);
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
    public void close() throws IOException {
        flush();
        target.close();
    }

}
