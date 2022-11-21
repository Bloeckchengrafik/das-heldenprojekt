import java.io.*;

public class BinaryUtils {
    public static byte[] toBin(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        throw new RuntimeException();
    }

    public static Object fromBin(byte[] bytes)
            throws IOException, ClassNotFoundException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return objectInputStream.readObject();
        }
    }

    public static String stringEncode(byte[] entitydata) {
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        StringBuilder sb = new StringBuilder();
        for (byte b : entitydata) {
            sb.append(alphabet.charAt((b >> 4) & 0xF));
            sb.append(alphabet.charAt(b & 0xF));
        }

        return sb.toString();
    }

    public static byte[] stringDecode(String string) {
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        byte[] res = new byte[string.length() / 2];
        for (int i = 0; i < string.length(); i += 2) {
            res[i / 2] = (byte) (alphabet.indexOf(string.charAt(i)) << 4 | alphabet.indexOf(string.charAt(i + 1)));
        }

        return res;
    }
}
