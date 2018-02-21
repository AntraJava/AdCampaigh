package com.adCampaigh.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AdCampaigh {
    private String adContent;
    private String partnerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date expiration;

    public AdCampaigh() {
    }

    public AdCampaigh(String adContent, String partnerId, Date expiration) {
        this.adContent = adContent;
        this.partnerId = partnerId;
        this.expiration = expiration;
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
