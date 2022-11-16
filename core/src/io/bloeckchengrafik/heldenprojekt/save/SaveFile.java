package io.bloeckchengrafik.heldenprojekt.save;

import io.bloeckchengrafik.heldenprojekt.game.Gruppe;
import io.bloeckchengrafik.heldenprojekt.game.GruppenBuilder;
import io.bloeckchengrafik.heldenprojekt.utils.BinaryUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.harawata.appdirs.AppDirsFactory;

import java.io.*;
import java.util.HashMap;

@Getter
public class SaveFile {
    private final File saveFile;
    private final String dataDir;
    private SaveData data;

    @SneakyThrows
    public SaveFile() {
        dataDir = AppDirsFactory.getInstance()
                .getUserDataDir("Heldenprojekt", "0.1", "Chris B.", true);

        File dataDirFile = new File(dataDir);
        if (!dataDirFile.exists()) {
            dataDirFile.mkdirs();
        }

        System.setProperty("user.dir", dataDir);

        saveFile = new File(dataDirFile, "data.hpw");
        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }

        /* Read file contents */
        BufferedReader reader = new BufferedReader(new FileReader(saveFile));
        String fileContents = reader.readLine();
        reader.close();

        if (fileContents == null) {
            data = new SaveData();
        } else {
            data = (SaveData) BinaryUtils.fromBin(BinaryUtils.stringDecode(fileContents));
        }

        save();
    }

    @SneakyThrows
    public void save() {
        /* Write file contents */
        String fileContents = BinaryUtils.stringEncode(BinaryUtils.toBin(data));

        saveFile.delete();
        saveFile.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
        writer.write(fileContents);
        writer.close();
    }

    @Getter
    @Setter
    public static class SaveData implements Serializable {
        private static final long serialVersionUID = 1L;
        private int campX = 0;
        private int campY = 0;
        private Gruppe heldengruppe = new GruppenBuilder(4).fuelleMitHelden().build();
    }
}
