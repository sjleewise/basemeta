package kr.wise.meta.symn.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WamSymnItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WamSymnItemExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andSymnItemIdIsNull() {
            addCriterion("SYMN_ITEM_ID is null");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdIsNotNull() {
            addCriterion("SYMN_ITEM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdEqualTo(String value) {
            addCriterion("SYMN_ITEM_ID =", value, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdNotEqualTo(String value) {
            addCriterion("SYMN_ITEM_ID <>", value, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdGreaterThan(String value) {
            addCriterion("SYMN_ITEM_ID >", value, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdGreaterThanOrEqualTo(String value) {
            addCriterion("SYMN_ITEM_ID >=", value, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdLessThan(String value) {
            addCriterion("SYMN_ITEM_ID <", value, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdLessThanOrEqualTo(String value) {
            addCriterion("SYMN_ITEM_ID <=", value, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdLike(String value) {
            addCriterion("SYMN_ITEM_ID like", value, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdNotLike(String value) {
            addCriterion("SYMN_ITEM_ID not like", value, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdIn(List<String> values) {
            addCriterion("SYMN_ITEM_ID in", values, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdNotIn(List<String> values) {
            addCriterion("SYMN_ITEM_ID not in", values, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdBetween(String value1, String value2) {
            addCriterion("SYMN_ITEM_ID between", value1, value2, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemIdNotBetween(String value1, String value2) {
            addCriterion("SYMN_ITEM_ID not between", value1, value2, "symnItemId");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmIsNull() {
            addCriterion("SYMN_ITEM_LNM is null");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmIsNotNull() {
            addCriterion("SYMN_ITEM_LNM is not null");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmEqualTo(String value) {
            addCriterion("SYMN_ITEM_LNM =", value, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmNotEqualTo(String value) {
            addCriterion("SYMN_ITEM_LNM <>", value, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmGreaterThan(String value) {
            addCriterion("SYMN_ITEM_LNM >", value, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmGreaterThanOrEqualTo(String value) {
            addCriterion("SYMN_ITEM_LNM >=", value, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmLessThan(String value) {
            addCriterion("SYMN_ITEM_LNM <", value, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmLessThanOrEqualTo(String value) {
            addCriterion("SYMN_ITEM_LNM <=", value, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmLike(String value) {
            addCriterion("SYMN_ITEM_LNM like", value, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmNotLike(String value) {
            addCriterion("SYMN_ITEM_LNM not like", value, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmIn(List<String> values) {
            addCriterion("SYMN_ITEM_LNM in", values, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmNotIn(List<String> values) {
            addCriterion("SYMN_ITEM_LNM not in", values, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmBetween(String value1, String value2) {
            addCriterion("SYMN_ITEM_LNM between", value1, value2, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemLnmNotBetween(String value1, String value2) {
            addCriterion("SYMN_ITEM_LNM not between", value1, value2, "symnItemLnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmIsNull() {
            addCriterion("SYMN_ITEM_PNM is null");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmIsNotNull() {
            addCriterion("SYMN_ITEM_PNM is not null");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmEqualTo(String value) {
            addCriterion("SYMN_ITEM_PNM =", value, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmNotEqualTo(String value) {
            addCriterion("SYMN_ITEM_PNM <>", value, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmGreaterThan(String value) {
            addCriterion("SYMN_ITEM_PNM >", value, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmGreaterThanOrEqualTo(String value) {
            addCriterion("SYMN_ITEM_PNM >=", value, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmLessThan(String value) {
            addCriterion("SYMN_ITEM_PNM <", value, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmLessThanOrEqualTo(String value) {
            addCriterion("SYMN_ITEM_PNM <=", value, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmLike(String value) {
            addCriterion("SYMN_ITEM_PNM like", value, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmNotLike(String value) {
            addCriterion("SYMN_ITEM_PNM not like", value, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmIn(List<String> values) {
            addCriterion("SYMN_ITEM_PNM in", values, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmNotIn(List<String> values) {
            addCriterion("SYMN_ITEM_PNM not in", values, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmBetween(String value1, String value2) {
            addCriterion("SYMN_ITEM_PNM between", value1, value2, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemPnmNotBetween(String value1, String value2) {
            addCriterion("SYMN_ITEM_PNM not between", value1, value2, "symnItemPnm");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeIsNull() {
            addCriterion("SYMN_ITEM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeIsNotNull() {
            addCriterion("SYMN_ITEM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeEqualTo(String value) {
            addCriterion("SYMN_ITEM_TYPE =", value, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeNotEqualTo(String value) {
            addCriterion("SYMN_ITEM_TYPE <>", value, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeGreaterThan(String value) {
            addCriterion("SYMN_ITEM_TYPE >", value, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SYMN_ITEM_TYPE >=", value, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeLessThan(String value) {
            addCriterion("SYMN_ITEM_TYPE <", value, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeLessThanOrEqualTo(String value) {
            addCriterion("SYMN_ITEM_TYPE <=", value, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeLike(String value) {
            addCriterion("SYMN_ITEM_TYPE like", value, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeNotLike(String value) {
            addCriterion("SYMN_ITEM_TYPE not like", value, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeIn(List<String> values) {
            addCriterion("SYMN_ITEM_TYPE in", values, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeNotIn(List<String> values) {
            addCriterion("SYMN_ITEM_TYPE not in", values, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeBetween(String value1, String value2) {
            addCriterion("SYMN_ITEM_TYPE between", value1, value2, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSymnItemTypeNotBetween(String value1, String value2) {
            addCriterion("SYMN_ITEM_TYPE not between", value1, value2, "symnItemType");
            return (Criteria) this;
        }

        public Criteria andSditmIdIsNull() {
            addCriterion("SDITM_ID is null");
            return (Criteria) this;
        }

        public Criteria andSditmIdIsNotNull() {
            addCriterion("SDITM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSditmIdEqualTo(String value) {
            addCriterion("SDITM_ID =", value, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdNotEqualTo(String value) {
            addCriterion("SDITM_ID <>", value, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdGreaterThan(String value) {
            addCriterion("SDITM_ID >", value, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdGreaterThanOrEqualTo(String value) {
            addCriterion("SDITM_ID >=", value, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdLessThan(String value) {
            addCriterion("SDITM_ID <", value, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdLessThanOrEqualTo(String value) {
            addCriterion("SDITM_ID <=", value, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdLike(String value) {
            addCriterion("SDITM_ID like", value, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdNotLike(String value) {
            addCriterion("SDITM_ID not like", value, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdIn(List<String> values) {
            addCriterion("SDITM_ID in", values, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdNotIn(List<String> values) {
            addCriterion("SDITM_ID not in", values, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdBetween(String value1, String value2) {
            addCriterion("SDITM_ID between", value1, value2, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmIdNotBetween(String value1, String value2) {
            addCriterion("SDITM_ID not between", value1, value2, "sditmId");
            return (Criteria) this;
        }

        public Criteria andSditmLnmIsNull() {
            addCriterion("SDITM_LNM is null");
            return (Criteria) this;
        }

        public Criteria andSditmLnmIsNotNull() {
            addCriterion("SDITM_LNM is not null");
            return (Criteria) this;
        }

        public Criteria andSditmLnmEqualTo(String value) {
            addCriterion("SDITM_LNM =", value, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmNotEqualTo(String value) {
            addCriterion("SDITM_LNM <>", value, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmGreaterThan(String value) {
            addCriterion("SDITM_LNM >", value, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmGreaterThanOrEqualTo(String value) {
            addCriterion("SDITM_LNM >=", value, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmLessThan(String value) {
            addCriterion("SDITM_LNM <", value, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmLessThanOrEqualTo(String value) {
            addCriterion("SDITM_LNM <=", value, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmLike(String value) {
            addCriterion("SDITM_LNM like", value, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmNotLike(String value) {
            addCriterion("SDITM_LNM not like", value, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmIn(List<String> values) {
            addCriterion("SDITM_LNM in", values, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmNotIn(List<String> values) {
            addCriterion("SDITM_LNM not in", values, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmBetween(String value1, String value2) {
            addCriterion("SDITM_LNM between", value1, value2, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmLnmNotBetween(String value1, String value2) {
            addCriterion("SDITM_LNM not between", value1, value2, "sditmLnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmIsNull() {
            addCriterion("SDITM_PNM is null");
            return (Criteria) this;
        }

        public Criteria andSditmPnmIsNotNull() {
            addCriterion("SDITM_PNM is not null");
            return (Criteria) this;
        }

        public Criteria andSditmPnmEqualTo(String value) {
            addCriterion("SDITM_PNM =", value, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmNotEqualTo(String value) {
            addCriterion("SDITM_PNM <>", value, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmGreaterThan(String value) {
            addCriterion("SDITM_PNM >", value, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmGreaterThanOrEqualTo(String value) {
            addCriterion("SDITM_PNM >=", value, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmLessThan(String value) {
            addCriterion("SDITM_PNM <", value, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmLessThanOrEqualTo(String value) {
            addCriterion("SDITM_PNM <=", value, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmLike(String value) {
            addCriterion("SDITM_PNM like", value, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmNotLike(String value) {
            addCriterion("SDITM_PNM not like", value, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmIn(List<String> values) {
            addCriterion("SDITM_PNM in", values, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmNotIn(List<String> values) {
            addCriterion("SDITM_PNM not in", values, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmBetween(String value1, String value2) {
            addCriterion("SDITM_PNM between", value1, value2, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andSditmPnmNotBetween(String value1, String value2) {
            addCriterion("SDITM_PNM not between", value1, value2, "sditmPnm");
            return (Criteria) this;
        }

        public Criteria andRqstNoIsNull() {
            addCriterion("RQST_NO is null");
            return (Criteria) this;
        }

        public Criteria andRqstNoIsNotNull() {
            addCriterion("RQST_NO is not null");
            return (Criteria) this;
        }

        public Criteria andRqstNoEqualTo(String value) {
            addCriterion("RQST_NO =", value, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoNotEqualTo(String value) {
            addCriterion("RQST_NO <>", value, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoGreaterThan(String value) {
            addCriterion("RQST_NO >", value, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoGreaterThanOrEqualTo(String value) {
            addCriterion("RQST_NO >=", value, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoLessThan(String value) {
            addCriterion("RQST_NO <", value, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoLessThanOrEqualTo(String value) {
            addCriterion("RQST_NO <=", value, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoLike(String value) {
            addCriterion("RQST_NO like", value, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoNotLike(String value) {
            addCriterion("RQST_NO not like", value, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoIn(List<String> values) {
            addCriterion("RQST_NO in", values, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoNotIn(List<String> values) {
            addCriterion("RQST_NO not in", values, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoBetween(String value1, String value2) {
            addCriterion("RQST_NO between", value1, value2, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstNoNotBetween(String value1, String value2) {
            addCriterion("RQST_NO not between", value1, value2, "rqstNo");
            return (Criteria) this;
        }

        public Criteria andRqstSnoIsNull() {
            addCriterion("RQST_SNO is null");
            return (Criteria) this;
        }

        public Criteria andRqstSnoIsNotNull() {
            addCriterion("RQST_SNO is not null");
            return (Criteria) this;
        }

        public Criteria andRqstSnoEqualTo(Integer value) {
            addCriterion("RQST_SNO =", value, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoNotEqualTo(Integer value) {
            addCriterion("RQST_SNO <>", value, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoGreaterThan(Integer value) {
            addCriterion("RQST_SNO >", value, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("RQST_SNO >=", value, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoLessThan(Integer value) {
            addCriterion("RQST_SNO <", value, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoLessThanOrEqualTo(Integer value) {
            addCriterion("RQST_SNO <=", value, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoIn(List<Integer> values) {
            addCriterion("RQST_SNO in", values, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoNotIn(List<Integer> values) {
            addCriterion("RQST_SNO not in", values, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoBetween(Integer value1, Integer value2) {
            addCriterion("RQST_SNO between", value1, value2, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andRqstSnoNotBetween(Integer value1, Integer value2) {
            addCriterion("RQST_SNO not between", value1, value2, "rqstSno");
            return (Criteria) this;
        }

        public Criteria andObjDescnIsNull() {
            addCriterion("OBJ_DESCN is null");
            return (Criteria) this;
        }

        public Criteria andObjDescnIsNotNull() {
            addCriterion("OBJ_DESCN is not null");
            return (Criteria) this;
        }

        public Criteria andObjDescnEqualTo(String value) {
            addCriterion("OBJ_DESCN =", value, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnNotEqualTo(String value) {
            addCriterion("OBJ_DESCN <>", value, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnGreaterThan(String value) {
            addCriterion("OBJ_DESCN >", value, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnGreaterThanOrEqualTo(String value) {
            addCriterion("OBJ_DESCN >=", value, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnLessThan(String value) {
            addCriterion("OBJ_DESCN <", value, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnLessThanOrEqualTo(String value) {
            addCriterion("OBJ_DESCN <=", value, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnLike(String value) {
            addCriterion("OBJ_DESCN like", value, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnNotLike(String value) {
            addCriterion("OBJ_DESCN not like", value, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnIn(List<String> values) {
            addCriterion("OBJ_DESCN in", values, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnNotIn(List<String> values) {
            addCriterion("OBJ_DESCN not in", values, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnBetween(String value1, String value2) {
            addCriterion("OBJ_DESCN between", value1, value2, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjDescnNotBetween(String value1, String value2) {
            addCriterion("OBJ_DESCN not between", value1, value2, "objDescn");
            return (Criteria) this;
        }

        public Criteria andObjVersIsNull() {
            addCriterion("OBJ_VERS is null");
            return (Criteria) this;
        }

        public Criteria andObjVersIsNotNull() {
            addCriterion("OBJ_VERS is not null");
            return (Criteria) this;
        }

        public Criteria andObjVersEqualTo(Integer value) {
            addCriterion("OBJ_VERS =", value, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersNotEqualTo(Integer value) {
            addCriterion("OBJ_VERS <>", value, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersGreaterThan(Integer value) {
            addCriterion("OBJ_VERS >", value, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersGreaterThanOrEqualTo(Integer value) {
            addCriterion("OBJ_VERS >=", value, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersLessThan(Integer value) {
            addCriterion("OBJ_VERS <", value, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersLessThanOrEqualTo(Integer value) {
            addCriterion("OBJ_VERS <=", value, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersIn(List<Integer> values) {
            addCriterion("OBJ_VERS in", values, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersNotIn(List<Integer> values) {
            addCriterion("OBJ_VERS not in", values, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersBetween(Integer value1, Integer value2) {
            addCriterion("OBJ_VERS between", value1, value2, "objVers");
            return (Criteria) this;
        }

        public Criteria andObjVersNotBetween(Integer value1, Integer value2) {
            addCriterion("OBJ_VERS not between", value1, value2, "objVers");
            return (Criteria) this;
        }

        public Criteria andRegTypCdIsNull() {
            addCriterion("REG_TYP_CD is null");
            return (Criteria) this;
        }

        public Criteria andRegTypCdIsNotNull() {
            addCriterion("REG_TYP_CD is not null");
            return (Criteria) this;
        }

        public Criteria andRegTypCdEqualTo(String value) {
            addCriterion("REG_TYP_CD =", value, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdNotEqualTo(String value) {
            addCriterion("REG_TYP_CD <>", value, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdGreaterThan(String value) {
            addCriterion("REG_TYP_CD >", value, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdGreaterThanOrEqualTo(String value) {
            addCriterion("REG_TYP_CD >=", value, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdLessThan(String value) {
            addCriterion("REG_TYP_CD <", value, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdLessThanOrEqualTo(String value) {
            addCriterion("REG_TYP_CD <=", value, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdLike(String value) {
            addCriterion("REG_TYP_CD like", value, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdNotLike(String value) {
            addCriterion("REG_TYP_CD not like", value, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdIn(List<String> values) {
            addCriterion("REG_TYP_CD in", values, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdNotIn(List<String> values) {
            addCriterion("REG_TYP_CD not in", values, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdBetween(String value1, String value2) {
            addCriterion("REG_TYP_CD between", value1, value2, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andRegTypCdNotBetween(String value1, String value2) {
            addCriterion("REG_TYP_CD not between", value1, value2, "regTypCd");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmIsNull() {
            addCriterion("FRS_RQST_DTM is null");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmIsNotNull() {
            addCriterion("FRS_RQST_DTM is not null");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmEqualTo(Date value) {
            addCriterion("FRS_RQST_DTM =", value, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmNotEqualTo(Date value) {
            addCriterion("FRS_RQST_DTM <>", value, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmGreaterThan(Date value) {
            addCriterion("FRS_RQST_DTM >", value, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("FRS_RQST_DTM >=", value, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmLessThan(Date value) {
            addCriterion("FRS_RQST_DTM <", value, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmLessThanOrEqualTo(Date value) {
            addCriterion("FRS_RQST_DTM <=", value, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmIn(List<Date> values) {
            addCriterion("FRS_RQST_DTM in", values, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmNotIn(List<Date> values) {
            addCriterion("FRS_RQST_DTM not in", values, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmBetween(Date value1, Date value2) {
            addCriterion("FRS_RQST_DTM between", value1, value2, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstDtmNotBetween(Date value1, Date value2) {
            addCriterion("FRS_RQST_DTM not between", value1, value2, "frsRqstDtm");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdIsNull() {
            addCriterion("FRS_RQST_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdIsNotNull() {
            addCriterion("FRS_RQST_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdEqualTo(String value) {
            addCriterion("FRS_RQST_USER_ID =", value, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdNotEqualTo(String value) {
            addCriterion("FRS_RQST_USER_ID <>", value, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdGreaterThan(String value) {
            addCriterion("FRS_RQST_USER_ID >", value, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("FRS_RQST_USER_ID >=", value, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdLessThan(String value) {
            addCriterion("FRS_RQST_USER_ID <", value, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdLessThanOrEqualTo(String value) {
            addCriterion("FRS_RQST_USER_ID <=", value, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdLike(String value) {
            addCriterion("FRS_RQST_USER_ID like", value, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdNotLike(String value) {
            addCriterion("FRS_RQST_USER_ID not like", value, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdIn(List<String> values) {
            addCriterion("FRS_RQST_USER_ID in", values, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdNotIn(List<String> values) {
            addCriterion("FRS_RQST_USER_ID not in", values, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdBetween(String value1, String value2) {
            addCriterion("FRS_RQST_USER_ID between", value1, value2, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andFrsRqstUserIdNotBetween(String value1, String value2) {
            addCriterion("FRS_RQST_USER_ID not between", value1, value2, "frsRqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstDtmIsNull() {
            addCriterion("RQST_DTM is null");
            return (Criteria) this;
        }

        public Criteria andRqstDtmIsNotNull() {
            addCriterion("RQST_DTM is not null");
            return (Criteria) this;
        }

        public Criteria andRqstDtmEqualTo(Date value) {
            addCriterion("RQST_DTM =", value, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmNotEqualTo(Date value) {
            addCriterion("RQST_DTM <>", value, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmGreaterThan(Date value) {
            addCriterion("RQST_DTM >", value, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("RQST_DTM >=", value, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmLessThan(Date value) {
            addCriterion("RQST_DTM <", value, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmLessThanOrEqualTo(Date value) {
            addCriterion("RQST_DTM <=", value, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmIn(List<Date> values) {
            addCriterion("RQST_DTM in", values, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmNotIn(List<Date> values) {
            addCriterion("RQST_DTM not in", values, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmBetween(Date value1, Date value2) {
            addCriterion("RQST_DTM between", value1, value2, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstDtmNotBetween(Date value1, Date value2) {
            addCriterion("RQST_DTM not between", value1, value2, "rqstDtm");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdIsNull() {
            addCriterion("RQST_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdIsNotNull() {
            addCriterion("RQST_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdEqualTo(String value) {
            addCriterion("RQST_USER_ID =", value, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdNotEqualTo(String value) {
            addCriterion("RQST_USER_ID <>", value, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdGreaterThan(String value) {
            addCriterion("RQST_USER_ID >", value, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("RQST_USER_ID >=", value, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdLessThan(String value) {
            addCriterion("RQST_USER_ID <", value, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdLessThanOrEqualTo(String value) {
            addCriterion("RQST_USER_ID <=", value, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdLike(String value) {
            addCriterion("RQST_USER_ID like", value, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdNotLike(String value) {
            addCriterion("RQST_USER_ID not like", value, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdIn(List<String> values) {
            addCriterion("RQST_USER_ID in", values, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdNotIn(List<String> values) {
            addCriterion("RQST_USER_ID not in", values, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdBetween(String value1, String value2) {
            addCriterion("RQST_USER_ID between", value1, value2, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andRqstUserIdNotBetween(String value1, String value2) {
            addCriterion("RQST_USER_ID not between", value1, value2, "rqstUserId");
            return (Criteria) this;
        }

        public Criteria andAprvDtmIsNull() {
            addCriterion("APRV_DTM is null");
            return (Criteria) this;
        }

        public Criteria andAprvDtmIsNotNull() {
            addCriterion("APRV_DTM is not null");
            return (Criteria) this;
        }

        public Criteria andAprvDtmEqualTo(Date value) {
            addCriterion("APRV_DTM =", value, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmNotEqualTo(Date value) {
            addCriterion("APRV_DTM <>", value, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmGreaterThan(Date value) {
            addCriterion("APRV_DTM >", value, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("APRV_DTM >=", value, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmLessThan(Date value) {
            addCriterion("APRV_DTM <", value, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmLessThanOrEqualTo(Date value) {
            addCriterion("APRV_DTM <=", value, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmIn(List<Date> values) {
            addCriterion("APRV_DTM in", values, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmNotIn(List<Date> values) {
            addCriterion("APRV_DTM not in", values, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmBetween(Date value1, Date value2) {
            addCriterion("APRV_DTM between", value1, value2, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvDtmNotBetween(Date value1, Date value2) {
            addCriterion("APRV_DTM not between", value1, value2, "aprvDtm");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdIsNull() {
            addCriterion("APRV_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdIsNotNull() {
            addCriterion("APRV_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdEqualTo(String value) {
            addCriterion("APRV_USER_ID =", value, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdNotEqualTo(String value) {
            addCriterion("APRV_USER_ID <>", value, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdGreaterThan(String value) {
            addCriterion("APRV_USER_ID >", value, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("APRV_USER_ID >=", value, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdLessThan(String value) {
            addCriterion("APRV_USER_ID <", value, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdLessThanOrEqualTo(String value) {
            addCriterion("APRV_USER_ID <=", value, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdLike(String value) {
            addCriterion("APRV_USER_ID like", value, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdNotLike(String value) {
            addCriterion("APRV_USER_ID not like", value, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdIn(List<String> values) {
            addCriterion("APRV_USER_ID in", values, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdNotIn(List<String> values) {
            addCriterion("APRV_USER_ID not in", values, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdBetween(String value1, String value2) {
            addCriterion("APRV_USER_ID between", value1, value2, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andAprvUserIdNotBetween(String value1, String value2) {
            addCriterion("APRV_USER_ID not between", value1, value2, "aprvUserId");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsIsNull() {
            addCriterion("PNM_CRI_DS is null");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsIsNotNull() {
            addCriterion("PNM_CRI_DS is not null");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsEqualTo(String value) {
            addCriterion("PNM_CRI_DS =", value, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsNotEqualTo(String value) {
            addCriterion("PNM_CRI_DS <>", value, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsGreaterThan(String value) {
            addCriterion("PNM_CRI_DS >", value, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsGreaterThanOrEqualTo(String value) {
            addCriterion("PNM_CRI_DS >=", value, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsLessThan(String value) {
            addCriterion("PNM_CRI_DS <", value, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsLessThanOrEqualTo(String value) {
            addCriterion("PNM_CRI_DS <=", value, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsLike(String value) {
            addCriterion("PNM_CRI_DS like", value, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsNotLike(String value) {
            addCriterion("PNM_CRI_DS not like", value, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsIn(List<String> values) {
            addCriterion("PNM_CRI_DS in", values, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsNotIn(List<String> values) {
            addCriterion("PNM_CRI_DS not in", values, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsBetween(String value1, String value2) {
            addCriterion("PNM_CRI_DS between", value1, value2, "pnmCriDs");
            return (Criteria) this;
        }

        public Criteria andPnmCriDsNotBetween(String value1, String value2) {
            addCriterion("PNM_CRI_DS not between", value1, value2, "pnmCriDs");
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