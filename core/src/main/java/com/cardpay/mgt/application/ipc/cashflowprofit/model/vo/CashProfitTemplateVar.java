package com.cardpay.mgt.application.ipc.cashflowprofit.model.vo;


import com.cardpay.mgt.application.ipc.cashflowprofit.model.TApplicationCashProfitExt;
import com.cardpay.mgt.application.ipc.normal.model.TTemplateVarOption;

import java.math.BigDecimal;
import java.util.List;

public class CashProfitTemplateVar {

    /**
     * 选项id
     */
    private Integer optionId;

    /**
     * children
     */
    private List<CashProfitTemplateVar> vars;

    /**
     * options
     */
    private List<TTemplateVarOption> options;

    /**
     * extras
     */
    private List<TApplicationCashProfitExt> extras;

    /**
     * 进件模板利润现金值id
     */
    private BigDecimal applicationCashProfitVarId;

    /**
     * 利润现金模板值id
     */
    private Integer cashProfitVarId;

    /**
     * 建议模板值value
     */
    private String cashProfitVarValue;

    /**
     * 建议选项值id
     */
    private Integer cashProfitOptionId;

    /**
     * 第一个月
     */
    private BigDecimal varMonthOne;

    /**
     * 第二个月
     */
    private BigDecimal varMonthTwo;

    /**
     * 第三个月
     */
    private BigDecimal varMonthThree;

    /**
     * 第四个月
     */
    private Long varMonthFour;

    /**
     * 第五个月
     */
    private Long varMonthFive;

    /**
     * 第六个月
     */
    private Long varMonthSix;

    /**
     * 第七个月
     */
    private BigDecimal varMonthSeven;

    /**
     * 第八个月
     */
    private BigDecimal varMonthEight;

    /**
     * 第九个月
     */
    private BigDecimal varMonthNine;

    /**
     * 第十个月
     */
    private BigDecimal varMonthTen;

    /**
     * 第十一个月
     */
    private BigDecimal varMonthEleven;

    /**
     * 第十二个月
     */
    private BigDecimal varMonthTwelve;

    /**
     * 总计
     */
    private BigDecimal varCrossValidateAll;

    /**
     * 月平均
     */
    private BigDecimal varCrossValidateMonthAvg;

    /**
     * 进件id
     */
    private Integer applicationId;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public List<CashProfitTemplateVar> getVars() {
        return vars;
    }

    public void setVars(List<CashProfitTemplateVar> vars) {
        this.vars = vars;
    }

    public List<TTemplateVarOption> getOptions() {
        return options;
    }

    public void setOptions(List<TTemplateVarOption> options) {
        this.options = options;
    }

    public List<TApplicationCashProfitExt> getExtras() {
        return extras;
    }

    public void setExtras(List<TApplicationCashProfitExt> extras) {
        this.extras = extras;
    }

    /**
     * 获取进件模板利润现金值id
     *
     * @return APPLICATION_CASH_PROFIT_VAR_ID - 进件模板利润现金值id
     */
    public BigDecimal getApplicationCashProfitVarId() {
        return applicationCashProfitVarId;
    }

    /**
     * 设置进件模板利润现金值id
     *
     * @param applicationCashProfitVarId 进件模板利润现金值id
     */
    public void setApplicationCashProfitVarId(BigDecimal applicationCashProfitVarId) {
        this.applicationCashProfitVarId = applicationCashProfitVarId;
    }

    /**
     * 获取利润现金模板值id
     *
     * @return CASH_PROFIT_VAR_ID - 利润现金模板值id
     */
    public Integer getCashProfitVarId() {
        return cashProfitVarId;
    }

    /**
     * 设置利润现金模板值id
     *
     * @param cashProfitVarId 利润现金模板值id
     */
    public void setCashProfitVarId(Integer cashProfitVarId) {
        this.cashProfitVarId = cashProfitVarId;
    }

    /**
     * 获取建议模板值value
     *
     * @return CASH_PROFIT_VAR_VALUE - 建议模板值value
     */
    public String getCashProfitVarValue() {
        return cashProfitVarValue;
    }

    /**
     * 设置建议模板值value
     *
     * @param cashProfitVarValue 建议模板值value
     */
    public void setCashProfitVarValue(String cashProfitVarValue) {
        this.cashProfitVarValue = cashProfitVarValue;
    }

    /**
     * 获取建议选项值id
     *
     * @return CASH_PROFIT_OPTION_ID - 建议选项值id
     */
    public Integer getCashProfitOptionId() {
        return cashProfitOptionId;
    }

    /**
     * 设置建议选项值id
     *
     * @param cashProfitOptionId 建议选项值id
     */
    public void setCashProfitOptionId(Integer cashProfitOptionId) {
        this.cashProfitOptionId = cashProfitOptionId;
    }

    /**
     * 获取第一个月
     *
     * @return VAR_MONTH_ONE - 第一个月
     */
    public BigDecimal getVarMonthOne() {
        return varMonthOne;
    }

    /**
     * 设置第一个月
     *
     * @param varMonthOne 第一个月
     */
    public void setVarMonthOne(BigDecimal varMonthOne) {
        this.varMonthOne = varMonthOne;
    }

    /**
     * 获取第二个月
     *
     * @return VAR_MONTH_TWO - 第二个月
     */
    public BigDecimal getVarMonthTwo() {
        return varMonthTwo;
    }

