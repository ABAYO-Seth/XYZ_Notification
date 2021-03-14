package rw.gov.irembo.notification.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.gov.irembo.notification.domain.Subsciber;
import rw.gov.irembo.notification.domain.ValidatePerSecond;
import rw.gov.irembo.notification.repository.AppRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Service
public class AppService {

    @Autowired
    private AppRepository repo;

    private HashMap<String, ValidatePerSecond> validate = new HashMap<>();

    public String saveSubscribution(Subsciber subsciber){
        if(subsciber.getSubscriberPlan().equalsIgnoreCase("premium")){
         subsciber.setRequestPerSecond(10);
         subsciber.setRequestPerMonth(30);
        }else{
          subsciber.setRequestPerSecond(5);
           subsciber.setRequestPerMonth(10);
        }
        subsciber.setRequest(0);
        subsciber.setSubscriptionStartDate(LocalDate.now());
        subsciber.setSubscriptionEndDate(subsciber.getSubscriptionStartDate().plusDays(30));
        Subsciber sub = repo.save(subsciber);
        if(sub.getId() !=0){
            sub.setSubscriberKey(generateApiKey(sub.getId()+""));
            return repo.save(sub).getSubscriberKey();
        }else {
            return "Subscriber not saved";
        }
    }

    public String generateApiKey(String id) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 12;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        System.out.println("Generated API Key is=>=>=>"+generatedString);
        return generatedString+id;
    }

    public String checkSubscription(String key){
        Subsciber sub = repo.findBySubscriberKey(key);
        if(sub.getId()!= 0){
            if(sub.getSubscriberPlan().equalsIgnoreCase("premium")){
                return "true";
            }else{
                return "false";
            }
        }else {
            return null;
        }
    }
//
//    public boolean validateRequestPerSecond(String key){
//        Subsciber sub = getSubscriber(key);
//        if(sub!=null){
//            if(validate.get(key)!=null){
//                ValidatePerSecond val = validate.get(key);
//                val.setCount(val.getCount()+1);
//            }else{
//
//            }
//        }else{
//
//        }
//
//
//    }

   public Subsciber getByKey(String key){
       Subsciber sub = repo.findBySubscriberKey(key);
       if(sub.getId()!= 0){
          return sub;
       }else {
           return null;
       }
   }
    public boolean countSessions(String key){
       Subsciber sub = getByKey(key);
        if(sub.getSubscriptionEndDate().compareTo(LocalDate.now())>0){
            if(sub.getRequest()>= sub.getRequestPerMonth()){
                return false;
            }else{
                sub.setRequest(sub.getRequest()+1);
                repo.save(sub);
                return true;
              
                
            }
        }else{
            return false;
        }
    }

}
