import java.util.List;
import processing.core.PImage;

interface Entitys {
    public Point getposition();
    public void setposition(Point temp);
    public int getimageIndex();
    public List<PImage> getimages();
    public PImage getCurrentImage();
}