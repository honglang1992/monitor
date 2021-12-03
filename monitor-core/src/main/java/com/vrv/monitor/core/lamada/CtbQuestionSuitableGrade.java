package com.vrv.monitor.core.lamada;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @table ctb_question_suitable_grade
 * @author MyBatis Generator
 * @version 1.0.0, 2020-07-29 10:12:41
 */
public class CtbQuestionSuitableGrade implements Serializable {
    /**
     * 
     * 
     * @column ctb_question_suitable_grade.id
     */
    private Integer id;

    /**
     * 习题ID
     * 
     * @column ctb_question_suitable_grade.question_id
     */
    private Integer questionId;

    /**
     * 年级（学期）
     * 
     * @column ctb_question_suitable_grade.grade_term
     */
    private Integer gradeTerm;

    /**
     * 
     * 
     * @column ctb_question_suitable_grade.create_time
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * @column ctb_question_suitable_grade.id
     * 
     * @return 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @column ctb_question_suitable_grade.id
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @column ctb_question_suitable_grade.question_id
     * 
     * @return 习题ID
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * @column ctb_question_suitable_grade.question_id
     * 
     * @param questionId 习题ID
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * @column ctb_question_suitable_grade.grade_term
     * 
     * @return 年级（学期）
     */
    public Integer getGradeTerm() {
        return gradeTerm;
    }

    /**
     * @column ctb_question_suitable_grade.grade_term
     * 
     * @param gradeTerm 年级（学期）
     */
    public void setGradeTerm(Integer gradeTerm) {
        this.gradeTerm = gradeTerm;
    }

    /**
     * @column ctb_question_suitable_grade.create_time
     * 
     * @return 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @column ctb_question_suitable_grade.create_time
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}