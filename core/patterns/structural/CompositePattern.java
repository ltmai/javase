import java.util.ArrayList;
import java.util.List;

/**
 * CompositePattern
 */
public class CompositePattern {

    /**
     * Component: the bussiness logic interface
     * 
     * This interface can be intergrated into Composite so that the 
     * concrete classes (File, Folder) do not have to implement both
     * interfaces. However since the methods are not much related we
     * separate them into two interfaces. 
     */
    public interface Component {

        int getSize();

        String getPath();
    }

    /**
     * Composite : The composite logic interface
     */
    public interface Composite {
        
        void addChild(Component child);

        void removeChild(Component child);

        List<Component> getChildren();
    }

    /**
     * File : concrete component
     */
    public static class File implements Component, Composite {
        private String filePath;

        public File(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public String getPath() {
            return filePath;
        }

        @Override
        public int getSize() {
            return filePath.length();
        }

        @Override
        public void addChild(Component child) {
            throw new RuntimeException("Oops!");
        }

        @Override
        public List<Component> getChildren() {
            throw new RuntimeException("Oops!");
        }

        @Override
        public void removeChild(Component child) {
            throw new RuntimeException("Oops!");
        }
    }

    /**
     * Folder : concrete component
     */
    public static class Folder implements Component, Composite {
        
        private String folder;
        private List<Component> children = new ArrayList<>();

        public Folder(String folder) {
            this.folder = folder;
        }

        @Override
        public String getPath() {
            return folder;
        }

        @Override
        public int getSize() {
            return children.stream().mapToInt(Component::getSize).sum();
        }

        @Override
        public void addChild(Component child) {
            children.add(child);
        }

        @Override
        public List<Component> getChildren() {
            return children;
        }

        @Override
        public void removeChild(Component child) {
            children.remove(child);
        }
    }
    

    public static void main(String[] args) {
        Folder folder = new Folder("/file/path");
        File file1 = new File("/file/path/File1");
        File file2 = new File("/file/path/File2");
        File file3 = new File("/file/path/File3");
        
        folder.addChild(file1);
        folder.addChild(file2);
        folder.addChild(file3);

        System.out.println(String.format("Folder size: %d bytes", folder.getSize()));
    }
}