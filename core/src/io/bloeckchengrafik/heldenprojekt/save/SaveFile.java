package io.bloeckchengrafik.heldenprojekt.save;

import io.bloeckchengrafik.heldenprojekt.game.Gruppe;
import io.bloeckchengrafik.heldenprojekt.game.GruppenBuilder;
import io.bloeckchengrafik.heldenprojekt.utils.BinaryUtils;
import net.harawata.appdirs.AppDirsFactory;

import java.io.*;

public class SaveFile {
    private final File saveFile;
    private final String dataDir;
    private final SaveData data;

    public SaveFile() throws RuntimeException {
        dataDir = AppDirsFactory.getInstance()
                .getUserDataDir("Heldenprojekt", "0.1", "Chris B.", true);

        File dataDirFile = new File(dataDir);
        if (!dataDirFile.exists()) {
            dataDirFile.mkdirs();
        }

        System.setProperty("user.dir", dataDir);

        saveFile = new File(dataDirFile, "data.hpw");
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /* Read file contents */
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(saveFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String fileContents = null;
        try {
            fileContents = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (fileContents == null) {
            data = new SaveData();
        } else {
            try {
                data = (SaveData) BinaryUtils.fromBin(BinaryUtils.stringDecode(fileContents));
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        save();
    }

    public void save() {
        /* Write file contents */
        String fileContents = BinaryUtils.stringEncode(BinaryUtils.toBin(data));

        saveFile.delete();
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(fileContents);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDataDir() {
        return this.dataDir;
    }

    public SaveData getData() {
        return this.data;
    }

    public static class SaveData implements Serializable {
        private static final long serialVersionUID = 1L;
        private int campX = 0;
        private int campY = 0;
        private Gruppe heldengruppe = new GruppenBuilder(4).fuelleMitHelden().build();
        private float scale = 1.0f;

        public int getCampX() {
            return this.campX;
        }

        public int getCampY() {
            return this.campY;
        }

        public Gruppe getHeldengruppe() {
            return this.heldengruppe;
        }

        public float getScale() {
            return this.scale;
        }

        public void setCampX(int campX) {
            this.campX = campX;
        }

        public void setCampY(int campY) {
            this.campY = campY;
        }

        public void setHeldengruppe(Gruppe heldengruppe) {
            this.heldengruppe = heldengruppe;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }
    }
}
