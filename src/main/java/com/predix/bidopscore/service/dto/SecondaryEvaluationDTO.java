package com.predix.bidopscore.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SecondaryEvaluation entity.
 */
public class SecondaryEvaluationDTO implements Serializable {

    private Long id;

    private Long user_id;

    private Long evaluator_id;

    private String doc_url;

    private Long solicitation_id;

    private Integer score;
    
    private Integer minimum_score;

    public Integer getMinimum_score() {
		return minimum_score;
	}

	public void setMinimum_score(Integer minimum_score) {
		this.minimum_score = minimum_score;
	}

	private String eligible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getEvaluator_id() {
        return evaluator_id;
    }

    public void setEvaluator_id(Long evaluator_id) {
        this.evaluator_id = evaluator_id;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public Long getSolicitation_id() {
        return solicitation_id;
    }

    public void setSolicitation_id(Long solicitation_id) {
        this.solicitation_id = solicitation_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getEligible() {
        return eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SecondaryEvaluationDTO secondaryEvaluationDTO = (SecondaryEvaluationDTO) o;
        if(secondaryEvaluationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), secondaryEvaluationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SecondaryEvaluationDTO{" +
            "id=" + getId() +
            ", user_id=" + getUser_id() +
            ", evaluator_id=" + getEvaluator_id() +
            ", doc_url='" + getDoc_url() + "'" +
            ", solicitation_id=" + getSolicitation_id() +
            ", score=" + getScore() +
            ", eligible='" + getEligible() + "'" +
            "}";
    }
}
