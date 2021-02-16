/**
 * ProcessStarter
 * Start command in a separate process and monitor it for 60 seconds.
 * If the process exits during this time, restart it again.
 */
public class ProcessStarter {

    public void startProcess(String[] args) {
        try {
            Process process = Runtime.getRuntime().exec(args);
            for (int i = 1; i < 60; i++) {
                if (!process.isAlive()) {
                    System.out.println("process not alive: " + process);
                    process = Runtime.getRuntime().exec(args);
                }
                else {
                    Thread.sleep(1000);
                }
            }    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void debug(ProcessHandle p){
        if (p.info().command().isPresent())
            System.out.println(p.pid() + ": " + p.info().command());
        if (p.info().arguments().isPresent())
            System.out.println("arguments: " + p.info().arguments());
        if (p.parent().isPresent()) {
            System.out.println("   started by " + p.parent().get().pid());
        }
    }

    public boolean isAlive(String[] args) {
        ProcessHandle.allProcesses().forEach(p->{
            debug(p);
            //p.children().forEach(this::debug);
        });

        return true;
    }

    public static void main(String[] args) {
        new ProcessStarter().startProcess(args);
        //new ProcessStarter().isAlive(args);
    }
}