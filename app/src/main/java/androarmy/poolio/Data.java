package androarmy.poolio;

/**
 * Created by kjaganmohan on 17/07/16.
 */
public class Data {

    public String id;
    public String first_name;
    public String last_name;
    public String gender;
    public String mobile;
    public String source;
    public String destination;
    public String type;
    public String date;
    public String time;
    public String vehicle_name;
    public String vehicle_number;
    public String seats;


    Data(String id ,String first_name,String last_name,String mobile,String gender, String source , String destination,String type,
         String date,String time,String vehicle_name,String vehicle_number,String seats){
        this.id = id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.gender=gender;
        this.mobile = mobile;
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.date = date;
        this.time = time;
        this.vehicle_name = vehicle_name;
        this.vehicle_number = vehicle_number;
        this.seats = seats;

    }


    public String getid(){
        return  id;
    }
    public String getFirst_name(){
        return  first_name;
    }
    public String getLast_name(){
        return  last_name;
    }
    public String getGender(){
        return  gender;
    }
    public String getMobile(){
        return  mobile;
    }
    public String getSource(){
        return source;
    }
    public String getDestination(){
        return destination;
    }
    public String getType(){
        return type;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }

    public String getVehicle_name(){
        return vehicle_name;
    }
    public String getVehicle_number(){
        return vehicle_number;
    }
    public String getSeats(){
        return seats;
    }



}
