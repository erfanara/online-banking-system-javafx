package obsjApp.server;

import obsjApp.core.User;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class UserStorage {
    private final File outputDir;

    // Map for loading users into the ram for faster operations, Map<NationalCode,>
    private static final Map<String, User> usersMap = new HashMap<String, User>();

    public UserStorage(String outputDir) {
        this.outputDir = new File(outputDir);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(outputDir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    usersMap.put(path.getFileName().toString(), readUserFromBlk(path.getFileName().toString()));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeUser(User user) {
        // first overwrite that user in map
        usersMap.put(user.getNationalCode(), user);
        // the overwrite in block device
        try (
                ObjectOutputStream out =
                        new ObjectOutputStream(
                                new FileOutputStream(new File(outputDir, user.getNationalCode())))
        ) {
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User readUserFromBlk(String nationalCode)
            throws IOException, ClassNotFoundException {
        try (
                ObjectInputStream in =
                        new ObjectInputStream(
                                new FileInputStream(new File(outputDir, nationalCode)))
        ) {
            return (User) (in.readObject());
        }
    }

    public User readUser(String nationalCode) {
        return usersMap.get(nationalCode);
    }

    public boolean userExist(String nationalCode) {
        return usersMap.containsKey(nationalCode);
    }

    public boolean userExist(User user) {
        return usersMap.containsValue(user);
    }

    private boolean userExistInBlk(String nationalCode) {
        try (
                FileInputStream fin = new FileInputStream(new File(outputDir, nationalCode))
        ) {
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
