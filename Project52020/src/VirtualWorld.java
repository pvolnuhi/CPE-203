import processing.core.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
VirtualWorld is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */

//Only start actions for entities that include action (not those with just animations)
//         if (entity.actionPeriod > 0)
//            Functions.scheduleActions(entity, scheduler, world, imageStore);

public final class VirtualWorld
   extends PApplet {
    private static final int TIMER_ACTION_PERIOD = 100;

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    private static final String LOAD_FILE_NAME = "world.sav";

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    public static final String SPONGEBOB_ID_PREFIX = "spongebob -- ";

    public static final String SPONGEBOB_KEY = "spongebob";

    public static final String PATRICK_KEY = "patrick";

    public static final String PATRICK_ID_PREFIX = "patrick -- ";

    public static final Random rand1 = new Random();

    private static double timeScale = 1.0;

    public ImageStore imageStore;
    public WorldModel world;
    public WorldView view;
    public EventScheduler scheduler;

    private boolean check = false;
    public long next_time;

    private List<Point> path;

    public void settings()
    {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
//    public static void main(String [] args)
//    {
//        parseCommandLine(args);
//        PApplet.main(VirtualWorld.class);
//    }
    public static void parseCommandLine(String [] args)
    {
        for (String arg : args)
        {
            switch (arg)
            {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    public static void scheduleActions(WorldModel world,
                                       EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entitys entity : world.getentities())
        {
            //Only start actions for entities that include action (not those with just animations)

            if(entity instanceof Actionable){
                if (((Actionable)entity).getactionPeriod() > 0){
                    if(entity instanceof Move){
                        ((AnimatedEntity)entity).scheduleActions(scheduler, world, imageStore);

                    }else{
                        ((Actionable)entity).scheduleActions(scheduler, world, imageStore, ((ActiveEntity)entity).actionPeriod);
                    }
                }
            }
        }

    }

    public void setup() {

        path = new LinkedList<>(); //check
        this.imageStore = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
                TILE_WIDTH, TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
            if(check){
                int x = view.getviewPort().getCol();
                int y = view.getviewPort().getRow();
                Point temp = new Point((this.mouseX/TILE_WIDTH+x), this.mouseY/TILE_HEIGHT+y);
                String id =  "pineapple";
                int count = 0;
                for(int j = -1; j< 2;j++){
                    for(int i = -1;i<2;i++){
                        Point temp2 = new Point(temp.x+i,temp.y+j);
                        Optional<Entitys> occupent = world.getOccupant(temp2);
                        world.setBackground(temp2,new Background(id,imageStore.getImageList(id+count)));
                        count++;
                        if(occupent.isPresent() && (occupent.get().getClass() == Crab.class || occupent.get().getClass() == Crab.class)){
                            world.removeEntityAt(temp2);
                            Patrick pat = new Patrick(PATRICK_ID_PREFIX + rand1.nextInt(), temp2, imageStore.getImageList(PATRICK_KEY), 100, 10, path);
                            world.addEntity(pat);
                            pat.scheduleActions( scheduler, world, imageStore);
                        }
                    }
                }
                if(!world.isOccupied(temp)) {
                    Spongebob temp1 = new Spongebob(SPONGEBOB_ID_PREFIX + rand1.nextInt(), temp, imageStore.getImageList(SPONGEBOB_KEY), 100, 100, path);
                    world.addEntity(temp1);
                    temp1.scheduleActions(scheduler, world, imageStore);
                }

                check =!check;
            }

            long time = System.currentTimeMillis();
            if (time >= next_time)
            {
                this.scheduler.updateOnTime(time);
                next_time = time + TIMER_ACTION_PERIOD;
            }

            view.drawViewport();
        }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            view.shiftView(dx, dy);
        }
    }

    private static void loadImages(String filename, ImageStore imageStore,
                                   PApplet screen) {
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in, screen);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadWorld(WorldModel world, String filename,
                                 ImageStore imageStore) {
        try {
            Scanner in = new Scanner(new File(filename));
            world.load(in, imageStore);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void mousePressed(){
        check = !check;
    }

    public static void main(String [] args)
    {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }
}

