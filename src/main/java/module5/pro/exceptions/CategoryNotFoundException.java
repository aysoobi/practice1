package module5.pro.exceptions;

public class CategoryNotFoundException extends  Exception {

    @Override
    public String getMessage(){
        return "Category Not found";
    }
}
