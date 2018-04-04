package com.predix.bidopscore.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Bidders entity. This class is used in BiddersResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /bidders?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BiddersCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter solicitationId;

    private StringFilter name;

    private StringFilter bidCategories;

    private LongFilter userId;

    private LongFilter biddersId;

    private LongFilter jhi_userId;

    private LongFilter solicitationsId;

    public BiddersCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(LongFilter solicitationId) {
        this.solicitationId = solicitationId;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getBidCategories() {
        return bidCategories;
    }

    public void setBidCategories(StringFilter bidCategories) {
        this.bidCategories = bidCategories;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getBiddersId() {
        return biddersId;
    }

    public void setBiddersId(LongFilter biddersId) {
        this.biddersId = biddersId;
    }

    public LongFilter getJhi_userId() {
        return jhi_userId;
    }

    public void setJhi_userId(LongFilter jhi_userId) {
        this.jhi_userId = jhi_userId;
    }

    public LongFilter getSolicitationsId() {
        return solicitationsId;
    }

    public void setSolicitationsId(LongFilter solicitationsId) {
        this.solicitationsId = solicitationsId;
    }

    @Override
    public String toString() {
        return "BiddersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (solicitationId != null ? "solicitationId=" + solicitationId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (bidCategories != null ? "bidCategories=" + bidCategories + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (biddersId != null ? "biddersId=" + biddersId + ", " : "") +
                (jhi_userId != null ? "jhi_userId=" + jhi_userId + ", " : "") +
                (solicitationsId != null ? "solicitationsId=" + solicitationsId + ", " : "") +
            "}";
    }

}
