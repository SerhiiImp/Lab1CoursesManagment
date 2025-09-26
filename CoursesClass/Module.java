package CoursesClass;

import java.util.List;

import ua.util.ValidationHelper;
import ua.util.Utils;
import java.util.Objects;

public class Module {
    private String title;
    private String content;

    private Module(String title, String content) {
        Utils.validateModule(title,content);

        this.content = content;
        this.title = title;
    }

    public static Module createModule(String title, String content){
        return new Module(title,content);
    }

    public String getTitle(){ return title;}

    public String getContent() {return content;}

    public void setTitle(String title){
        ValidationHelper.requireNonEmpty(title, "Module Title");
        this.title = title;
    }

    public void setContent(String content) {
        ValidationHelper.requireNonEmpty(content, "Module Content");
        this.content = content;
    }

    @Override
    public String toString(){
        return "Module {Title = \'" + title + ", Content = \'" + content + "\'.}";
    }
    public List<String> getModuletitle(){ return List.of(title,content);}

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Module other = (Module) obj;
        return Objects.equals( title, other.title);
    }

    @Override
    public int hashCode(){
        return Objects.hash(title,content);
    }
}