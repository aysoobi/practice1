package module5.pro.exceptions;

public class CountryNotFoundException extends  Exception{

    @Override
    public String getMessage(){
        return "Country Not Found";
    }
}
