import processing.core.PImage;

import java.util.List;

public class Atlantis implements Entity{

    private Point position;
    private List<PImage> images;

    public Atlantis(String id, Point position, List<PImage> images)
    {
        this.position = position;
        this.images = images;
    }

    public Point getPosition()
    {
       return position;
    }

    public void setPosition(Point newPosition)
    {
        position = newPosition;
    }

    public PImage getCurrentImage()
    {
        return images.get(0);
    }

}
