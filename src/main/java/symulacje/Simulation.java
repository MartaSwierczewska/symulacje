package symulacje;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

public class Simulation {

    public class AnimationThread extends Thread {
        private boolean suspend = false;
        private boolean stop = false;

        public synchronized void wakeup() {
            suspend = false;
            notify();
        }

        public void safeStop() { stop = true; }

        public void safeSuspend() { suspend = true; }

        public void run(){
            while(!stop && board.isPersonOnBoard()) {

                synchronized (this) {
                    try {
                        if(suspend) {
                            System.out.println("Suspending");
                            wait();
                        }
                    } catch (InterruptedException e) { }
                }


                tickCounter++;
                detectFirePlaces();

                people.stream().forEach(p -> p.runToExit());
                firePlaces.stream().forEach(f -> f.spread());


                board.repaint();

                try { sleep(Params.sleepInterval); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            System.exit(0);
        }
    }

    private static Simulation simulationInstance = null;

    private Board board;
    private ArrayList <Person> people;
    public ArrayList <Fire> firePlaces;

    private AnimationThread animationThread;
    public long tickCounter = 0;


    public static Simulation getInstance() {
        if(simulationInstance == null)
            simulationInstance = new Simulation();
        return simulationInstance;
    }

    private Simulation() {
        this.initBoard();
        this.initPeople();
        this.initFire();

        animationThread = new AnimationThread();
        animationThread.start();
    }


    private void initBoard() {
        board = Board.getInstance();
        board.setSize(Params.boardLongitude * Params.cellDimension, Params.boardLatitude * Params.cellDimension);
    }

    private void initPeople() {
        people = new ArrayList<>();
        for(int i = 0; i < Params.peopleAmount; i++) {
            people.add(new Person(Params.EntityType.PERSON));
        }
    }

    private void initFire(){
        firePlaces = new ArrayList<>();
        firePlaces.add(new Fire(Params.EntityType.FIRE));
    }


    private void detectFirePlaces() {
        firePlaces.clear();

        board.getCellsAsArrayList().stream().forEach(c -> {
            if(c.getEntities().stream().anyMatch(e -> e.getEntityType() == Params.EntityType.FIRE))
                firePlaces.add(new Fire(Params.EntityType.FIRE, c));
        });
    }


}