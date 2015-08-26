package su.shev4enkostr.easycode;

/**
 * Created by stas on 25.08.15.
 */
public class Courses
{
    private String name;
    private String about;
    private int pictureId;

    public Courses(String name, String about, int pictureId)
    {
        this.name = name;
        this.about = about;
        this.pictureId = pictureId;
    }

    public String getName()
    {
        return name;
    }

    public String getAbout()
    {
        return about;
    }

    public int getPictureId()
    {
        return pictureId;
    }
}
