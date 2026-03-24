package module5.pro.exceptions;

public class FoodNotFoundException extends  Exception{
    @Override
    public String getMessage(){
        return "Food Not Found";
    }
}
