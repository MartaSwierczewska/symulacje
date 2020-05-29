package symulacje;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;


public class Simulation {

    private static Simulation simulationInstance = null;
    public static HashMap< Cell, Long>  cellsWithBurnTime;

    private Board board;
    private ArrayList <Person> people;
    public ArrayList <Fire> firePlaces;

    public AnimationThread animationThread;
    public long tickCounter = 0;

    public long timeOfSimulation=0L;

    public static Simulation getInstance() {
        if(simulationInstance == null)
            simulationInstance = new Simulation();
        return simulationInstance;
    }

    private Simulation() {
        animationThread = new AnimationThread();
    }

    public void start() {
        this.initBoard();
        this.initFire();
        this.initPeople();

        animationThread.start();
        System.out.println("Simulation started successfully...");
    }

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
            LocalDateTime startTime = LocalDateTime.now();
            while(!stop) {
                // in case of suspending simulation, maybe it will be added in the future
                synchronized (this) {
                    try {
                        if(suspend) {
                            // System.out.println("Suspending");
                            wait();
                        }
                    } catch (InterruptedException e) { }
                }

                tickCounter++;
                // updating fire places list
                detectFirePlaces();

                // actions of people and fire entities
                people.forEach(Person::runToExit);
                firePlaces.forEach(Fire::spread);


                deactivateEvacuated();
                deactivateDead();

                // repainting all components
                board.repaint();

                // System.out.println(String.format("Dead: %d\nEvacuated: %d\nStill fighting: %d\n\n", countDead(), countEvacuated(), Params.peopleAmount - (countDead() + countEvacuated())));

                // checking for break conditions
                if(!board.isPersonOnBoard()) {
                    System.out.println("Simulation finished!");
                    LocalDateTime endTime = LocalDateTime.now();
                    countTimeOfEvacuation(startTime, endTime);

                    break;
                }
                //System.out.println(board.howManyBurned());

                // sleep
                try { sleep(Params.sleepInterval); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            try { sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            MainFrame.getInstance().simulationStopped();

        }
    }


    private void initBoard() {
        board = Board.getInstance();
        board.setSize(Params.boardLongitude * Params.cellDimension, Params.boardLatitude * Params.cellDimension);
    }

    public void initPeople() {
        people = new ArrayList<>();
        for(int i = 0; i < Params.peopleAmount; i++) {
            people.add(new Person(Params.EntityType.PERSON));
        }
    }

    private void initFire(){
        cellsWithBurnTime = new HashMap<>();
        firePlaces = new ArrayList<>();
        firePlaces.add(new Fire(Params.EntityType.FIRE));
    }


    private void detectFirePlaces() {
        firePlaces.clear();
        board.getCellsAsArrayList().forEach(c -> {
            if(c.getEntities().stream().anyMatch(e -> e.getEntityType() == Params.EntityType.FIRE))
                firePlaces.add(new Fire(Params.EntityType.FIRE, c));
        });

        for (Fire firePlace : firePlaces) {
            if (!cellsWithBurnTime.containsKey(firePlace.currentCell)) {
                cellsWithBurnTime.put(firePlace.currentCell, Simulation.getInstance().tickCounter - 1);
            }
        }
    }


    private void deactivateEvacuated() {
        people.stream().filter(person -> person.getCell().getCellType() == Params.CellType.EXIT).forEach(person -> person.setActive(false));
    }

    private void deactivateDead() {
        people.stream().filter(person ->
                person.getCell().getEntities().stream().anyMatch(entity -> entity.getEntityType() == Params.EntityType.FIRE))
                .forEach(person -> person.setActive(false));
    }

    public long countDead() {
        return people.stream().filter(person ->
                person.getCell().getEntities().stream().anyMatch(entity -> entity.getEntityType() == Params.EntityType.FIRE))
                .count();
    }

    public long countEvacuated() {
        return people.stream().filter(person -> person.getCell().getCellType() == Params.CellType.EXIT).count();
    }

    public void countTimeOfEvacuation(LocalDateTime start, LocalDateTime end){
        timeOfSimulation = Duration.between(start, end).toSeconds();
    }

    public long getTimeOfEvacuation(){
        return timeOfSimulation;
    }

    public long countFireCellsCoverage(){
        Board board = Board.getInstance();
        return board.countFireCells()/(Params.boardLatitude*Params.boardLongitude)*100;
    }
}
