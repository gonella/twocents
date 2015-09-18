package com.twocents.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@NamedQueries( {
		@NamedQuery(name = "custody.findCustodyByStockAndAccount", query = "select c from Custody c where c.stock.id = :stockId and c.account.id = :accountId"),
		@NamedQuery(name = "custody.findCustodyByAccount", query = "select c from Custody c left join c.stock as s where c.account.id = :accountId order by s.code"),
		@NamedQuery(name = "custody.findCustodyMapByAccount", query = "select new map(c.id as id, c.amount as amount, c.amountBlocked as amountBlocked, "
				+ "c.averagePrice as averagePrice, s.code as stockCode, s.id as stockId) from Custody c "
				+ "left join c.stock as s where c.account.id = :accountId "
				+ "order by s.code "),
		@NamedQuery(name = "custody.removeByAccount", query = "delete from Custody c where c.account.id = :accountId"),
		@NamedQuery(name = "custody.findByAccountBeforeDate", query = "select c from Custody c where c.stock.id = :stockId and c.account.id = :accountId and c.lastUpdate < :beforeDate"),
		@NamedQuery(name = "custody.getInvestmentMade", query = "select sum(c.amount * c.averagePrice) from Custody c where c.account.id = :accountId"),
		@NamedQuery(name = "custody.getCurrentPositionValue", query = "select sum(q.price * c.amount) from Custody c, Quote q where c.account.id = :accountId and c.stock.id = q.stock.id"),
		@NamedQuery(name = "custody.findCustodyViewByAccount", query = "select new com.twocents.model.CustodyView(c.id, s.code, c.amount, c.amountBlocked, c.averagePrice, c.lastPrice) from Custody c, Stock s where c.account.id = :accountId and c.stock.id = s.id"),
		@NamedQuery(name = "custody.updateCustodyLastPrice", query = "update Custody c set c.lastPrice = :lastPrice where c.stock.id = :stockId")

})
public class Custody extends Persistent {

	private static final long serialVersionUID = 3906753500481081222L;

	public static final String FIND_CUSTODY_BY_STOCK_ACCOUNT_AND_BEFORE_A_DATE = "custody.findByAccountBeforeDate";

	public static final String FIND_CUSTODY_VIEW_BY_STOCK_ACCOUNT = "custody.findCustodyViewByAccount";
	
	public static final String FIND_CUSTODY_BY_STOCK_ACCOUNT = "custody.findCustodyByStockAndAccount";

	public static final String FIND_CUSTODY_BY_ACCOUNT = "custody.findCustodyByAccount";

	public static final String FIND_CUSTODY_MAP_BY_ACCOUNT = "custody.findCustodyMapByAccount";;

	public static final String REMOVE_CUSTODY_BY_ACCOUNT = "custody.removeByAccount";

	public static final String INVESTMENT_MADE_BY_ACCOUNT = "custody.getInvestmentMade";

	public static final String INVESTMENT_POSITION_BY_ACCOUNT = "custody.getCurrentPositionValue";
	
	public static final String UPDATE_CUSTODY_STOCK_LAST_PRICE = "custody.updateCustodyLastPrice";

	private Integer amount;
	private Integer amountBlocked = 0;
	private Stock stock;
	private Account account;
	private Double averagePrice;
	private Timestamp lastUpdate;
	private Double lastPrice;

	// private List<CallSell> bloquerOperations = new ArrayList<CallSell>();

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Custody() {
		super();
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(nullable = false)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Column(nullable = false)
	public Double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}

	/*
	 * @OneToMany
	 * 
	 * @JoinTable( name="BloquerOperations", joinColumns = @JoinColumn(
	 * name="custody_id"), inverseJoinColumns = @JoinColumn(
	 * name="operation_id") ) public List<CallSell> getBloquerOperations() {
	 * return bloquerOperations; }
	 * 
	 * public void setBloquerOperations(List<CallSell> bloquerOperations) {
	 * this.bloquerOperations = bloquerOperations; }
	 */

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.TW_DEFAULT);
	}

	public Integer getAmountBlocked() {
		return amountBlocked;
	}

	public void setAmountBlocked(Integer amountBlocked) {
		this.amountBlocked = amountBlocked;
	}

}