    /**
     * 设置第二个月
     *
     * @param varMonthTwo 第二个月
     */
    public void setVarMonthTwo(BigDecimal varMonthTwo) {
        this.varMonthTwo = varMonthTwo;
    }

    /**
     * 获取第三个月
     *
     * @return VAR_MONTH_THREE - 第三个月
     */
    public BigDecimal getVarMonthThree() {
        return varMonthThree;
    }

    /**
     * 设置第三个月
     *
     * @param varMonthThree 第三个月
     */
    public void setVarMonthThree(BigDecimal varMonthThree) {
        this.varMonthThree = varMonthThree;
    }

    /**
     * 获取第四个月
     *
     * @return VAR_MONTH_FOUR - 第四个月
     */
    public Long getVarMonthFour() {
        return varMonthFour;
    }

    /**
     * 设置第四个月
     *
     * @param varMonthFour 第四个月
     */
    public void setVarMonthFour(Long varMonthFour) {
        this.varMonthFour = varMonthFour;
    }

    /**
     * 获取第五个月
     *
     * @return VAR_MONTH_FIVE - 第五个月
     */
    public Long getVarMonthFive() {
        return varMonthFive;
    }

    /**
     * 设置第五个月
     *
     * @param varMonthFive 第五个月
     */
    public void setVarMonthFive(Long varMonthFive) {
        this.varMonthFive = varMonthFive;
    }

    /**
     * 获取第六个月
     *
     * @return VAR_MONTH_SIX - 第六个月
     */
    public Long getVarMonthSix() {
        return varMonthSix;
    }

    /**
     * 设置第六个月
     *
     * @param varMonthSix 第六个月
     */
    public void setVarMonthSix(Long varMonthSix) {
        this.varMonthSix = varMonthSix;
    }

    /**
     * 获取第七个月
     *
     * @return VAR_MONTH_SEVEN - 第七个月
     */
    public BigDecimal getVarMonthSeven() {
        return varMonthSeven;
    }

    /**
     * 设置第七个月
     *
     * @param varMonthSeven 第七个月
     */
    public void setVarMonthSeven(BigDecimal varMonthSeven) {
        this.varMonthSeven = varMonthSeven;
    }

    /**
     * 获取第八个月
     *
     * @return VAR_MONTH_EIGHT - 第八个月
     */
    public BigDecimal getVarMonthEight() {
        return varMonthEight;
    }

    /**
     * 设置第八个月
     *
     * @param varMonthEight 第八个月
     */
    public void setVarMonthEight(BigDecimal varMonthEight) {
        this.varMonthEight = varMonthEight;
    }

    /**
     * 获取第九个月
     *
     * @return VAR_MONTH_NINE - 第九个月
     */
    public BigDecimal getVarMonthNine() {
        return varMonthNine;
    }

    /**
     * 设置第九个月
     *
     * @param varMonthNine 第九个月
     */
    public void setVarMonthNine(BigDecimal varMonthNine) {
        this.varMonthNine = varMonthNine;
    }

    /**
     * 获取第十个月
     *
     * @return VAR_MONTH_TEN - 第十个月
     */
    public BigDecimal getVarMonthTen() {
        return varMonthTen;
    }

    /**
     * 设置第十个月
     *
     * @param varMonthTen 第十个月
     */
    public void setVarMonthTen(BigDecimal varMonthTen) {
        this.varMonthTen = varMonthTen;
    }

    /**
     * 获取第十一个月
     *
     * @return VAR_MONTH_ELEVEN - 第十一个月
     */
    public BigDecimal getVarMonthEleven() {
        return varMonthEleven;
    }

    /**
     * 设置第十一个月
     *
     * @param varMonthEleven 第十一个月
     */
    public void setVarMonthEleven(BigDecimal varMonthEleven) {
        this.varMonthEleven = varMonthEleven;
    }

    /**
     * 获取第十二个月
     *
     * @return VAR_MONTH_TWELVE - 第十二个月
     */
    public BigDecimal getVarMonthTwelve() {
        return varMonthTwelve;
    }

    /**
     * 设置第十二个月
     *
     * @param varMonthTwelve 第十二个月
     */
    public void setVarMonthTwelve(BigDecimal varMonthTwelve) {
        this.varMonthTwelve = varMonthTwelve;
    }

    /**
     * 获取总计
     *
     * @return VAR_CROSS_VALIDATE_ALL - 总计
     */
    public BigDecimal getVarCrossValidateAll() {
        return varCrossValidateAll;
    }

    /**
     * 设置总计
     *
     * @param varCrossValidateAll 总计
     */
    public void setVarCrossValidateAll(BigDecimal varCrossValidateAll) {
        this.varCrossValidateAll = varCrossValidateAll;
    }

    /**
     * 获取月平均
     *
     * @return VAR_CROSS_VALIDATE_MONTH_AVG - 月平均
     */
    public BigDecimal getVarCrossValidateMonthAvg() {
        return varCrossValidateMonthAvg;
    }

    /**
     * 设置月平均
     *
     * @param varCrossValidateMonthAvg 月平均
     */
    public void setVarCrossValidateMonthAvg(BigDecimal varCrossValidateMonthAvg) {
        this.varCrossValidateMonthAvg = varCrossValidateMonthAvg;
    }

    /**
     * 获取进件id
     *
     * @return APPLICATION_ID - 进件id
     */
    public Integer getApplicationId() {
        return applicationId;
    }

    /**
     * 设置进件id
     *
     * @param applicationId 进件id
     */
    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

}