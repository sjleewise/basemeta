package kr.wise.meta.dbc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WatDbcSeqExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WatDbcSeqExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andDbSchIdIsNull() {
            addCriterion("DB_SCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andDbSchIdIsNotNull() {
            addCriterion("DB_SCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDbSchIdEqualTo(String value) {
            addCriterion("DB_SCH_ID =", value, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdNotEqualTo(String value) {
            addCriterion("DB_SCH_ID <>", value, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdGreaterThan(String value) {
            addCriterion("DB_SCH_ID >", value, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdGreaterThanOrEqualTo(String value) {
            addCriterion("DB_SCH_ID >=", value, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdLessThan(String value) {
            addCriterion("DB_SCH_ID <", value, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdLessThanOrEqualTo(String value) {
            addCriterion("DB_SCH_ID <=", value, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdLike(String value) {
            addCriterion("DB_SCH_ID like", value, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdNotLike(String value) {
            addCriterion("DB_SCH_ID not like", value, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdIn(List<String> values) {
            addCriterion("DB_SCH_ID in", values, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdNotIn(List<String> values) {
            addCriterion("DB_SCH_ID not in", values, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdBetween(String value1, String value2) {
            addCriterion("DB_SCH_ID between", value1, value2, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbSchIdNotBetween(String value1, String value2) {
            addCriterion("DB_SCH_ID not between", value1, value2, "dbSchId");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmIsNull() {
            addCriterion("DBC_SEQ_NM is null");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmIsNotNull() {
            addCriterion("DBC_SEQ_NM is not null");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmEqualTo(String value) {
            addCriterion("DBC_SEQ_NM =", value, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmNotEqualTo(String value) {
            addCriterion("DBC_SEQ_NM <>", value, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmGreaterThan(String value) {
            addCriterion("DBC_SEQ_NM >", value, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmGreaterThanOrEqualTo(String value) {
            addCriterion("DBC_SEQ_NM >=", value, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmLessThan(String value) {
            addCriterion("DBC_SEQ_NM <", value, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmLessThanOrEqualTo(String value) {
            addCriterion("DBC_SEQ_NM <=", value, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmLike(String value) {
            addCriterion("DBC_SEQ_NM like", value, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmNotLike(String value) {
            addCriterion("DBC_SEQ_NM not like", value, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmIn(List<String> values) {
            addCriterion("DBC_SEQ_NM in", values, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmNotIn(List<String> values) {
            addCriterion("DBC_SEQ_NM not in", values, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmBetween(String value1, String value2) {
            addCriterion("DBC_SEQ_NM between", value1, value2, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andDbcSeqNmNotBetween(String value1, String value2) {
            addCriterion("DBC_SEQ_NM not between", value1, value2, "dbcSeqNm");
            return (Criteria) this;
        }

        public Criteria andVersIsNull() {
            addCriterion("VERS is null");
            return (Criteria) this;
        }

        public Criteria andVersIsNotNull() {
            addCriterion("VERS is not null");
            return (Criteria) this;
        }

        public Criteria andVersEqualTo(Integer value) {
            addCriterion("VERS =", value, "vers");
            return (Criteria) this;
        }

        public Criteria andVersNotEqualTo(Integer value) {
            addCriterion("VERS <>", value, "vers");
            return (Criteria) this;
        }

        public Criteria andVersGreaterThan(Integer value) {
            addCriterion("VERS >", value, "vers");
            return (Criteria) this;
        }

        public Criteria andVersGreaterThanOrEqualTo(Integer value) {
            addCriterion("VERS >=", value, "vers");
            return (Criteria) this;
        }

        public Criteria andVersLessThan(Integer value) {
            addCriterion("VERS <", value, "vers");
            return (Criteria) this;
        }

        public Criteria andVersLessThanOrEqualTo(Integer value) {
            addCriterion("VERS <=", value, "vers");
            return (Criteria) this;
        }

        public Criteria andVersIn(List<Integer> values) {
            addCriterion("VERS in", values, "vers");
            return (Criteria) this;
        }

        public Criteria andVersNotIn(List<Integer> values) {
            addCriterion("VERS not in", values, "vers");
            return (Criteria) this;
        }

        public Criteria andVersBetween(Integer value1, Integer value2) {
            addCriterion("VERS between", value1, value2, "vers");
            return (Criteria) this;
        }

        public Criteria andVersNotBetween(Integer value1, Integer value2) {
            addCriterion("VERS not between", value1, value2, "vers");
            return (Criteria) this;
        }

        public Criteria andRegTypIsNull() {
            addCriterion("REG_TYP is null");
            return (Criteria) this;
        }

        public Criteria andRegTypIsNotNull() {
            addCriterion("REG_TYP is not null");
            return (Criteria) this;
        }

        public Criteria andRegTypEqualTo(String value) {
            addCriterion("REG_TYP =", value, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypNotEqualTo(String value) {
            addCriterion("REG_TYP <>", value, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypGreaterThan(String value) {
            addCriterion("REG_TYP >", value, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypGreaterThanOrEqualTo(String value) {
            addCriterion("REG_TYP >=", value, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypLessThan(String value) {
            addCriterion("REG_TYP <", value, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypLessThanOrEqualTo(String value) {
            addCriterion("REG_TYP <=", value, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypLike(String value) {
            addCriterion("REG_TYP like", value, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypNotLike(String value) {
            addCriterion("REG_TYP not like", value, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypIn(List<String> values) {
            addCriterion("REG_TYP in", values, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypNotIn(List<String> values) {
            addCriterion("REG_TYP not in", values, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypBetween(String value1, String value2) {
            addCriterion("REG_TYP between", value1, value2, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegTypNotBetween(String value1, String value2) {
            addCriterion("REG_TYP not between", value1, value2, "regTyp");
            return (Criteria) this;
        }

        public Criteria andRegDtmIsNull() {
            addCriterion("REG_DTM is null");
            return (Criteria) this;
        }

        public Criteria andRegDtmIsNotNull() {
            addCriterion("REG_DTM is not null");
            return (Criteria) this;
        }

        public Criteria andRegDtmEqualTo(Date value) {
            addCriterion("REG_DTM =", value, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmNotEqualTo(Date value) {
            addCriterion("REG_DTM <>", value, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmGreaterThan(Date value) {
            addCriterion("REG_DTM >", value, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("REG_DTM >=", value, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmLessThan(Date value) {
            addCriterion("REG_DTM <", value, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmLessThanOrEqualTo(Date value) {
            addCriterion("REG_DTM <=", value, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmIn(List<Date> values) {
            addCriterion("REG_DTM in", values, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmNotIn(List<Date> values) {
            addCriterion("REG_DTM not in", values, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmBetween(Date value1, Date value2) {
            addCriterion("REG_DTM between", value1, value2, "regDtm");
            return (Criteria) this;
        }

        public Criteria andRegDtmNotBetween(Date value1, Date value2) {
            addCriterion("REG_DTM not between", value1, value2, "regDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmIsNull() {
            addCriterion("UPD_DTM is null");
            return (Criteria) this;
        }

        public Criteria andUpdDtmIsNotNull() {
            addCriterion("UPD_DTM is not null");
            return (Criteria) this;
        }

        public Criteria andUpdDtmEqualTo(Date value) {
            addCriterion("UPD_DTM =", value, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmNotEqualTo(Date value) {
            addCriterion("UPD_DTM <>", value, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmGreaterThan(Date value) {
            addCriterion("UPD_DTM >", value, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("UPD_DTM >=", value, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmLessThan(Date value) {
            addCriterion("UPD_DTM <", value, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmLessThanOrEqualTo(Date value) {
            addCriterion("UPD_DTM <=", value, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmIn(List<Date> values) {
            addCriterion("UPD_DTM in", values, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmNotIn(List<Date> values) {
            addCriterion("UPD_DTM not in", values, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmBetween(Date value1, Date value2) {
            addCriterion("UPD_DTM between", value1, value2, "updDtm");
            return (Criteria) this;
        }

        public Criteria andUpdDtmNotBetween(Date value1, Date value2) {
            addCriterion("UPD_DTM not between", value1, value2, "updDtm");
            return (Criteria) this;
        }

        public Criteria andMinvalIsNull() {
            addCriterion("MINVAL is null");
            return (Criteria) this;
        }

        public Criteria andMinvalIsNotNull() {
            addCriterion("MINVAL is not null");
            return (Criteria) this;
        }

        public Criteria andMinvalEqualTo(BigDecimal value) {
            addCriterion("MINVAL =", value, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalNotEqualTo(BigDecimal value) {
            addCriterion("MINVAL <>", value, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalGreaterThan(BigDecimal value) {
            addCriterion("MINVAL >", value, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MINVAL >=", value, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalLessThan(BigDecimal value) {
            addCriterion("MINVAL <", value, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MINVAL <=", value, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalIn(List<BigDecimal> values) {
            addCriterion("MINVAL in", values, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalNotIn(List<BigDecimal> values) {
            addCriterion("MINVAL not in", values, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MINVAL between", value1, value2, "minval");
            return (Criteria) this;
        }

        public Criteria andMinvalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MINVAL not between", value1, value2, "minval");
            return (Criteria) this;
        }

        public Criteria andMaxvalIsNull() {
            addCriterion("MAXVAL is null");
            return (Criteria) this;
        }

        public Criteria andMaxvalIsNotNull() {
            addCriterion("MAXVAL is not null");
            return (Criteria) this;
        }

        public Criteria andMaxvalEqualTo(BigDecimal value) {
            addCriterion("MAXVAL =", value, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalNotEqualTo(BigDecimal value) {
            addCriterion("MAXVAL <>", value, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalGreaterThan(BigDecimal value) {
            addCriterion("MAXVAL >", value, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MAXVAL >=", value, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalLessThan(BigDecimal value) {
            addCriterion("MAXVAL <", value, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MAXVAL <=", value, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalIn(List<BigDecimal> values) {
            addCriterion("MAXVAL in", values, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalNotIn(List<BigDecimal> values) {
            addCriterion("MAXVAL not in", values, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MAXVAL between", value1, value2, "maxval");
            return (Criteria) this;
        }

        public Criteria andMaxvalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MAXVAL not between", value1, value2, "maxval");
            return (Criteria) this;
        }

        public Criteria andIncbyIsNull() {
            addCriterion("INCBY is null");
            return (Criteria) this;
        }

        public Criteria andIncbyIsNotNull() {
            addCriterion("INCBY is not null");
            return (Criteria) this;
        }

        public Criteria andIncbyEqualTo(BigDecimal value) {
            addCriterion("INCBY =", value, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyNotEqualTo(BigDecimal value) {
            addCriterion("INCBY <>", value, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyGreaterThan(BigDecimal value) {
            addCriterion("INCBY >", value, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("INCBY >=", value, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyLessThan(BigDecimal value) {
            addCriterion("INCBY <", value, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("INCBY <=", value, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyIn(List<BigDecimal> values) {
            addCriterion("INCBY in", values, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyNotIn(List<BigDecimal> values) {
            addCriterion("INCBY not in", values, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INCBY between", value1, value2, "incby");
            return (Criteria) this;
        }

        public Criteria andIncbyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INCBY not between", value1, value2, "incby");
            return (Criteria) this;
        }

        public Criteria andCycYnIsNull() {
            addCriterion("CYC_YN is null");
            return (Criteria) this;
        }

        public Criteria andCycYnIsNotNull() {
            addCriterion("CYC_YN is not null");
            return (Criteria) this;
        }

        public Criteria andCycYnEqualTo(String value) {
            addCriterion("CYC_YN =", value, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnNotEqualTo(String value) {
            addCriterion("CYC_YN <>", value, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnGreaterThan(String value) {
            addCriterion("CYC_YN >", value, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnGreaterThanOrEqualTo(String value) {
            addCriterion("CYC_YN >=", value, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnLessThan(String value) {
            addCriterion("CYC_YN <", value, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnLessThanOrEqualTo(String value) {
            addCriterion("CYC_YN <=", value, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnLike(String value) {
            addCriterion("CYC_YN like", value, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnNotLike(String value) {
            addCriterion("CYC_YN not like", value, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnIn(List<String> values) {
            addCriterion("CYC_YN in", values, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnNotIn(List<String> values) {
            addCriterion("CYC_YN not in", values, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnBetween(String value1, String value2) {
            addCriterion("CYC_YN between", value1, value2, "cycYn");
            return (Criteria) this;
        }

        public Criteria andCycYnNotBetween(String value1, String value2) {
            addCriterion("CYC_YN not between", value1, value2, "cycYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnIsNull() {
            addCriterion("ORD_YN is null");
            return (Criteria) this;
        }

        public Criteria andOrdYnIsNotNull() {
            addCriterion("ORD_YN is not null");
            return (Criteria) this;
        }

        public Criteria andOrdYnEqualTo(String value) {
            addCriterion("ORD_YN =", value, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnNotEqualTo(String value) {
            addCriterion("ORD_YN <>", value, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnGreaterThan(String value) {
            addCriterion("ORD_YN >", value, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnGreaterThanOrEqualTo(String value) {
            addCriterion("ORD_YN >=", value, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnLessThan(String value) {
            addCriterion("ORD_YN <", value, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnLessThanOrEqualTo(String value) {
            addCriterion("ORD_YN <=", value, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnLike(String value) {
            addCriterion("ORD_YN like", value, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnNotLike(String value) {
            addCriterion("ORD_YN not like", value, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnIn(List<String> values) {
            addCriterion("ORD_YN in", values, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnNotIn(List<String> values) {
            addCriterion("ORD_YN not in", values, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnBetween(String value1, String value2) {
            addCriterion("ORD_YN between", value1, value2, "ordYn");
            return (Criteria) this;
        }

        public Criteria andOrdYnNotBetween(String value1, String value2) {
            addCriterion("ORD_YN not between", value1, value2, "ordYn");
            return (Criteria) this;
        }

        public Criteria andCacheSzIsNull() {
            addCriterion("CACHE_SZ is null");
            return (Criteria) this;
        }

        public Criteria andCacheSzIsNotNull() {
            addCriterion("CACHE_SZ is not null");
            return (Criteria) this;
        }

        public Criteria andCacheSzEqualTo(BigDecimal value) {
            addCriterion("CACHE_SZ =", value, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzNotEqualTo(BigDecimal value) {
            addCriterion("CACHE_SZ <>", value, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzGreaterThan(BigDecimal value) {
            addCriterion("CACHE_SZ >", value, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CACHE_SZ >=", value, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzLessThan(BigDecimal value) {
            addCriterion("CACHE_SZ <", value, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CACHE_SZ <=", value, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzIn(List<BigDecimal> values) {
            addCriterion("CACHE_SZ in", values, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzNotIn(List<BigDecimal> values) {
            addCriterion("CACHE_SZ not in", values, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CACHE_SZ between", value1, value2, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andCacheSzNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CACHE_SZ not between", value1, value2, "cacheSz");
            return (Criteria) this;
        }

        public Criteria andLstNumIsNull() {
            addCriterion("LST_NUM is null");
            return (Criteria) this;
        }

        public Criteria andLstNumIsNotNull() {
            addCriterion("LST_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andLstNumEqualTo(BigDecimal value) {
            addCriterion("LST_NUM =", value, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumNotEqualTo(BigDecimal value) {
            addCriterion("LST_NUM <>", value, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumGreaterThan(BigDecimal value) {
            addCriterion("LST_NUM >", value, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LST_NUM >=", value, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumLessThan(BigDecimal value) {
            addCriterion("LST_NUM <", value, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LST_NUM <=", value, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumIn(List<BigDecimal> values) {
            addCriterion("LST_NUM in", values, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumNotIn(List<BigDecimal> values) {
            addCriterion("LST_NUM not in", values, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LST_NUM between", value1, value2, "lstNum");
            return (Criteria) this;
        }

        public Criteria andLstNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LST_NUM not between", value1, value2, "lstNum");
            return (Criteria) this;
        }

        public Criteria andDescnIsNull() {
            addCriterion("DESCN is null");
            return (Criteria) this;
        }

        public Criteria andDescnIsNotNull() {
            addCriterion("DESCN is not null");
            return (Criteria) this;
        }

        public Criteria andDescnEqualTo(String value) {
            addCriterion("DESCN =", value, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnNotEqualTo(String value) {
            addCriterion("DESCN <>", value, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnGreaterThan(String value) {
            addCriterion("DESCN >", value, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnGreaterThanOrEqualTo(String value) {
            addCriterion("DESCN >=", value, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnLessThan(String value) {
            addCriterion("DESCN <", value, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnLessThanOrEqualTo(String value) {
            addCriterion("DESCN <=", value, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnLike(String value) {
            addCriterion("DESCN like", value, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnNotLike(String value) {
            addCriterion("DESCN not like", value, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnIn(List<String> values) {
            addCriterion("DESCN in", values, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnNotIn(List<String> values) {
            addCriterion("DESCN not in", values, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnBetween(String value1, String value2) {
            addCriterion("DESCN between", value1, value2, "descn");
            return (Criteria) this;
        }

        public Criteria andDescnNotBetween(String value1, String value2) {
            addCriterion("DESCN not between", value1, value2, "descn");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdIsNull() {
            addCriterion("DB_CONN_TRG_ID is null");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdIsNotNull() {
            addCriterion("DB_CONN_TRG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdEqualTo(String value) {
            addCriterion("DB_CONN_TRG_ID =", value, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdNotEqualTo(String value) {
            addCriterion("DB_CONN_TRG_ID <>", value, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdGreaterThan(String value) {
            addCriterion("DB_CONN_TRG_ID >", value, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("DB_CONN_TRG_ID >=", value, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdLessThan(String value) {
            addCriterion("DB_CONN_TRG_ID <", value, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdLessThanOrEqualTo(String value) {
            addCriterion("DB_CONN_TRG_ID <=", value, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdLike(String value) {
            addCriterion("DB_CONN_TRG_ID like", value, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdNotLike(String value) {
            addCriterion("DB_CONN_TRG_ID not like", value, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdIn(List<String> values) {
            addCriterion("DB_CONN_TRG_ID in", values, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdNotIn(List<String> values) {
            addCriterion("DB_CONN_TRG_ID not in", values, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdBetween(String value1, String value2) {
            addCriterion("DB_CONN_TRG_ID between", value1, value2, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDbConnTrgIdNotBetween(String value1, String value2) {
            addCriterion("DB_CONN_TRG_ID not between", value1, value2, "dbConnTrgId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdIsNull() {
            addCriterion("DDL_SEQ_ID is null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdIsNotNull() {
            addCriterion("DDL_SEQ_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdEqualTo(String value) {
            addCriterion("DDL_SEQ_ID =", value, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdNotEqualTo(String value) {
            addCriterion("DDL_SEQ_ID <>", value, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdGreaterThan(String value) {
            addCriterion("DDL_SEQ_ID >", value, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdGreaterThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_ID >=", value, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdLessThan(String value) {
            addCriterion("DDL_SEQ_ID <", value, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdLessThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_ID <=", value, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdLike(String value) {
            addCriterion("DDL_SEQ_ID like", value, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdNotLike(String value) {
            addCriterion("DDL_SEQ_ID not like", value, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdIn(List<String> values) {
            addCriterion("DDL_SEQ_ID in", values, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdNotIn(List<String> values) {
            addCriterion("DDL_SEQ_ID not in", values, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_ID between", value1, value2, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDdlSeqIdNotBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_ID not between", value1, value2, "ddlSeqId");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeIsNull() {
            addCriterion("DBMS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeIsNotNull() {
            addCriterion("DBMS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeEqualTo(String value) {
            addCriterion("DBMS_TYPE =", value, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeNotEqualTo(String value) {
            addCriterion("DBMS_TYPE <>", value, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeGreaterThan(String value) {
            addCriterion("DBMS_TYPE >", value, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("DBMS_TYPE >=", value, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeLessThan(String value) {
            addCriterion("DBMS_TYPE <", value, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeLessThanOrEqualTo(String value) {
            addCriterion("DBMS_TYPE <=", value, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeLike(String value) {
            addCriterion("DBMS_TYPE like", value, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeNotLike(String value) {
            addCriterion("DBMS_TYPE not like", value, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeIn(List<String> values) {
            addCriterion("DBMS_TYPE in", values, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeNotIn(List<String> values) {
            addCriterion("DBMS_TYPE not in", values, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeBetween(String value1, String value2) {
            addCriterion("DBMS_TYPE between", value1, value2, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andDbmsTypeNotBetween(String value1, String value2) {
            addCriterion("DBMS_TYPE not between", value1, value2, "dbmsType");
            return (Criteria) this;
        }

        public Criteria andSubjIdIsNull() {
            addCriterion("SUBJ_ID is null");
            return (Criteria) this;
        }

        public Criteria andSubjIdIsNotNull() {
            addCriterion("SUBJ_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSubjIdEqualTo(String value) {
            addCriterion("SUBJ_ID =", value, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdNotEqualTo(String value) {
            addCriterion("SUBJ_ID <>", value, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdGreaterThan(String value) {
            addCriterion("SUBJ_ID >", value, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUBJ_ID >=", value, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdLessThan(String value) {
            addCriterion("SUBJ_ID <", value, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdLessThanOrEqualTo(String value) {
            addCriterion("SUBJ_ID <=", value, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdLike(String value) {
            addCriterion("SUBJ_ID like", value, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdNotLike(String value) {
            addCriterion("SUBJ_ID not like", value, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdIn(List<String> values) {
            addCriterion("SUBJ_ID in", values, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdNotIn(List<String> values) {
            addCriterion("SUBJ_ID not in", values, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdBetween(String value1, String value2) {
            addCriterion("SUBJ_ID between", value1, value2, "subjId");
            return (Criteria) this;
        }

        public Criteria andSubjIdNotBetween(String value1, String value2) {
            addCriterion("SUBJ_ID not between", value1, value2, "subjId");
            return (Criteria) this;
        }

        public Criteria andAnaDtmIsNull() {
            addCriterion("ANA_DTM is null");
            return (Criteria) this;
        }

        public Criteria andAnaDtmIsNotNull() {
            addCriterion("ANA_DTM is not null");
            return (Criteria) this;
        }

        public Criteria andAnaDtmEqualTo(Date value) {
            addCriterion("ANA_DTM =", value, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmNotEqualTo(Date value) {
            addCriterion("ANA_DTM <>", value, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmGreaterThan(Date value) {
            addCriterion("ANA_DTM >", value, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("ANA_DTM >=", value, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmLessThan(Date value) {
            addCriterion("ANA_DTM <", value, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmLessThanOrEqualTo(Date value) {
            addCriterion("ANA_DTM <=", value, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmIn(List<Date> values) {
            addCriterion("ANA_DTM in", values, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmNotIn(List<Date> values) {
            addCriterion("ANA_DTM not in", values, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmBetween(Date value1, Date value2) {
            addCriterion("ANA_DTM between", value1, value2, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andAnaDtmNotBetween(Date value1, Date value2) {
            addCriterion("ANA_DTM not between", value1, value2, "anaDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmIsNull() {
            addCriterion("CRT_DTM is null");
            return (Criteria) this;
        }

        public Criteria andCrtDtmIsNotNull() {
            addCriterion("CRT_DTM is not null");
            return (Criteria) this;
        }

        public Criteria andCrtDtmEqualTo(Date value) {
            addCriterion("CRT_DTM =", value, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmNotEqualTo(Date value) {
            addCriterion("CRT_DTM <>", value, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmGreaterThan(Date value) {
            addCriterion("CRT_DTM >", value, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("CRT_DTM >=", value, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmLessThan(Date value) {
            addCriterion("CRT_DTM <", value, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmLessThanOrEqualTo(Date value) {
            addCriterion("CRT_DTM <=", value, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmIn(List<Date> values) {
            addCriterion("CRT_DTM in", values, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmNotIn(List<Date> values) {
            addCriterion("CRT_DTM not in", values, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmBetween(Date value1, Date value2) {
            addCriterion("CRT_DTM between", value1, value2, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andCrtDtmNotBetween(Date value1, Date value2) {
            addCriterion("CRT_DTM not between", value1, value2, "crtDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmIsNull() {
            addCriterion("CHG_DTM is null");
            return (Criteria) this;
        }

        public Criteria andChgDtmIsNotNull() {
            addCriterion("CHG_DTM is not null");
            return (Criteria) this;
        }

        public Criteria andChgDtmEqualTo(Date value) {
            addCriterion("CHG_DTM =", value, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmNotEqualTo(Date value) {
            addCriterion("CHG_DTM <>", value, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmGreaterThan(Date value) {
            addCriterion("CHG_DTM >", value, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("CHG_DTM >=", value, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmLessThan(Date value) {
            addCriterion("CHG_DTM <", value, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmLessThanOrEqualTo(Date value) {
            addCriterion("CHG_DTM <=", value, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmIn(List<Date> values) {
            addCriterion("CHG_DTM in", values, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmNotIn(List<Date> values) {
            addCriterion("CHG_DTM not in", values, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmBetween(Date value1, Date value2) {
            addCriterion("CHG_DTM between", value1, value2, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andChgDtmNotBetween(Date value1, Date value2) {
            addCriterion("CHG_DTM not between", value1, value2, "chgDtm");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnIsNull() {
            addCriterion("DDL_SEQ_DESCN is null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnIsNotNull() {
            addCriterion("DDL_SEQ_DESCN is not null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnEqualTo(String value) {
            addCriterion("DDL_SEQ_DESCN =", value, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnNotEqualTo(String value) {
            addCriterion("DDL_SEQ_DESCN <>", value, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnGreaterThan(String value) {
            addCriterion("DDL_SEQ_DESCN >", value, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnGreaterThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_DESCN >=", value, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnLessThan(String value) {
            addCriterion("DDL_SEQ_DESCN <", value, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnLessThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_DESCN <=", value, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnLike(String value) {
            addCriterion("DDL_SEQ_DESCN like", value, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnNotLike(String value) {
            addCriterion("DDL_SEQ_DESCN not like", value, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnIn(List<String> values) {
            addCriterion("DDL_SEQ_DESCN in", values, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnNotIn(List<String> values) {
            addCriterion("DDL_SEQ_DESCN not in", values, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_DESCN between", value1, value2, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqDescnNotBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_DESCN not between", value1, value2, "ddlSeqDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsIsNull() {
            addCriterion("DDL_SEQ_ERR_EXS is null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsIsNotNull() {
            addCriterion("DDL_SEQ_ERR_EXS is not null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_EXS =", value, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsNotEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_EXS <>", value, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsGreaterThan(String value) {
            addCriterion("DDL_SEQ_ERR_EXS >", value, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsGreaterThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_EXS >=", value, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsLessThan(String value) {
            addCriterion("DDL_SEQ_ERR_EXS <", value, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsLessThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_EXS <=", value, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsLike(String value) {
            addCriterion("DDL_SEQ_ERR_EXS like", value, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsNotLike(String value) {
            addCriterion("DDL_SEQ_ERR_EXS not like", value, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsIn(List<String> values) {
            addCriterion("DDL_SEQ_ERR_EXS in", values, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsNotIn(List<String> values) {
            addCriterion("DDL_SEQ_ERR_EXS not in", values, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_ERR_EXS between", value1, value2, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrExsNotBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_ERR_EXS not between", value1, value2, "ddlSeqErrExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdIsNull() {
            addCriterion("DDL_SEQ_ERR_CD is null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdIsNotNull() {
            addCriterion("DDL_SEQ_ERR_CD is not null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_CD =", value, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdNotEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_CD <>", value, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdGreaterThan(String value) {
            addCriterion("DDL_SEQ_ERR_CD >", value, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdGreaterThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_CD >=", value, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdLessThan(String value) {
            addCriterion("DDL_SEQ_ERR_CD <", value, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdLessThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_CD <=", value, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdLike(String value) {
            addCriterion("DDL_SEQ_ERR_CD like", value, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdNotLike(String value) {
            addCriterion("DDL_SEQ_ERR_CD not like", value, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdIn(List<String> values) {
            addCriterion("DDL_SEQ_ERR_CD in", values, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdNotIn(List<String> values) {
            addCriterion("DDL_SEQ_ERR_CD not in", values, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_ERR_CD between", value1, value2, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrCdNotBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_ERR_CD not between", value1, value2, "ddlSeqErrCd");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnIsNull() {
            addCriterion("DDL_SEQ_ERR_DESCN is null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnIsNotNull() {
            addCriterion("DDL_SEQ_ERR_DESCN is not null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_DESCN =", value, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnNotEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_DESCN <>", value, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnGreaterThan(String value) {
            addCriterion("DDL_SEQ_ERR_DESCN >", value, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnGreaterThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_DESCN >=", value, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnLessThan(String value) {
            addCriterion("DDL_SEQ_ERR_DESCN <", value, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnLessThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_ERR_DESCN <=", value, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnLike(String value) {
            addCriterion("DDL_SEQ_ERR_DESCN like", value, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnNotLike(String value) {
            addCriterion("DDL_SEQ_ERR_DESCN not like", value, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnIn(List<String> values) {
            addCriterion("DDL_SEQ_ERR_DESCN in", values, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnNotIn(List<String> values) {
            addCriterion("DDL_SEQ_ERR_DESCN not in", values, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_ERR_DESCN between", value1, value2, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqErrDescnNotBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_ERR_DESCN not between", value1, value2, "ddlSeqErrDescn");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsIsNull() {
            addCriterion("DDL_SEQ_EXTNC_EXS is null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsIsNotNull() {
            addCriterion("DDL_SEQ_EXTNC_EXS is not null");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsEqualTo(String value) {
            addCriterion("DDL_SEQ_EXTNC_EXS =", value, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsNotEqualTo(String value) {
            addCriterion("DDL_SEQ_EXTNC_EXS <>", value, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsGreaterThan(String value) {
            addCriterion("DDL_SEQ_EXTNC_EXS >", value, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsGreaterThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_EXTNC_EXS >=", value, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsLessThan(String value) {
            addCriterion("DDL_SEQ_EXTNC_EXS <", value, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsLessThanOrEqualTo(String value) {
            addCriterion("DDL_SEQ_EXTNC_EXS <=", value, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsLike(String value) {
            addCriterion("DDL_SEQ_EXTNC_EXS like", value, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsNotLike(String value) {
            addCriterion("DDL_SEQ_EXTNC_EXS not like", value, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsIn(List<String> values) {
            addCriterion("DDL_SEQ_EXTNC_EXS in", values, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsNotIn(List<String> values) {
            addCriterion("DDL_SEQ_EXTNC_EXS not in", values, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_EXTNC_EXS between", value1, value2, "ddlSeqExtncExs");
            return (Criteria) this;
        }

        public Criteria andDdlSeqExtncExsNotBetween(String value1, String value2) {
            addCriterion("DDL_SEQ_EXTNC_EXS not between", value1, value2, "ddlSeqExtncExs");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}