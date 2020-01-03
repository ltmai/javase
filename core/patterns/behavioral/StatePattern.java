/**
 * StatePattern
 */
public class StatePattern {
    
    /**
     * The context class
     */
    static class AudioPlayer {

        private State state; 
        private boolean isPlaying = false;

        public AudioPlayer() {
            state = new ReadyState(this);
        }

        public void setState(State state) {
            System.out.println("New state: " + state.getStateName());
            this.state = state;
        }

        public boolean isPlaying() {
            return isPlaying;
        }

        public void setPlaying(boolean isPlaying) {
            this.isPlaying = isPlaying;
        }

        public void lockClicked() {
            System.out.println("Lock clicked");
            state.lockClicked();
        }

        public void playClicked() {
            System.out.println("Play clicked");
            state.playClicked();
        }

        public void stopClicked() {
            System.out.println("Stop clicked");
            state.stopClicked();
        }

        private void play() {
            setPlaying(true);
            System.out.println("Playing music: la la la");
        }

        private void stop() {
            setPlaying(false);
            System.out.println("Stop playing music");
        }

        /**
         * The abstract State
         */
        abstract class State {
            
            protected AudioPlayer player;

            public State(AudioPlayer player) {
                this.player = player;
            }

            public abstract String getStateName();

            public abstract void lockClicked();
            public abstract void playClicked();
            public abstract void stopClicked();
        }

        /**
         * Concrete state: Locked
         */
        class LockedState extends State {

            private static final String STATE_NAME = "LOCKED"; 

            public LockedState(AudioPlayer player) {
                super(player);
            }

            @Override
            public String getStateName() {
                return STATE_NAME;
            }
        
            /**
             * When it is locked, click lock to unlock
             */
            @Override
            public void lockClicked() {
                if (player.isPlaying())
                    player.setState(new PlayingState(player));
                else
                    player.setState(new ReadyState(player));
            }

            @Override
            public void playClicked() {
            }

            @Override
            public void stopClicked() {
            }
        }

        /**
         * Concrete state: Playing
         */
        class PlayingState extends State {

            private static final String STATE_NAME = "PLAYING";

            public PlayingState(AudioPlayer player) {
                super(player);
            }

            @Override
            public String getStateName() {
                return STATE_NAME;
            }

            @Override
            public void lockClicked() {
                player.setState(new LockedState(player));
            }

            @Override
            public void playClicked() {
            }

            @Override
            public void stopClicked() {
                player.stop();
                player.setState(new ReadyState(player));
            }

        }

        /**
         * Concrete state: READY
         */
        class ReadyState extends State {

            private static final String STATE_NAME = "READY";

            public ReadyState(AudioPlayer player) {
                super(player);
            }

            @Override
            public String getStateName() {
                return STATE_NAME;
            }
            
            @Override
            public void lockClicked() {
                player.setState(new LockedState(player));
            }

            @Override
            public void playClicked() {
                player.play();
            }

            @Override
            public void stopClicked() {
            }
        }

    } /* AudioPlayer */ 

    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();

        player.playClicked();
        player.lockClicked();
        player.stopClicked();
        player.lockClicked();
        player.stopClicked();
    }
}