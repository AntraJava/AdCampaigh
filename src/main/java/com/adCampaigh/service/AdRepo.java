package com.adCampaigh.service;

import com.adCampaigh.DTO.AdCampaigh;
import com.adCampaigh.DTO.AddCampReq;
import com.adCampaigh.error.AdAlreadThereException;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdRepo {

    private static AdRepo instance = new AdRepo();

    private Map<String, AdCampaigh> repo = null;

    private AdRepo(){
        repo = new ConcurrentHashMap<String, AdCampaigh>();
    }

    public static AdRepo getInstance() {
        return instance;
    }

    public Map<String, AdCampaigh> getRepo() {
        return repo;
    }

    public void add(AddCampReq request) {
        if(existInRepo(request.getPartnerId())){
            throw new AdAlreadThereException();
        }
        AdCampaigh ad = new AdCampaigh();
        ad.setAdContent(request.getAdContent());
        ad.setPartnerId(request.getPartnerId());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,request.getDuration());
        ad.setExpiration(cal.getTime());
        repo.put(request.getPartnerId(),ad);
    }

    private boolean existInRepo(String partnerId) {
        boolean res = false;
        if (repo.containsKey(partnerId)) {
            AdCampaigh ad = repo.get(partnerId);
            if(new Date().after(ad.getExpiration())){//if expired
                res = true;
            }
        }
        return res;
    }
}
