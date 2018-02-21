package com.adCampaigh.controller;

import com.adCampaigh.error.AdAlreadThereException;
import com.adCampaigh.service.AdRepo;
import com.adCampaigh.DTO.AdCampaigh;
import com.adCampaigh.DTO.AddCampReq;
import com.adCampaigh.error.AdCampaighError;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
public class MainController {

    private final static Logger LOGGER = Logger.getLogger(MainController.class);

    @RequestMapping(value = "/ad", method = RequestMethod.POST)
    public AddCampReq createNewAd(@Valid  @RequestBody AddCampReq request) {
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("POST JSON Request got : "+request);
        }

        AdRepo.getInstance().add(request);

        return request;
    }

    @RequestMapping(value = "/ad/{partner_id}", method = RequestMethod.GET)
    public AdCampaigh getAd(@PathVariable("partner_id") String partnerId) {
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("GET Request got : "+partnerId);
        }
        return AdRepo.getInstance().getRepo().get(partnerId);
    }

    @ExceptionHandler(AdAlreadThereException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public AdCampaighError handleAdTimeConflictException(MethodArgumentNotValidException e){
        LOGGER.error(e);
        return new AdCampaighError("Ad Already Exists " + HttpStatus.BAD_REQUEST,"The Partner already have an existing AdCampaigh.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public AdCampaighError handleValidationException(MethodArgumentNotValidException e){
        LOGGER.error(e);
        return new AdCampaighError("Parameter Error " + HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public AdCampaighError handleException(Exception e){
        LOGGER.error(e);
        return new AdCampaighError("Something Unexpected Error " + HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error.");
    }
}
