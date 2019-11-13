import java.util.ArrayList;
import java.util.List;

/**
 * CompositePattern
 */
public class CompositePattern {

    /**
     * Component: the bussiness logic interface
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
    public static class File implements Component {
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
    
    /**
     * Client code knows nothing about the difference
     * between File and Folder and treats them equally.
     */
    public static class Client {
        public void process(Component component) {
            System.out.println(String.format("Component %s size: %d bytes", component.getPath(), component.getSize()));    
        }
    }

    public static void main(String[] args) {
        Folder folder = new Folder("/folder/path");
        File file1 = new File("/folder/path/file1");
        File file2 = new File("/folder/path/file2");
        File file3 = new File("/folder/path/file3");
        
        folder.addChild(file1);
        folder.addChild(file2);
        folder.addChild(file3);

        Client client = new Client();
        
        client.process(file1);
        client.process(file2);
        client.process(file3);
        client.process(folder);
    }
}