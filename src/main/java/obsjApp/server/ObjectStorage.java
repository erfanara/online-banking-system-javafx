package obsjApp.server;

import obsjApp.core.User;

import java.io.*;

public final class ObjectStorage {
    private final File outputDir;

    public ObjectStorage(String outputDir) {
        this.outputDir = new File(outputDir);
    }

    public void writeUser(User user) {
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

    public User readUser(String nationalCode)
            throws FileNotFoundException, ClassNotFoundException {
        try (
                ObjectInputStream in =
                        new ObjectInputStream(
                                new FileInputStream(new File(outputDir, nationalCode)))
        ) {
            return (User) (in.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean userExist(String nationalCode) {
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
